<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
      <meta charset="utf-8" />
      <title>${pageTitle != null ? pageTitle : 'HRMS - Human Resource Management System'}</title>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <meta name="description" content="HRMS Admin Dashboard" />
      <meta name="keywords" content="HR, Management, System, Dashboard" />
      <meta name="author" content="Group 3" />
      <meta name="Version" content="v1.0.0" />
      <!-- favicon -->
      <link rel="shortcut icon" href="<c:url value='/assets/img/favicon.ico.png'/>">
      <!-- Bootstrap -->
      <link href="<c:url value='/assets/img/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css" />
      <!-- simplebar -->
      <link href="<c:url value='/assets/img/css/simplebar.css'/>" rel="stylesheet" type="text/css" />
      <!-- Select2 -->
      <link href="<c:url value='/assets/img/css/select2.min.css'/>" rel="stylesheet" />
      <!-- Icons -->
      <!-- Material Design Icons from CDN -->
      <link href="https://cdn.jsdelivr.net/npm/@mdi/font@7.4.47/css/materialdesignicons.min.css" rel="stylesheet" type="text/css" />
      <link href="<c:url value='/assets/img/css/remixicon.css'/>" rel="stylesheet" type="text/css" />
      <link href="https://unicons.iconscout.com/release/v3.0.6/css/line.css" rel="stylesheet">
      <!-- SLIDER -->
      <link href="<c:url value='/assets/img/css/tiny-slider.css'/>" rel="stylesheet" />
      <!-- Css -->
      <link href="<c:url value='/assets/img/css/style.min.css'/>" rel="stylesheet" type="text/css" id="theme-opt" />

      <!-- Additional CSS từ page -->
      <c:if test="${not empty additionalCSS}">
        ${additionalCSS}
      </c:if>
      <style>
        /* Topbar luôn cố định trên đầu */
        #topnav,
        .topbar,
        .navbar {
          position: fixed;
          top: 0;
          left: 0;
          right: 0;
          z-index: 1000;
          background: #fff;
          height: 80px;
          /* Đặt chiều cao thật sự của topbar (đo bằng F12 nếu cần) */
        }

        /* Phần main (chứa iframe) phải đẩy xuống sau topbar */
        .page-content {
          margin-top: 80px;
          /* Cùng giá trị với height của topbar */
          overflow: hidden;
          background: #f8f9fa;
        }

        /* Iframe hiển thị toàn màn hình bên dưới topbar */
        #mainContentFrame {
          width: 100%;
          height: calc(100vh - 80px);
          /* Trừ đúng chiều cao topbar */
          border: none;
          display: block;
        }
      </style>

    </head>

    <body>