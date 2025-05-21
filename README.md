# JSON Test Data Management Framework

## Overview
A robust Java-based test automation framework for managing test data in JSON format, with support for parallel test execution, intelligent caching, and comprehensive performance monitoring.

## Key Features
- **Advanced Test Data Management**
  - JSON Schema validation
  - Smart caching with TTL
  - Thread-safe operations
  - Automated data backup

- **Parallel Test Execution**
  - Dynamic thread pool sizing
  - Test status tracking
  - Real-time performance metrics
  - Configurable retry mechanism

- **Performance Monitoring**
  - Operation timing tracking
  - Cache statistics
  - Detailed performance logs
  - Resource usage monitoring

- **Comprehensive Logging**
  - Structured logging format
  - Separate performance logs
  - Log rotation and archiving
  - Debug-level tracing

## Project Structure
```
src/
├── test_data/           # Test data and schema files
│   ├── schema.json      # JSON Schema definition
│   ├── HomeData.json    # Home policy test data
│   └── SandboxData.json # Sandbox policy test data
├── main/
│   ├── java/
│   │   ├── json_core/   # Core framework classes
│   │   └── models/      # Data models
│   └── resources/
│       └── log4j2.xml   # Logging configuration
└── docs/
    └── json/           # Framework documentation
```

## Getting Started

### Prerequisites
- Java 8 or higher
- Maven 3.6 or higher
- Chrome/Firefox browser for UI tests

### Installation
1. Clone the repository
2. Install dependencies:
```bash
mvn clean install
```

### Configuration
1. Update test data in `src/test_data/`
2. Configure logging in `src/main/resources/log4j2.xml`
3. Adjust thread pool settings if needed in `TestOrchestrator`

### Running Tests
```bash
mvn test
```

For running specific test suites:
```bash
mvn test -DsuiteFile=TestNG.xml
```

## Core Components

### JsonUtils
Handles JSON file operations with:
- Thread-safe file access
- Automatic backups
- Caching mechanism
- Performance monitoring

### TestOrchestrator
Manages test execution with:
- Dynamic thread pool
- Test status tracking
- Performance metrics
- Retry mechanism

### PerformanceMonitor
Tracks system performance:
- Operation timing
- Resource usage
- Cache statistics
- Detailed logging

## Reporting
- Allure reports integration
- Performance metrics dashboard
- Test execution logs
- Cache statistics

## Best Practices
1. Use schema validation for all test data
2. Implement proper error handling
3. Monitor performance metrics
4. Review logs regularly
5. Maintain test data versioning

## Contributing
1. Follow coding standards
2. Add unit tests
3. Update documentation
4. Monitor performance impact
5. Review error handling

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Contact
For questions and support, please contact the development team.
