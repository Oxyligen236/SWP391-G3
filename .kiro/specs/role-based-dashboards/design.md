# Design Document: Role-Based Dashboards

## Overview

This design implements role-specific dashboards for the HRMS system, replacing the current generic welcome page with data-driven, actionable interfaces. Each dashboard will be tailored to display relevant metrics, quick actions, and information based on the user's role (Admin, HR Manager, HR, Department Manager, Employee).

The solution follows the existing architecture pattern using servlets for backend logic and JSP pages for frontend rendering, maintaining consistency with the current HRMS codebase.

## Architecture

### High-Level Architecture

```
┌─────────────────┐
│   User Login    │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│  HomeServlet    │ (Existing - routes to role-specific JSP)
└────────┬────────┘
         │
         ▼
┌─────────────────────────────────────────────────────┐
│         Role-Specific Home Page JSP                 │
│  (homePage_Admin.jsp, homePage_HRManager.jsp, etc) │
└────────┬────────────────────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────────────────────┐
│         DashboardServlet (NEW)                      │
│  - Fetches role-specific metrics                    │
│  - Prepares dashboard data                          │
│  - Forwards to dashboard content JSP                │
└────────┬────────────────────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────────────────────┐
│    Role-Specific Dashboard Content JSP (NEW)        │
│  - dashboard_admin.jsp                              │
│  - dashboard_hr_manager.jsp                         │
│  - dashboard_hr.jsp                                 │
│  - dashboard_dept_manager.jsp                       │
│  - dashboard_employee.jsp                           │
└─────────────────────────────────────────────────────┘
```

### Component Interaction Flow

1. User logs in and clicks "Home"
2. `HomeServlet` routes to role-specific home page JSP
3. Home page JSP iframe loads `DashboardServlet?role=X`
4. `DashboardServlet` identifies role and calls appropriate service methods
5. Dashboard services query DAOs for metrics data
6. Servlet forwards to role-specific dashboard content JSP
7. JSP renders metrics, charts, and quick actions

## Components and Interfaces

### 1. Backend Components

#### 1.1 DashboardServlet (NEW)

**Purpose**: Central controller for dashboard data retrieval and routing

**Location**: `src/main/java/hrms/controller/DashboardServlet.java`

**Key Methods**:
```java
protected void doGet(HttpServletRequest request, HttpServletResponse response)
    - Validates user session
    - Determines user role
    - Calls appropriate dashboard service method
    - Sets dashboard data as request attributes
    - Forwards to role-specific dashboard JSP

private void loadAdminDashboard(HttpServletRequest request, int userId)
private void loadHRManagerDashboard(HttpServletRequest request, int userId)
private void loadHRDashboard(HttpServletRequest request, int userId)
private void loadDeptManagerDashboard(HttpServletRequest request, int userId, int deptId)
private void loadEmployeeDashboard(HttpServletRequest request, int userId)
```

#### 1.2 DashboardService (NEW)

**Purpose**: Business logic layer for dashboard data aggregation

**Location**: `src/main/java/hrms/service/DashboardService.java`

**Key Methods**:
```java
// Admin Dashboard
AdminDashboardDTO getAdminDashboardData()
    - Returns: total users, departments, active accounts, pending accounts
    - Returns: recent activities list
    - Returns: user distribution by role

// HR Manager Dashboard
HRManagerDashboardDTO getHRManagerDashboardData()
    - Returns: total employees, pending tickets, pending JDs, payroll status
    - Returns: pending approvals list
    - Returns: employee distribution by department
    - Returns: recruitment pipeline stats

// HR Dashboard
HRDashboardDTO getHRDashboardData()
    - Returns: total employees, pending tickets, new CVs, expiring contracts
    - Returns: recent tickets list
    - Returns: upcoming contract expirations
    - Returns: monthly attendance summary

// Department Manager Dashboard
DeptManagerDashboardDTO getDeptManagerDashboardData(int deptId)
    - Returns: dept employee count, pending dept tickets, attendance rate, OT hours
    - Returns: pending department tickets list
    - Returns: team attendance overview
    - Returns: department performance trends

// Employee Dashboard
EmployeeDashboardDTO getEmployeeDashboardData(int userId)
    - Returns: remaining leave days, attendance rate, pending tickets, last payroll
    - Returns: personal attendance calendar
    - Returns: ticket status list
    - Returns: upcoming holidays
```

#### 1.3 Dashboard DTOs (NEW)

**Purpose**: Data transfer objects for dashboard metrics

**Location**: `src/main/java/hrms/dto/dashboard/`

**Classes**:
- `AdminDashboardDTO`
- `HRManagerDashboardDTO`
- `HRDashboardDTO`
- `DeptManagerDashboardDTO`
- `EmployeeDashboardDTO`
- `MetricCardDTO` (reusable for individual metrics)
- `RecentActivityDTO`
- `PendingApprovalDTO`

#### 1.4 Enhanced DAOs

**Purpose**: Add dashboard-specific query methods to existing DAOs

**Modifications**:

**AccountDAO** - Add methods:
```java
int getTotalActiveAccounts()
int getTotalPendingAccounts()
Map<String, Integer> getUserCountByRole()
List<RecentActivityDTO> getRecentActivities(int limit)
```

**UserDAO** - Add methods:
```java
int getTotalEmployeeCount()
Map<String, Integer> getEmployeeCountByDepartment()
int getDepartmentEmployeeCount(int deptId)
```

**TicketDAO** - Add methods:
```java
int getPendingTicketCount()
int getPendingTicketCountByDepartment(int deptId)
List<TicketDTO> getPendingTicketsForApproval(int approverId, int limit)
List<TicketDTO> getRecentTicketsForHR(int limit)
```

**JobDAO** - Add methods:
```java
int getPendingJobDescriptionCount()
```

**CVsDAO** - Add methods:
```java
int getNewCVCount()
Map<String, Integer> getCVCountByStatus()
```

**ContractDAO** - Add methods:
```java
int getExpiringContractsCount(int days)
List<ContractDTO> getExpiringContracts(int days, int limit)
```

**AttendanceDAO** - Add methods:
```java
double getUserAttendanceRate(int userId, int month, int year)
double getDepartmentAttendanceRate(int deptId, int month, int year)
List<AttendanceDTO> getUserMonthlyAttendance(int userId, int month, int year)
Map<String, Integer> getDepartmentDailyAttendance(int deptId, LocalDate date)
```

**OTDetailDAO** - Add methods:
```java
double getDepartmentMonthlyOTHours(int deptId, int month, int year)
```

**PayrollDAO** - Add methods:
```java
PayrollDTO getLatestPayrollByUser(int userId)
String getCurrentMonthPayrollStatus()
```

**LeaveDetailDAO** - Add methods:
```java
int getRemainingLeaveDays(int userId, int year)
```

### 2. Frontend Components

#### 2.1 Dashboard Content JSP Pages (NEW)

**Location**: `src/main/webapp/view/home/content/`

**Files**:
- `dashboard_admin.jsp`
- `dashboard_hr_manager.jsp`
- `dashboard_hr.jsp`
- `dashboard_dept_manager.jsp`
- `dashboard_employee.jsp`

**Structure** (common pattern for all):
```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container-fluid">
    <!-- Page Header -->
    <div class="row mb-4">
        <div class="col-12">
            <h4>Welcome, ${sessionScope.users.fullname}!</h4>
            <p class="text-muted">[Role-specific subtitle]</p>
        </div>
    </div>

    <!-- Metric Cards Row -->
    <div class="row">
        <!-- 4 metric cards in 3-column grid -->
    </div>

    <!-- Quick Actions Row -->
    <div class="row mt-4">
        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <h5>Quick Actions</h5>
                    <!-- Action buttons -->
                </div>
            </div>
        </div>
    </div>

    <!-- Data Tables/Lists Row -->
    <div class="row mt-4">
        <!-- Role-specific lists and charts -->
    </div>
</div>
```

#### 2.2 Reusable Dashboard Components

**Metric Card Component** (`dashboard_metric_card.jsp`):
```jsp
<div class="col-xl-3 col-lg-6 col-md-6 mt-4">
    <div class="card border-0 shadow rounded metric-card">
        <div class="card-body">
            <div class="d-flex justify-content-between align-items-center">
                <div>
                    <h6 class="text-muted mb-2">${param.title}</h6>
                    <h3 class="mb-0">${param.value}</h3>
                    <c:if test="${not empty param.subtitle}">
                        <small class="text-muted">${param.subtitle}</small>
                    </c:if>
                </div>
                <div class="icon-box">
                    <i class="${param.icon} h2 text-primary mb-0"></i>
                </div>
            </div>
        </div>
        <c:if test="${not empty param.link}">
            <div class="card-footer bg-transparent border-top">
                <a href="${param.link}" class="text-primary">
                    View Details <i class="uil uil-arrow-right"></i>
                </a>
            </div>
        </c:if>
    </div>
</div>
```

**Quick Action Button Component**:
```jsp
<a href="${param.link}" class="btn btn-primary btn-lg me-2 mb-2">
    <i class="${param.icon} me-2"></i>${param.label}
</a>
```

#### 2.3 Chart Integration

Use existing ApexCharts library (already in project) for:
- Pie charts (user distribution by role, CV status)
- Bar charts (employee by department, attendance trends)
- Line charts (performance trends)

**Chart Configuration Pattern**:
```javascript
var options = {
    series: [${chartData}],
    chart: {
        type: 'pie',
        height: 350
    },
    labels: [${chartLabels}],
    colors: ['#2f55d4', '#10b981', '#f59e0b', '#ef4444']
};
var chart = new ApexCharts(document.querySelector("#chartDiv"), options);
chart.render();
```

### 3. URL Routing

**New Servlet Mapping**:
```
/dashboard -> DashboardServlet
```

**Dashboard Access URLs**:
- Admin: `/dashboard?role=admin`
- HR Manager: `/dashboard?role=hr_manager`
- HR: `/dashboard?role=hr`
- Dept Manager: `/dashboard?role=dept_manager`
- Employee: `/dashboard?role=employee`

**Update Home Page JSPs**:
Change iframe src from:
```jsp
src="<c:url value='/view/home/content/dashboard_employee.jsp'/>"
```
To:
```jsp
src="<c:url value='/dashboard'/>"
```

## Data Models

### Dashboard DTOs Structure

#### AdminDashboardDTO
```java
public class AdminDashboardDTO {
    private int totalUsers;
    private int totalDepartments;
    private int activeAccounts;
    private int pendingAccounts;
    private List<RecentActivityDTO> recentActivities;
    private Map<String, Integer> userDistributionByRole;
    // getters and setters
}
```

#### HRManagerDashboardDTO
```java
public class HRManagerDashboardDTO {
    private int totalEmployees;
    private int pendingTickets;
    private int pendingJobDescriptions;
    private String payrollStatus;
    private List<PendingApprovalDTO> pendingApprovals;
    private Map<String, Integer> employeesByDepartment;
    private Map<String, Integer> recruitmentPipeline;
    // getters and setters
}
```

#### HRDashboardDTO
```java
public class HRDashboardDTO {
    private int totalEmployees;
    private int pendingTickets;
    private int newCVs;
    private int expiringContracts;
    private List<TicketDTO> recentTickets;
    private List<ContractDTO> upcomingExpirations;
    private Map<String, Double> monthlyAttendanceSummary;
    // getters and setters
}
```

#### DeptManagerDashboardDTO
```java
public class DeptManagerDashboardDTO {
    private int departmentEmployeeCount;
    private int pendingDepartmentTickets;
    private double attendanceRate;
    private double overtimeHours;
    private List<TicketDTO> pendingTickets;
    private Map<String, Integer> teamAttendanceOverview;
    private Map<String, Double> performanceTrends;
    // getters and setters
}
```

#### EmployeeDashboardDTO
```java
public class EmployeeDashboardDTO {
    private int remainingLeaveDays;
    private double attendanceRate;
    private int pendingTickets;
    private double lastPayrollAmount;
    private List<AttendanceDTO> monthlyAttendance;
    private List<TicketDTO> ticketStatus;
    private List<HolidayDTO> upcomingHolidays;
    // getters and setters
}
```

#### Supporting DTOs
```java
public class RecentActivityDTO {
    private String activityType;
    private String description;
    private LocalDateTime timestamp;
    private String performedBy;
}

public class PendingApprovalDTO {
    private int ticketId;
    private String ticketType;
    private String employeeName;
    private LocalDate requestDate;
    private String priority;
}
```

## Error Handling

### Error Scenarios and Handling

1. **Session Validation Failure**
   - Check: User session exists and is valid
   - Action: Redirect to login page
   - Message: "Session expired. Please login again."

2. **Database Connection Failure**
   - Check: DAO operations catch SQLException
   - Action: Display error message on dashboard
   - Message: "Unable to load dashboard data. Please try again."
   - Fallback: Show cached data if available

3. **Insufficient Permissions**
   - Check: User role matches requested dashboard
   - Action: Redirect to appropriate dashboard
   - Message: "Access denied. Redirecting to your dashboard."

4. **Partial Data Load Failure**
   - Check: Individual metric calculation failures
   - Action: Display "N/A" or error icon on specific metric card
   - Message: Tooltip explaining the specific metric is unavailable
   - Behavior: Other metrics continue to display normally

5. **Chart Rendering Failure**
   - Check: Chart data format validation
   - Action: Display fallback table view
   - Message: "Chart unavailable. Showing data in table format."

### Error Handling Pattern

```java
try {
    // Fetch dashboard data
    dashboardData = dashboardService.getXXXDashboardData(userId);
    request.setAttribute("dashboardData", dashboardData);
    request.setAttribute("error", null);
} catch (SQLException e) {
    logger.error("Database error loading dashboard", e);
    request.setAttribute("error", "Unable to load dashboard data");
    request.setAttribute("dashboardData", null);
} catch (Exception e) {
    logger.error("Unexpected error loading dashboard", e);
    request.setAttribute("error", "An unexpected error occurred");
    request.setAttribute("dashboardData", null);
}
```

## Testing Strategy

### Unit Testing

**Service Layer Tests** (`DashboardServiceTest.java`):
- Test each dashboard data retrieval method
- Mock DAO dependencies
- Verify correct data aggregation
- Test error handling scenarios

**DAO Layer Tests**:
- Test new dashboard query methods
- Verify SQL query correctness
- Test with sample data
- Verify performance with large datasets

### Integration Testing

**Servlet Tests** (`DashboardServletTest.java`):
- Test role-based routing
- Verify session validation
- Test request attribute setting
- Verify JSP forwarding

**End-to-End Tests**:
- Test complete user flow from login to dashboard
- Verify data accuracy across all roles
- Test navigation from dashboard to detail pages
- Verify quick action functionality

### Manual Testing Checklist

For each role:
- [ ] Dashboard loads within 3 seconds
- [ ] All metrics display correct values
- [ ] Quick action buttons navigate correctly
- [ ] Charts render properly
- [ ] Lists display appropriate data
- [ ] Error states display gracefully
- [ ] Navigation maintains session
- [ ] Permission checks work correctly

### Performance Testing

**Metrics to Monitor**:
- Dashboard load time (target: < 3 seconds)
- Database query execution time (target: < 500ms per query)
- Memory usage per dashboard load
- Concurrent user handling (target: 100+ simultaneous users)

**Optimization Strategies**:
- Use connection pooling (already in DBContext)
- Implement query result caching for frequently accessed data
- Optimize SQL queries with proper indexing
- Lazy load charts and secondary data
- Implement pagination for large lists

## Implementation Notes

### Phase 1: Backend Foundation
1. Create DTO classes
2. Add dashboard query methods to existing DAOs
3. Create DashboardService class
4. Create DashboardServlet

### Phase 2: Frontend Development
5. Create role-specific dashboard JSP files
6. Implement metric card components
7. Integrate charts
8. Add quick action buttons

### Phase 3: Integration
9. Update home page JSPs to load dashboard servlet
10. Test role-based routing
11. Verify data accuracy

### Phase 4: Polish
12. Add error handling
13. Optimize performance
14. Add loading indicators
15. Final testing

### Database Considerations

**No Schema Changes Required**: All dashboard data can be derived from existing tables using aggregate queries.

**Recommended Indexes** (if not already present):
```sql
CREATE INDEX idx_ticket_status ON Ticket(Status);
CREATE INDEX idx_ticket_approver ON Ticket(ApproverID);
CREATE INDEX idx_attendance_user_date ON Attendance(UserID, Date);
CREATE INDEX idx_contract_end_date ON Contract(EndDate, Status);
CREATE INDEX idx_cv_status ON CVs(Status);
```

### Security Considerations

1. **Role Verification**: Always verify user role matches requested dashboard
2. **Session Validation**: Check session validity before loading any dashboard
3. **SQL Injection Prevention**: Use PreparedStatement for all queries (already implemented)
4. **XSS Prevention**: Escape all user-generated content in JSP (use JSTL c:out)
5. **Authorization**: Verify user has permission to view requested data

### Styling Guidelines

**Color Scheme** (consistent with existing HRMS theme):
- Primary: #2f55d4 (blue)
- Success: #10b981 (green)
- Warning: #f59e0b (orange)
- Danger: #ef4444 (red)
- Muted: #6c757d (gray)

**Card Styling**:
- Border: none
- Shadow: 0 0 20px rgba(0,0,0,0.1)
- Border-radius: 8px
- Padding: 1.5rem

**Typography**:
- Metric values: h3 (2rem)
- Metric labels: h6 (0.875rem, text-muted)
- Section headers: h5 (1.25rem)

**Icons**: Use Unicons (already in project) for consistency
