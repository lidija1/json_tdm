package json_core;

import models.LobConfig;
import models.TestCase;
import models.TestTypeConfig;
import models.TestResult;
import models.TestStatus;
import java.util.concurrent.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.Level;

public class TestExecutionManager {
    private static final Logger logger = Logger.getLogger(TestExecutionManager.class.getName());
    private final Map<String, ExecutorService> executors = new ConcurrentHashMap<>();
    private final Map<String, LobConfig> lobConfigs = new ConcurrentHashMap<>();
    private final Map<String, Map<String, Future<TestResult>>> runningTests = new ConcurrentHashMap<>();
    
    public TestExecutionManager() {
        // Create thread pools for parallel execution based on test types
        lobConfigs.forEach((lobName, config) -> {
            executors.put(lobName, 
                new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>()));
            runningTests.put(lobName, new ConcurrentHashMap<>());
            config.getTestTypes().forEach((typeName, typeConfig) -> {
                if (typeConfig.isParallel()) {
                    executors.put(
                        getExecutorKey(lobName, typeName),
                        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
                    );
                }
            });
        });
    }

    public void registerLob(LobConfig config) {
        lobConfigs.put(config.getLobName(), config);
        executors.put(config.getLobName(), 
            new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>()));
        runningTests.put(config.getLobName(), new ConcurrentHashMap<>());
    }

    public Future<TestResult> executeTest(String lobName, String testType, TestCase testCase) {
        LobConfig config = lobConfigs.get(lobName);
        if (config == null) {
            throw new IllegalArgumentException("LOB not registered: " + lobName);
        }

        TestTypeConfig typeConfig = config.getTestTypes().get(testType);
        if (typeConfig == null) {
            throw new IllegalArgumentException("Test type not configured: " + testType);
        }

        ExecutorService executor = executors.get(lobName);
        return executor.submit(() -> {
            TestResult result = new TestResult(testCase.getTestCaseId());
            try {
                // Execute test logic here
                result.setStatus(TestStatus.COMPLETED);
            } catch (Exception e) {
                result.setStatus(TestStatus.FAILED);
                result.addError(e.getMessage());
            }
            return result;
        });
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
        executors.values().forEach(ExecutorService::shutdown);
    }
}
