<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đổi Mật Khẩu Thành Công</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { display: flex; justify-content: center; align-items: center; min-height: 100vh; background: #f0f2f5; }
        .box { background: white; padding: 40px; border-radius: 15px; text-align: center; max-width: 500px; box-shadow: 0 10px 40px rgba(0,0,0,0.2); }
    </style>
</head>
<body>
<div class="box">
    <h2 class="text-success">✅ Thành Công!</h2>
    <p><c:out value="Changge password successfully"/></p>
    <a href="<c:url value='/authenticate'/>" class="btn btn-primary">Đăng nhập lại</a>
</div>
</body>
</html>
