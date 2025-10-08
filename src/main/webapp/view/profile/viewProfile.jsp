<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông Tin Cá Nhân - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px 0;
        }
        .profile-container { max-width: 1000px; margin: 0 auto; }
        .profile-card {
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
            overflow: visible;
            position: relative;
            padding: 35px 35px 120px 35px;
            min-height: 700px;
        }
        .profile-header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 40px 30px;
            text-align: center;
        }
        .avatar-container { margin-bottom: 20px; }
        .avatar {
            width: 130px;
            height: 130px;
            border-radius: 50%;
            border: 5px solid white;
            object-fit: cover;
            box-shadow: 0 5px 20px rgba(0,0,0,0.3);
        }
        .profile-body { padding-top: 20px; }
        .section-title {
            font-size: 1.3rem;
            font-weight: 700;
            color: #667eea;
            margin-bottom: 25px;
            padding-bottom: 12px;
            border-bottom: 3px solid #667eea;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .info-row { padding: 18px 0; border-bottom: 1px solid #f0f0f0; }
        .info-row:last-child { border-bottom: none; }
        .info-label {
            font-weight: 600;
            color: #6c757d;
            margin-bottom: 8px;
            font-size: 0.9rem;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }
        .info-value { color: #212529; font-size: 1.1rem; }
        .badge-custom { padding: 10px 18px; border-radius: 25px; font-size: 0.95rem; font-weight: 600; }
        .btn-edit {
            display: inline-block;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border: none;
            padding: 14px 40px;
            border-radius: 30px;
            color: white;
            font-weight: 600;
            font-size: 1.05rem;
            transition: all 0.3s;
            position: absolute;
            bottom: 20px;
            left: 50%;
            transform: translateX(-50%);
            text-decoration: none;
        }
        .btn-edit:hover {
            transform: translate(-50%, -3px);
            box-shadow: 0 8px 25px rgba(102,126,234,0.6);
            color: white;
        }
    </style>
</head>
<body>
<div class="container">

    <!-- Success Message -->
    <c:if test="${not empty sessionScope.successMessage}">
        <div class="profile-container mb-3">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="fas fa-check-circle me-2"></i>${sessionScope.successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <c:remove var="successMessage" scope="session" />
        </div>
    </c:if>

    <div class="profile-container">
        <div class="profile-card">
            <!-- Profile Header -->
            <div class="profile-header">
                <div class="avatar-container">
                    <img src="https://ui-avatars.com/api/?name=${user.fullname}&size=130&background=random&color=fff&bold=true"
                         alt="Avatar" class="avatar" />
                </div>
                <h2 class="mb-2">${user.fullname}</h2>
            </div>

            <!-- Profile Body -->
            <div class="profile-body">

                <!-- Thông tin cơ bản -->
                <div class="section-title"><i class="fas fa-user"></i><span>Thông Tin Cơ Bản</span></div>
                <div class="row mb-4">
                    <div class="col-md-6">
                        <div class="info-row">
                            <div class="info-label"><i class="fas fa-envelope me-2"></i>Email</div>
                            <div class="info-value">${user.email}</div>
                        </div>
                        <div class="info-row">
                            <div class="info-label"><i class="fas fa-phone me-2"></i>Số điện thoại</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${not empty user.phoneNumber}">${user.phoneNumber}</c:when>
                                    <c:otherwise><span class="text-muted fst-italic">Chưa cập nhật</span></c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="info-label"><i class="fas fa-birthday-cake me-2"></i>Ngày sinh</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${not empty user.birthDate}">
                                        <fmt:formatDate value="${user.birthDate}" pattern="dd/MM/yyyy"/>
                                    </c:when>
                                    <c:otherwise><span class="text-muted fst-italic">Chưa cập nhật</span></c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="info-row">
                            <div class="info-label"><i class="fas fa-venus-mars me-2"></i>Giới tính</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${not empty user.gender}">${user.gender}</c:when>
                                    <c:otherwise><span class="text-muted fst-italic">Chưa cập nhật</span></c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="info-label"><i class="fas fa-map-marker-alt me-2"></i>Địa chỉ</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${not empty user.address}">${user.address}</c:when>
                                    <c:otherwise><span class="text-muted fst-italic">Chưa cập nhật</span></c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Thông tin bổ sung -->
                <div class="section-title"><i class="fas fa-info-circle"></i><span>Thông Tin Bổ Sung</span></div>
                <div class="row mb-4">
                    <div class="col-md-6">
                        <div class="info-row">
                            <div class="info-label"><i class="fas fa-flag me-2"></i>Quốc tịch</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${not empty user.nation}">${user.nation}</c:when>
                                    <c:otherwise><span class="text-muted fst-italic">Chưa cập nhật</span></c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="info-row">
                            <div class="info-label"><i class="fas fa-users me-2"></i>Dân tộc</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${not empty user.ethnicity}">${user.ethnicity}</c:when>
                                    <c:otherwise><span class="text-muted fst-italic">Chưa cập nhật</span></c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Thông tin công việc -->
                <div class="section-title"><i class="fas fa-briefcase"></i><span>Thông Tin Công Việc</span></div>
                <div class="row mb-4">
                    <div class="col-md-6">
                        <div class="info-row">
                            <div class="info-label"><i class="fas fa-building me-2"></i>Phòng ban</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${not empty user.departmentName}">
                                        <span class="badge bg-primary badge-custom">${user.departmentName}</span>
                                    </c:when>
                                    <c:otherwise><span class="text-muted fst-italic">Chưa phân công</span></c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="info-label"><i class="fas fa-user-tag me-2"></i>Chức vụ</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${not empty user.positionName}">
                                        <span class="badge bg-info badge-custom">${user.positionName}</span>
                                    </c:when>
                                    <c:otherwise><span class="text-muted fst-italic">Chưa xác định</span></c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="info-row">
                            <div class="info-label"><i class="fas fa-shield-alt me-2"></i>Vai trò hệ thống</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${not empty user.roleName}">
                                        <span class="badge bg-warning text-dark badge-custom">${user.roleName}</span>
                                    </c:when>
                                    <c:otherwise><span class="text-muted fst-italic">Chưa gán quyền</span></c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="info-row">
                            <div class="info-label"><i class="fas fa-graduation-cap me-2"></i>Bằng cấp</div>
                            <div class="info-value">
                                <c:choose>
                                    <c:when test="${not empty user.degreeName}">
                                        <span class="badge bg-secondary badge-custom">${user.degreeName}</span>
                                    </c:when>
                                    <c:otherwise><span class="text-muted fst-italic">Chưa cập nhật</span></c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Nút chỉnh sửa -->
            <a href="<c:url value='/edit' />" class="btn-edit">
                <i class="fas fa-pencil-alt me-2"></i>Chỉnh sửa thông tin
            </a>

        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
