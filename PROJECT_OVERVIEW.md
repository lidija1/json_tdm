# Project Overview

## Project Structure
```
json_tdm_v1.3/
├── src/
│   └── test/
│       └── java/
│           ├── pages/
│           │   ├── common/
│           │   │   └── BasePage.java
│           │   ├── home/
│           │   │   ├── LocationCoverage.java
│           │   │   ├── PolicyInformation.java
│           │   │   ├── QuoteRegistration.java
│           │   │   └── Rate.java
│           │   ├── Customer.java
│           │   ├── LandingPage.java
│           │   ├── LoginPage.java
│           │   └── Quotes.java
│           ├── config/
│           │   └── EnhancedLogger.java
│           └── json_core/
│               ├── JsonDataReader.java
│               └── JsonResultWriter.java
```

## Migration from Excel to JSON
### Previous Excel Implementation
- Used Apache POI for Excel operations
- Data stored in structured worksheets
- Limited data type support
- Complex cell reference handling
- Performance overhead with large datasets

### Current JSON Implementation
- Lightweight and flexible JSON format
- Native data type support
- Easier data manipulation
- Better performance
- Simplified data structure
- Enhanced maintainability

## JSON Data Flow
### 1. Test Data Structure
```json
{
    "testCases": [
        {
            "tcId": "TC001",
            "description": "Basic Quote Creation",
            "data": {
                "SubmissionType": "Quote",
                "Producer": "Test Producer",
                "EffectiveDate": "01/01/2024",
                "Program": "Homeowners",
                "PolicyCoverageOption": "HO3",
                "ResidenceType": "Single Family",
                "ReplacementCost": "250000"
            }
        }
    ]
}
```

### 2. Results Structure
```json
{
    "testResults": [
        {
            "tcId": "TC001",
            "status": "PASS",
            "Quote_ID": "Q123456",
            "Actual_Total_Location_Premium": "$1,234.56"
        }
    ]
}
```

## JSON Integration Points

### 1. Data Reading (JsonDataReader.java)
- Loads test data from JSON files
- Parses test case information
- Provides data to test classes
- Methods:
  ```java
  public static Map<String, String> getTestData(String filePath, String tcId);
  public static List<TestCase> getAllTestCases(String filePath);
  ```

### 2. Result Writing (JsonResultWriter.java)
- Writes test execution results
- Updates test status
- Stores quote information
- Methods:
  ```java
  public static void writeResult(String filePath, String tcId, String key, String value);
  public static void updateTestStatus(String filePath, String tcId, String status);
  ```

## Class Dependencies and JSON Usage

### 1. Page Classes
#### QuoteRegistration.java
- Uses JSON data for:
  - Submission type
  - Producer information
  - Program selection
  - Effective dates

#### PolicyInformation.java
- Uses JSON data for:
  - Program type
  - Policy options
  - Coverage selections

#### LocationCoverage.java
- Uses JSON data for:
  - Coverage options
  - Property details
  - Construction information
  - Risk factors

#### Rate.java
- Uses JSON data for:
  - Premium calculations
  - Quote IDs
- Writes results:
  - Quote numbers
  - Premium amounts
  - Policy costs

### 2. Utility Classes
#### EnhancedLogger.java
- Integrates with JSON results
- Logs test execution details
- Captures error information

## Data Flow Process
1. **Test Initialization**
   - Load test case from JSON
   - Parse test data
   - Initialize test environment

2. **Test Execution**
   - Pass JSON data to page objects
   - Execute test steps
   - Collect results

3. **Result Recording**
   - Write test status
   - Store quote information
   - Update result JSON
   - Generate reports

## Benefits of JSON Implementation
1. **Performance**
   - Faster data access
   - Reduced memory usage
   - Better scalability

2. **Maintainability**
   - Clear data structure
   - Easy to modify
   - Version control friendly

3. **Integration**
   - API compatibility
   - Easy data exchange
   - Tool integration

4. **Flexibility**
   - Dynamic data structures
   - Easy to extend
   - Support for complex data types

## Best Practices
1. **JSON Data Management**
   - Use consistent naming
   - Validate JSON schema
   - Handle missing data
   - Implement error handling

2. **Test Data Organization**
   - Separate test data files
   - Use meaningful test IDs
   - Include test descriptions
   - Maintain data versions

3. **Result Management**
   - Structured result format
   - Regular backup
   - Clear status indicators
   - Detailed error information

## Future Considerations
1. **Data Enhancement**
   - Add data validation
   - Implement data generators
   - Support multiple formats
   - Add data encryption

2. **Integration**
   - REST API integration
   - Cloud storage support
   - Real-time reporting
   - Analytics integration 