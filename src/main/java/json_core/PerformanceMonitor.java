package json_core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PerformanceMonitor {
    private static final Logger logger = LogManager.getLogger(PerformanceMonitor.class);
    private static final Map<String, AtomicLong> operationTimes = new ConcurrentHashMap<>();
    private static final Map<String, AtomicLong> operationCounts = new ConcurrentHashMap<>();

    public static void recordOperation(String operationType, long durationMs) {
        operationTimes.computeIfAbsent(operationType, k -> new AtomicLong(0)).addAndGet(durationMs);
        operationCounts.computeIfAbsent(operationType, k -> new AtomicLong(0)).incrementAndGet();
    }

    public static void logStats() {
        logger.info("Performance Statistics:");
        operationTimes.forEach((operation, totalTime) -> {
            long count = operationCounts.get(operation).get();
            double avgTime = count > 0 ? (double) totalTime.get() / count : 0;
            logger.info("{}: Count={}, Total Time={}ms, Avg Time={}ms",
                    operation, count, totalTime.get(), String.format("%.2f", avgTime));
        });
    }

    public static void reset() {
        operationTimes.clear();
        operationCounts.clear();
    }

    public static Map<String, Double> getAverageOperationTimes() {
        Map<String, Double> averages = new ConcurrentHashMap<>();
        operationTimes.forEach((operation, totalTime) -> {
            long count = operationCounts.get(operation).get();
            double avgTime = count > 0 ? (double) totalTime.get() / count : 0;
            averages.put(operation, avgTime);
        });
        return averages;
    }
}
