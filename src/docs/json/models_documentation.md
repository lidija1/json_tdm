# Models Package Documentation

## TestCase
Core model class representing a single test case.

### Features
- Comprehensive field validation
- Type-safe data access
- Format validation
- Builder pattern implementation
- Immutable data structure

### Key Fields
```java
@NotNull
private String policyType;

@NotNull
private String customerType;

@Pattern(regexp = "^[\\d,]+$")
private String replacementCost;

@Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$")
private String effectiveDate;
```

### Validation Rules
- Required fields validation
- Format pattern matching
- Range validation
- Business rule validation

### Usage Examples
```java
TestCase testCase = TestCase.builder()
    .policyType("Home")
    .customerType("Individual")
    .replacementCost("800,000")
    .effectiveDate("01/30/2024")
    .build();
```

## TestTypeConfig
Configuration model for different test types.

### Features
- Flexible test configuration
- Environment-specific settings
- Validation rules
- Builder pattern

### Fields
```java
@NotNull
private String type;

@Min(1)
private int priority;

private boolean parallel;

@Min(0)
private int retries;

@Min(1)
private int timeoutSeconds;
```

### Usage Examples
```java
TestTypeConfig config = TestTypeConfig.builder()
    .type("api")
    .priority(1)
    .parallel(true)
    .retries(2)
    .timeoutSeconds(30)
    .build();
```

## LobConfig
Line of Business configuration model.

### Features
- Business-specific configuration
- Environment settings
- Test type configuration
- Validation rules

### Fields
```java
private String lobName;
private Map<String, TestTypeConfig> testTypes;
private Map<String, String> environments;
```

### Usage Examples
```java
LobConfig config = LobConfig.builder()
    .lobName("Home")
    .addTestType("api", apiConfig)
    .addTestType("ui", uiConfig)
    .addEnvironment("dev", "https://dev.example.com")
    .build();
```

## TestDataContainer
Container class for managing collections of test cases.

### Features
- Thread-safe operations
- Validation support
- Data filtering
- Immutable views
- Performance optimization

### Methods
```java
public void addTestCase(String id, TestCase testCase)
public TestCase getTestCase(String id)
public Map<String, TestCase> getTestCasesByType(String type)
```

### Usage Examples
```java
TestDataContainer container = new TestDataContainer();
container.addTestCase("TC_001", testCase);
Map<String, TestCase> homePolicies = container.getTestCasesByType("Home");
```

## Data Validation

### Schema Validation
- JSON Schema compliance
- Field format validation
- Business rule validation
- Cross-field validation

### Custom Validation Rules
```java
@AssertTrue(message = "Invalid coverage amounts")
public boolean isValidCoverageAmounts() {
    return validateCoverageAmounts();
}
```

## Best Practices

### Model Design
1. Use proper annotations
2. Implement validation
3. Follow builder pattern
4. Maintain immutability
5. Document constraints

### Data Access
1. Use type-safe methods
2. Validate input data
3. Handle null values
4. Cache when appropriate
5. Log validation failures

### Thread Safety
1. Use immutable fields
2. Synchronized when needed
3. Thread-safe collections
4. Atomic operations
5. Proper locking

## Common Patterns

### Builder Pattern
```java
public static class Builder {
    private final TestCase testCase;
    
    public Builder() {
        testCase = new TestCase();
    }
    
    public Builder field(String value) {
        testCase.field = value;
        return this;
    }
    
    public TestCase build() {
        validate();
        return testCase;
    }
}
```

### Validation Pattern
```java
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseModel {
    @NotNull(message = "ID cannot be null")
    private String id;
    
    public void validate() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<BaseModel>> violations = validator.validate(this);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations);
        }
    }
}
