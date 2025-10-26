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
    <link rel="stylesheet" href="<c:url value='/css/view-profile.css'/>">
</head>
<body>
    <div class="container py-5">
        <div class="profile-container shadow-lg">
            <div class="profile-header text-center">
                <img src="https://ui-avatars.com/api/?name=${user.fullname}&size=140&background=ff80ab&color=fff&bold=true"
                     alt="Avatar" class="avatar" />
                <h2 class="mt-3 fw-bold text-pink">${user.fullname}</h2>
                <p class="text-muted">ID: ${user.userId}</p>
            </div>

            <div class="profile-body">
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

                        <div class="profile-body">


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
                                    <p class="info-value">
                                        <fmt:formatDate value="${user.birthDate}" pattern="dd/MM/yyyy" />
                                    </p>

                                    <label class="info-label mt-3">Giới tính</label>
                                    <p class="info-value">${user.gender}</p>

                                    <label class="info-label mt-3">CCCD</label>
                                    <p class="info-value">${user.cccd}</p>
                                </div>
                            </div>

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


                            <div class="section-title"><i class="fas fa-briefcase"></i> Thông Tin Công Việc</div>
                            <div class="row mb-4">
                                <div class="col-md-6">
                                    <label class="info-label">Phòng ban</label>
                                    <p class="info-value">${user.departmentName != null ? user.departmentName : 'Chưa
                                        phân công'}</p>

                                    <label class="info-label mt-3">Chức vụ</label>
                                    <p class="info-value">${user.positionName != null ? user.positionName : 'Chưa xác
                                        định'}</p>
                                </div>

                                <div class="col-md-6">
                                    <label class="info-label">Bằng cấp</label>
                                    <p class="info-value">${user.degreeName != null ? user.degreeName : ''}</p>
                                </div>
                            </div>
                        </div>

                        <div class="button-container">
                            <a href="<c:url value='/edit' />" class="btn-edit btn-custom me-3"><i
                                    class="fas fa-edit"></i>Chỉnh sửa</a>
                            <a href="<c:url value='/home' />" class="btn-back btn-custom"><i
                                    class="fas fa-arrow-left"></i>Quay lại</a>
                        </div>

                        <label class="info-label mt-3">CCCD</label>
                        <p class="info-value">${user.cccd}</p>
                    </div>
                </div>

                <div class="section-title"><i class="fas fa-info-circle"></i> Thông Tin Bổ Sung</div>
                <div class="row mb-4">
                    <div class="col-md-6">
                        <label class="info-label">Địa chỉ</label>
                        <p class="info-value">${user.address}</p>
                    </div>
                    <div class="col-md-3">
                        <label class="info-label">Dân tộc</label>
                        <p class="info-value">${user.ethnicity}</p>
                    </div>
                    <div class="col-md-3">
                        <label class="info-label">Quốc tịch</label>
                        <p class="info-value">${user.nation}</p>
                    </div>
                </div>

                <div class="section-title"><i class="fas fa-briefcase"></i> Thông Tin Công Việc</div>
                <div class="row mb-4">
                    <div class="col-md-4">
                        <label class="info-label">Phòng ban</label>
                        <p class="info-value">${user.departmentName != null ? user.departmentName : 'Chưa phân công'}</p>
                    </div>
                    <div class="col-md-4">
                        <label class="info-label">Chức vụ</label>
                        <p class="info-value">${user.positionName != null ? user.positionName : 'Chưa xác định'}</p>
                    </div>
                    <div class="col-md-4">
                        <label class="info-label">Bằng cấp</label>
                        <p class="info-value">${user.degreeName != null ? user.degreeName : ''}</p>
                    </div>
                </div>
            </div>

            <div class="button-container text-center mt-4">
                <a href="<c:url value='/edit' />" class="btn-edit btn-custom me-3">
                    <i class="fas fa-edit"></i> Chỉnh sửa
                </a>
                <a href="<c:url value='/home' />" class="btn-back btn-custom">
                    <i class="fas fa-arrow-left"></i> Quay lại
                </a>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>