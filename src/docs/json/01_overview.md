# Test Data Management with JSON

## Overview
This document describes our enhanced JSON-based test data management system for the Sandbox Policy Automation framework. The system now includes advanced caching, parallel execution, and comprehensive performance monitoring.

## Key Components

1. **Test Data Management**
   - Smart caching with TTL and size limits
   - Thread-safe operations
   - Automated backup system
   - Schema validation
   - Performance monitoring

2. **Test Execution**
   - Dynamic thread pool management
   - Test status tracking
   - Retry mechanism
   - Performance metrics
   - Resource monitoring

3. **Core Classes**
   - `JsonUtils` - Enhanced JSON operations with caching
   - `TestOrchestrator` - Advanced parallel test execution
   - `PerformanceMonitor` - System performance tracking
   - `RetryManager` - Configurable retry mechanism
   - `TestDataCache` - Intelligent caching system

## Directory Structure
```
src/
├── test_data/
│   ├── schema.json        # JSON Schema definition
│   ├── HomeData.json      # Home policy test data
│   └── SandboxData.json   # Sandbox policy test data
└── main/java/
    ├── models/
    │   ├── TestCase.java
    │   └── TestDataContainer.java
    └── json_core/
        ├── JsonDataReader.java
        └── JsonUtils.java
```

## Key Features
- Strong type safety with comprehensive field validations
- Policy coverage options (Gold/Silver/Bronze)
- Property characteristics validation (YearBuilt, RoofType, ConstructionType)
- Insurance coverage amount validations (ReplacementCost, Contents, LossOfUse)
- Deductible and liability validations
- Schema enforcement with strict field requirements
- Thread safety and concurrent operations support
- Result caching and performance optimization
- Built-in backup management and versioning
- Comprehensive audit trail and logging
- Clear error handling with detailed validation messages
