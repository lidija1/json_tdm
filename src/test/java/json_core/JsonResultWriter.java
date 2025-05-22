package json_core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import config.EnhancedLogger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonResultWriter {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void writeResult(String testDataPath, String tcId, String fieldName, String value) throws IOException {
        // Create results directory if it doesn't exist
        String resultsDir = "test-results";
        if (!Files.exists(Paths.get(resultsDir))) {
            Files.createDirectories(Paths.get(resultsDir));
        }

        // Create results file path
        String resultsPath = resultsDir + "/results.json";
        File resultsFile = new File(resultsPath);

        // Create or load existing results
        ObjectNode results;
        if (resultsFile.exists()) {
            results = (ObjectNode) mapper.readTree(resultsFile);
        } else {
            results = mapper.createObjectNode();
        }

        // Create or update test case results
        ObjectNode testCase;
        if (results.has(tcId)) {
            testCase = (ObjectNode) results.get(tcId);
        } else {
            testCase = mapper.createObjectNode();
            results.set(tcId, testCase);
        }

        // Add the result
        testCase.put(fieldName, value);

        // Write back to file
        mapper.writerWithDefaultPrettyPrinter().writeValue(resultsFile, results);
        EnhancedLogger.info("Written " + fieldName + " to results.json for " + tcId);
    }
} 