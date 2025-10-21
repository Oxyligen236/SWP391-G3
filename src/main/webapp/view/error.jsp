<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Access Denied</title>
    <link rel="stylesheet" href="<c:url value='/css/error.css'/>">
</head>
<body>
    <div class="error-container">
        <div class="error-icon">🚫</div>
        <h1>403 - Access Denied</h1>
        <h2>Truy cập bị từ chối</h2>
        <p>Xin lỗi, bạn không có quyền truy cập trang này.</p>
        <p>Vui lòng liên hệ với quản trị viên nếu bạn cho rằng đây là lỗi.</p>
        
        <div class="error-actions">
            <a href="<c:url value='/home'/>" class="btn-primary">
                ← Quay lại trang chủ
            </a>
            <a href="<c:url value='/logout'/>" class="btn-secondary">
                Đăng xuất
            </a>
        </div>
        
        <div class="error-info">
            <p><strong>User:</strong> ${sessionScope.account.username}</p>
            <p><strong>Role ID:</strong> ${sessionScope.account.role}</p>
        </div>
    </div>
</body>
</html>