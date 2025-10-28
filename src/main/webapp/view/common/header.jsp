<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
    <link href="<c:url value='/assets/img/css/materialdesignicons.min.css'/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value='/assets/img/css/remixicon.css'/>" rel="stylesheet" type="text/css" />
    <link href="https://unicons.iconscout.com/release/v3.0.6/css/line.css"  rel="stylesheet">
    <!-- SLIDER -->
    <link href="<c:url value='/assets/img/css/tiny-slider.css'/>" rel="stylesheet" />
    <!-- Css -->
    <link href="<c:url value='/assets/img/css/style.min.css'/>" rel="stylesheet" type="text/css" id="theme-opt" />
    
    <!-- Additional CSS từ page -->
    <c:if test="${not empty additionalCSS}">
        ${additionalCSS}
    </c:if>
</head>
<body>
