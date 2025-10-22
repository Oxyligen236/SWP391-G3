<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chỉnh Sửa Thông Tin Cá Nhân - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a2e0e6ad53.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="<c:url value='/css/edit-profile.css'/>">
</head>

<body>
    <div class="profile-container">
        <div class="profile-header text-center mb-4">
            <h2 class="fw-bold text-primary">Chỉnh Sửa Thông Tin Cá Nhân</h2>
            <hr>
        </div>

        <c:if test="${not empty successMessage}">
            <div class="alert alert-success text-center">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger text-center">${errorMessage}</div>
        </c:if>

        <form action="<c:url value='/edit' />" method="post" class="p-4 bg-light rounded shadow-sm">
            <!-- Thông tin cơ bản -->
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="info-label fw-semibold">Họ và tên:</label>
                    <input type="text" class="form-control" name="fullname" value="${user.fullname}" required>
                </div>
                <div class="col-md-6">
                    <label class="info-label fw-semibold">CCCD:</label>
                    <input type="text" class="form-control" name="cccd" value="${user.cccd}">
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="info-label fw-semibold">Ngày sinh:</label>
                    <input type="date" class="form-control" name="birthDate"
                        value="<fmt:formatDate value='${user.birthDate}' pattern='yyyy-MM-dd'/>">
                </div>
                <div class="col-md-6">
                    <label class="info-label fw-semibold">Giới tính:</label>
                    <select name="gender" class="form-select">
                        <option value="Male" ${user.gender=='Male' ? 'selected' : '' }>Nam</option>
                        <option value="Female" ${user.gender=='Female' ? 'selected' : '' }>Nữ</option>
                        <option value="Other" ${user.gender=='Other' ? 'selected' : '' }>Khác</option>
                    </select>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="info-label fw-semibold">Số điện thoại:</label>
                    <input type="text" class="form-control" name="phoneNumber" value="${user.phoneNumber}">
                </div>
                <div class="col-md-6">
                    <label class="info-label fw-semibold">Email:</label>
                    <input type="email" class="form-control" name="email" value="${user.email}">
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-12">
                    <label class="info-label fw-semibold">Địa chỉ:</label>
                    <input type="text" class="form-control" name="address" value="${user.address}">
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-4">
                    <label class="info-label fw-semibold">Dân tộc:</label>
                    <input type="text" class="form-control" name="ethnicity" value="${user.ethnicity}">
                </div>
                <div class="col-md-4">
                    <label class="info-label fw-semibold">Quốc tịch:</label>
                    <input type="text" class="form-control" name="nation" value="${user.nation}">
                </div>
            </div>

            <!-- Phần thông tin công việc -->
            <hr class="my-4">
            <h5 class="text-primary fw-bold mb-3"><i class="fas fa-briefcase"></i> Thông Tin Công Việc</h5>

            <div class="row mb-3">
                <div class="col-md-4">
                    <label class="info-label fw-semibold">Phòng ban:</label>
                    <select name="departmentId" class="form-select" 
                        <c:if test="${roleId != 2 && roleId != 3}">disabled</c:if>>
                        <option value="">-- Chọn phòng ban --</option>
                        <c:forEach var="dept" items="${departmentList}">
                            <option value="${dept.departmentId}" ${user.departmentId==dept.departmentId?'selected':''}>
                                ${dept.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-4">
                    <label class="info-label fw-semibold">Chức vụ:</label>
                    <select name="positionId" class="form-select" 
                        <c:if test="${roleId != 2 && roleId != 3}">disabled</c:if>>
                        <option value="">-- Chọn chức vụ --</option>
                        <c:forEach var="pos" items="${positionList}">
                            <option value="${pos.positionId}" ${user.positionId==pos.positionId?'selected':''}>
                                ${pos.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-4">
                    <label class="info-label fw-semibold">Bằng cấp:</label>
                    <select name="degreeId" class="form-select">
                        <option value="">-- Chọn bằng cấp --</option>
                        <c:forEach var="d" items="${degreeList}">
                            <option value="${d.degreeId}" ${user.degreeId==d.degreeId?'selected':''}>
                                ${d.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="text-center mt-4">
                <button type="submit" class="btn btn-success px-4">
                    <i class="fas fa-save"></i> Lưu thay đổi
                </button>
                <a href="<c:url value='/view' />" class="btn btn-secondary px-4">
                    <i class="fas fa-arrow-left"></i> Quay lại
                </a>
            </div>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
