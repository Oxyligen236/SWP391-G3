<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông Tin Cá Nhân - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a2e0e6ad53.js" crossorigin="anonymous"></script>
    <style>
        body {
            background-color: #f0f5ff; /* pastel xanh nhạt */
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        }

        .profile-container {
            max-width: 950px;
            margin: 40px auto;
        }

        .profile-header {
            text-align: center;
            margin-bottom: 30px;
        }

        .avatar {
            border-radius: 50%;
            width: 140px;
            height: 140px;
            border: 3px solid #b5d3ff;
        }

        .profile-header h2 {
            color: #0d3b66;
            font-weight: 700;
            margin-top: 15px;
        }

        .profile-header p {
            color: #6c757d;
        }

        /* Info cards */
        .info-card {
            background-color: #ffffff; /* trắng pastel */
            border: 1px solid #cfe2ff; /* viền xanh nhạt */
            border-radius: 12px;
            padding: 25px 30px;
            margin-bottom: 20px;
        }

        .section-title {
            font-weight: 600;
            color: #0d3b66;
            margin-bottom: 15px;
            font-size: 18px;
        }

        .info-label {
            font-weight: 500;
            color: #0d3b66;
        }

        .info-row p {
            color: #1f2a44;
            margin-bottom: 5px;
        }

        .btn-custom {
            border-radius: 8px;
            padding: 8px 20px;
            font-weight: 500;
        }

        .btn-edit {
            background-color: #b5d3ff;
            color: #0d3b66;
            border: none;
        }

        .btn-back {
            background-color: #d9eaff;
            color: #0d3b66;
            border: none;
        }

        @media (max-width: 768px) {
            .avatar {
                width: 120px;
                height: 120px;
            }

            .profile-container {
                padding: 0 15px;
            }
        }
    </style>
</head>
<body>
<div class="container py-5">
    <div class="profile-container">

        <!-- Header -->
        <div class="profile-header">
            <img src="https://ui-avatars.com/api/?name=${user.fullname}&size=140&background=b5d3ff&color=0d3b66&bold=true"
                 alt="Avatar" class="avatar">
            <h2>${user.fullname}</h2>
            <p>ID: ${user.userId}</p>
        </div>

        <!-- Thông Tin Cơ Bản -->
        <div class="info-card">
            <div class="section-title"><i class="fas fa-user"></i> Thông Tin Cơ Bản</div>
            <div class="row">
                <div class="col-md-6 info-row">
                    <label class="info-label">Họ và tên</label>
                    <p>${user.fullname}</p>
                    <label class="info-label">Email</label>
                    <p>${user.email}</p>
                    <label class="info-label">Số điện thoại</label>
                    <p>${user.phoneNumber}</p>
                </div>
                <div class="col-md-6 info-row">
                    <label class="info-label">Ngày sinh</label>
                    <p><fmt:formatDate value="${user.birthDate}" pattern="dd/MM/yyyy"/></p>
                    <label class="info-label">Giới tính</label>
                    <p>${user.gender}</p>
                    <label class="info-label">CCCD</label>
                    <p>${user.cccd}</p>
                </div>
            </div>
        </div>

        <!-- Thông Tin Bổ Sung -->
        <div class="info-card">
            <div class="section-title"><i class="fas fa-info-circle"></i> Thông Tin Bổ Sung</div>
            <div class="row">
                <div class="col-md-6 info-row">
                    <label class="info-label">Địa chỉ</label>
                    <p>${user.address}</p>
                </div>
                <div class="col-md-3 info-row">
                    <label class="info-label">Dân tộc</label>
                    <p>${user.ethnicity}</p>
                </div>
                <div class="col-md-3 info-row">
                    <label class="info-label">Quốc tịch</label>
                    <p>${user.nation}</p>
                </div>
            </div>
        </div>

        <!-- Thông Tin Công Việc -->
        <div class="info-card">
            <div class="section-title"><i class="fas fa-briefcase"></i> Thông Tin Công Việc</div>
            <div class="row">
                <div class="col-md-4 info-row">
                    <label class="info-label">Phòng ban</label>
                    <p>${user.departmentName != null ? user.departmentName : 'Chưa phân công'}</p>
                </div>
                <div class="col-md-4 info-row">
                    <label class="info-label">Chức vụ</label>
                    <p>${user.positionName != null ? user.positionName : 'Chưa xác định'}</p>
                </div>
                <div class="col-md-4 info-row">
                    <label class="info-label">Bằng cấp</label>
                    <p>${user.degreeName != null ? user.degreeName : ''}</p>
                </div>
            </div>
        </div>

        <!-- Nút hành động -->
        <div class="mt-4 text-center">
            <a href="<c:url value='/edit' />" class="btn-edit btn-custom me-3"><i class="fas fa-edit"></i> Chỉnh sửa</a>
            <a href="<c:url value='/home' />" class="btn-back btn-custom"><i class="fas fa-arrow-left"></i> Quay lại</a>
        </div>

    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
