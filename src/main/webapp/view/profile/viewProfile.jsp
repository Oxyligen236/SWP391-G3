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
     <link rel="stylesheet" href="<c:url value='/css/view-profile.css'/>">
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
