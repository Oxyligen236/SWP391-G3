<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tạo Tài Khoản - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a2e0e6ad53.js" crossorigin="anonymous"></script>
    <style>
        body {
            background-color: #e9f2ff;
        }
        .form-container {
            max-width: 550px;
            margin: 60px auto;
            padding: 30px;
            background-color: #fff;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        .form-header h2 {
            text-align: center;
            color: #0d6efd;
            margin-bottom: 15px;
        }
        .btn-create {
            background-color: #0d6efd;
            color: #fff;
            border-radius: 8px;
        }
        .btn-create:hover {
            background-color: #0b5ed7;
        }
        .btn-back {
            background-color: #cfe2ff;
            color: #084298;
            border-radius: 8px;
            text-decoration: none;
        }
        .btn-back:hover {
            background-color: #0d6efd;
            color: #fff;
        }
        input, select {
            border-radius: 6px;
        }
    </style>
</head>
<body>

<div class="form-container">
    <div class="form-header">
        <h2><i class="fas fa-user-plus"></i> Tạo Tài Khoản Mới</h2>
        <hr>
    </div>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <form action="<c:url value='/account/create'/>" method="post" id="createAccountForm">
        <div class="row mb-3">
            <div class="col-md-6">
                <label class="form-label">User ID:</label>
                <input type="number" class="form-control" name="userID" required>
            </div>
            <div class="col-md-6">
                <label class="form-label">Username:</label>
                <input type="text" class="form-control" name="username" required>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label class="form-label">Password:</label>
                <input type="password" class="form-control" name="password" required minlength="6">
            </div>
            <div class="col-md-6">
                <label class="form-label">Xác nhận mật khẩu:</label>
                <input type="password" class="form-control" name="confirmPassword" required minlength="6">
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label class="form-label">Role:</label>
                <select class="form-select" name="roleID" required>
                    <option value="">-- Chọn vai trò --</option>
                    <c:forEach var="role" items="${roleList}">
                        <option value="${role.roleID}">${role.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-6">
                <label class="form-label">Trạng thái:</label>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="isActive" value="true" checked>
                    <label class="form-check-label">Active</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="isActive" value="false">
                    <label class="form-check-label">Inactive</label>
                </div>
            </div>
        </div>

        <div class="text-center mt-4 d-flex justify-content-center gap-3">
            <button type="submit" class="btn-create btn-custom"><i class="fas fa-user-plus me-2"></i>Tạo tài khoản</button>
            <a href="<c:url value='/account/view'/>" class="btn-back btn-custom"><i class="fas fa-arrow-left me-2"></i>Quay lại</a>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.getElementById('createAccountForm').addEventListener('submit', function(e) {
        const password = this.password.value;
        const confirmPassword = this.confirmPassword.value;

        if(password !== confirmPassword){
            e.preventDefault();
            alert('Mật khẩu và xác nhận mật khẩu không khớp!');
            return false;
        }
        if(password.length < 6){
            e.preventDefault();
            alert('Mật khẩu phải có ít nhất 6 ký tự!');
            return false;
        }
    });
</script>

</body>
</html>
