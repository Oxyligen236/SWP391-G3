<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

    <nav id="sidebar" class="sidebar-wrapper">
      <div class="sidebar-content" data-simplebar style="height: calc(100% - 60px)">
        <div class="sidebar-brand">
          <a href="javascript:void(0)"
            onclick='loadInFrame("<c:url value="/view/home/content/dashboard_employee.jsp"/>")'>
            <img src="<c:url value='/picture/hrms-removebg-preview.png'/>" height="24" class="logo-light-mode"
              alt="HRMS" />
            HRMS
          </a>
        </div>

        <ul class="sidebar-menu pt-3">
          <!-- PROFILE -->
          <li class="sidebar-dropdown">
            <a href="javascript:void(0)"><i class="uil uil-user me-2 d-inline-block"></i>Profile</a>
            <div class="sidebar-submenu">
              <ul>
                <li>
                  <a href="<c:url value='/view'/>" target="mainFrame">View Profile</a>
                </li>
                <li>
                  <a href="<c:url value='/edit'/>" target="mainFrame">Edit Profile</a>
                </li>
              </ul>
            </div>
          </li>

          <!-- USER MANAGEMENT -->
          <li class="sidebar-dropdown">
            <a href="javascript:void(0)"><i class="uil uil-users-alt me-2 d-inline-block"></i>User Management</a>
            <div class="sidebar-submenu">
              <ul>
                <li>
                  <a href="<c:url value='/userlist'/>" target="mainFrame">User List</a>
                </li>
                <li>
                  <a href="<c:url value='/user/create'/>" target="mainFrame">Create User</a>
                </li>
              </ul>
            </div>
          </li>

          <!-- CONTRACT -->
          <li class="sidebar-dropdown">
            <a href="javascript:void(0)"><i class="uil uil-file-contract me-2 d-inline-block"></i>Contract</a>
            <div class="sidebar-submenu">
              <ul>
                <li>
                  <a href="<c:url value='/myContracts'/>" target="mainFrame">My Contract</a>
                </li>
                <li>
                  <a href="<c:url value='/addContracts'/>" target="mainFrame">Create Contract</a>
                </li>
                <li>
                  <a href="<c:url value='/viewContracts'/>" target="mainFrame">View Contract List</a>
                </li>
              </ul>
            </div>
          </li>

          <!-- CV -->
          <li class="sidebar-dropdown">
            <a href="javascript:void(0)"><i class="uil uil-file-alt me-2 d-inline-block"></i>CV</a>
            <div class="sidebar-submenu">
              <ul>
                <li>
                  <a href="<c:url value='/cv'/>" target="mainFrame">View CV List</a>
                </li>
              </ul>
            </div>
          </li>

          <!-- JOB DESCRIPTION -->
          <li class="sidebar-dropdown">
            <a href="javascript:void(0)"><i class="uil uil-briefcase me-2 d-inline-block"></i>Job Description</a>
            <div class="sidebar-submenu">
              <ul>
                <li>
                  <a href="<c:url value='/jdlist'/>" target="mainFrame">JD List</a>
                </li>
              </ul>
            </div>
          </li>

          <!-- PAYROLL -->
          <li class="sidebar-dropdown">
            <a href="javascript:void(0)"><i class="uil uil-money-bill me-2 d-inline-block"></i>Payroll</a>
            <div class="sidebar-submenu">
              <ul>
                <li>
                  <a href="<c:url value='/payroll/personal'/>" target="mainFrame">Personal Payroll</a>
                </li>
              </ul>
            </div>
          </li>

          <!-- TICKET -->
          <li class="sidebar-dropdown">
            <a href="javascript:void(0)"><i class="uil uil-ticket me-2 d-inline-block"></i>Ticket</a>
            <div class="sidebar-submenu">
              <ul>
                <li>
                  <a href="<c:url value='/ticketList'/>" target="mainFrame">My Ticket</a>
                </li>
                <li>
                  <a href="<c:url value='/create-ticket'/>" target="mainFrame">Create Ticket</a>
                </li>
              </ul>
            </div>
          </li>

          <!-- ATTENDANCE -->
          <li class="sidebar-dropdown">
            <a href="javascript:void(0)"><i class="uil uil-calendar-alt me-2 d-inline-block"></i>Attendance</a>
            <div class="sidebar-submenu">
              <ul>
                <li>
                  <a href="<c:url value='/myattendance'/>" target="mainFrame">My Attendance</a>
                </li>
                <li>
                  <a href="<c:url value='/company-attendance'/>" target="mainFrame">Company Attendance</a>
                </li>
              </ul>
            </div>
          </li>

          <!-- SHIFT -->
          <li class="sidebar-dropdown">
            <a href="javascript:void(0)"><i class="uil uil-clock me-2 d-inline-block"></i>Shift</a>
            <div class="sidebar-submenu">
              <ul>
                <li>
                  <a href="<c:url value='/shift'/>" target="mainFrame">Shift Management</a>
                </li>
              </ul>
            </div>
          </li>
        </ul>
      </div>
    </nav>

    <!-- JS helper -->
    <script>
      // Function giúp load trang vào iframe theo đường dẫn
      function loadInFrame(url) {
        const frame = document.getElementById("mainFrame");
        if (frame) frame.src = url;
      }
    </script>