package json_core;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import models.TestResult;

public class TestResultManager {
    private final String resultBasePath;
    private final ObjectMapper mapper;
    private final Map<String, Map<String, TestResult>> results;

    public TestResultManager(String resultBasePath) {
        this.resultBasePath = resultBasePath;
        this.mapper = new ObjectMapper();
        this.results = new ConcurrentHashMap<>();
    }

    public void recordTestResult(String lobName, String testType, TestResult result) {
        String testCaseId = result.getTestCaseId();
        results.computeIfAbsent(lobName, k -> new ConcurrentHashMap<>())
               .put(testCaseId, result);
        
        try {
            File resultFile = new File(resultBasePath, 
                String.format("%s/%s/%s.json", lobName, testType, testCaseId));
            resultFile.getParentFile().mkdirs();
            mapper.writeValue(resultFile, result);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write test result", e);
        }
    }

    public Map<String, TestResult> getResults(String lobName) {
        return new ConcurrentHashMap<>(results.getOrDefault(lobName, new ConcurrentHashMap<>()));
    }
}
