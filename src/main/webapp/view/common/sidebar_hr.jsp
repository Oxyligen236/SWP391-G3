<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<nav id="sidebar" class="sidebar-wrapper">
    <div class="sidebar-content" data-simplebar style="height: calc(100% - 60px);">
        <div class="sidebar-brand">
            <a href="<c:url value='/view/home/homePage_guest.jsp'/>">
                <img src="<c:url value='/picture/hrms-removebg-preview.png'/>" height="24" class="logo-light-mode" alt="HRMS"> HRMS
            </a>
        </div>
        
        <ul class="sidebar-menu pt-3">
            <li>
                <a href="<c:url value='/hr/dashboard'/>">
                    <i class="uil uil-dashboard me-2 d-inline-block"></i>Dashboard
                </a>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-users-alt me-2 d-inline-block"></i>Employees</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/employeeList'/>">Employee List</a></li>
                        <li><a href="<c:url value='/createEmployee'/>">Add Employee</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-file-contract me-2 d-inline-block"></i>Contracts</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/contractList'/>">All Contracts</a></li>
                        <li><a href="<c:url value='/createContract'/>">Create Contract</a></li>
                        <li><a href="<c:url value='/expiring-contracts'/>">Expiring Soon</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-briefcase me-2 d-inline-block"></i>Recruitment</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/jdList'/>">Job Descriptions</a></li>
                        <li><a href="<c:url value='/createJd'/>">Create JD</a></li>
                        <li><a href="<c:url value='/cvList'/>">Submitted CVs</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-file-check-alt me-2 d-inline-block"></i>Attendance</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/companyAttendance'/>">Company Attendance</a></li>
                        <li><a href="<c:url value='/updateAttendance'/>">Update Attendance</a></li>
                        <li><a href="<c:url value='/shift'/>">Manage Shifts</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-usd-circle me-2 d-inline-block"></i>Payroll</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/companyPayroll'/>">Company Payroll</a></li>
                        <li><a href="<c:url value='/processPayroll'/>">Process Payroll</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-ticket me-2 d-inline-block"></i>Tickets</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/allTickets'/>">All Tickets</a></li>
                        <li><a href="<c:url value='/pendingTickets'/>">Pending Tickets</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-chart-line me-2 d-inline-block"></i>Reports</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/reports/hr-attendance'/>">Attendance Report</a></li>
                        <li><a href="<c:url value='/reports/hr-payroll'/>">Payroll Report</a></li>
                    </ul>
                </div>
            </li>
        </ul>
        <!-- sidebar-menu  -->
    </div>
</nav>
<!-- sidebar-wrapper  -->
