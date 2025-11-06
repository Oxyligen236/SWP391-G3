# Dashboard Design Document

## Overview

This document outlines the design for role-based dashboards in the HRMS application. Each role (Admin, HR Manager, HR Staff, Department Manager, and Employee) will have a customized dashboard displaying relevant metrics, charts, and quick actions. The dashboards will be built using JSP pages with Bootstrap 5 for styling, ApexCharts for data visualization, and AJAX for dynamic data loading.

### Design Goals

- Provide role-specific information at a glance
- Enable quick access to frequently used features
- Display actionable insights through charts and metrics
- Maintain consistent UI/UX across all dashboards
- Optimize performance with efficient data loading
- Support dashboard customization for user preferences

## Architecture

### High-Level Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                        Browser (Client)                      │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  Dashboard JSP Pages (View Layer)                      │ │
│  │  - homePage_Admin.jsp                                  │ │
│  │  - homePage_HRManager.jsp                              │ │
│  │  - homePage_HR.jsp                                     │ │
│  │  - homePage_DeptManager.jsp                            │ │
│  │  - homePage_employee.jsp                               │ │
│  └────────────────────────────────────────────────────────┘ │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  JavaScript Components                                  │ │
│  │  - dashboard.js (common utilities)                     │ │
│  │  - chart-config.js (ApexCharts configurations)        │ │
│  │  - widget-loader.js (AJAX data loading)               │ │
│  └────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
                            │
                            │ HTTP/AJAX
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                    Application Server                        │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  Dashboard Servlets (Controller Layer)                 │ │
│  │  - DashboardDataServlet.java                           │ │
│  │  - AdminDashboardServlet.java                          │ │
│  │  - HRManagerDashboardServlet.java                      │ │
│  │  - HRDashboardServlet.java                             │ │
│  │  - DeptManagerDashboardServlet.java                    │ │
│  │  - EmployeeDashboardServlet.java                       │ │
│  └────────────────────────────────────────────────────────┘ │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  Dashboard Services (Business Logic Layer)             │ │
│  │  - DashboardService.java                               │ │
│  │  - MetricsCalculator.java                              │ │
│  └────────────────────────────────────────────────────────┘ │
│  ┌────────────────────────────────────────────────────────┐ │
│  │  Existing DAOs (Data Access Layer)                     │ │
│  │  - UserDAO, AttendanceDAO, PayrollDAO, etc.           │ │
│  └────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
                            │
                            │ JDBC
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                      MySQL Database                          │
└─────────────────────────────────────────────────────────────┘
```


### Technology Stack

- **Frontend**: Pure JSP with JSTL, Bootstrap 5, ApexCharts.js (for charts only)
- **Backend**: Java Servlets, existing DAO layer
- **Data Loading**: Server-side rendering with JSP/JSTL (no AJAX)
- **Styling**: Bootstrap 5 grid system, custom CSS
- **Icons**: Feather Icons (already in project)

## Components and Interfaces

### 1. Dashboard Widget Component

Each dashboard widget is a reusable UI component that displays specific information.

#### Widget Structure

```html
<div class="dashboard-widget card">
    <div class="card-header">
        <h5 class="card-title">Widget Title</h5>
        <div class="widget-actions">
            <!-- Optional actions: refresh, settings, etc. -->
        </div>
    </div>
    <div class="card-body">
        <div class="widget-loading" style="display:none;">
            <div class="spinner-border" role="status"></div>
        </div>
        <div class="widget-content">
            <!-- Widget content goes here -->
        </div>
        <div class="widget-error" style="display:none;">
            <p class="text-danger">Failed to load data</p>
        </div>
    </div>
</div>
```

#### Widget Types

1. **Metric Card Widget**: Displays a single numeric metric with icon and label
2. **Chart Widget**: Displays data visualization using ApexCharts
3. **List Widget**: Displays a list of items (tickets, activities, etc.)
4. **Table Widget**: Displays tabular data
5. **Calendar Widget**: Displays calendar view for leaves/events

### 2. Metric Card Component

```html
<div class="metric-card">
    <div class="metric-icon">
        <i data-feather="icon-name"></i>
    </div>
    <div class="metric-content">
        <h3 class="metric-value">0</h3>
        <p class="metric-label">Metric Name</p>
    </div>
    <div class="metric-trend">
        <span class="trend-indicator">↑ 5%</span>
    </div>
</div>
```

### 3. Dashboard Service Interface

```java
public interface DashboardService {
    // Get dashboard data for specific role
    DashboardData getDashboardData(int userId, int roleId);
    
    // Get specific metrics
    Map<String, Object> getMetrics(int userId, int roleId);
    
    // Get chart data
    ChartData getChartData(String chartType, int userId, int roleId);
    
    // Get recent activities
    List<Activity> getRecentActivities(int userId, int roleId, int limit);
}
```

### 4. Dashboard Data Transfer Objects

```java
// Base dashboard data
public class DashboardData {
    private Map<String, Object> metrics;
    private List<ChartData> charts;
    private List<Activity> recentActivities;
    private List<QuickAction> quickActions;
    // getters and setters
}

// Chart data structure
public class ChartData {
    private String chartId;
    private String chartType; // line, bar, pie, donut
    private List<String> labels;
    private List<Series> series;
    // getters and setters
}

// Activity data
public class Activity {
    private String type;
    private String description;
    private LocalDateTime timestamp;
    private String actorName;
    // getters and setters
}
```


## Dashboard Layouts

### Admin Dashboard Layout

```
┌─────────────────────────────────────────────────────────────────┐
│  Header: Welcome Admin | [Profile] [Logout]                     │
├─────────────────────────────────────────────────────────────────┤
│  Quick Actions: [+ Create User] [Manage Accounts] [View Tickets]│
│                 [System Settings]                                │
├──────────────┬──────────────┬──────────────┬────────────────────┤
│ Total Users  │ Departments  │  Positions   │ Active Accounts    │
│    [150]     │     [12]     │     [25]     │      [142]         │
├──────────────┴──────────────┴──────────────┴────────────────────┤
│                                                                   │
│  User Distribution by Role (Pie Chart)                           │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │                                                           │   │
│  │         [Pie Chart showing role distribution]            │   │
│  │                                                           │   │
│  └─────────────────────────────────────────────────────────┘   │
│                                                                   │
├───────────────────────────────────┬───────────────────────────────┤
│ Account Status Distribution       │ Recent System Activities      │
│ ┌───────────────────────────────┐ │ • New user: John Doe          │
│ │  [Donut Chart]                │ │   2 hours ago                 │
│ │  Active: 142                  │ │ • Account deactivated: Jane   │
│ │  Inactive: 8                  │ │   5 hours ago                 │
│ └───────────────────────────────┘ │ • Role changed: Mike Smith    │
│                                    │   1 day ago                   │
│                                    │ [View All Activities]         │
└───────────────────────────────────┴───────────────────────────────┘
```

### HR Manager Dashboard Layout

```
┌─────────────────────────────────────────────────────────────────┐
│  Header: Welcome [HR Manager Name] | [Profile] [Logout]          │
├─────────────────────────────────────────────────────────────────┤
│  Quick Actions: [+ Create JD] [View Payrolls] [Manage Contracts]│
│                 [Attendance Reports]                             │
├──────────────┬──────────────┬──────────────┬────────────────────┤
│ Total        │ Active       │ Pending      │ Open Job           │
│ Employees    │ Contracts    │ Tickets      │ Descriptions       │
│   [150]      │    [145]     │     [12]     │      [5]           │
├──────────────┴──────────────┴──────────────┴────────────────────┤
│                                                                   │
│  Employee Distribution by Department (Bar Chart)                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  [Bar chart showing employee count per department]      │   │
│  └─────────────────────────────────────────────────────────┘   │
│                                                                   │
├───────────────────────────────────┬───────────────────────────────┤
│ Contract Status Distribution      │ Pending Tickets               │
│ ┌───────────────────────────────┐ │ ┌───────────────────────────┐│
│ │  [Donut Chart]                │ │ │ Leave - John Doe          ││
│ │  Active: 145                  │ │ │ 2024-11-10                ││
│ │  Expiring Soon: 8             │ │ ├───────────────────────────┤│
│ │  Expired: 2                   │ │ │ OT - Jane Smith           ││
│ └───────────────────────────────┘ │ │ 2024-11-09                ││
│                                    │ └───────────────────────────┘│
│                                    │ [View All Tickets]            │
├───────────────────────────────────┴───────────────────────────────┤
│ Upcoming Contract Expirations (Next 30 Days)                      │
│ ┌─────────────────────────────────────────────────────────────┐ │
│ │ Employee Name    │ Position      │ End Date    │ Action      │ │
│ │ John Doe         │ Developer     │ 2024-12-01  │ [Renew]     │ │
│ │ Jane Smith       │ Designer      │ 2024-12-15  │ [Renew]     │ │
│ └─────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

### HR Staff Dashboard Layout

```
┌─────────────────────────────────────────────────────────────────┐
│  Header: Welcome [HR Staff Name] | [Profile] [Logout]            │
├─────────────────────────────────────────────────────────────────┤
│  Quick Actions: [Review CVs] [+ Create User] [Manage Tickets]   │
│                 [+ Create Contract]                              │
├──────────────┬──────────────┬──────────────┬────────────────────┤
│ Pending CVs  │ Active       │ Pending      │ Contracts          │
│              │ Employees    │ Tickets      │ Requiring Action   │
│    [25]      │    [150]     │     [8]      │      [3]           │
├──────────────┴──────────────┴──────────────┴────────────────────┤
│                                                                   │
│  Recent CV Submissions                                           │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ Applicant Name  │ Position      │ Date       │ Action   │   │
│  │ John Doe        │ Developer     │ 2024-11-05 │ [Review] │   │
│  │ Jane Smith      │ Designer      │ 2024-11-04 │ [Review] │   │
│  │ Mike Johnson    │ Analyst       │ 2024-11-03 │ [Review] │   │
│  └─────────────────────────────────────────────────────────┘   │
│                                                                   │
├───────────────────────────────────┬───────────────────────────────┤
│ Ticket Distribution by Type       │ Recent Onboarding Activities  │
│ ┌───────────────────────────────┐ │ • User created: John Doe      │
│ │  [Pie Chart]                  │ │   1 day ago                   │
│ │  Leave: 45%                   │ │ • Contract signed: Jane Smith │
│ │  Overtime: 35%                │ │   2 days ago                  │
│ │  Other: 20%                   │ │ • Account activated: Mike     │
│ └───────────────────────────────┘ │   3 days ago                  │
│                                    │ [View All Activities]         │
└───────────────────────────────────┴───────────────────────────────┘
```


### Department Manager Dashboard Layout

```
┌─────────────────────────────────────────────────────────────────┐
│  Header: Welcome [Manager Name] | [Profile] [Logout]             │
├─────────────────────────────────────────────────────────────────┤
│  Quick Actions: [View Dept Attendance] [Approve Tickets]        │
│                 [View Dept Payroll] [Team Profiles]             │
├──────────────┬──────────────┬──────────────┬────────────────────┤
│ Department   │ Pending      │ Attendance   │ Avg OT Hours       │
│ Employees    │ Tickets      │ Rate (Month) │ (Month)            │
│    [25]      │     [5]      │    95.5%     │     12.5           │
├──────────────┴──────────────┴──────────────┴────────────────────┤
│                                                                   │
│  Pending Tickets from Department                                 │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │ Employee     │ Type    │ Date       │ Details  │ Action │   │
│  │ John Doe     │ Leave   │ 2024-11-10 │ 3 days   │[Approve]│  │
│  │ Jane Smith   │ OT      │ 2024-11-09 │ 4 hours  │[Approve]│  │
│  └─────────────────────────────────────────────────────────┘   │
│                                                                   │
├───────────────────────────────────┬───────────────────────────────┤
│ Department Attendance Trend       │ Late/Early Departures         │
│ ┌───────────────────────────────┐ │ This Week:                    │
│ │  [Line Chart - 6 months]      │ │ ┌───────────────────────────┐│
│ │  Shows attendance % over time │ │ │ John Doe - Late (Mon)     ││
│ │                               │ │ │ Jane Smith - Early (Tue)  ││
│ └───────────────────────────────┘ │ │ Mike Johnson - Late (Wed) ││
│                                    │ └───────────────────────────┘│
│                                    │ [View Details]                │
├───────────────────────────────────┴───────────────────────────────┤
│ Department Leave Calendar (Current Month)                         │
│ ┌─────────────────────────────────────────────────────────────┐ │
│ │  Mon  Tue  Wed  Thu  Fri  Sat  Sun                          │ │
│ │   1    2    3    4    5    6    7                           │ │
│ │   8    9   [10]  11   12   13   14   [John on leave]       │ │
│ │  15   16   17   [18] [19] 20   21   [Jane on leave]        │ │
│ └─────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

### Employee Dashboard Layout

```
┌─────────────────────────────────────────────────────────────────┐
│  Header: Welcome [Employee Name] | [Profile] [Logout]            │
├─────────────────────────────────────────────────────────────────┤
│  Quick Actions: [Submit Leave] [Submit OT] [View Attendance]    │
│                 [View Payroll History]                           │
├──────────────┬──────────────┬──────────────┬────────────────────┤
│ Total Work   │ Late         │ Early        │ OT Hours           │
│ Hours (Month)│ Arrivals     │ Departures   │ (Month)            │
│   168.5      │     2        │     1        │     8.5            │
├──────────────┴──────────────┴──────────────┴────────────────────┤
│                                                                   │
│  Current Month Payroll                                           │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  Net Salary: $3,500                                      │   │
│  │  Payment Date: 2024-11-30                                │   │
│  │  Status: Pending                                         │   │
│  │  [View Details]                                          │   │
│  └─────────────────────────────────────────────────────────┘   │
│                                                                   │
├───────────────────────────────────┬───────────────────────────────┤
│ Leave Balance                     │ My Tickets                    │
│ ┌───────────────────────────────┐ │ ┌───────────────────────────┐│
│ │ Annual Leave: 12 days         │ │ │ Leave Request - Approved  ││
│ │ Sick Leave: 5 days            │ │ │ 2024-11-05                ││
│ │ Personal Leave: 3 days        │ │ ├───────────────────────────┤│
│ └───────────────────────────────┘ │ │ OT Request - Pending      ││
│                                    │ │ 2024-11-08                ││
│                                    │ └───────────────────────────┘│
│                                    │ [View All Tickets]            │
├───────────────────────────────────┴───────────────────────────────┤
│ Monthly Attendance Pattern (Last 6 Months)                        │
│ ┌─────────────────────────────────────────────────────────────┐ │
│ │  [Line Chart showing work hours, late arrivals, OT]         │ │
│ └─────────────────────────────────────────────────────────────┘ │
├─────────────────────────────────────────────────────────────────┤
│ Upcoming Schedule                                                 │
│ ┌─────────────────────────────────────────────────────────────┐ │
│ │ • Approved Leave: Nov 15-17 (3 days)                        │ │
│ │ • Scheduled OT: Nov 20 (4 hours)                            │ │
│ └─────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```


## Data Models

### Dashboard Metrics Models

```java
// Admin metrics
public class AdminMetrics {
    private int totalUsers;
    private int totalDepartments;
    private int totalPositions;
    private int activeAccounts;
    private Map<String, Integer> usersByRole;
    private Map<String, Integer> accountsByStatus;
}

// HR Manager metrics
public class HRManagerMetrics {
    private int totalEmployees;
    private int activeContracts;
    private int pendingTickets;
    private int openJobDescriptions;
    private Map<String, Integer> employeesByDepartment;
    private Map<String, Integer> contractsByStatus;
    private List<ContractExpiration> upcomingExpirations;
}

// HR Staff metrics
public class HRStaffMetrics {
    private int pendingCVs;
    private int activeEmployees;
    private int pendingTickets;
    private int contractsRequiringAction;
    private Map<String, Integer> ticketsByType;
    private List<CVSubmission> recentCVs;
}

// Department Manager metrics
public class DeptManagerMetrics {
    private int departmentEmployees;
    private int pendingTickets;
    private double attendanceRate;
    private double avgOTHours;
    private List<Double> attendanceTrend; // 6 months
    private List<AttendanceIssue> lateEarlyDepartures;
    private Map<LocalDate, List<String>> leaveCalendar;
}

// Employee metrics
public class EmployeeMetrics {
    private double totalWorkHours;
    private int lateArrivals;
    private int earlyDepartures;
    private double otHours;
    private PayrollSummary currentPayroll;
    private Map<String, Integer> leaveBalance;
    private List<TicketStatus> myTickets;
    private List<ScheduledEvent> upcomingSchedule;
    private List<Double> attendancePattern; // 6 months
}
```

### Supporting Models

```java
public class ContractExpiration {
    private int userId;
    private String employeeName;
    private String position;
    private LocalDate endDate;
    private int daysRemaining;
}

public class CVSubmission {
    private int cvId;
    private String applicantName;
    private String position;
    private LocalDate submissionDate;
    private String status;
}

public class AttendanceIssue {
    private int userId;
    private String employeeName;
    private LocalDate date;
    private String issueType; // "late" or "early"
    private int minutes;
}

public class PayrollSummary {
    private int payrollId;
    private double netSalary;
    private LocalDate paymentDate;
    private String status;
}

public class TicketStatus {
    private int ticketId;
    private String type;
    private String status;
    private LocalDate submissionDate;
    private String details;
}

public class ScheduledEvent {
    private String type; // "leave" or "overtime"
    private LocalDate date;
    private String details;
}
```


## Data Flow

### Server-Side Rendering Approach

Instead of using AJAX, all dashboard data will be loaded server-side and passed to JSP pages:

1. **User accesses dashboard** → HomeServlet (existing)
2. **HomeServlet determines role** → Forwards to appropriate role-specific servlet
3. **Role-specific servlet**:
   - Calls DashboardService to get all dashboard data
   - Sets data as request attributes
   - Forwards to JSP page
4. **JSP page**:
   - Uses JSTL to render metrics, lists, tables
   - Uses inline JavaScript with JSP variables for chart data
   - No AJAX calls needed

### Example Data Flow

```
User Request → HomeServlet → AdminDashboardServlet → DashboardService → DAOs
                                      ↓
                              Set request attributes:
                              - adminMetrics
                              - userDistribution
                              - accountStatus
                              - recentActivities
                                      ↓
                              Forward to homePage_Admin.jsp
                                      ↓
                              JSP renders with JSTL + inline JS for charts
```


## Error Handling

### Error Response Format

All API endpoints will return errors in a consistent format:

```json
{
    "success": false,
    "error": {
        "code": "ERROR_CODE",
        "message": "Human-readable error message"
    }
}
```

### Error Codes

- `AUTH_REQUIRED`: User not authenticated
- `PERMISSION_DENIED`: User lacks permission for requested data
- `INVALID_PARAMETER`: Invalid request parameter
- `DATA_NOT_FOUND`: Requested data not found
- `SERVER_ERROR`: Internal server error

### Server-Side Error Handling

Errors will be handled in servlets and displayed in JSP:

```java
// In servlet
try {
    DashboardData data = dashboardService.getDashboardData(userId, roleId);
    request.setAttribute("dashboardData", data);
} catch (Exception e) {
    request.setAttribute("error", "Failed to load dashboard data");
    logger.error("Dashboard error for user " + userId, e);
}
request.getRequestDispatcher("/view/home/homePage_Admin.jsp").forward(request, response);
```

```jsp
<!-- In JSP -->
<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>
```

## Testing Strategy

### Unit Testing

Test individual components and services:

1. **DashboardService Tests**
   - Test metric calculations
   - Test data aggregation
   - Test date range filtering

2. **DAO Method Tests**
   - Test new query methods for dashboard data
   - Test data retrieval accuracy

3. **Servlet Tests**
   - Test request handling
   - Test response formatting
   - Test authentication/authorization

### Integration Testing

Test end-to-end flows:

1. **Dashboard Loading Tests**
   - Test complete dashboard load for each role
   - Verify correct data displayed
   - Test error scenarios

2. **API Endpoint Tests**
   - Test all dashboard API endpoints
   - Verify JSON response format
   - Test with different user roles

3. **Chart Rendering Tests**
   - Test chart data formatting
   - Verify chart displays correctly
   - Test with edge cases (empty data, single data point)

### Manual Testing Checklist

For each role dashboard:

- [ ] Dashboard loads without errors
- [ ] All metric cards display correct values
- [ ] All charts render properly
- [ ] Quick action buttons navigate correctly
- [ ] Data refreshes when page is reloaded
- [ ] Loading indicators appear during data fetch
- [ ] Error messages display when data fails to load
- [ ] Dashboard layout is consistent with design
- [ ] Icons display correctly
- [ ] Responsive behavior works on different screen sizes (desktop only)


## Performance Considerations

### Data Caching Strategy

1. **Server-Side Caching**
   - Cache dashboard metrics for 5 minutes
   - Use in-memory cache (e.g., HashMap with timestamp)
   - Invalidate cache on relevant data changes

2. **Client-Side Caching**
   - Store dashboard data in sessionStorage
   - Refresh data on page reload or manual refresh
   - Cache chart configurations

### Query Optimization

1. **Database Queries**
   - Use indexed columns in WHERE clauses
   - Limit result sets with appropriate LIMIT clauses
   - Use aggregate functions (COUNT, SUM, AVG) in database
   - Avoid N+1 query problems

2. **Batch Data Loading**
   - Load all dashboard data in single request when possible
   - Use JOIN operations to reduce query count
   - Implement pagination for large lists

### Frontend Optimization

1. **Lazy Loading**
   - Load charts only when visible
   - Defer loading of below-the-fold widgets

2. **Minimize DOM Operations**
   - Batch DOM updates
   - Use document fragments for multiple insertions

3. **Asset Optimization**
   - Minify JavaScript and CSS
   - Use CDN for libraries (Bootstrap, ApexCharts)
   - Compress images and icons

## Security Considerations

### Authentication & Authorization

1. **Session Validation**
   - Verify user session on every dashboard request
   - Check user role matches requested dashboard
   - Implement session timeout

2. **API Endpoint Security**
   - Validate user authentication for all API calls
   - Verify user has permission to access requested data
   - Implement rate limiting to prevent abuse

### Data Protection

1. **Input Validation**
   - Validate all query parameters
   - Sanitize user inputs
   - Use prepared statements for database queries

2. **Output Encoding**
   - Encode data before rendering in HTML
   - Use JSON.stringify for JavaScript data
   - Prevent XSS attacks

3. **Sensitive Data**
   - Don't expose sensitive information in client-side code
   - Mask salary information appropriately
   - Log access to sensitive data

## Deployment Considerations

### Database Changes

No new tables required. May need to add indexes for performance:

```sql
-- Indexes for dashboard queries
CREATE INDEX idx_attendance_date ON Attendance(Date);
CREATE INDEX idx_attendance_user ON Attendance(UserID);
CREATE INDEX idx_ticket_status ON Ticket(Status);
CREATE INDEX idx_ticket_user ON Ticket(UserID);
CREATE INDEX idx_contract_enddate ON Contract(EndDate);
CREATE INDEX idx_payroll_user_month ON Payroll(UserID, Month, Year);
```

### Configuration

Add dashboard configuration to application properties:

```properties
# Dashboard settings
dashboard.cache.ttl=300000
dashboard.metrics.refresh.interval=60000
dashboard.chart.default.height=300
dashboard.list.default.limit=5
```

### Rollout Plan

1. **Phase 1**: Deploy Admin and Employee dashboards (simplest)
2. **Phase 2**: Deploy HR Manager and HR Staff dashboards
3. **Phase 3**: Deploy Department Manager dashboard
4. **Phase 4**: Add customization features

### Monitoring

Track dashboard performance metrics:

- Dashboard load time
- API response times
- Error rates by endpoint
- User engagement (which widgets are viewed most)


## JSP Implementation Examples

### Example: Admin Dashboard JSP Structure

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - HRMS</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/dashboard.css">
</head>
<body>
    <!-- Include header -->
    <jsp:include page="/view/common/header.jsp" />
    
    <div class="container-fluid">
        <div class="row">
            <!-- Include sidebar -->
            <jsp:include page="/view/common/sidebar.jsp" />
            
            <main class="col-md-10 ms-sm-auto px-md-4">
                <h2 class="mt-4">Admin Dashboard</h2>
                
                <!-- Quick Actions -->
                <div class="quick-actions mb-4">
                    <a href="${pageContext.request.contextPath}/create-user" class="btn btn-primary">
                        <i data-feather="user-plus"></i> Create User
                    </a>
                    <a href="${pageContext.request.contextPath}/view-accounts" class="btn btn-secondary">
                        <i data-feather="users"></i> Manage Accounts
                    </a>
                    <!-- More quick actions -->
                </div>
                
                <!-- Metric Cards -->
                <div class="row mb-4">
                    <div class="col-md-3">
                        <div class="metric-card card">
                            <div class="card-body">
                                <div class="d-flex align-items-center">
                                    <div class="metric-icon">
                                        <i data-feather="users"></i>
                                    </div>
                                    <div class="metric-content ms-3">
                                        <h3 class="metric-value">${adminMetrics.totalUsers}</h3>
                                        <p class="metric-label">Total Users</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- More metric cards -->
                </div>
                
                <!-- Charts -->
                <div class="row mb-4">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-header">
                                <h5>User Distribution by Role</h5>
                            </div>
                            <div class="card-body">
                                <div id="userDistributionChart"></div>
                            </div>
                        </div>
                    </div>
                    <!-- More charts -->
                </div>
                
                <!-- Recent Activities -->
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header">
                                <h5>Recent System Activities</h5>
                            </div>
                            <div class="card-body">
                                <ul class="activity-list">
                                    <c:forEach items="${recentActivities}" var="activity">
                                        <li class="activity-item">
                                            <span class="activity-description">${activity.description}</span>
                                            <span class="activity-time">
                                                <fmt:formatDate value="${activity.timestamp}" pattern="yyyy-MM-dd HH:mm" />
                                            </span>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>
    
    <!-- Scripts -->
    <script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/feather.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/apexcharts.min.js"></script>
    
    <!-- Chart initialization with JSP data -->
    <script>
        // Initialize Feather icons
        feather.replace();
        
        // User Distribution Chart
        var userDistOptions = {
            series: [
                <c:forEach items="${userDistribution}" var="entry" varStatus="status">
                    ${entry.value}<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ],
            chart: {
                type: 'pie',
                height: 300
            },
            labels: [
                <c:forEach items="${userDistribution}" var="entry" varStatus="status">
                    '${entry.key}'<c:if test="${!status.last}">,</c:if>
                </c:forEach>
            ],
            responsive: [{
                breakpoint: 480,
                options: {
                    chart: {
                        width: 200
                    },
                    legend: {
                        position: 'bottom'
                    }
                }
            }]
        };
        
        var userDistChart = new ApexCharts(
            document.querySelector("#userDistributionChart"), 
            userDistOptions
        );
        userDistChart.render();
    </script>
    
    <!-- Include footer -->
    <jsp:include page="/view/common/footer.jsp" />
</body>
</html>
```

### Example: Servlet Data Preparation

```java
@WebServlet("/dashboard/admin")
public class AdminDashboardServlet extends HttpServlet {
    
    private DashboardService dashboardService = new DashboardService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }
        
        Account account = (Account) session.getAttribute("account");
        
        // Verify admin role
        if (account.getRole() != 5) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        
        try {
            // Get all dashboard data
            AdminMetrics metrics = dashboardService.getAdminMetrics();
            Map<String, Integer> userDistribution = dashboardService.getUserDistribution();
            Map<String, Integer> accountStatus = dashboardService.getAccountStatus();
            List<Activity> recentActivities = dashboardService.getRecentActivities(10);
            
            // Set as request attributes
            request.setAttribute("adminMetrics", metrics);
            request.setAttribute("userDistribution", userDistribution);
            request.setAttribute("accountStatus", accountStatus);
            request.setAttribute("recentActivities", recentActivities);
            
            // Forward to JSP
            request.getRequestDispatcher("/view/home/homePage_Admin.jsp")
                   .forward(request, response);
            
        } catch (Exception e) {
            request.setAttribute("error", "Failed to load dashboard data");
            request.getRequestDispatcher("/view/error.jsp").forward(request, response);
        }
    }
}
```

This approach eliminates the need for AJAX and uses pure JSP with JSTL for rendering, while still supporting interactive charts through ApexCharts with server-provided data.
