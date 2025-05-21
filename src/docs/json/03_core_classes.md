# Core Classes Documentation

## TestCase Class
The `TestCase` class is the fundamental model representing a single test case.

### Features
- Comprehensive field validation annotations
- JSON property mapping with format validations
- Builder pattern implementation with required field checks
- Helper methods for data type conversion (String to Date, BigDecimal)
- Policy coverage option validation (Gold/Silver/Bronze)
- Currency field formatting and validation
- Property characteristics validation
- Insurance coverage amount calculations
- Deductible and liability validations
- Thread safety considerations

### Usage Example
```java
// Creating a test case with all required fields
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

// Using helper methods
LocalDate dob = testCase.getDOBAsDate();
BigDecimal replacementCost = testCase.getReplacementCostAsBigDecimal();
BigDecimal contents = testCase.getContentsAsBigDecimal();
BigDecimal lossOfUse = testCase.getLossOfUseAsBigDecimal();
BigDecimal liability = testCase.getLiabilityAsBigDecimal();
```

## TestDataContainer Class
Container class managing collections of test cases.

### Features
- Thread-safe operations
- Validation support
- Utility methods for data access
- Immutable data views
- Custom filtering capabilities

### Usage Example
```java
TestDataContainer container = new TestDataContainer();

// Add test case
container.addTestCase("TC_001", testCase);

// Get test cases by policy type
Map<String, TestCase> homePolicies = 
    container.getTestCasesByPolicyType("Home");

// Validate container
boolean isValid = container.isValid();
```

## JsonDataReader Class
Main utility for reading and validating test data.

### Features
- JSON schema validation
- Data caching
- Error handling
- Type-safe data access
- Performance optimization

### Usage Example
```java
TestCase testCase = JsonDataReader.getTestData(
    "src/test_data/HomeData.json", 
    "TC_ID_0001"
);
```

## JsonUtils Class
Utility class for JSON operations and result management.

### Features
- File backup management
- Thread-safe operations
- Result caching
- Audit trail
- Error handling

### Usage Example
```java
JsonUtils.writeTestResult(
    "test-results.json",
    "TC_001",
    "status",
    "PASSED"
);
```
