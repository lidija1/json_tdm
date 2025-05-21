package json_core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import models.TestCase;
import models.TestDataContainer;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class JsonDataReader {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Map<String, TestDataContainer> dataCache = new ConcurrentHashMap<>();
    private static final Map<String, Set<ValidationMessage>> validationCache = new ConcurrentHashMap<>();
    private static JsonSchema schema;
    private static final String SCHEMA_PATH = "src/test_data/schema.json";

    static {
        try {
            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
            JsonNode schemaNode = objectMapper.readTree(new File(SCHEMA_PATH));
            schema = factory.getSchema(schemaNode);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load JSON schema from " + SCHEMA_PATH, e);
        }
    }

    /**
     * Gets a test case by its ID from a JSON file
     * @param fileName The JSON file containing test data
     * @param testCaseId The ID of the test case to retrieve
     * @return The TestCase object
     * @throws TestDataException if the file cannot be read or validation fails
     */
    public TestCase getTestCase(String fileName, String testCaseId) {
        try {
            validateTestDataFile(fileName);
            TestDataContainer container = getTestDataContainer(fileName);
            return Optional.ofNullable(container.getTestData().get(testCaseId))
                    .orElseThrow(() -> new TestDataException("Test case " + testCaseId + " not found in " + fileName));
        } catch (IOException e) {
            throw new TestDataException("Failed to read test data", e);
        }
    }

    /**
     * Validates a test data file against the JSON schema
     * @param fileName The JSON file to validate
     * @throws TestDataException if validation fails
     */
    public void validateTestDataFile(String fileName) {
        try {
            if (!validationCache.containsKey(fileName)) {
                JsonNode jsonNode = objectMapper.readTree(new File(fileName));
                Set<ValidationMessage> validationResult = schema.validate(jsonNode);
                
                if (!validationResult.isEmpty()) {
                    String errors = validationResult.stream()
                            .map(ValidationMessage::getMessage)
                            .collect(Collectors.joining("\n"));
                    throw new TestDataException("Test data validation failed:\n" + errors);
                }
                
                validationCache.put(fileName, validationResult);
            }
        } catch (IOException e) {
            throw new TestDataException("Failed to validate test data file: " + fileName, e);
        }
    }

    /**
     * Gets the test data container from cache or loads it from file
     * @param fileName The JSON file to load
     * @return The TestDataContainer object
     * @throws IOException if the file cannot be read
     */
    private TestDataContainer getTestDataContainer(String fileName) throws IOException {
        return dataCache.computeIfAbsent(fileName, k -> {
            try {
                return objectMapper.readTree(new File(k)).traverse(objectMapper)
                        .readValueAs(TestDataContainer.class);
            } catch (IOException e) {
                throw new TestDataException("Failed to read test data file: " + k, e);
            }
        });
    }

    /**
     * Clears both data and validation caches
     */
    public void clearCache() {
        dataCache.clear();
        validationCache.clear();
    }

    /**
     * Clears cache for a specific file
     * @param fileName The file to clear from cache
     */
    public void clearCache(String fileName) {
        dataCache.remove(fileName);
        validationCache.remove(fileName);
    }
}

/**
 * Custom exception for test data related errors
 */
class TestDataException extends RuntimeException {
    public TestDataException(String message) {
        super(message);
    }

    public TestDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
