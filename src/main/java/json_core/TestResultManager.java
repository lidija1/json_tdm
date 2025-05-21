package json_core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.logging.Level;

public class TestResultManager {
    private static final Logger logger = Logger.getLogger(TestResultManager.class.getName());
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final String baseResultPath;
    private final Map<String, ObjectNode> resultCache;

    public TestResultManager(String baseResultPath) {
        this.baseResultPath = baseResultPath;
        this.resultCache = new ConcurrentHashMap<>();
    }

    public void recordTestResult(String lobName, String testType, TestResult result) {
        try {
            String resultFilePath = getResultFilePath(lobName, testType);
            ObjectNode resultNode = getOrCreateResultFile(resultFilePath);
            
            // Add test result
            ObjectNode testNode = resultNode.putObject(result.getTestCaseId());
            testNode.put("status", result.getStatus().toString());
            testNode.put("duration", result.getDuration());
            testNode.put("timestamp", getCurrentTimestamp());
            
            // Add errors if any
            if (!result.getErrors().isEmpty()) {
                ArrayNode errorsNode = testNode.putArray("errors");
                result.getErrors().forEach(errorsNode::add);
            }

            // Update summary statistics
            updateSummaryStats(resultNode, result.getStatus());
            
            // Write to file
            writeResultFile(resultFilePath, resultNode);
            
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to record test result", e);
        }
    }

    private ObjectNode getOrCreateResultFile(String filePath) throws IOException {
        return resultCache.computeIfAbsent(filePath, path -> {
            try {
                File file = new File(path);
                if (file.exists()) {
                    return (ObjectNode) objectMapper.readTree(file);
                } else {
                    ObjectNode root = objectMapper.createObjectNode();
                    root.putObject("summary");
                    root.putObject("results");
                    return root;
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to read/create result file: " + path, e);
            }
        });
    }

    private void updateSummaryStats(ObjectNode rootNode, TestStatus status) {
        ObjectNode summary = (ObjectNode) rootNode.get("summary");
        summary.put("lastUpdated", getCurrentTimestamp());
        
        // Update counts
        String statusKey = status.toString().toLowerCase() + "Count";
        int currentCount = summary.has(statusKey) ? summary.get(statusKey).asInt() : 0;
        summary.put(statusKey, currentCount + 1);
        
        // Update total
        int total = summary.has("totalTests") ? summary.get("totalTests").asInt() : 0;
        summary.put("totalTests", total + 1);
    }

    private void writeResultFile(String filePath, ObjectNode content) throws IOException {
        // Create directories if they don't exist
        new File(filePath).getParentFile().mkdirs();
        
        // Write the file with pretty printing
        objectMapper.writerWithDefaultPrettyPrinter()
                   .writeValue(new File(filePath), content);
    }

    private String getResultFilePath(String lobName, String testType) {
        return String.format("%s/%s/%s/results.json", 
                           baseResultPath, 
                           sanitizeFileName(lobName), 
                           sanitizeFileName(testType));
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    private String sanitizeFileName(String name) {
        return name.toLowerCase().replaceAll("[^a-z0-9-]", "-");
    }

    public void clearCache() {
        resultCache.clear();
    }

    public void clearCache(String lobName, String testType) {
        resultCache.remove(getResultFilePath(lobName, testType));
    }
}
