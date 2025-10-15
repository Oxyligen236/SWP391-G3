<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Home page</title>
    <link rel="stylesheet" href="<c:url value='/css/home.css'/>" />
    <script src="" defer></script>
  </head>
  <body>
    <header>

      <div class="header-top">
        <div class="logo">
          <a href="<c:url value='/view/home/homepage_quest.jsp'/>" title="Trang chủ">
            <img src="<c:url value='/picture/hrms-removebg-preview.png' />" 
                 alt="Logo" 
                 width="125" 
                 height="55" />
          </a>
        </div>

        <div class="welcome-text">
          WELCOME to our Company
        </div>

        <div class="login-btn">
          <a href="<c:url value='/authenticate'/>">Log in</a>
        </div>
      </div>

      <nav>
        <ul>
          <li><a href="<c:url value='/view/home/homepage_quest.jsp'/>">Trang chủ</a></li>
          <li><a href="#">Thông tin tuyển dụng</a></li>
          <li><a href="#">Thông tin công ty</a></li>
        </ul>
      </nav>
    </header>

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
  </body>
</html>
