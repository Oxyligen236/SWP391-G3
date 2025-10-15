<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đổi Mật Khẩu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            font-family: 'Segoe UI', sans-serif;
        }
        .card {
            background: white;
            border-radius: 12px;
            padding: 30px;
            width: 400px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.1);
        }
        h3 {
            text-align: center;
            margin-bottom: 25px;
            color: #495057;
        }
        .form-control:focus {
            box-shadow: none;
            border-color: #6c63ff;
        }
        .btn-primary {
            background-color: #6c63ff;
            border-color: #6c63ff;
        }
        .btn-primary:hover {
            background-color: #5751d9;
            border-color: #5751d9;
        }
        .alert {
            font-size: 0.9rem;
        }
    </style>
</head>
<body>
<div class="card">
    <h3>Đổi Mật Khẩu</h3>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${errorMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <form action="<c:url value='/account/change-password'/>" method="post">
        <div class="mb-3">
            <label for="currentPassword" class="form-label">Mật khẩu hiện tại</label>
            <input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
        </div>
        <div class="mb-3">
            <label for="newPassword" class="form-label">Mật khẩu mới</label>
            <input type="password" class="form-control" id="newPassword" name="newPassword" required>
        </div>
        <div class="mb-3">
            <label for="confirmPassword" class="form-label">Xác nhận mật khẩu mới</label>
            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
        </div>

        <div class="d-flex justify-content-between">
            <a href="<c:url value='/account/view'/>" class="btn btn-secondary">Quay lại</a>
            <button type="submit" class="btn btn-primary">Đổi mật khẩu</button>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
