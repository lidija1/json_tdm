package json_core;

import models.LobConfig;
import models.TestCase;
import models.TestTypeConfig;
import java.util.concurrent.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

public class TestExecutionManager {
    private static final Logger logger = Logger.getLogger(TestExecutionManager.class.getName());
    private final Map<String, ExecutorService> executors;
    private final Map<String, LobConfig> lobConfigs;
    
    public TestExecutionManager() {
        this.executors = new ConcurrentHashMap<>();
        this.lobConfigs = new ConcurrentHashMap<>();
    }

    public void registerLob(LobConfig config) {
        lobConfigs.put(config.getLobName(), config);
        // Create thread pools for parallel execution based on test types
        config.getTestTypes().forEach((typeName, typeConfig) -> {
            if (typeConfig.isParallel()) {
                executors.put(
                    getExecutorKey(config.getLobName(), typeName),
                    Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
                );
            }
        });
    }

    public Future<TestResult> executeTest(String lobName, String testType, TestCase testCase) {
        LobConfig lobConfig = lobConfigs.get(lobName);
        if (lobConfig == null) {
            throw new IllegalArgumentException("Unknown LOB: " + lobName);
        }

        TestTypeConfig typeConfig = lobConfig.getTestTypes().get(testType);
        if (typeConfig == null) {
            throw new IllegalArgumentException("Unknown test type: " + testType);
        }

        String executorKey = getExecutorKey(lobName, testType);
        if (typeConfig.isParallel() && executors.containsKey(executorKey)) {
            return executors.get(executorKey).submit(() -> runTest(testCase, typeConfig));
        } else {
            // Run synchronously for non-parallel tests
            TestResult result = runTest(testCase, typeConfig);
            return CompletableFuture.completedFuture(result);
        }
    }

    private TestResult runTest(TestCase testCase, TestTypeConfig config) {
        TestResult result = new TestResult(testCase.getTestCaseId());
        
        for (int attempt = 0; attempt <= config.getRetries(); attempt++) {
            try {
                // Set up test timeout
                ExecutorService timeoutExecutor = Executors.newSingleThreadExecutor();
                Future<Boolean> testExecution = timeoutExecutor.submit(() -> {
                    // Execute the test case
                    return executeTestCase(testCase);
                });

                try {
                    Boolean success = testExecution.get(config.getTimeoutSeconds(), TimeUnit.SECONDS);
                    if (success) {
                        result.setStatus(TestStatus.PASSED);
                        break;
                    }
                } catch (TimeoutException e) {
                    result.setStatus(TestStatus.TIMEOUT);
                    result.addError("Test execution timed out after " + config.getTimeoutSeconds() + " seconds");
                } catch (Exception e) {
                    result.setStatus(TestStatus.FAILED);
                    result.addError("Test execution failed: " + e.getMessage());
                } finally {
                    timeoutExecutor.shutdownNow();
                }

                if (result.getStatus() != TestStatus.FAILED || attempt == config.getRetries()) {
                    break;
                }
                
                // Wait before retry
                Thread.sleep(1000 * (attempt + 1));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error executing test case: " + testCase.getTestCaseId(), e);
                result.setStatus(TestStatus.ERROR);
                result.addError("Unexpected error: " + e.getMessage());
                break;
            }
        }

        return result;
    }

    private boolean executeTestCase(TestCase testCase) {
        // Implement actual test execution logic here
        // This would integrate with your existing test runner
        return true;
    }

    private String getExecutorKey(String lobName, String testType) {
        return lobName + "-" + testType;
    }

    public void shutdown() {
        executors.values().forEach(ExecutorService::shutdownNow);
    }
}

class TestResult {
    private final String testCaseId;
    private TestStatus status;
    private final List<String> errors;
    private final long startTime;
    private long endTime;

    public TestResult(String testCaseId) {
        this.testCaseId = testCaseId;
        this.errors = new ArrayList<>();
        this.startTime = System.currentTimeMillis();
    }

    public void setStatus(TestStatus status) {
        this.status = status;
        this.endTime = System.currentTimeMillis();
    }

    public void addError(String error) {
        this.errors.add(error);
    }

    public TestStatus getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    public long getDuration() {
        return endTime - startTime;
    }
}

enum TestStatus {
    PASSED,
    FAILED,
    ERROR,
    TIMEOUT
}
