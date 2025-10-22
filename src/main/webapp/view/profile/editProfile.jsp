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
        <div class="profile-header">
            <h2>Chỉnh Sửa Thông Tin Cá Nhân</h2>
            <hr>
        </div>

        <!-- Thông báo lỗi / thành công -->
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success text-center">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger text-center">${errorMessage}</div>
        </c:if>

        <form action="<c:url value='/edit' />" method="post">
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="info-label">Họ và tên:</label>
                    <input type="text" name="fullname" value="${user.fullname}" required>
                </div>
                <div class="col-md-6">
                    <label class="info-label">CCCD:</label>
                    <input type="text" name="cccd" value="${user.cccd}">
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="info-label">Ngày sinh:</label>
                    <input type="date" name="birthDate"
                        value="<fmt:formatDate value='${user.birthDate}' pattern='yyyy-MM-dd'/>">
                </div>
                <div class="col-md-6">
                    <label class="info-label">Giới tính:</label>
                    <select name="gender">
                        <option value="Male" ${user.gender=='Male' ? 'selected' : '' }>Nam</option>
                        <option value="Female" ${user.gender=='Female' ? 'selected' : '' }>Nữ</option>
                        <option value="Other" ${user.gender=='Other' ? 'selected' : '' }>Khác</option>
                    </select>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="info-label">Số điện thoại:</label>
                    <input type="text" name="phoneNumber" value="${user.phoneNumber}">
                </div>
                <div class="col-md-6">
                    <label class="info-label">Email:</label>
                    <input type="email" name="email" value="${user.email}">
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-12">
                    <label class="info-label">Địa chỉ:</label>
                    <input type="text" name="address" value="${user.address}">
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-4">
                    <label class="info-label">Dân tộc:</label>
                    <input type="text" name="ethnicity" value="${user.ethnicity}">
                </div>
                <div class="col-md-4">
                    <label class="info-label">Quốc tịch:</label>
                    <input type="text" name="nation" value="${user.nation}">
                </div>
                <div class="col-md-4">
                    <label class="info-label">Trình độ học vấn:</label>
                    <select name="degreeId">
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
                <button type="submit" class="btn btn-edit btn-custom">
                    <i class="fas fa-save"></i>Lưu thay đổi
                </button>
                <a href="<c:url value='/view' />" class="btn btn-back btn-custom">
                    <i class="fas fa-arrow-left"></i>Quay lại
                </a>
            </div>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
