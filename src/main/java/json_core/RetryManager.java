package json_core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class RetryManager {
    private static final Logger logger = LogManager.getLogger(RetryManager.class);
    private static final int DEFAULT_MAX_RETRIES = 3;
    private static final long DEFAULT_WAIT_TIME_MS = 1000;

    public static <T> T retry(Supplier<T> action, String operationName) {
        return retry(action, operationName, DEFAULT_MAX_RETRIES, DEFAULT_WAIT_TIME_MS);
    }

    public static <T> T retry(Supplier<T> action, String operationName, int maxRetries, long waitTimeMs) {
        int attempts = 0;
        while (true) {
            try {
                attempts++;
                return action.get();
            } catch (Exception e) {
                if (attempts >= maxRetries) {
                    logger.error("Operation '{}' failed after {} attempts", operationName, attempts, e);
                    throw new TestExecutionException("Failed to execute " + operationName, e);
                }
                logger.warn("Attempt {} for operation '{}' failed, retrying...", attempts, operationName);
                try {
                    Thread.sleep(waitTimeMs * attempts);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new TestExecutionException("Operation interrupted: " + operationName, ie);
                }
            }
        }
    }
}

class TestExecutionException extends RuntimeException {
    public TestExecutionException(String message) {
        super(message);
    }

    public TestExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
