# Design Document - Contract Static Testing

## Overview

Tài liệu này mô tả thiết kế chi tiết cho việc implement static testing cho Contract module trong hệ thống HRMS. Static testing sẽ tập trung vào unit testing cho Contract model class, đảm bảo tất cả các methods (getter/setter) và constructors hoạt động đúng cách.

### Objectives

- Tạo comprehensive unit tests cho Contract model class
- Đảm bảo test coverage cho tất cả 11 fields của Contract
- Thiết lập test infrastructure với JUnit 5 và Mockito
- Tạo test structure dễ maintain và mở rộng

### Scope

**In Scope:**
- Unit tests cho Contract model (getter/setter/constructor)
- Test configuration và dependencies
- Test file structure và organization

**Out of Scope:**
- Integration tests với database
- Tests cho ContractDAO (sẽ làm trong phase sau nếu cần)
- Tests cho Servlet layer
- Performance testing

## Architecture

### Test Framework Stack

```
┌─────────────────────────────────────┐
│     JUnit 5 (Jupiter)               │
│  - Test runner và assertions        │
└─────────────────────────────────────┘
           │
           ▼
┌─────────────────────────────────────┐
│     Contract Model Tests            │
│  - ContractTest.java                │
└─────────────────────────────────────┘
           │
           ▼
┌─────────────────────────────────────┐
│     Contract.java                   │
│  - Model class under test           │
└─────────────────────────────────────┘
```

### Directory Structure

```
src/
├── main/
│   └── java/
│       └── hrms/
│           └── model/
│               └── Contract.java
└── test/
    └── java/
        └── hrms/
            └── model/
                └── ContractTest.java
```

## Components and Interfaces

### 1. Test Dependencies (pom.xml)

Cần upgrade từ JUnit 4 sang JUnit 5 và thêm Mockito:

```xml
<!-- JUnit 5 (Jupiter) -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>

<!-- Mockito (for future DAO/Servlet tests) -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.5.0</version>
    <scope>test</scope>
</dependency>

<!-- Mockito JUnit Jupiter integration -->
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <version>5.5.0</version>
    <scope>test</scope>
</dependency>
```

### 2. ContractTest Class Design

```java
package hrms.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

@DisplayName("Contract Model Tests")
public class ContractTest {
    
    private Contract contract;
    private LocalDate testStartDate;
    private LocalDate testEndDate;
    private LocalDate testSignDate;
    
    @BeforeEach
    void setUp() {
        // Initialize test data before each test
    }
    
    // Test methods will be defined here
}
```

## Data Models

### Test Data Strategy

**Test Constants:**
```java
private static final int TEST_CONTRACT_ID = 1;
private static final int TEST_USER_ID = 100;
private static final int TEST_DURATION = 12;
private static final double TEST_BASE_SALARY = 15000000.0;
private static final String TEST_NOTE = "Test contract note";
private static final int TEST_TYPE_ID = 1;
private static final int TEST_POSITION_ID = 5;
private static final int TEST_SIGNER_ID = 50;
```

**Test Date Setup:**
```java
testStartDate = LocalDate.of(2024, 1, 1);
testEndDate = LocalDate.of(2024, 12, 31);
testSignDate = LocalDate.of(2023, 12, 15);
```

## Testing Strategy

### Test Categories

#### 1. Constructor Tests

**Test 1.1: Default Constructor**
- Verify default constructor creates non-null object
- Verify all fields are initialized to default values

**Test 1.2: Parameterized Constructor**
- Verify all 11 fields are set correctly
- Verify date fields handle LocalDate properly

#### 2. Getter Tests

**Test 2.1-2.11: Individual Getter Tests**
- Test each getter method returns correct value
- Cover all 11 fields:
  - contractId
  - userId
  - startDate
  - endDate
  - signDate
  - duration
  - baseSalary
  - note
  - typeID
  - positionId
  - signerId

#### 3. Setter Tests

**Test 3.1-3.11: Individual Setter Tests**
- Test each setter method updates value correctly
- Verify getter returns updated value
- Cover all 11 fields

#### 4. Date Handling Tests

**Test 4.1: Null Date Handling**
- Verify endDate can be null (optional field)
- Verify startDate and signDate handle null appropriately

**Test 4.2: Date Validation**
- Test with various LocalDate values
- Verify date immutability

#### 5. Edge Cases

**Test 5.1: Boundary Values**
- Test with zero and negative values for numeric fields
- Test with empty and null strings for note field
- Test with very large salary values

**Test 5.2: Object State**
- Test multiple setter calls on same object
- Verify object state consistency

### Test Naming Convention

Format: `test<MethodName>_<Scenario>_<ExpectedResult>`

Examples:
- `testDefaultConstructor_CreatesObject_NotNull()`
- `testGetContractId_AfterSet_ReturnsCorrectValue()`
- `testSetBaseSalary_WithValidValue_UpdatesSuccessfully()`

### Assertions Strategy

**Primary Assertions:**
- `assertEquals()` - for value comparisons
- `assertNotNull()` - for null checks
- `assertTrue()/assertFalse()` - for boolean conditions
- `assertThrows()` - for exception testing (if needed)

**Assertion Messages:**
- Include descriptive messages for all assertions
- Format: "Expected [expected] but got [actual]"

## Error Handling

### Test Failure Scenarios

1. **Getter returns wrong value**
   - Clear assertion message indicating expected vs actual
   - Test isolation ensures no side effects

2. **Setter doesn't update value**
   - Verify with getter after setter call
   - Clear failure message

3. **Constructor doesn't initialize properly**
   - Test each field individually
   - Identify which field failed

### Test Isolation

- Each test method is independent
- Use `@BeforeEach` to reset state
- No shared mutable state between tests

## Testing Strategy

### Test Execution

**Maven Command:**
```bash
mvn test
```

**Run specific test class:**
```bash
mvn test -Dtest=ContractTest
```

**Run with coverage (if jacoco added):**
```bash
mvn clean test jacoco:report
```

### Expected Test Coverage

- **Target Coverage:** 100% for Contract model
- **Lines Covered:** All getter/setter methods
- **Branches:** N/A (simple POJO, no branching logic)

### Test Organization

```
ContractTest.java
├── Constructor Tests (2 tests)
│   ├── testDefaultConstructor
│   └── testParameterizedConstructor
├── Getter Tests (11 tests)
│   ├── testGetContractId
│   ├── testGetUserId
│   ├── testGetStartDate
│   ├── testGetEndDate
│   ├── testGetSignDate
│   ├── testGetDuration
│   ├── testGetBaseSalary
│   ├── testGetNote
│   ├── testGetTypeID
│   ├── testGetPositionId
│   └── testGetSignerId
├── Setter Tests (11 tests)
│   ├── testSetContractId
│   ├── testSetUserId
│   ├── testSetStartDate
│   ├── testSetEndDate
│   ├── testSetSignDate
│   ├── testSetDuration
│   ├── testSetBaseSalary
│   ├── testSetNote
│   ├── testSetTypeID
│   ├── testSetPositionId
│   └── testSetSignerId
└── Edge Case Tests (3 tests)
    ├── testNullDateHandling
    ├── testBoundaryValues
    └── testMultipleSetterCalls
```

**Total Tests:** ~27 test methods

### Continuous Integration Considerations

- Tests should run quickly (< 1 second total)
- No external dependencies (database, network)
- Deterministic results (no random data)
- Can run in any order

## Implementation Notes

### Best Practices

1. **Test Readability**
   - Use descriptive test names
   - Add `@DisplayName` annotations
   - Keep tests simple and focused

2. **Test Maintainability**
   - Follow DRY principle with `@BeforeEach`
   - Use constants for test data
   - Group related tests

3. **Test Quality**
   - One assertion per test (when possible)
   - Test one thing at a time
   - Clear failure messages

### Future Enhancements

1. **Phase 2: DAO Testing**
   - Add integration tests for ContractDAO
   - Use H2 in-memory database for testing
   - Test database operations

2. **Phase 3: Servlet Testing**
   - Add tests for MyContractServlet
   - Mock HttpServletRequest/Response
   - Test request handling logic

3. **Code Coverage**
   - Add JaCoCo plugin for coverage reports
   - Set minimum coverage thresholds
   - Generate HTML coverage reports
