<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Personal Information - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a2e0e6ad53.js" crossorigin="anonymous"></script>
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body { font-family:"Segoe UI", Tahoma, Geneva, Verdana, sans-serif; background-color:#f5f5f5; }

        .profile-container { max-width:950px; margin:40px auto; padding:20px; }
        h2.page-title { color:#2c3e50; font-weight:700; text-align:center; margin-bottom:25px; border-bottom:3px solid #3498db; padding-bottom:10px; }

        .profile-header { text-align:center; margin-bottom:30px; }
        .avatar { border-radius:50%; width:140px; height:140px; border:3px solid #b5d3ff; }
        .profile-header h2 { color:#2c3e50; font-weight:700; margin-top:15px; }
        .profile-header p { color:#6c757d; }

        .info-card {
            background:#fff;
            border-radius:12px;
            padding:25px 30px;
            margin-bottom:20px;
            box-shadow:0 2px 8px rgba(0,0,0,0.1);
        }

        .section-title { font-weight:600; color:#2c3e50; margin-bottom:15px; font-size:18px; border-bottom:2px solid #3498db; padding-bottom:5px; }

        .info-row { margin-bottom:15px; }
        .info-label { font-weight:500; color:#2c3e50; margin-bottom:5px; display:block; }
        .info-value { color:#495057; font-size:14px; }

        .btn-custom { padding:8px 20px; font-weight:500; border-radius:8px; font-size:14px; cursor:pointer; transition:all 0.2s ease; text-decoration:none; }
        .btn-edit { background:#3498db; color:#fff; border:none; }
        .btn-edit:hover { background:#2980b9; transform:translateY(-1px); box-shadow:0 4px 8px rgba(52,152,219,0.3); text-decoration:none; }
        .btn-back { background:#6c757d; color:#fff; border:none; }
        .btn-back:hover { background:#5a6268; transform:translateY(-1px); }

        @media (max-width:768px) {
            .avatar { width:120px; height:120px; }
            .profile-container { padding:0 15px; }
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

        <!-- Basic Information -->
        <div class="info-card">
            <div class="section-title"><i class="fas fa-user"></i> Basic Information</div>
            <div class="row">
                <div class="col-md-6 info-row">
                    <span class="info-label">Full Name</span>
                    <span class="info-value">${user.fullname}</span>

                    <span class="info-label">Email</span>
                    <span class="info-value">${user.email}</span>

                    <span class="info-label">Phone Number</span>
                    <span class="info-value">${user.phoneNumber}</span>
                </div>
                <div class="col-md-6 info-row">
                    <span class="info-label">Birth Date</span>
                    <span class="info-value"><fmt:formatDate value="${user.birthDate}" pattern="dd/MM/yyyy"/></span>

                    <span class="info-label">Gender</span>
                    <span class="info-value">${user.gender}</span>

                    <span class="info-label">ID Number</span>
                    <span class="info-value">${user.cccd}</span>
                </div>
            </div>
        </div>

        <!-- Additional Information -->
        <div class="info-card">
            <div class="section-title"><i class="fas fa-info-circle"></i> Additional Information</div>
            <div class="row">
                <div class="col-md-6 info-row">
                    <span class="info-label">Address</span>
                    <span class="info-value">${user.address}</span>
                </div>
                <div class="col-md-3 info-row">
                    <span class="info-label">Ethnicity</span>
                    <span class="info-value">${user.ethnicity}</span>
                </div>
                <div class="col-md-3 info-row">
                    <span class="info-label">Nationality</span>
                    <span class="info-value">${user.nation}</span>
                </div>
            </div>
        </div>

        <!-- Job Information -->
        <div class="info-card">
            <div class="section-title"><i class="fas fa-briefcase"></i> Job Information</div>
            <div class="row">
                <div class="col-md-4 info-row">
                    <span class="info-label">Department</span>
                    <span class="info-value">${user.departmentName != null ? user.departmentName : 'Not Assigned'}</span>
                </div>
                <div class="col-md-4 info-row">
                    <span class="info-label">Position</span>
                    <span class="info-value">${user.positionName != null ? user.positionName : 'Not Specified'}</span>
                </div>
                <div class="col-md-4 info-row">
                    <span class="info-label">Degree</span>
                    <span class="info-value">${user.degreeName != null ? user.degreeName : ''}</span>
                </div>
            </div>
        </div>

        <!-- Action Button -->
        <div class="mt-4 text-center">
            <a href="<c:url value='/edit' />" class="btn-edit btn-custom"><i class="fas fa-edit"></i> Edit</a>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
