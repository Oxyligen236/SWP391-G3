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
                <a href="<c:url value='/admin/dashboard'/>">
                    <i class="uil uil-dashboard me-2 d-inline-block"></i>Dashboard
                </a>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-users-alt me-2 d-inline-block"></i>User Management</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/userList'/>">User List</a></li>
                        <li><a href="<c:url value='/createUser'/>">Create User</a></li>
                        <li><a href="<c:url value='/userRoles'/>">Manage Roles</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-building me-2 d-inline-block"></i>Department</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/departmentList'/>">Department List</a></li>
                        <li><a href="<c:url value='/createDepartment'/>">Create Department</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-briefcase-alt me-2 d-inline-block"></i>Position</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/positionList'/>">Position List</a></li>
                        <li><a href="<c:url value='/createPosition'/>">Create Position</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-file-alt me-2 d-inline-block"></i>System</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/systemSettings'/>">Settings</a></li>
                        <li><a href="<c:url value='/systemLogs'/>">System Logs</a></li>
                        <li><a href="<c:url value='/backup'/>">Backup</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-chart-bar me-2 d-inline-block"></i>Reports</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/reports/users'/>">User Reports</a></li>
                        <li><a href="<c:url value='/reports/attendance'/>">Attendance Reports</a></li>
                        <li><a href="<c:url value='/reports/payroll'/>">Payroll Reports</a></li>
                    </ul>
                </div>
            </li>
        </ul>
        <!-- sidebar-menu  -->
    </div>
</nav>
<!-- sidebar-wrapper  -->
