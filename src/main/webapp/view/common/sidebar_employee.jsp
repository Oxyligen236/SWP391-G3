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
            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-user me-2 d-inline-block"></i>Profile</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/view'/>">View profile</a></li>
                        <li><a href="<c:url value='/edit'/>">Edit Profile</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-wheelchair me-2 d-inline-block"></i>Contract</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/myContracts'/>">My Contract</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-apps me-2 d-inline-block"></i>CV</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/cvSubmit'/>">Submit CV</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-shopping-cart me-2 d-inline-block"></i>Payroll</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/personalPayroll'/>">Personal Payroll</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-flip-h me-2 d-inline-block"></i>Ticket</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/myTicket'/>">My ticket</a></li>
                        <li><a href="<c:url value='/createTicket'/>">Create ticket</a></li>
                    </ul>
                </div>
            </li>

            <li class="sidebar-dropdown">
                <a href="javascript:void(0)"><i class="uil uil-file me-2 d-inline-block"></i>Attendance</a>
                <div class="sidebar-submenu">
                    <ul>
                        <li><a href="<c:url value='/myAttendance'/>">My attendance</a></li>
                    </ul>
                </div>
            </li>
        </ul>
        <!-- sidebar-menu  -->
    </div>
</nav>
<!-- sidebar-wrapper  -->
