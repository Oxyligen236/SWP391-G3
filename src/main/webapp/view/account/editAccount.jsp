<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chỉnh Sửa Tài Khoản - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            background-color: #e9f2ff;
        }
        .edit-card {
            background-color: #fff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            margin-top: 60px;
        }
        .edit-card h3 {
            color: #0d6efd;
        }
        .btn-primary {
            background-color: #0d6efd;
            border-radius: 8px;
        }
        .btn-primary:hover {
            background-color: #0b5ed7;
        }
        .btn-secondary {
            border-radius: 8px;
        }
        .password-input-wrapper {
            position: relative;
        }
        .password-toggle {
            position: absolute;
            top: 50%;
            right: 12px;
            transform: translateY(-50%);
            cursor: pointer;
            color: #6c757d;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="edit-card">
                <div class="text-center mb-4">
                    <h3><i class="fas fa-edit"></i> Chỉnh Sửa Tài Khoản</h3>
                </div>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger">${errorMessage}</div>
                </c:if>

                <form action="<c:url value='/account/edit'/>" method="post">
                    <input type="hidden" name="accountID" value="${account.accountID}">

                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-user"></i> Username:</label>
                        <input type="text" name="username" class="form-control" 
                               value="${account.username}" required>
                    </div>

                    <div class="mb-3 password-input-wrapper">
                        <label class="form-label"><i class="fas fa-lock"></i> Mật khẩu:</label>
                        <input type="password" name="password" class="form-control" 
                               id="passwordField" value="${account.password}">
                        <i class="fas fa-eye password-toggle" onclick="togglePassword('passwordField', this)"></i>
                        <small class="text-muted">Để trống nếu không muốn đổi mật khẩu</small>
                    </div>

                    <div class="mb-3">
                        <label class="form-label"><i class="fas fa-user-tag"></i> Vai trò:</label>
                        <select name="roleID" class="form-select" required>
                            <c:forEach var="role" items="${roleList}">
                                <option value="${role.roleID}" 
                                        ${role.roleID == account.role ? 'selected' : ''}>
                                    ${role.roleName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="mb-4 form-check">
                        <input type="checkbox" name="isActive" value="true" 
                               class="form-check-input" id="isActive"
                               ${account.isActive ? 'checked' : ''}>
                        <label class="form-check-label" for="isActive">
                            <i class="fas fa-check-circle"></i> Kích hoạt tài khoản
                        </label>
                    </div>

                    <div class="d-flex justify-content-between">
                        <a href="<c:url value='/account/list'/>" class="btn btn-secondary">
                            <i class="fas fa-arrow-left"></i> Hủy
                        </a>
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save"></i> Lưu thay đổi
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    // Toggle password visibility
    function togglePassword(inputId, icon) {
        const input = document.getElementById(inputId);
        if (input.type === 'password') {
            input.type = 'text';
            icon.classList.remove('fa-eye');
            icon.classList.add('fa-eye-slash');
        } else {
            input.type = 'password';
            icon.classList.remove('fa-eye-slash');
            icon.classList.add('fa-eye');
        }
    }
</script>
</body>
</html>
