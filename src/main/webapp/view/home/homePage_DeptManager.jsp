<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Home page for Dept Manager</title>
    <link rel="stylesheet" href="<c:url value='/css/home.css'/>" />
    <script src="" defer></script>
  </head>
  
  <body>
    <header>
      <div class="header-top">
        <button class="avatar-btn" onclick="toggleSidebar()">
          <img src="https://ui-avatars.com/api/?name=${users.fullname}&size=120&background=ff80ab&color=fff&bold=true" 
               alt="Avatar" 
               class="avatar" 
               onerror="this.src='<c:url value='/picture/avatar-mac-dinh-facebook-11-removebg-preview.png'/>'" />
        </button>
        <div class="profile-header">
            <h2>WELCOME back, ${users.fullname}</h2>
        </div>

        <div class="login-btn">
          <a href="<c:url value='/view/home/homepage_guest.jsp'/>">Log out</a>
        </div>
      </div>
    </header>
    
    <nav>
      <ul>
        <li><a href="<c:url value='/home'/>">Trang chủ</a></li>
        <li><a href="<c:url value='/jd-list'/>">Thông tin tuyển dụng</a></li>
        <li><a href="#">Thông tin công ty</a></li>
      </ul>
    </nav>

    <div id="sidebar" class="sidebar">
      <div class="sidebar-header">
        <img src="https://ui-avatars.com/api/?name=${users.fullname}&size=40&background=00aaff&color=fff&bold=true" 
             alt="Avatar" 
             onerror="this.src='<c:url value='/picture/avatar-mac-dinh-facebook-11-removebg-preview.png'/>'" />
        <h3>${users.fullname}</h3>
      </div>
      <ul>
        <li><a href="<c:url value='/home'/>">Trang chủ</a></li>
        <li>
          <a href="#" onclick="toggleSubmenu('profile-submenu')">Profile</a>
          <ul class="submenu" id="profile-submenu">
            <li><a href="<c:url value='/view'/>">View Profile</a></li>
            <li><a href="<c:url value='/edit'/>">Edit Profile</a></li>
            <li><a href="#">View feedback</a></li>
          </ul>
        </li>
        <li>
          <a href="#" onclick="toggleSubmenu('contract-submenu')">Contract</a>
          <ul class="submenu" id="contract-submenu">
            <li><a href="<c:url value='/myContracts'/>">My Contract</a></li>
          </ul>
        </li>
        <li>
          <a href="#" onclick="toggleSubmenu('cv-submenu')">CV</a>
          <ul class="submenu" id="cv-submenu">
            <li><a href="#">My CV</a></li>
            <li><a href="<c:url value='/cv-submit'/>">Submit CV</a></li>
            <li><a href="<c:url value='/cv'/>">View CV List</a></li>
          </ul>
        </li>
        <li>
          <a href="#" onclick="toggleSubmenu('attendance-submenu')">Attendance</a>
          <ul class="submenu" id="attendance-submenu">
            <li><a href="<c:url value='/my-attendance'/>">My Attendance</a></li>
          </ul>
        </li>
        <li>
          <a href="#" onclick="toggleSubmenu('payroll-submenu')">Payroll</a>
          <ul class="submenu" id="payroll-submenu">
            <li><a href="<c:url value='/payroll/personal'/>">Personal Payroll</a></li>
          </ul>
        </li>
        <li>
          <a href="#" onclick="toggleSubmenu('ticket-submenu')">Ticket</a>
          <ul class="submenu" id="ticket-submenu">
            <li><a href="<c:url value='/ticketList'/>">My Ticket</a></li>
            <li><a href="#">View Sent Tickets</a></li>
            <li><a href="<c:url value='/department-ticket'/>">View Employee Tickets</a></li>
          </ul>
        </li>
        <li>
          <a href="#" onclick="toggleSubmenu('attendance-submenu')">Attendance</a>
          <ul class="submenu" id="attendance-submenu">
            <li><a href="<c:url value='/myattendance'/>">My Attendance</a></li>
          </ul>
        </li>
      </ul>
      <button class="close-btn" onclick="toggleSidebar()">&larr;</button>
    </div>

    <main id="main-content">
      <c:choose>
        <c:when test="${not empty page}">
          <jsp:include page="${page}" />
        </c:when>
        <c:otherwise>
          <div class="content-placeholder">
          </div>
        </c:otherwise>
      </c:choose>
    </main>

    <footer>
      <div class="footer-container">
        <div class="footer-left">
          <h3>LIÊN HỆ</h3>
          <ul>
            <li><a href="#">Contact</a></li>
            <li><a href="#">Phone</a></li>
            <li><a href="#">Email</a></li>
          </ul>
        </div>
        <div class="footer-right">
          <p>
            Thiết kế bởi nhóm 3 - 2025 <br>
            Trưởng nhóm: Nguyễn Văn A <br>
            Thành viên: Trần Thị B, Lê Văn C <br>
            Giảng viên hướng dẫn: ThS. Phạm Thị D <br>
            Địa chỉ: 123 Đường ABC, Quận 1, TP.HCM <br>
            Số điện thoại hỗ trợ: (0123) 456-789 <br>
            Bản quyền thuộc về Nhóm 3. Mọi quyền được bảo lưu.
          </p>
        </div>
      </div>
    </footer>

    <script>
      function toggleSidebar() { 
        const sidebar = document.getElementById('sidebar'); 
        sidebar.classList.toggle('active'); 
      }

      function toggleSubmenu(submenuId) {
        event.preventDefault();
        const submenu = document.getElementById(submenuId);
        if (submenu) {
          const isVisible = submenu.style.display === 'block';
          submenu.style.display = isVisible ? 'none' : 'block';
        }
      }
    </script>
  </body>
</html>