<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Profile View Content -->
<div class="row">
    <div class="col-12">
        <div class="card border-0 shadow rounded">
            <div class="card-body">
                <div class="profile-header text-center mb-4">
                    <img src="https://ui-avatars.com/api/?name=${user.fullname}&size=140&background=ff80ab&color=fff&bold=true"
                         alt="Avatar" 
                         class="rounded-circle mb-3"
                         style="width: 140px; height: 140px; border: 4px solid #ff80ab;" />
                    <h2 class="fw-bold text-primary">${user.fullname}</h2>
                    <p class="text-muted">ID: ${user.userId}</p>
                </div>

                <hr class="my-4">

                <!-- Thông Tin Cơ Bản -->
                <h5 class="mb-3"><i class="uil uil-user me-2"></i>Thông Tin Cơ Bản</h5>
                <div class="row mb-4">
                    <div class="col-md-6 mb-3">
                        <label class="text-muted small">Họ và tên</label>
                        <p class="fw-bold mb-0">${user.fullname}</p>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="text-muted small">Ngày sinh</label>
                        <p class="fw-bold mb-0"><fmt:formatDate value="${user.birthDate}" pattern="dd/MM/yyyy"/></p>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="text-muted small">Email</label>
                        <p class="fw-bold mb-0">${user.email}</p>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="text-muted small">Giới tính</label>
                        <p class="fw-bold mb-0">${user.gender}</p>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="text-muted small">Số điện thoại</label>
                        <p class="fw-bold mb-0">${user.phoneNumber}</p>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="text-muted small">CCCD</label>
                        <p class="fw-bold mb-0">${user.cccd}</p>
                    </div>
                </div>

                <hr class="my-4">

                <!-- Thông Tin Bổ Sung -->
                <h5 class="mb-3"><i class="uil uil-info-circle me-2"></i>Thông Tin Bổ Sung</h5>
                <div class="row mb-4">
                    <div class="col-md-6 mb-3">
                        <label class="text-muted small">Địa chỉ</label>
                        <p class="fw-bold mb-0">${user.address}</p>
                    </div>
                    <div class="col-md-3 mb-3">
                        <label class="text-muted small">Dân tộc</label>
                        <p class="fw-bold mb-0">${user.ethnicity}</p>
                    </div>
                    <div class="col-md-3 mb-3">
                        <label class="text-muted small">Quốc tịch</label>
                        <p class="fw-bold mb-0">${user.nation}</p>
                    </div>
                </div>

                <hr class="my-4">

                <!-- Thông Tin Công Việc -->
                <h5 class="mb-3"><i class="uil uil-briefcase me-2"></i>Thông Tin Công Việc</h5>
                <div class="row mb-4">
                    <div class="col-md-4 mb-3">
                        <label class="text-muted small">Phòng ban</label>
                        <p class="fw-bold mb-0">${user.departmentName != null ? user.departmentName : 'Chưa phân công'}</p>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label class="text-muted small">Chức vụ</label>
                        <p class="fw-bold mb-0">${user.positionName != null ? user.positionName : 'Chưa xác định'}</p>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label class="text-muted small">Bằng cấp</label>
                        <p class="fw-bold mb-0">${user.degreeName != null ? user.degreeName : 'N/A'}</p>
                    </div>
                </div>

                <!-- Buttons -->
                <div class="text-center mt-4">
                    <a href="<c:url value='/edit' />" class="btn btn-primary me-2">
                        <i class="uil uil-edit me-1"></i>Chỉnh sửa
                    </a>
                    <a href="<c:url value='/home' />" class="btn btn-outline-secondary">
                        <i class="uil uil-arrow-left me-1"></i>Quay lại
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
