<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi Tiết Tài Khoản - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        .detail-card {
            background: white;
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.1);
        }
        .info-row {
            padding: 15px;
            border-bottom: 1px solid #e9ecef;
        }
        .info-row:last-child { border-bottom: none; }
        .info-label { font-weight: 600; color: #6c757d; margin-bottom: 5px; }
        .info-value { font-size: 1.1em; color: #212529; }
        .status-active { color: #28a745; }
        .status-inactive { color: #dc3545; }
    </style>
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="detail-card">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2><i class="fas fa-user-circle"></i> Chi Tiết Tài Khoản</h2>
                    <a href="<c:url value='/account/view'/>" class="btn btn-secondary">
                        <i class="fas fa-arrow-left"></i> Quay lại
                    </a>
                </div>

                <div class="info-row">
                    <div class="info-label">Mã Tài Khoản:</div>
                    <div class="info-value">#${account.accountID}</div>
                </div>

                <div class="info-row">
                    <div class="info-label">Username:</div>
                    <div class="info-value"><strong>${account.username}</strong></div>
                </div>

                <div class="info-row">
                    <div class="info-label">Họ và Tên:</div>
                    <div class="info-value">${account.fullName}</div>
                </div>

                <div class="info-row">
                    <div class="info-label">Vai Trò:</div>
                    <div class="info-value">
                        <span class="badge bg-info fs-6">${account.roleName}</span>
                    </div>
                </div>

                <div class="info-row">
                    <div class="info-label">Trạng Thái:</div>
                    <div class="info-value">
                        <c:choose>
                            <c:when test="${account.active}">
                                <span class="status-active">
                                    <i class="fas fa-check-circle"></i> Đang hoạt động
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="status-inactive">
                                    <i class="fas fa-times-circle"></i> Đã vô hiệu hóa
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <div class="mt-4 text-center">
                    <a href="<c:url value='/account/change-password'/>" 
                       class="btn btn-warning">
                        <i class="fas fa-edit"></i> Đổi mật khẩu
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
