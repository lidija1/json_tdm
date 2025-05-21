# JSON Core Classes Documentation

## JsonUtils
Primary utility class for JSON operations and file management.

### Key Features
- Thread-safe file operations with ReentrantLock
- Automatic file backup before modifications
- Smart caching with performance monitoring
- Retry mechanism for transient failures
- Structured logging

### Methods
```java
public static void writeTestResult(String filePath, String testCaseId, 
                                 String fieldName, String value)
```
Writes test results with automatic backup, caching, and retry mechanism.

```java
public static void clearCache()
public static void clearCache(String filePath)
```
Cache management methods with full or selective clearing.

```java
public static String getCacheStats()
```
Returns cache statistics and performance metrics.

### Usage Examples
```java
// Writing test results
JsonUtils.writeTestResult(
    "results.json",
    "TC_001",
    "status",
    "PASSED"
);

// Cache management
JsonUtils.clearCache();
String stats = JsonUtils.getCacheStats();
```

## TestResultManager
Manages test execution results and statistics.

### Key Features
- Hierarchical result organization
- Summary statistics tracking
- Automatic file management
- Thread-safe operations
- Cache optimization

### Methods
```java
public void recordTestResult(String lobName, String testType, TestResult result)
```
Records test results with proper categorization.

```java
public void clearCache()
public void clearCache(String lobName, String testType)
```
Cache management methods.

### Usage Examples
```java
TestResultManager manager = new TestResultManager("results/");
manager.recordTestResult("Home", "UI", testResult);
```

## TestExecutionManager
Manages test execution flow and parallel processing.

### Key Features
- Dynamic test scheduling
- Resource management
- Status tracking
- Performance monitoring
- Error handling

### Methods
```java
public void executeTest(String testId, Runnable test)
public void executeTests(List<Runnable> tests)
```
Test execution methods with parallel processing support.

### Usage Examples
```java
TestExecutionManager manager = new TestExecutionManager();
manager.executeTest("TC_001", () -> {
    // Test logic
});
```

## PerformanceMonitor
Tracks and analyzes system performance metrics.

### Key Features
- Operation timing tracking
- Statistical analysis
- Resource monitoring
- Performance logging
- Trend analysis

### Methods
```java
public static void recordOperation(String operationType, long durationMs)
public static Map<String, Double> getAverageOperationTimes()
```

### Usage Examples
```java
// Record operation timing
PerformanceMonitor.recordOperation("database-query", 150);

// Get performance stats
Map<String, Double> stats = PerformanceMonitor.getAverageOperationTimes();
```

## TestDataCache
Intelligent caching system for test data.

### Key Features
- TTL-based caching
- Size-limited cache
- Thread-safe operations
- Performance monitoring
- Auto cleanup

### Methods
```java
public static void cacheTestData(String key, Object data)
public static Object getTestData(String key)
public static void invalidateCache()
```

### Usage Examples
```java
// Cache test data
TestDataCache.cacheTestData("TC_001", testData);

// Retrieve cached data
Object data = TestDataCache.getTestData("TC_001");
```

## Error Handling

### Custom Exceptions
```java
class TestDataException extends RuntimeException
```
Custom exception for test data related errors.

### Retry Mechanism
```java
RetryManager.retry(
    () -> operation(),
    "operationName",
    maxRetries,
    delayMs
);
```

## Thread Safety Considerations

### Synchronized Operations
- File operations use ReentrantLock
- Cache operations are thread-safe
- Performance monitoring is atomic

### Resource Management
- Automatic resource cleanup
- Memory leak prevention
- Proper lock release

## Performance Optimization

### Caching Strategy
- Smart cache invalidation
- Size-based eviction
- TTL-based cleanup

### Monitoring
- Operation timing tracking
- Resource usage monitoring
- Performance logging
