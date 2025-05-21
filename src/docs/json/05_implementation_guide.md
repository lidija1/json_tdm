# Implementation Guide

## Setting Up New Test Data

### 1. Create Test Cases
```java
// Create a new test case using builder
TestCase testCase = TestCase.builder()
    .policyType("Home")
    .customerType("Individual")
    .firstName("John")
    .lastName("Doe")
    .dob("01/15/1980")
    .phoneNum("921-555-1234")
    .email("john.doe@example.com")
    .address("123 Main St")
    .zip("02766")
    .producer("Agent Name")
    .effectiveDate("01/30/2024")
    .program("Homeowner")
    .policyCoverageOption("Gold")
    .residenceType("Homeowner")
    .replacementCost("800,000")
    .contents("480,000")
    .lossOfUse("160,000")
    .allPerilsDeductible("1,000")
    .windsHailDeductible("10%")
    .liability("100,000")
    .medicalPayments("1,000")
    .yearBuilt("2015")
    .roofType("Other")
    .constructionType("Frame")
    .build();

// Add to container
TestDataContainer container = new TestDataContainer();
container.addTestCase("TC_001", testCase);
```

### 2. Write Test Data to JSON
```java
// Write test results
JsonUtils.writeTestResult(
    "test-results.json",
    "TC_001",
    "status",
    "PASSED"
);
```

### 3. Read Test Data
```java
// Read test case
TestCase testCase = JsonDataReader.getTestData(
    "src/test_data/HomeData.json", 
    "TC_ID_0001"
);
```

## Running Tests

### 1. Test Setup
```java
@Before
public void setUp() {
    testData = JsonDataReader.getTestData(
        "src/test_data/HomeData.json", 
        "TC_ID_0001"
    );
}
```

### 2. Test Execution
```java
@Test
public void testHomePolicy() {
    customer.createNewCustomer(testData, 10);
    // ... test steps
}
```

### 3. Result Recording
```java
@After
public void recordResults() {
    JsonUtils.writeTestResult(
        "test-results.json",
        testData.getTestCaseId(),
        "result",
        testResult
    );
}
```

## Error Handling

### 1. Data Validation
```java
try {
    JsonDataReader.validateTestDataFile("src/test_data/HomeData.json");
} catch (TestDataException e) {
    // Handle validation error
}
```

### 2. Test Execution
```java
try {
    TestCase testCase = container.getTestCase("TC_001");
    // Use test case
} catch (IllegalArgumentException e) {
    // Handle missing test case
}
```

### 3. Result Recording
```java
try {
    JsonUtils.writeTestResult(...);
} catch (IOException e) {
    // Handle write error
}
