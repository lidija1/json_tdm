package config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnhancedLogger {
    private static final Logger logger = LogManager.getLogger(EnhancedLogger.class);
    private static final ThreadLocal<String> testId = new ThreadLocal<>();

    public static void setTestId(String id) {
        testId.set(id);
    }

    public static String getTestId() {
        return testId.get();
    }

    public static void clearTestId() {
        testId.remove();
    }

    public static void info(String message) {
        String currentTestId = testId.get();
        if (currentTestId != null) {
            logger.info("[Test: {}] {}", currentTestId, message);
        } else {
            logger.info(message);
        }
    }

    public static void error(String message) {
        String currentTestId = testId.get();
        if (currentTestId != null) {
            logger.error("[Test: {}] {}", currentTestId, message);
        } else {
            logger.error(message);
        }
    }

    public static void warn(String message) {
        String currentTestId = testId.get();
        if (currentTestId != null) {
            logger.warn("[Test: {}] {}", currentTestId, message);
        } else {
            logger.warn(message);
        }
    }

    public static void debug(String message) {
        String currentTestId = testId.get();
        if (currentTestId != null) {
            logger.debug("[Test: {}] {}", currentTestId, message);
        } else {
            logger.debug(message);
        }
    }
} 