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
            background-color: #f8faff;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        }
        .detail-card {
            border-radius: 12px;
            padding: 25px;
            background-color: #ffffff;
            box-shadow: 0 4px 15px rgba(0,0,0,0.08);
            margin-top: 40px;
        }
        h2 {
            color: #0d6efd;
            font-weight: 600;
        }
        .info-row {
            display: flex;
            margin-bottom: 15px;
        }
        .info-label {
            width: 150px;
            font-weight: 600;
            color: #0d3b66;
        }
        .info-value {
            flex: 1;
            color: #1f2a44;
        }
        .status-active {
            color: #0d6efd;
            font-weight: 500;
        }
        .status-inactive {
            color: #6c757d;
            font-weight: 500;
        }
        .badge-role {
            background-color: #bde0fe;
            color: #0353a4;
            font-size: 0.95rem;
            padding: 0.4em 0.7em;
            border-radius: 0.5rem;
        }
        .btn-primary {
            background-color: #a2d2ff;
            border-color: #a2d2ff;
            color: #0d3b66;
        }
        .btn-primary:hover {
            background-color: #7bb8ff;
            border-color: #7bb8ff;
            color: #0d3b66;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="detail-card">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2><i class="fas fa-user-circle"></i> Chi Tiết Tài Khoản</h2>
                    <a href="<c:url value='/home'/>" class="btn btn-primary">
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
                        <span class="badge-role">${account.roleName}</span>
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
                       class="btn btn-primary">
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
