# System Test Cases Design Document

## Overview

This design document outlines the structure and organization of comprehensive system test cases for the HRMS application. The test cases will be organized by functional modules, following a consistent format that includes test case ID, description, preconditions, test steps, expected results, and test data requirements. The design emphasizes end-to-end testing, integration validation, and both positive and negative test scenarios.

## Architecture

### Test Case Organization Structure

```
System Test Cases
├── Authentication & Authorization Module
│   ├── Login Tests
│   ├── Logout Tests
│   ├── Password Management Tests
│   └── Authorization Tests
├── User Management Module
│   ├── Create User Tests
│   ├── Update User Tests
│   ├── Role Management Tests
│   └── Department/Position Assignment Tests
├── Attendance Management Module
│   ├── Check-in/Check-out Tests
│   ├── Attendance Calculation Tests
│   ├── Shift Management Tests
│   └── Attendance Export Tests
├── Payroll Management Module
│   ├── Payroll Calculation Tests
│   ├── Payroll Item Management Tests
│   ├── Tax & Insurance Tests
│   └── Payroll Report Tests
├── Leave & Overtime Module
│   ├── Leave Request Tests
│   ├── Overtime Request Tests
│   ├── Ticket Approval Tests
│   └── Validation Tests
├── Recruitment Module
│   ├── Job Description Tests
│   ├── CV Submission Tests
│   ├── CV Management Tests
│   └── Recruitment Workflow Tests
├── Contract Management Module
│   ├── Contract Creation Tests
│   ├── Contract Status Tests
│   └── Contract History Tests
├── Profile Management Module
│   ├── View Profile Tests
│   ├── Update Profile Tests
│   └── Work History Tests
├── Integration Tests
│   └── Cross-Module Data Flow Tests
└── Negative & Edge Case Tests
    ├── Validation Tests
    ├── Error Handling Tests
    └── Boundary Tests
```

### Test Case Format

Each test case will follow this standardized format:

```
Test Case ID: TC-[MODULE]-[NUMBER]
Test Case Name: [Descriptive name]
Module: [Module name]
Priority: [High/Medium/Low]
Test Type: [Positive/Negative/Integration]

Preconditions:
- [List of conditions that must be met before test execution]

Test Data:
- [Specific data values needed for the test]

Test Steps:
1. [Step 1]
2. [Step 2]
...

Expected Results:
1. [Expected result for step 1]
2. [Expected result for step 2]
...

Postconditions:
- [System state after test execution]

Related Requirements: [Requirement numbers from requirements.md]
```

## Components and Interfaces

### Test Data Management

**Test User Accounts:**
- Admin User: username=admin_test, role=Admin
- HR User: username=hr_test, role=HR
- Manager User: username=manager_test, role=Manager
- Employee User: username=employee_test, role=Employee

**Test Organizational Data:**
- Departments: IT, HR, Finance, Sales
- Positions: Developer, HR Manager, Accountant, Sales Rep
- Degrees: Bachelor, Master, PhD

**Test Attendance Data:**
- Shifts: Morning (8:00-12:00, 13:00-17:00), Evening (13:00-22:00)
- Sample dates with various check-in/out scenarios

**Test Payroll Data:**
- Base salaries: 10,000,000 VND, 15,000,000 VND, 20,000,000 VND
- Payroll types: Bonus, Overtime Pay, Tax, Insurance
- Policy values: Tax rate 10%, Insurance rate 8%

### Test Environment Requirements

**Application Server:**
- Tomcat 10.x running on port 8080
- Context path: /hrms

**Database:**
- MySQL 8.x with test database schema
- Test data populated before test execution
- Database reset capability between test runs

**Browser Support:**
- Chrome (latest version)
- Firefox (latest version)
- Edge (latest version)

**Test Execution Tools:**
- JUnit 4.13.2 for test framework
- Selenium WebDriver for UI automation (if automated)
- Manual test execution tracking spreadsheet

## Data Models

### Test Case Data Model

```java
public class TestCase {
    private String testCaseId;
    private String testCaseName;
    private String module;
    private String priority;
    private String testType;
    private List<String> preconditions;
    private Map<String, String> testData;
    private List<TestStep> testSteps;
    private List<String> expectedResults;
    private List<String> postconditions;
    private List<String> relatedRequirements;
    private String status; // Not Run, Pass, Fail, Blocked
    private String executedBy;
    private Date executionDate;
    private String comments;
}

public class TestStep {
    private int stepNumber;
    private String action;
    private String expectedResult;
}
```

### Test Execution Result Model

```java
public class TestExecutionResult {
    private String testCaseId;
    private String executionId;
    private Date executionDate;
    private String executedBy;
    private String status; // Pass, Fail, Blocked, Skipped
    private String actualResult;
    private String comments;
    private List<String> screenshots; // paths to screenshot files
    private String defectId; // if test failed
}
```

## Error Handling

### Test Failure Handling

**When a test case fails:**
1. Capture screenshot of failure state
2. Log detailed error message and stack trace
3. Record actual vs expected results
4. Create defect ticket with:
   - Test case ID
   - Steps to reproduce
   - Expected vs actual behavior
   - Environment details
   - Screenshots/logs

**Test Blocking Conditions:**
- Environment unavailable
- Prerequisite test failed
- Test data unavailable
- Application crash/unresponsive

**Recovery Actions:**
- Reset database to known state
- Clear browser cache and cookies
- Restart application server if needed
- Re-run prerequisite tests

### Validation Error Testing

Test cases will verify proper error handling for:
- Required field validation
- Data type validation
- Business rule validation
- Database constraint violations
- Session timeout scenarios
- Concurrent access conflicts

## Testing Strategy

### Test Execution Approach

**Phase 1: Smoke Testing**
- Execute critical path test cases
- Verify basic functionality of each module
- Ensure test environment is stable

**Phase 2: Functional Testing**
- Execute all positive test cases
- Verify each feature works as designed
- Test all user workflows end-to-end

**Phase 3: Integration Testing**
- Execute cross-module test cases
- Verify data flows between modules
- Test module dependencies

**Phase 4: Negative Testing**
- Execute all negative test cases
- Verify error handling and validation
- Test boundary conditions

**Phase 5: Regression Testing**
- Re-execute all test cases after changes
- Verify existing functionality not broken
- Focus on areas impacted by changes

### Test Coverage Goals

**Functional Coverage:**
- 100% of user stories covered
- All acceptance criteria validated
- All user roles tested

**Code Coverage (if automated):**
- Minimum 70% line coverage
- 80% branch coverage for critical modules
- 100% coverage for authentication/authorization

**Integration Points:**
- All module interactions tested
- Database operations validated
- External service integrations verified

### Test Prioritization

**Priority High:**
- Authentication and authorization
- User account creation
- Attendance recording
- Payroll calculation
- Leave/OT approval workflows

**Priority Medium:**
- Profile management
- Report generation
- Search and filter functionality
- Contract management

**Priority Low:**
- UI cosmetic issues
- Optional features
- Performance optimization

## Test Case Categories

### 1. Authentication & Authorization Tests (TC-AUTH-001 to TC-AUTH-020)

**Login Tests:**
- Valid login with different roles
- Invalid username/password
- Account locked scenarios
- Google OAuth login

**Authorization Tests:**
- Role-based access control
- URL-based authorization
- Feature-level permissions
- Cross-role access attempts

### 2. User Management Tests (TC-USER-001 to TC-USER-030)

**Create User Tests:**
- Create user with all required fields
- Create user with optional fields
- Duplicate email/phone/CCCD validation
- Account auto-creation on user creation

**Update User Tests:**
- Update personal information
- Change department with work history
- Change position with work history
- Change role with permission updates

### 3. Attendance Management Tests (TC-ATT-001 to TC-ATT-025)

**Check-in/Check-out Tests:**
- Normal check-in/check-out flow
- Multiple check-in/check-out in one day
- Late arrival calculation
- Early departure calculation
- Overtime hours calculation

**Attendance Export Tests:**
- Export with filters
- Excel format validation
- Data accuracy verification

### 4. Payroll Management Tests (TC-PAY-001 to TC-PAY-030)

**Payroll Calculation Tests:**
- Base salary from contract
- Earnings additions (bonus, OT pay)
- Deductions (tax, insurance)
- Net salary calculation
- Working hours impact

**Payroll Item Management Tests:**
- Add payroll item
- Update payroll item
- Delete payroll item
- Recalculate net salary

### 5. Leave & Overtime Tests (TC-LEAVE-001 to TC-LEAVE-025)

**Leave Request Tests:**
- Create leave request
- Approve/reject leave
- Leave date validation
- Overlapping leave detection
- Leave balance tracking

**Overtime Request Tests:**
- Create OT request
- Approve/reject OT
- OT time validation
- Overlapping OT detection
- OT hours calculation

### 6. Recruitment Tests (TC-REC-001 to TC-REC-020)

**Job Description Tests:**
- Create job posting
- Approve/cancel job
- Job listing display
- Job search and filter

**CV Management Tests:**
- Submit CV
- Update CV status
- CV search and filter
- CV-Job association

### 7. Contract Management Tests (TC-CON-001 to TC-CON-015)

**Contract Tests:**
- Create contract
- Auto status updates
- Contract history
- Contract-salary linkage

### 8. Profile Management Tests (TC-PROF-001 to TC-PROF-015)

**Profile Tests:**
- View profile
- Update profile
- Change password
- View work history
- View payroll history

### 9. Integration Tests (TC-INT-001 to TC-INT-020)

**Cross-Module Tests:**
- User creation → Account creation
- Attendance → Payroll calculation
- Department change → Work history
- Contract → Payroll base salary
- Leave approval → Attendance records

### 10. Negative Tests (TC-NEG-001 to TC-NEG-030)

**Validation Tests:**
- Missing required fields
- Invalid data formats
- Boundary value violations
- Business rule violations

**Error Handling Tests:**
- Database errors
- Session timeout
- Concurrent updates
- Invalid URLs

## Test Execution Tracking

### Test Metrics

**Track the following metrics:**
- Total test cases: [Count]
- Test cases executed: [Count]
- Test cases passed: [Count]
- Test cases failed: [Count]
- Test cases blocked: [Count]
- Pass rate: [Percentage]
- Defects found: [Count]
- Critical defects: [Count]

### Test Execution Report Format

```
Test Execution Summary Report
Date: [Date]
Tester: [Name]
Build/Version: [Version]

Module: [Module Name]
Total Test Cases: [Count]
Executed: [Count]
Passed: [Count]
Failed: [Count]
Blocked: [Count]
Pass Rate: [Percentage]

Failed Test Cases:
- TC-XXX-YYY: [Brief description]
- TC-XXX-ZZZ: [Brief description]

Defects Logged:
- DEF-001: [Description]
- DEF-002: [Description]

Comments:
[Any additional notes]
```

## Test Data Setup and Teardown

### Setup Scripts

**Before test execution:**
1. Reset database to clean state
2. Execute data population scripts:
   - Insert test roles
   - Insert test departments
   - Insert test positions
   - Insert test degrees
   - Insert test shifts
   - Insert test users and accounts
   - Insert test payroll policies

### Teardown Scripts

**After test execution:**
1. Archive test execution results
2. Clean up temporary data
3. Reset database if needed for next run

### Data Isolation

- Each test module uses distinct test data
- Test data prefixed with "TEST_" for identification
- Automated cleanup of test data after execution

## Traceability Matrix

Each test case will be mapped to:
- Requirements (from requirements.md)
- User stories
- Acceptance criteria
- Code modules (Servlets, DAOs, Services)

This ensures complete coverage and allows impact analysis when requirements change.
