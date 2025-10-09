<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông Tin Cá Nhân - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a2e0e6ad53.js" crossorigin="anonymous"></script>
    <style>
        body {
            background: linear-gradient(135deg, #ffe6f0, #ffb3d1);
            font-family: 'Poppins', sans-serif;
            margin: 0;
            padding: 20px 0;
        }

        .profile-container {
            max-width: 1000px;
            margin: 0 auto;
            background: #fff;
            border-radius: 20px;
            box-shadow: 0 12px 40px rgba(255, 105, 180, 0.25);
            overflow: hidden;
            transition: transform 0.3s, box-shadow 0.3s;
        }
        .profile-container:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 50px rgba(255, 105, 180, 0.35);
        }

        .profile-header {
            background: linear-gradient(135deg, #ff80ab, #ff4da6);
            text-align: center;
            color: #fff;
            padding: 40px 20px;
        }

        .profile-header img.avatar {
            width: 120px;
            height: 120px;
            border-radius: 50%;
            border: 5px solid #fff;
            object-fit: cover;
            box-shadow: 0 5px 20px rgba(255, 182, 193, 0.5);
        }

        .profile-header h2 {
            margin-top: 15px;
            font-size: 2rem;
            font-weight: 600;
        }

        .profile-body {
            padding: 30px 40px;
        }

        .section-title {
            font-size: 1.25rem;
            font-weight: 700;
            color: #ff4da6;
            margin-bottom: 20px;
            padding-bottom: 8px;
            border-bottom: 3px solid #ffb6c1;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .info-label {
            font-weight: 600;
            color: #d16ba5;
            text-transform: uppercase;
            font-size: 0.85rem;
        }

        .info-value {
            font-size: 1rem;
            color: #2c3e50;
            margin-top: 4px;
        }

        .btn-custom {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            border-radius: 30px;
            padding: 12px 35px;
            font-weight: 600;
            font-size: 1rem;
            transition: all 0.3s;
            cursor: pointer;
            text-decoration: none;
        }

        .btn-edit {
            background: linear-gradient(135deg, #ff80ab, #ff4da6);
            color: #fff;
            box-shadow: 0 4px 15px rgba(255, 105, 180, 0.4);
        }
        .btn-edit:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 25px rgba(255, 105, 180, 0.6);
        }

        .btn-back {
            background: #ffe4eb;
            color: #d63384;
        }
        .btn-back:hover {
            background: #ffd6e0;
            color: #b91d73;
        }

        .button-container {
            text-align: center;
            margin-top: 30px;
        }

        @media (max-width: 768px) {
            .profile-body { padding: 20px; }
            .btn-custom { width: 100%; margin-bottom: 10px; }
            .button-container { display: flex; flex-direction: column; gap: 10px; }
        }
    </style>
</head>
<body>

<div class="container">
    <div class="profile-container">

        <!-- Header -->
        <div class="profile-header">
            <img src="https://ui-avatars.com/api/?name=${user.fullname}&size=120&background=ff80ab&color=fff&bold=true" alt="Avatar" class="avatar"/>
            <h2>${user.fullname}</h2>
            <p>ID: ${user.userId}</p>
        </div>

        <div class="profile-body">

            <!-- Thông tin cơ bản -->
            <div class="section-title"><i class="fas fa-user"></i> Thông Tin Cơ Bản</div>
            <div class="row mb-4">
                <div class="col-md-6">
                    <label class="info-label">Họ và tên</label>
                    <p class="info-value">${user.fullname}</p>

                    <label class="info-label mt-3">Email</label>
                    <p class="info-value">${user.email}</p>

                    <label class="info-label mt-3">Số điện thoại</label>
                    <p class="info-value">${user.phoneNumber}</p>
                </div>

                <div class="col-md-6">
                    <label class="info-label">Ngày sinh</label>
                    <p class="info-value"><fmt:formatDate value="${user.birthDate}" pattern="dd/MM/yyyy"/></p>

                    <label class="info-label mt-3">Giới tính</label>
                    <p class="info-value">${user.gender}</p>

                    <label class="info-label mt-3">CCCD</label>
                    <p class="info-value">${user.cccd}</p>
                </div>
            </div>

            <!-- Thông tin bổ sung -->
            <div class="section-title"><i class="fas fa-info-circle"></i> Thông Tin Bổ Sung</div>
            <div class="row mb-4">
                <div class="col-md-6">
                    <label class="info-label">Quốc tịch</label>
                    <p class="info-value">${user.nation}</p>
                </div>
                <div class="col-md-6">
                    <label class="info-label">Dân tộc</label>
                    <p class="info-value">${user.ethnicity}</p>
                </div>
            </div>

            <!-- Thông tin công việc -->
            <div class="section-title"><i class="fas fa-briefcase"></i> Thông Tin Công Việc</div>
            <div class="row mb-4">
                <div class="col-md-6">
                    <label class="info-label">Phòng ban</label>
                    <p class="info-value">${user.departmentName != null ? user.departmentName : 'Chưa phân công'}</p>

                    <label class="info-label mt-3">Chức vụ</label>
                    <p class="info-value">${user.positionName != null ? user.positionName : 'Chưa xác định'}</p>
                </div>

                <div class="col-md-6">
                    <label class="info-label">Bằng cấp</label>
                    <p class="info-value">${user.degreeName != null ? user.degreeName : ''}</p>
                </div>
            </div>
        </div>

        <!-- Nút hành động -->
        <div class="button-container">
            <a href="<c:url value='/edit' />" class="btn-edit btn-custom me-3"><i class="fas fa-edit"></i>Chỉnh sửa</a>
            <a href="<c:url value='/authenticate' />" class="btn-back btn-custom"><i class="fas fa-arrow-left"></i>Quay lại</a>
        </div>

    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
