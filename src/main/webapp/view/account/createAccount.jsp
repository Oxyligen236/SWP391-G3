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
            <link rel="stylesheet" href="<c:url value='/css/create-account.css'/>">
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
                        <a href="<c:url value='/account/view'/>" class="btn btn-back btn-custom">
                            <i class="fas fa-arrow-left"></i>Quay lại
                        </a>
                    </div>

                </form>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>