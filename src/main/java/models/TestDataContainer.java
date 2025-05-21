package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import java.util.*;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TestDataContainer {
    
    @JsonProperty("testData")
    @NotEmpty(message = "Test data map cannot be empty")
    @Valid // Cascades validation to TestCase objects
    private Map<String, TestCase> testData;

    // Constructor
    public TestDataContainer() {
        this.testData = new LinkedHashMap<>(); // Maintains insertion order
    }

    // Standard getter/setter with defensive copying
    public Map<String, TestCase> getTestData() {
        return Collections.unmodifiableMap(testData);
    }

    public void setTestData(Map<String, TestCase> testData) {
        this.testData = new LinkedHashMap<>(testData);
    }

    // Utility methods
    /**
     * Gets a test case by ID with validation
     * @param testCaseId The ID of the test case to retrieve
     * @return The TestCase object
     * @throws IllegalArgumentException if test case not found
     */
    public TestCase getTestCase(String testCaseId) {
        TestCase testCase = testData.get(testCaseId);
        if (testCase == null) {
            throw new IllegalArgumentException("Test case not found: " + testCaseId);
        }
        return testCase;
    }

    /**
     * Adds a new test case with validation
     * @param testCaseId The ID for the new test case
     * @param testCase The test case to add
     * @throws IllegalArgumentException if ID already exists
     */
    public void addTestCase(String testCaseId, TestCase testCase) {
        if (testData.containsKey(testCaseId)) {
            throw new IllegalArgumentException("Test case ID already exists: " + testCaseId);
        }
        testData.put(testCaseId, testCase);
    }

    /**
     * Updates an existing test case
     * @param testCaseId The ID of the test case to update
     * @param testCase The new test case data
     * @throws IllegalArgumentException if test case not found
     */
    public void updateTestCase(String testCaseId, TestCase testCase) {
        if (!testData.containsKey(testCaseId)) {
            throw new IllegalArgumentException("Test case not found: " + testCaseId);
        }
        testData.put(testCaseId, testCase);
    }

    /**
     * Removes a test case
     * @param testCaseId The ID of the test case to remove
     * @return true if removed, false if not found
     */
    public boolean removeTestCase(String testCaseId) {
        return testData.remove(testCaseId) != null;
    }

    /**
     * Gets all test cases with a specific policy type
     * @param policyType The policy type to filter by
     * @return Map of matching test cases
     */
    public Map<String, TestCase> getTestCasesByPolicyType(String policyType) {
        return testData.entrySet().stream()
                .filter(e -> policyType.equals(e.getValue().getPolicyType()))
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
                ));
    }

    /**
     * Checks if all test cases are valid
     * @return true if all test cases are valid
     */
    public boolean isValid() {
        return testData.values().stream()
                .allMatch(this::validateTestCase);
    }

    private boolean validateTestCase(TestCase testCase) {
        // Add custom validation logic here
        return testCase != null &&
               testCase.getPolicyType() != null &&
               testCase.getCustomerType() != null;
    }

    @Override
    public String toString() {
        return String.format("TestDataContainer{testCases=%d}", testData.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestDataContainer that = (TestDataContainer) o;
        return Objects.equals(testData, that.testData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testData);
    }
}
