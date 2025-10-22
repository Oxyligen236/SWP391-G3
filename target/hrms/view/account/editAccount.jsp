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
    <link rel="stylesheet" href="<c:url value='/css/edit-account.css'/>">
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

                        <div class="mb-3">
                            <label class="form-label"><i class="fas fa-lock"></i> Mật khẩu:</label>
                            <input type="password" name="password" class="form-control" 
                                   value="${account.password}" required>
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
</body>
</html>