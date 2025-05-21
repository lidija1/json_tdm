package json_core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import models.LobConfig;
import models.TestCase;
import models.TestTypeConfig;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.logging.Level;

public class TestOrchestrator {
    private static final Logger logger = LogManager.getLogger(TestOrchestrator.class);
    private static final int CORE_COUNT = Runtime.getRuntime().availableProcessors();
    private final ExecutorService executorService;
    private final AtomicInteger activeTests = new AtomicInteger(0);
    private final ConcurrentHashMap<String, TestStatus> testStatuses = new ConcurrentHashMap<>();
    private final TestExecutionManager executionManager;
    private final TestResultManager resultManager;
    private final Map<String, Set<String>> completedTests;
    private final Map<String, CompletableFuture<TestResult>> runningTests;

    public static class TestStatus {
        public final String name;
        public final long startTime;
        public String status;
        public Throwable error;

        public TestStatus(String name) {
            this.name = name;
            this.startTime = System.currentTimeMillis();
            this.status = "RUNNING";
        }
    }

    public TestOrchestrator(String resultBasePath) {
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger threadNumber = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "TestWorker-" + threadNumber.getAndIncrement());
                thread.setDaemon(true);
                return thread;
            }
        };

        this.executorService = new ThreadPoolExecutor(
                CORE_COUNT / 2,
                CORE_COUNT,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                threadFactory,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
        this.executionManager = new TestExecutionManager();
        this.resultManager = new TestResultManager(resultBasePath);
        this.completedTests = new ConcurrentHashMap<>();
        this.runningTests = new ConcurrentHashMap<>();
    }

    public void registerLob(LobConfig config) {
        executionManager.registerLob(config);
        completedTests.put(config.getLobName(), ConcurrentHashMap.newKeySet());
    }

    public <T> CompletableFuture<T> executeTest(String testName, Supplier<T> test) {
        logger.info("Starting test: {}", testName);
        TestStatus status = new TestStatus(testName);
        testStatuses.put(testName, status);
        
        return CompletableFuture.supplyAsync(() -> {
            activeTests.incrementAndGet();
            long startTime = System.currentTimeMillis();
            try {
                T result = RetryManager.retry(() -> test.get(), testName);
                status.status = "COMPLETED";
                return result;
            } catch (Exception e) {
                status.status = "FAILED";
                status.error = e;
                logger.error("Test {} failed: {}", testName, e.getMessage());
                throw e;
            } finally {
                activeTests.decrementAndGet();
                long duration = System.currentTimeMillis() - startTime;
                PerformanceMonitor.recordOperation("test-" + testName, duration);
                logger.info("Test {} completed with status {} in {}ms", testName, status.status, duration);
            }
        }, executorService);
    }

    public void executeTests(List<Runnable> tests) {
        try {
            CompletableFuture<?>[] futures = tests.stream()
                    .map(test -> CompletableFuture.runAsync(() -> {
                        activeTests.incrementAndGet();
                        try {
                            test.run();
                        } catch (Exception e) {
                            logger.error("Test execution failed", e);
                            throw e;
                        } finally {
                            activeTests.decrementAndGet();
                        }
                    }, executorService))
                    .toArray(CompletableFuture[]::new);

            CompletableFuture.allOf(futures).join();
        } catch (Exception e) {
            logger.error("Error in test execution", e);
            throw e;
        }
    }

    public CompletableFuture<List<TestResult>> executeTests(Collection<TestCase> testCases) {
        // Sort test cases by priority and dependencies
        List<TestCase> sortedTests = sortTestCases(testCases);
        
        // Create a map to store all test futures
        Map<String, CompletableFuture<TestResult>> testFutures = new HashMap<>();
        
        // Execute tests in sorted order
        for (TestCase testCase : sortedTests) {
            CompletableFuture<TestResult> future = CompletableFuture
                .supplyAsync(() -> {
                    // Wait for dependencies to complete
                    if (testCase.getDependsOn() != null) {
                        waitForDependencies(testCase.getDependsOn(), testFutures);
                    }
                    
                    try {
                        Future<TestResult> resultFuture = executionManager.executeTest(
                            testCase.getLobName(),
                            testCase.getTestType(),
                            testCase
                        );
                        
                        TestResult result = resultFuture.get();
                        resultManager.recordTestResult(
                            testCase.getLobName(),
                            testCase.getTestType(),
                            result
                        );
                        
                        completedTests.get(testCase.getLobName()).add(testCase.getTestCaseId());
                        return result;
                        
                    } catch (Exception e) {
                        logger.log(Level.SEVERE, "Test execution failed: " + testCase.getTestCaseId(), e);
                        TestResult errorResult = new TestResult(testCase.getTestCaseId());
                        errorResult.setStatus(TestStatus.ERROR);
                        errorResult.addError(e.getMessage());
                        return errorResult;
                    }
                });
                
            testFutures.put(testCase.getTestCaseId(), future);
            runningTests.put(testCase.getTestCaseId(), future);
        }
        
        // Return a future that completes when all tests are done
        return CompletableFuture.allOf(testFutures.values().toArray(new CompletableFuture[0]))
            .thenApply(v -> testFutures.values().stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));
    }

    private List<TestCase> sortTestCases(Collection<TestCase> testCases) {
        // Create a graph for topological sort
        Map<String, Set<String>> graph = new HashMap<>();
        Map<String, TestCase> testCaseMap = new HashMap<>();
        
        for (TestCase test : testCases) {
            String id = test.getTestCaseId();
            testCaseMap.put(id, test);
            graph.put(id, new HashSet<>());
            
            if (test.getDependsOn() != null) {
                Collections.addAll(graph.get(id), test.getDependsOn());
            }
        }
        
        // Perform topological sort
        List<String> sortedIds = topologicalSort(graph);
        
        // Sort by priority within same dependency level
        return sortedIds.stream()
            .map(testCaseMap::get)
            .sorted(Comparator.comparingInt(TestCase::getPriority))
            .collect(Collectors.toList());
    }

    private List<String> topologicalSort(Map<String, Set<String>> graph) {
        Set<String> visited = new HashSet<>();
        Set<String> temp = new HashSet<>();
        List<String> order = new ArrayList<>();
        
        for (String node : graph.keySet()) {
            if (!visited.contains(node)) {
                topologicalSortUtil(node, visited, temp, order, graph);
            }
        }
        
        Collections.reverse(order);
        return order;
    }

    private void topologicalSortUtil(String node, Set<String> visited, Set<String> temp, 
                                   List<String> order, Map<String, Set<String>> graph) {
        if (temp.contains(node)) {
            throw new IllegalStateException("Circular dependency detected: " + node);
        }
        if (!visited.contains(node)) {
            temp.add(node);
            for (String dependency : graph.get(node)) {
                topologicalSortUtil(dependency, visited, temp, order, graph);
            }
            temp.remove(node);
            visited.add(node);
            order.add(node);
        }
    }

    private void waitForDependencies(String[] dependencies, 
                                   Map<String, CompletableFuture<TestResult>> futures) {
        CompletableFuture.allOf(
            Arrays.stream(dependencies)
                .map(futures::get)
                .toArray(CompletableFuture[]::new)
        ).join();
    }

    public boolean isTestComplete(String testCaseId) {
        return !runningTests.containsKey(testCaseId) || 
               runningTests.get(testCaseId).isDone();
    }

    public Set<String> getCompletedTests(String lobName) {
        return new HashSet<>(completedTests.getOrDefault(lobName, Collections.emptySet()));
    }

    public int getActiveTestCount() {
        return activeTests.get();
    }

    public ConcurrentHashMap<String, TestStatus> getTestStatuses() {
        return testStatuses;
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
