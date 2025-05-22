package json_core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import models.TestData;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonDataLoader {
    private static final ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static final Map<String, Map<String, TestData>> cache = new HashMap<>();

    public static TestData loadTestData(String jsonPath, String tcId) {
        try {
            // Check cache first
            if (cache.containsKey(jsonPath)) {
                Map<String, TestData> testDataMap = cache.get(jsonPath);
                if (testDataMap.containsKey(tcId)) {
                    return testDataMap.get(tcId);
                }
            }

            // Load and parse JSON file
            File jsonFile = new File(jsonPath);
            Map<String, Map<String, TestData>> root = mapper.readValue(jsonFile, 
                new com.fasterxml.jackson.core.type.TypeReference<Map<String, Map<String, TestData>>>() {});
            
            // Get the testData map
            Map<String, TestData> testDataMap = root.get("testData");
            
            // Cache the results
            cache.put(jsonPath, testDataMap);
            
            // Return the requested test case data
            return testDataMap.get(tcId);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load test data from " + jsonPath + " for TC " + tcId, e);
        }
    }
} 