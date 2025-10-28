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
                <a href="<c:url value='/manager/dashboard'/>">
                    <i class="uil uil-dashboard me-2 d-inline-block"></i>Dashboard
                </a>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-users-alt me-2 d-inline-block"></i>Team</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/teamMembers'/>">Team Members</a></li>
                        <li><a href="<c:url value='/teamPerformance'/>">Performance</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-file-check-alt me-2 d-inline-block"></i>Attendance</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/deptAttendance'/>">Department Attendance</a></li>
                        <li><a href="<c:url value='/approveLeave'/>">Approve Leave</a></li>
                        <li><a href="<c:url value='/myAttendance'/>">My Attendance</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-ticket me-2 d-inline-block"></i>Tickets</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/deptTickets'/>">Department Tickets</a></li>
                        <li><a href="<c:url value='/approveTickets'/>">Approve Tickets</a></li>
                        <li><a href="<c:url value='/myTicket'/>">My Tickets</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-chart-bar me-2 d-inline-block"></i>Reports</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/deptReport'/>">Department Report</a></li>
                        <li><a href="<c:url value='/teamReport'/>">Team Report</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-user me-2 d-inline-block"></i>Profile</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/profile'/>">View Profile</a></li>
                        <li><a href="<c:url value='/editProfile'/>">Edit Profile</a></li>
                    </ul>
                </div>
            </li>
        </ul>
        <!-- sidebar-menu  -->
    </div>
</nav>
<!-- sidebar-wrapper  -->
