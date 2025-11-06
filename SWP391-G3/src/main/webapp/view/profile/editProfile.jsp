<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chỉnh Sửa Thông Tin Cá Nhân - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a2e0e6ad53.js" crossorigin="anonymous"></script>
    <style>
        body {
            background-color: #f0f5ff; /* pastel xanh nhạt */
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        }

        .profile-container {
            max-width: 900px;
            margin: 40px auto;
        }

        h2.page-title {
            color: #0d3b66;
            font-weight: 700;
            text-align: center;
            margin-bottom: 25px;
        }

        form {
            background-color: #ffffff; /* trắng pastel */
            border-radius: 15px;
            padding: 30px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.08);
        }

        .info-card {
            background-color: #e6f0ff; /* pastel xanh nhạt */
            padding: 20px;
            border-radius: 12px;
            margin-bottom: 25px;
        }

        .info-label {
            font-weight: 500;
            color: #0d3b66;
        }

        .btn-custom {
            border-radius: 8px;
            padding: 8px 20px;
            font-weight: 500;
        }

        .btn-primary {
            background-color: #a2d2ff;
            color: #0d3b66;
            border: none;
        }

        .btn-primary:hover {
            background-color: #a2d2ff;
            color: #0d3b66;
        }

        .btn-outline-primary {
            color: #0d3b66;
            border-color: #0d3b66;
        }

        .btn-outline-primary:hover {
            background-color: #ffffff;
            color: #0d3b66;
        }

        hr {
            border-top: 2px solid #0d3b66;
        }

        .alert-success {
            background-color: #d1e7dd;
            color: #0f5132;
        }

        .alert-danger {
            background-color: #f8d7da;
            color: #842029;
        }

        @media (max-width: 768px) {
            form {
                padding: 20px;
            }
        }
    </style>
</head>

<body>
    <div class="profile-container">
        <h2 class="page-title"><i class="fas fa-user-edit"></i> Chỉnh Sửa Thông Tin Cá Nhân</h2>

        <c:if test="${not empty successMessage}">
            <div class="alert alert-success text-center">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger text-center">${errorMessage}</div>
        </c:if>

        <form action="<c:url value='/edit' />" method="post">
            <!-- Thông tin cơ bản -->
            <div class="info-card">
                <h5 class="mb-3"><i class="fas fa-user"></i> Thông Tin Cơ Bản</h5>
                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="info-label">Họ và tên:</label>
                        <input type="text" class="form-control" name="fullname" value="${user.fullname}" required>
                    </div>
                    <div class="col-md-6">
                        <label class="info-label">CCCD:</label>
                        <input type="text" class="form-control" name="cccd" value="${user.cccd}">
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="info-label">Ngày sinh:</label>
                        <input type="date" class="form-control" name="birthDate"
                               value="<fmt:formatDate value='${user.birthDate}' pattern='yyyy-MM-dd'/>">
                    </div>
                    <div class="col-md-6">
                        <label class="info-label">Giới tính:</label>
                        <select name="gender" class="form-select">
                            <option value="Male" ${user.gender=='Male' ? 'selected' : '' }>Nam</option>
                            <option value="Female" ${user.gender=='Female' ? 'selected' : '' }>Nữ</option>
                            <option value="Other" ${user.gender=='Other' ? 'selected' : '' }>Khác</option>
                        </select>
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="info-label">Số điện thoại:</label>
                        <input type="text" class="form-control" name="phoneNumber" value="${user.phoneNumber}">
                    </div>
                    <div class="col-md-6">
                        <label class="info-label">Email:</label>
                        <input type="email" class="form-control" name="email" value="${user.email}">
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-12">
                        <label class="info-label">Địa chỉ:</label>
                        <input type="text" class="form-control" name="address" value="${user.address}">
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="info-label">Dân tộc:</label>
                        <input type="text" class="form-control" name="ethnicity" value="${user.ethnicity}">
                    </div>
                    <div class="col-md-6">
                        <label class="info-label">Quốc tịch:</label>
                        <input type="text" class="form-control" name="nation" value="${user.nation}">
                    </div>
                </div>
            </div>

            <!-- Thông tin công việc -->
            <div class="info-card">
                <h5 class="mb-3"><i class="fas fa-briefcase"></i> Thông Tin Công Việc</h5>
                <div class="row mb-3">
                    <div class="col-md-4">
                        <label class="info-label">Phòng ban:</label>
                        <select name="departmentId" class="form-select" <c:if test="${roleId != 2 && roleId != 3}">disabled</c:if>>
                            <option value="">-- Chọn phòng ban --</option>
                            <c:forEach var="dept" items="${departmentList}">
                                <option value="${dept.departmentId}" ${user.departmentId==dept.departmentId?'selected':''}>
                                    ${dept.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-4">
                        <label class="info-label">Chức vụ:</label>
                        <select name="positionId" class="form-select" <c:if test="${roleId != 2 && roleId != 3}">disabled</c:if>>
                            <option value="">-- Chọn chức vụ --</option>
                            <c:forEach var="pos" items="${positionList}">
                                <option value="${pos.positionId}" ${user.positionId==pos.positionId?'selected':''}>
                                    ${pos.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-4">
                        <label class="info-label">Bằng cấp:</label>
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
            </div>

            <!-- Nút hành động -->
            <div class="text-center mt-4">
                <button type="submit" class="btn btn-primary btn-custom px-4"><i class="fas fa-save"></i> Lưu thay đổi</button>
                <a href="<c:url value='/view' />" class="btn btn-outline-primary btn-custom px-4"><i class="fas fa-arrow-left"></i> Quay lại</a>
            </div>

        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
