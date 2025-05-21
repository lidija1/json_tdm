# Framework Documentation

## Table of Contents
1. [Overview](#overview)
2. [Core Components](#core-components)
3. [Implementation Guide](#implementation-guide)
4. [Best Practices](#best-practices)
5. [Troubleshooting](#troubleshooting)

## Overview

The JSON Test Data Management Framework provides a robust solution for managing test data and execution in automation projects. Key features include:

- Advanced caching with TTL
- Parallel test execution
- Performance monitoring
- Comprehensive logging
- Error handling with retry mechanism

## Core Components

### JsonUtils
Enhanced utility for JSON operations:
```java
// Write test results with monitoring
JsonUtils.writeTestResult(
    "results.json",
    "TC_001",
    "status",
    "PASSED"
);

// Get cache stats
String stats = JsonUtils.getCacheStats();
```

### TestOrchestrator
Manages parallel test execution:
```java
TestOrchestrator orchestrator = new TestOrchestrator();

// Execute test with monitoring
CompletableFuture<TestResult> future = orchestrator.executeTest(
    "LoginTest",
    () -> performLogin()
);
```

### PerformanceMonitor
Tracks system performance:
```java
// Record timing
PerformanceMonitor.recordOperation("query", 150);

// Get stats
Map<String, Double> stats = PerformanceMonitor.getAverageOperationTimes();
```

### RetryManager
Handles transient failures:
```java
// Retry with custom config
RetryManager.retry(
    () -> operation(),
    "customOp",
    5,
    1000
);
```

## Implementation Guide

### Setup
1. Configure logging (log4j2.xml)
2. Initialize components
3. Configure thread pools

### Usage

#### Basic Test Execution
```java
// Initialize
TestOrchestrator orchestrator = new TestOrchestrator();
TestDataCache.initialize();

// Execute test
orchestrator.executeTest("Test1", () -> {
    Object data = TestDataCache.getTestData("key");
    // Test logic
    return result;
});
```

#### Advanced Usage
```java
// With retry and monitoring
CompletableFuture<TestResult> future = orchestrator.executeTest(
    "ComplexTest",
    () -> RetryManager.retry(
        () -> {
            // Test logic
            return result;
        },
        "ComplexTest",
        3,
        1000
    )
);
```

## Best Practices

### 1. Error Handling
- Use RetryManager for transient failures
- Implement proper exception hierarchy
- Log errors with context

### 2. Performance
- Monitor cache hit rates
- Track operation timings
- Review thread pool usage

### 3. Resource Management
- Implement proper cleanup
- Monitor memory usage
- Handle file operations carefully

## Troubleshooting

### Common Issues

#### Cache Problems
```java
// Clear caches
TestDataCache.invalidateCache();
JsonUtils.clearCache();
```

#### Performance Issues
```java
// Check metrics
String stats = PerformanceMonitor.getCacheStats();
int activeTests = orchestrator.getActiveTestCount();
```

#### Thread Pool Issues
```java
// Proper shutdown
orchestrator.shutdown();
```

## Maintenance

### Regular Tasks
1. Review logs
2. Monitor performance
3. Clean up resources
4. Update configurations

### Monitoring
1. Check performance metrics
2. Review error rates
3. Monitor resource usage
4. Validate test results

## Security Considerations

### Data Protection
1. Secure sensitive data
2. Implement access control
3. Use secure protocols

### Error Handling
1. Sanitize error messages
2. Handle secrets properly
3. Validate inputs

## Future Enhancements

### Planned Features
1. Enhanced monitoring
2. Advanced caching
3. Improved parallelization
4. Better reporting
