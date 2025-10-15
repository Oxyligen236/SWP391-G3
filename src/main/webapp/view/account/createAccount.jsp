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
        body { background-color: #f9f2f7; font-family: "Segoe UI", sans-serif; margin: 0; padding: 0; }
        .form-container {
            max-width: 700px; background-color: #fff; margin: 50px auto; padding: 30px 40px;
            border-radius: 16px; box-shadow: 0 8px 25px rgba(0,0,0,0.1);
        }
        .form-header { text-align: center; margin-bottom: 30px; }
        .form-header h2 { font-size: 28px; font-weight: 600; color: #d63384; margin-bottom: 5px; }
        .form-header hr { width: 60px; border-top: 3px solid #d63384; margin: 0 auto; }
        .form-label { font-weight: 600; margin-top: 8px; display: block; color: #6c757d;}
        input, select { width: 100%; padding: 8px 10px; border-radius: 6px; border: 1px solid #ccc; margin-top: 4px; font-size: 16px;}
        .form-check-label { font-weight: 500; margin-left: 4px; }
        .btn-custom {
            display: inline-flex; align-items: center; justify-content: center;
            border-radius: 30px; padding: 10px 24px; font-weight: 500; transition: all 0.3s; cursor: pointer;
        }
        .btn-create { background: linear-gradient(135deg, #d63384, #a71d5d); color: #fff; border: none;}
        .btn-create:hover { background: linear-gradient(135deg, #a71d5d, #730036); transform: translateY(-2px); }
        .btn-back { background: linear-gradient(135deg, #6c757d, #495057); color: #fff; border:none;}
        .btn-back:hover { background: linear-gradient(135deg, #495057, #343a40); transform: translateY(-2px); }
        .btn-custom i { margin-right: 8px; }
        @media (max-width: 768px) { .btn-custom { width: 100%; margin-bottom: 10px; } }
    </style>
</head>
<body>

<div class="form-container">
    <div class="form-header">
        <h2>Tạo Tài Khoản Mới</h2>
        <hr>
    </div>


    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <form action="<c:url value='/account/create'/>" method="post">
        <div class="row mb-3">
            <div class="col-md-6">
                <label class="form-label">User ID:</label>
                <input type="number" name="userID" required>
            </div>
            <div class="col-md-6">
                <label class="form-label">Username:</label>
                <input type="text" name="username" required>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label class="form-label">Password:</label>
                <input type="password" name="password" required>
            </div>
            <div class="col-md-6">
                <label class="form-label">Xác nhận mật khẩu:</label>
                <input type="password" name="confirmPassword" required>
            </div>
        </div>

        <div class="row mb-3">
            <div class="col-md-6">
                <label class="form-label">Role:</label>
                <select name="roleID" required>
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

        <div class="text-center mt-4">
            <button type="submit" class="btn btn-create btn-custom">
                <i class="fas fa-user-plus"></i>Tạo tài khoản
            </button>
            <a href="<c:url value='/account/list'/>" class="btn btn-back btn-custom">
                <i class="fas fa-arrow-left"></i>Quay lại
            </a>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
