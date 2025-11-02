# Requirements Document

## Introduction

Tài liệu này mô tả các yêu cầu cho việc thực hiện static testing cho module Contract trong hệ thống HRMS. Static testing sẽ tập trung vào việc kiểm tra code quality, structure, và logic mà không cần chạy ứng dụng, bao gồm unit tests cho các thành phần chính của Contract module.

## Glossary

- **Contract Module**: Module quản lý hợp đồng lao động trong hệ thống HRMS, bao gồm Contract model, ContractDAO, và các Servlet liên quan
- **Static Testing**: Phương pháp kiểm thử phần mềm bằng cách phân tích code mà không cần thực thi chương trình
- **Unit Test**: Kiểm thử từng đơn vị code nhỏ nhất (method, class) một cách độc lập
- **Contract Model**: Entity class đại diện cho hợp đồng lao động với các thuộc tính như contractId, userId, startDate, endDate, duration, baseSalary, etc.
- **ContractDAO**: Data Access Object chịu trách nhiệm tương tác với database cho Contract
- **Test Coverage**: Tỷ lệ phần trăm code được kiểm thử bởi các test cases

## Requirements

### Requirement 1

**User Story:** Là một developer, tôi muốn có unit tests cho Contract model, để đảm bảo các getter/setter và constructor hoạt động đúng

#### Acceptance Criteria

1. THE Contract Module SHALL provide unit tests that verify all getter methods return correct values for all 11 fields
2. THE Contract Module SHALL provide unit tests that verify all setter methods update values correctly for all 11 fields
3. THE Contract Module SHALL provide unit tests that verify the default constructor initializes an empty Contract object
4. THE Contract Module SHALL provide unit tests that verify the parameterized constructor sets all 11 fields correctly
5. THE Contract Module SHALL provide unit tests that verify date fields (startDate, endDate, signDate) handle LocalDate objects correctly

### Requirement 2

**User Story:** Là một developer, tôi muốn có unit tests cho ContractDAO methods, để đảm bảo các database operations được implement đúng logic

#### Acceptance Criteria

1. THE Contract Module SHALL provide unit tests that verify getAllContracts method returns a list of contracts
2. THE Contract Module SHALL provide unit tests that verify getContractById method returns correct contract when valid ID is provided
3. THE Contract Module SHALL provide unit tests that verify getContractById method returns null when invalid ID is provided
4. THE Contract Module SHALL provide unit tests that verify getContractsByUserId method returns contracts for specific user
5. THE Contract Module SHALL provide unit tests that verify addContract method inserts new contract with valid data
6. THE Contract Module SHALL provide unit tests that verify updateContractNote method updates note field correctly

### Requirement 3

**User Story:** Là một developer, tôi muốn có unit tests cho MyContractServlet, để đảm bảo servlet xử lý request/response đúng cách

#### Acceptance Criteria

1. WHEN user is not authenticated, THE Contract Module SHALL redirect to authentication page
2. WHEN user is authenticated, THE Contract Module SHALL retrieve contracts for that user
3. WHEN database error occurs, THE Contract Module SHALL forward to error page with appropriate message
4. THE Contract Module SHALL set contracts attribute in request scope before forwarding to view

### Requirement 4

**User Story:** Là một developer, tôi muốn có test structure và configuration phù hợp, để dễ dàng chạy và maintain tests

#### Acceptance Criteria

1. THE Contract Module SHALL organize test files in src/test/java following same package structure as main code
2. THE Contract Module SHALL use JUnit 5 framework for writing unit tests
3. THE Contract Module SHALL use Mockito framework for mocking dependencies
4. THE Contract Module SHALL provide test configuration that allows running tests independently
5. THE Contract Module SHALL include Maven dependencies for testing frameworks in pom.xml
