<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url value="/user/list" var="userListUrl" />

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Truy cập bị từ chối</title>
    <link rel="stylesheet" href="<c:url value='/css/access-denied.css'/>">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            text-align: center;
            background-color: #fff;
            padding: 40px 60px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        }
        .container h1 {
            font-size: 48px;
            color: #dc3545;
            margin-bottom: 20px;
        }
        .container p {
            font-size: 18px;
            margin-bottom: 30px;
        }
        .btn-back {
            display: inline-block;
            padding: 12px 25px;
            background-color: #007bff;
            color: #fff;
            border-radius: 6px;
            text-decoration: none;
            font-weight: bold;
        }
        .btn-back:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1><i class="fas fa-ban"></i> Bị từ chối</h1>
        <p>Bạn không có quyền truy cập trang này hoặc thực hiện hành động này.</p>
        <a href="<c:url value='/home'/>" class="btn-back"><i class="fas fa-arrow-left"></i> Quay lại</a>
    </div>

</body>
</html>
