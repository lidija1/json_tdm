package json_core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JsonUtils {
    private static final Logger logger = LogManager.getLogger(JsonUtils.class);
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);
    private static final Map<String, ObjectNode> resultCache = new ConcurrentHashMap<>();
    private static final Map<String, ReentrantLock> fileLocks = new ConcurrentHashMap<>();
    private static final String RESULTS_NODE = "results";
    private static final String METADATA_NODE = "metadata";
    
    /**
     * Writes a test result to a JSON file with proper locking and backup
     * @param filePath The path to the JSON results file
     * @param testCaseId The ID of the test case
     * @param fieldName The name of the field to write
     * @param value The value to write
     * @throws TestDataException if writing fails
     */
    public static void writeTestResult(String filePath, String testCaseId, String fieldName, String value) {
        long startTime = System.currentTimeMillis();
        ReentrantLock lock = fileLocks.computeIfAbsent(filePath, k -> new ReentrantLock());
        
        try {
            RetryManager.retry(() -> {
                lock.lock();
                try {
                    backupFileIfExists(filePath);
                    ObjectNode rootNode = getOrCreateResultNode(filePath);
                    updateMetadata(rootNode);
                    
                    ObjectNode resultsNode = (ObjectNode) rootNode.get(RESULTS_NODE);
                    ObjectNode testCaseNode = resultsNode.has(testCaseId) ? 
                        (ObjectNode) resultsNode.get(testCaseId) : 
                        resultsNode.putObject(testCaseId);
                    
                    testCaseNode.put(fieldName, value);
                    testCaseNode.put("lastUpdated", getCurrentTimestamp());
                    
                    writeToFile(filePath, rootNode);
                    TestDataCache.cacheTestData(filePath + ":" + testCaseId, testCaseNode);
                    return null;
                } finally {
                    lock.unlock();
                }
            }, "writeTestResult", 3, 1000);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            PerformanceMonitor.recordOperation("writeTestResult", duration);
        }
    }

    /**
     * Creates a backup of the results file before modification
     * @param filePath The path to the file to backup
     */
    private static void backupFileIfExists(String filePath) {
        long startTime = System.currentTimeMillis();
        try {
            File file = new File(filePath);
            if (file.exists()) {
                Path source = file.toPath();
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                Path backup = Paths.get(filePath + "." + timestamp + ".bak");
                Files.copy(source, backup);
                logger.debug("Created backup file: {}", backup);
            }
        } catch (IOException e) {
            logger.warn("Failed to create backup for {}: {}", filePath, e.getMessage());
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            PerformanceMonitor.recordOperation("backupFile", duration);
        }
    }

    /**
     * Gets or creates the root node for results
     * @param filePath The path to the JSON file
     * @return The root ObjectNode
     */
    private static ObjectNode getOrCreateResultNode(String filePath) {
        long startTime = System.currentTimeMillis();
        try {
            return resultCache.computeIfAbsent(filePath, k -> {
                return RetryManager.retry(() -> {
                    try {
                        File file = new File(k);
                        if (file.exists()) {
                            return (ObjectNode) objectMapper.readTree(file);
                        } else {
                            ObjectNode node = objectMapper.createObjectNode();
                            node.putObject(RESULTS_NODE);
                            node.putObject(METADATA_NODE);
                            return node;
                        }
                    } catch (IOException e) {
                        throw new TestDataException("Failed to read/create results file: " + k, e);
                    }
                }, "getOrCreateResultNode", 3, 1000);
            });
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            PerformanceMonitor.recordOperation("getOrCreateResultNode", duration);
        }
    }

    /**
     * Updates metadata in the results file
     * @param rootNode The root node to update
     */
    private static void updateMetadata(ObjectNode rootNode) {
        ObjectNode metadata = (ObjectNode) rootNode.get(METADATA_NODE);
        metadata.put("lastModified", getCurrentTimestamp());
        metadata.put("totalTestCases", rootNode.get(RESULTS_NODE).size());
    }

    /**
     * Writes the node to file with proper error handling
     * @param filePath The path to write to 
     * @param node The node to write
     */
    private static void writeToFile(String filePath, ObjectNode node) {
        long startTime = System.currentTimeMillis();
        try {
            RetryManager.retry(() -> {
                try {
                    objectMapper.writeValue(new File(filePath), node);
                    return null;
                } catch (IOException e) {
                    throw new TestDataException("Failed to write results to file: " + filePath, e);
                }
            }, "writeToFile", 3, 1000);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            PerformanceMonitor.recordOperation("writeToFile", duration);
        }
    }

    /**
     * Gets the current timestamp in ISO format
     * @return The formatted timestamp
     */
    private static String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    /**
     * Clears all caches and locks
     */
    public static void clearCache() {
        resultCache.clear();
        fileLocks.clear();
        TestDataCache.invalidateCache();
        logger.info("All caches cleared");
    }

    /**
     * Clears cache and lock for a specific file
     * @param filePath The file to clear from cache
     */
    public static void clearCache(String filePath) {
        resultCache.remove(filePath);
        fileLocks.remove(filePath);
        TestDataCache.invalidateCache();
        logger.info("Cache cleared for file: {}", filePath);
    }

    /**
     * Gets cache statistics
     * @return A string with cache statistics
     */
    public static String getCacheStats() {
        return String.format("ResultCache size: %d, Performance stats: %s", 
            resultCache.size(), 
            PerformanceMonitor.getAverageOperationTimes());
    }
}
