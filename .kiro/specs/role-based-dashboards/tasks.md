# Implementation Plan

- [ ] 1. Create Dashboard DTO Classes

  - Create DTO package structure for dashboard data transfer objects
  - Implement DTOs for all five role-specific dashboards with proper getters/setters
  - _Requirements: 1.1, 2.1, 3.1, 4.1, 5.1_

- [ ] 1.1 Create base and supporting DTO classes

  - Create `src/main/java/hrms/dto/dashboard/` package
  - Implement `MetricCardDTO` class with title, value, subtitle, icon, and link fields
  - Implement `RecentActivityDTO` class with activityType, description, timestamp, and performedBy fields
  - Implement `PendingApprovalDTO` class with ticketId, ticketType, employeeName, requestDate, and priority fields
  - _Requirements: 1.1, 2.2, 3.3_

- [ ] 1.2 Create role-specific dashboard DTO classes

  - Implement `AdminDashboardDTO` with totalUsers, totalDepartments, activeAccounts, pendingAccounts, recentActivities list, and userDistributionByRole map
  - Implement `HRManagerDashboardDTO` with totalEmployees, pendingTickets, pendingJobDescriptions, payrollStatus, pendingApprovals list, employeesByDepartment map, and recruitmentPipeline map
  - Implement `HRDashboardDTO` with totalEmployees, pendingTickets, newCVs, expiringContracts, recentTickets list, upcomingExpirations list, and monthlyAttendanceSummary map
  - Implement `DeptManagerDashboardDTO` with departmentEmployeeCount, pendingDepartmentTickets, attendanceRate, overtimeHours, pendingTickets list, teamAttendanceOverview map, and performanceTrends map
  - Implement `EmployeeDashboardDTO` with remainingLeaveDays, attendanceRate, pendingTickets, lastPayrollAmount, monthlyAttendance list, ticketStatus list, and upcomingHolidays list
  - _Requirements: 1.1, 2.1, 3.1, 4.1, 5.1_

- [ ] 2. Enhance DAO Classes with Dashboard Query Methods

  - Add dashboard-specific query methods to existing DAO classes for data retrieval
  - Implement efficient SQL queries with proper error handling
  - _Requirements: 1.1, 2.1, 3.1, 4.1, 5.1, 6.1_

- [ ] 2.1 Enhance AccountDAO for admin dashboard

  - Add `getTotalActiveAccounts()` method to count accounts where isActive = true
  - Add `getTotalPendingAccounts()` method to count accounts with pending status
  - Add `getUserCountByRole()` method returning Map<String, Integer> with role names and counts
  - Add `getRecentActivities(int limit)` method returning list of recent account-related activities
  - _Requirements: 1.1, 1.3_

- [ ] 2.2 Enhance UserDAO for employee counts

  - Add `getTotalEmployeeCount()` method to count all active users
  - Add `getEmployeeCountByDepartment()` method returning Map<String, Integer> with department names and employee counts
  - Add `getDepartmentEmployeeCount(int deptId)` method to count employees in specific department
  - _Requirements: 1.1, 2.1, 3.1, 4.1_

- [ ] 2.3 Enhance TicketDAO for ticket metrics

  - Add `getPendingTicketCount()` method to count all tickets with pending status
  - Add `getPendingTicketCountByDepartment(int deptId)` method to count pending tickets for specific department
  - Add `getPendingTicketsForApproval(int approverId, int limit)` method returning list of tickets awaiting approval
  - Add `getRecentTicketsForHR(int limit)` method returning list of recent tickets for HR processing
  - _Requirements: 2.1, 2.3, 3.1, 3.3, 4.1, 4.3_

- [ ] 2.4 Enhance JobDAO and CVsDAO for recruitment metrics

  - Add `getPendingJobDescriptionCount()` method to JobDAO for counting pending job descriptions
  - Add `getNewCVCount()` method to CVsDAO for counting CVs with new status
  - Add `getCVCountByStatus()` method to CVsDAO returning Map<String, Integer> with status counts
  - _Requirements: 2.1, 2.5, 3.1_

- [ ] 2.5 Enhance ContractDAO for contract tracking

  - Add `getExpiringContractsCount(int days)` method to count contracts expiring within specified days
  - Add `getExpiringContracts(int days, int limit)` method returning list of contracts expiring soon
  - _Requirements: 3.1, 3.4_

- [ ] 2.6 Enhance AttendanceDAO for attendance metrics

  - Add `getUserAttendanceRate(int userId, int month, int year)` method calculating attendance percentage
  - Add `getDepartmentAttendanceRate(int deptId, int month, int year)` method calculating department attendance percentage
  - Add `getUserMonthlyAttendance(int userId, int month, int year)` method returning list of attendance records
  - Add `getDepartmentDailyAttendance(int deptId, LocalDate date)` method returning Map<String, Integer> with present, absent, and late counts
  - _Requirements: 3.5, 4.1, 4.4, 5.1, 5.3_

- [ ] 2.7 Enhance OTDetailDAO and PayrollDAO for additional metrics

  - Add `getDepartmentMonthlyOTHours(int deptId, int month, int year)` method to OTDetailDAO for calculating total overtime hours
  - Add `getLatestPayrollByUser(int userId)` method to PayrollDAO returning most recent payroll record
  - Add `getCurrentMonthPayrollStatus()` method to PayrollDAO returning payroll processing status
  - _Requirements: 2.1, 4.1, 5.1_

- [ ] 2.8 Enhance LeaveDetailDAO for leave balance

  - Add `getRemainingLeaveDays(int userId, int year)` method calculating remaining leave days for user
  - _Requirements: 5.1_

- [ ] 3. Create DashboardService Class

  - Implement business logic layer for aggregating dashboard data from multiple DAOs
  - Create methods for each role-specific dashboard data retrieval
  - _Requirements: 1.1, 2.1, 3.1, 4.1, 5.1, 6.1_

- [ ] 3.1 Create DashboardService class structure

  - Create `src/main/java/hrms/service/DashboardService.java` class
  - Initialize DAO dependencies in constructor (AccountDAO, UserDAO, TicketDAO, JobDAO, CVsDAO, ContractDAO, AttendanceDAO, OTDetailDAO, PayrollDAO, LeaveDetailDAO)
  - Implement error handling pattern with try-catch blocks and logging
  - _Requirements: 6.1, 6.3_

- [ ] 3.2 Implement admin dashboard data method

  - Create `getAdminDashboardData()` method returning AdminDashboardDTO
  - Call AccountDAO methods to get total users, active accounts, pending accounts, and user distribution
  - Call UserDAO to get total departments count
  - Call AccountDAO to get recent activities list
  - Populate and return AdminDashboardDTO with all metrics
  - _Requirements: 1.1, 1.2, 1.3, 1.4_

- [ ] 3.3 Implement HR Manager dashboard data method

  - Create `getHRManagerDashboardData()` method returning HRManagerDashboardDTO
  - Call UserDAO to get total employees and employee distribution by department
  - Call TicketDAO to get pending tickets count and pending approvals list
  - Call JobDAO to get pending job descriptions count
  - Call PayrollDAO to get current month payroll status
  - Call CVsDAO to get recruitment pipeline statistics
  - Populate and return HRManagerDashboardDTO with all metrics
  - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5_

- [ ] 3.4 Implement HR dashboard data method

  - Create `getHRDashboardData()` method returning HRDashboardDTO
  - Call UserDAO to get total employees count
  - Call TicketDAO to get pending tickets count and recent tickets list
  - Call CVsDAO to get new CVs count
  - Call ContractDAO to get expiring contracts count and list
  - Call AttendanceDAO to get monthly attendance summary
  - Populate and return HRDashboardDTO with all metrics
  - _Requirements: 3.1, 3.2, 3.3, 3.4, 3.5_

- [ ] 3.5 Implement Department Manager dashboard data method

  - Create `getDeptManagerDashboardData(int deptId)` method returning DeptManagerDashboardDTO
  - Call UserDAO to get department employee count
  - Call TicketDAO to get pending department tickets count and list
  - Call AttendanceDAO to get department attendance rate and daily attendance overview
  - Call OTDetailDAO to get department monthly overtime hours
  - Calculate performance trends from attendance and overtime data
  - Populate and return DeptManagerDashboardDTO with all metrics
  - _Requirements: 4.1, 4.2, 4.3, 4.4, 4.5_

- [ ] 3.6 Implement Employee dashboard data method

  - Create `getEmployeeDashboardData(int userId)` method returning EmployeeDashboardDTO
  - Call LeaveDetailDAO to get remaining leave days
  - Call AttendanceDAO to get user attendance rate and monthly attendance calendar
  - Call TicketDAO to get pending tickets count and ticket status list
  - Call PayrollDAO to get last payroll amount
  - Call HolidayDAO to get upcoming holidays list
  - Populate and return EmployeeDashboardDTO with all metrics
  - _Requirements: 5.1, 5.2, 5.3, 5.4, 5.5_

- [ ] 4. Create DashboardServlet Controller

  - Implement servlet to handle dashboard requests and route to appropriate JSP
  - Add session validation and role-based access control
  - _Requirements: 6.1, 6.2, 7.1, 7.2, 7.3, 7.4, 7.5_

- [ ] 4.1 Create DashboardServlet class structure

  - Create `src/main/java/hrms/controller/DashboardServlet.java` with @WebServlet("/dashboard") annotation
  - Implement doGet method with session validation checking for account in session
  - Extract user account, userId, and roleId from session
  - Implement role-based routing logic using switch statement on roleId
  - Add error handling for invalid sessions redirecting to login page
  - _Requirements: 6.1, 6.2, 7.4_

- [ ] 4.2 Implement role-specific dashboard loading methods

  - Create `loadAdminDashboard(HttpServletRequest request, int userId)` method calling DashboardService.getAdminDashboardData() and setting request attributes
  - Create `loadHRManagerDashboard(HttpServletRequest request, int userId)` method calling DashboardService.getHRManagerDashboardData() and setting request attributes
  - Create `loadHRDashboard(HttpServletRequest request, int userId)` method calling DashboardService.getHRDashboardData() and setting request attributes
  - Create `loadDeptManagerDashboard(HttpServletRequest request, int userId, int deptId)` method calling DashboardService.getDeptManagerDashboardData(deptId) and setting request attributes
  - Create `loadEmployeeDashboard(HttpServletRequest request, int userId)` method calling DashboardService.getEmployeeDashboardData(userId) and setting request attributes
  - _Requirements: 1.1, 2.1, 3.1, 4.1, 5.1_

- [ ] 4.3 Implement JSP forwarding logic

  - Add forward logic to route admin role to `/view/home/content/dashboard_admin.jsp`
  - Add forward logic to route HR Manager role to `/view/home/content/dashboard_hr_manager.jsp`
  - Add forward logic to route HR role to `/view/home/content/dashboard_hr.jsp`
  - Add forward logic to route Department Manager role to `/view/home/content/dashboard_dept_manager.jsp`
  - Add forward logic to route Employee role to `/view/home/content/dashboard_employee.jsp`
  - _Requirements: 1.1, 2.1, 3.1, 4.1, 5.1_

- [ ] 4.4 Add comprehensive error handling

  - Wrap dashboard data loading in try-catch blocks catching SQLException and Exception
  - Set error message as request attribute when exceptions occur
  - Log errors with appropriate severity levels
  - Forward to error page or display error message on dashboard when data loading fails
  - _Requirements: 6.3, 6.4_

- [ ] 5. Create Admin Dashboard JSP

  - Build admin dashboard page with system-wide metrics and management tools
  - Display user statistics, department counts, and activity monitoring
  - _Requirements: 1.1, 1.2, 1.3, 1.4, 1.5_

- [ ] 5.1 Create admin dashboard JSP structure

  - Create `src/main/webapp/view/home/content/dashboard_admin.jsp` file
  - Add JSTL taglib declarations for core and fmt
  - Create container-fluid div with page header showing welcome message with user fullname
  - Add subtitle "System Administration Dashboard"
  - _Requirements: 1.1_


- [ ] 5.2 Implement admin metric cards
  - Create row with 4 metric cards in 3-column grid layout
  - Add Total Users metric card displaying totalUsers from dashboardData with users icon and link to user list
  - Add Total Departments metric card displaying totalDepartments with building icon and link to department management
  - Add Active Accounts metric card displaying activeAccounts with check-circle icon in success color
  - Add Pending Accounts metric card displaying pendingAccounts with clock icon in warning color and link to account management
  - _Requirements: 1.1, 1.5_

- [ ] 5.3 Implement admin quick actions section

  - Create card with "Quick Actions" header
  - Add "Create New User" button with user-plus icon linking to create user page
  - Add "Manage Accounts" button with settings icon linking to account management page
  - Add "Manage Departments" button with briefcase icon linking to department page
  - Add "View System Logs" button with file-text icon linking to logs page
  - _Requirements: 1.2_

- [ ] 5.4 Implement recent activity feed

  - Create card with "Recent Activities" header and table structure
  - Display recentActivities list with columns for activity type, description, timestamp, and performed by
  - Format timestamp using JSTL fmt:formatDate
  - Add "View All" link at card footer
  - Handle empty list case with "No recent activities" message
  - _Requirements: 1.3_

- [ ] 5.5 Implement user distribution chart

  - Create card with "User Distribution by Role" header
  - Add div with id "userDistributionChart" for chart rendering
  - Add script section with ApexCharts configuration for pie chart
  - Convert userDistributionByRole map to JavaScript arrays for series and labels
  - Configure chart with colors matching HRMS theme
  - Initialize and render chart on page load
  - _Requirements: 1.4_

- [ ] 6. Create HR Manager Dashboard JSP

  - Build HR Manager dashboard with HR operations overview and approval queues
  - Display employee metrics, pending approvals, and recruitment pipeline
  - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5_

- [ ] 6.1 Create HR Manager dashboard JSP structure

  - Create `src/main/webapp/view/home/content/dashboard_hr_manager.jsp` file
  - Add JSTL taglib declarations
  - Create container with page header showing welcome message
  - Add subtitle "HR Management Dashboard"
  - _Requirements: 2.1_

- [ ] 6.2 Implement HR Manager metric cards

  - Create row with 4 metric cards
  - Add Total Employees metric card displaying totalEmployees with users icon
  - Add Pending Tickets metric card displaying pendingTickets with alert-circle icon in warning color and link to tickets page
  - Add Pending Job Descriptions metric card displaying pendingJobDescriptions with briefcase icon and link to JD management
  - Add Payroll Status metric card displaying payrollStatus text with dollar-sign icon
  - _Requirements: 2.1, 2.5_

- [ ] 6.3 Implement HR Manager quick actions

  - Create quick actions card
  - Add "Create Job Description" button linking to create JD page
  - Add "View All Tickets" button linking to ticket list page
  - Add "Manage Payroll" button linking to payroll management page
  - Add "Generate Reports" button linking to reports page
  - _Requirements: 2.2_

- [ ] 6.4 Implement pending approvals list

  - Create card with "Pending Approvals" header and table
  - Display pendingApprovals list with columns for ticket type, employee name, request date, and priority
  - Add priority badge with color coding (high=danger, medium=warning, low=info)
  - Add "View Details" action button for each approval
  - Handle empty list with "No pending approvals" message
  - _Requirements: 2.3_

- [ ] 6.5 Implement employee distribution and recruitment charts

  - Create two-column row for charts
  - Add "Employees by Department" bar chart in left column using employeesByDepartment map
  - Add "Recruitment Pipeline" pie chart in right column using recruitmentPipeline map
  - Configure ApexCharts for both charts with appropriate colors and labels
  - _Requirements: 2.4, 2.5_

- [ ] 7. Create HR Dashboard JSP

  - Build HR staff dashboard with daily HR tasks and employee management tools
  - Display ticket queue, contract tracking, and attendance summary
  - _Requirements: 3.1, 3.2, 3.3, 3.4, 3.5_

- [ ] 7.1 Create HR dashboard JSP structure

  - Create `src/main/webapp/view/home/content/dashboard_hr.jsp` file
  - Add JSTL taglib declarations
  - Create container with page header and subtitle "HR Operations Dashboard"
  - _Requirements: 3.1_

- [ ] 7.2 Implement HR metric cards

  - Create row with 4 metric cards
  - Add Total Employees metric card with users icon
  - Add Pending Tickets metric card with alert-circle icon and link to tickets
  - Add New CVs metric card with file-text icon and link to CV management
  - Add Expiring Contracts metric card with calendar icon in warning color and link to contracts
  - _Requirements: 3.1, 3.5_

- [ ] 7.3 Implement HR quick actions

  - Create quick actions card
  - Add "Create User" button linking to create user page
  - Add "Manage Attendance" button linking to attendance management
  - Add "Process Tickets" button linking to ticket processing page
  - Add "View Contracts" button linking to contract list
  - _Requirements: 3.2_

- [ ] 7.4 Implement recent tickets list

  - Create card with "Recent Tickets" header and table
  - Display recentTickets list with columns for ticket ID, type, employee, status, and created date
  - Add status badge with color coding
  - Add "Process" action button for each ticket
  - _Requirements: 3.3_

- [ ] 7.5 Implement contract expirations and attendance summary

  - Create two-column row
  - Add "Upcoming Contract Expirations" card in left column displaying upcomingExpirations list with employee name and expiration date
  - Add "Monthly Attendance Summary" card in right column with bar chart using monthlyAttendanceSummary map
  - _Requirements: 3.4, 3.5_

- [ ] 8. Create Department Manager Dashboard JSP

  - Build Department Manager dashboard with team performance and approval workflows
  - Display department metrics, team attendance, and pending requests
  - _Requirements: 4.1, 4.2, 4.3, 4.4, 4.5_

- [ ] 8.1 Create Department Manager dashboard JSP structure

  - Create `src/main/webapp/view/home/content/dashboard_dept_manager.jsp` file
  - Add JSTL taglib declarations
  - Create container with page header and subtitle "Department Management Dashboard"
  - _Requirements: 4.1_

- [ ] 8.2 Implement Department Manager metric cards

  - Create row with 4 metric cards
  - Add Department Employees metric card displaying departmentEmployeeCount with users icon
  - Add Pending Tickets metric card displaying pendingDepartmentTickets with alert-circle icon
  - Add Attendance Rate metric card displaying attendanceRate as percentage with trending-up icon in success color
  - Add Overtime Hours metric card displaying overtimeHours with clock icon
  - _Requirements: 4.1, 4.5_

- [ ] 8.3 Implement Department Manager quick actions

  - Create quick actions card
  - Add "View Department Attendance" button linking to department attendance page
  - Add "Approve Tickets" button linking to ticket approval page
  - Add "View Team Members" button linking to team list
  - Add "Department Reports" button linking to department reports
  - _Requirements: 4.2_

- [ ] 8.4 Implement pending department tickets list

  - Create card with "Pending Approvals" header and table
  - Display pendingTickets list with columns for ticket ID, employee name, type, request date, and action
  - Add "Approve" and "Reject" action buttons for each ticket
  - Handle empty list with "No pending tickets" message
  - _Requirements: 4.3_

- [ ] 8.5 Implement team attendance and performance charts

  - Create two-column row
  - Add "Today's Team Attendance" card in left column with pie chart using teamAttendanceOverview map showing present, absent, and late counts
  - Add "Department Performance Trends" card in right column with line chart using performanceTrends map
  - _Requirements: 4.4, 4.5_

- [ ] 9. Create Employee Dashboard JSP

  - Build Employee dashboard with personal information and self-service tools
  - Display leave balance, attendance calendar, and payroll information
  - _Requirements: 5.1, 5.2, 5.3, 5.4, 5.5_

- [ ] 9.1 Create Employee dashboard JSP structure

  - Create `src/main/webapp/view/home/content/dashboard_employee.jsp` file replacing existing generic version
  - Add JSTL taglib declarations
  - Create container with page header and subtitle "My Dashboard"
  - _Requirements: 5.1_

- [ ] 9.2 Implement Employee metric cards

  - Create row with 4 metric cards
  - Add Remaining Leave Days metric card displaying remainingLeaveDays with calendar icon in info color
  - Add Attendance Rate metric card displaying attendanceRate as percentage with trending-up icon
  - Add Pending Requests metric card displaying pendingTickets with clock icon
  - Add Last Payroll metric card displaying lastPayrollAmount formatted as currency with dollar-sign icon
  - _Requirements: 5.1, 5.5_

- [ ] 9.3 Implement Employee quick actions

  - Create quick actions card
  - Add "Submit Leave Request" button linking to leave request form
  - Add "Submit Overtime Request" button linking to overtime request form
  - Add "View Attendance History" button linking to personal attendance page
  - Add "View Payroll Details" button linking to personal payroll page
  - _Requirements: 5.2_

- [ ] 9.4 Implement personal attendance calendar

  - Create card with "My Attendance This Month" header
  - Display monthlyAttendance list in calendar or table format showing date, check-in time, check-out time, and total hours
  - Add color coding for late arrivals (warning) and absences (danger)
  - Format times using JSTL fmt:formatDate
  - _Requirements: 5.3_

- [ ] 9.5 Implement ticket status and holidays

  - Create two-column row
  - Add "My Requests Status" card in left column displaying ticketStatus list with ticket type, submission date, and status badge
  - Add "Upcoming Holidays" card in right column displaying upcomingHolidays list with holiday name and date
  - _Requirements: 5.4, 5.5_

- [ ] 10. Update Home Page JSPs to Load Dashboard Servlet

  - Modify existing home page JSPs to load dashboard servlet instead of static content
  - Ensure proper iframe integration with dashboard servlet
  - _Requirements: 1.1, 2.1, 3.1, 4.1, 5.1_

- [ ] 10.1 Update all role-specific home page JSPs


  - Modify `homePage_Admin.jsp` iframe src to `<c:url value='/dashboard'/>`
  - Modify `homePage_HRManager.jsp` iframe src to `<c:url value='/dashboard'/>`
  - Modify `homePage_HR.jsp` iframe src to `<c:url value='/dashboard'/>`
  - Modify `homePage_DeptManager.jsp` iframe src to `<c:url value='/dashboard'/>`
  - Modify `homePage_employee.jsp` iframe src to `<c:url value='/dashboard'/>`
  - _Requirements: 1.1, 2.1, 3.1, 4.1, 5.1_

- [ ] 11. Add Dashboard Styling and CSS

  - Create custom CSS for dashboard components ensuring consistent styling
  - Add hover effects and transitions for interactive elements
  - _Requirements: 1.1, 2.1, 3.1, 4.1, 5.1_

- [ ] 11.1 Create dashboard CSS file

  - Create `src/main/webapp/assets/css/dashboard.css` file
  - Add metric card styles with shadow, border-radius, and padding
  - Add hover effects for metric cards with transform scale and shadow increase
  - Add icon-box styles for metric card icons with background color and border-radius
  - Add quick action button styles with spacing and hover effects
  - Add table styles for dashboard lists with striped rows and hover effects
  - Add chart container styles with proper height and padding
  - Add responsive grid styles for metric cards
  - _Requirements: 1.1, 2.1, 3.1, 4.1, 5.1_

- [ ] 11.2 Include dashboard CSS in JSP pages

  - Add link tag to dashboard.css in all dashboard JSP files
  - Verify CSS loads correctly in browser
  - _Requirements: 1.1, 2.1, 3.1, 4.1, 5.1_

- [ ] 12. Implement Error Handling and Loading States

  - Add error message display for failed data loads
  - Implement loading indicators while dashboard data is being fetched
  - _Requirements: 6.3, 6.4_

- [ ] 12.1 Add error handling to dashboard JSPs

  - Add conditional check for error attribute in all dashboard JSPs
  - Display error alert with danger styling when error exists
  - Show "Unable to load dashboard data" message with retry button
  - Add conditional rendering to hide dashboard content when error occurs
  - _Requirements: 6.3, 6.4_

- [ ] 12.2 Add loading indicators

  - Add loading spinner div in all dashboard JSPs
  - Implement JavaScript to show spinner on page load
  - Hide spinner after dashboard content renders
  - Add CSS for spinner animation
  - _Requirements: 6.4_

- [ ] 13. Integration Testing and Bug Fixes


  - Test complete user flow from login to dashboard for all roles
  - Verify data accuracy and navigation functionality
  - Fix any bugs discovered during testing
  - _Requirements: 1.1, 2.1, 3.1, 4.1, 5.1, 6.1, 6.2, 7.1, 7.2, 7.3, 7.4, 7.5_

- [ ] 13.1 Test Admin dashboard
  - Login as Admin user and verify dashboard loads
  - Verify all metric cards display correct values
  - Test quick action buttons navigate to correct pages
  - Verify recent activities list displays properly
  - Test user distribution chart renders correctly
  - _Requirements: 1.1, 1.2, 1.3, 1.4, 1.5_

- [ ] 13.2 Test HR Manager dashboard
  - Login as HR Manager and verify dashboard loads
  - Verify all metrics display correctly
  - Test pending approvals list functionality
  - Verify charts render properly
  - Test navigation from dashboard elements
  - _Requirements: 2.1, 2.2, 2.3, 2.4, 2.5_

- [ ] 13.3 Test HR dashboard
  - Login as HR staff and verify dashboard loads
  - Verify ticket queue displays correctly
  - Test contract expiration list
  - Verify attendance summary chart
  - Test all quick actions
  - _Requirements: 3.1, 3.2, 3.3, 3.4, 3.5_

- [ ] 13.4 Test Department Manager dashboard
  - Login as Department Manager and verify dashboard loads
  - Verify department-specific metrics are accurate
  - Test pending ticket approvals functionality
  - Verify team attendance overview
  - Test performance trend charts
  - _Requirements: 4.1, 4.2, 4.3, 4.4, 4.5_

- [ ] 13.5 Test Employee dashboard
  - Login as Employee and verify dashboard loads
  - Verify personal metrics display correctly
  - Test attendance calendar rendering
  - Verify ticket status list
  - Test all self-service quick actions
  - _Requirements: 5.1, 5.2, 5.3, 5.4, 5.5_

- [ ] 13.6 Test error scenarios
  - Test dashboard behavior with database connection failure
  - Verify error messages display correctly
  - Test session expiration handling
  - Verify partial data load failures are handled gracefully
  - _Requirements: 6.3, 6.4_

- [ ] 13.7 Test navigation and permissions
  - Verify metric card clicks navigate to correct pages
  - Test quick action button navigation
  - Verify session is maintained during navigation
  - Test that users cannot access dashboards for other roles
  - _Requirements: 7.1, 7.2, 7.3, 7.4, 7.5_
