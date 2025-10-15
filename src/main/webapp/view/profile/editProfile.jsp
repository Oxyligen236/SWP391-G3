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
    <style>
        body {
            background: linear-gradient(135deg, #ffe6f0, #ffb3d1);
            font-family: 'Poppins', sans-serif;
            margin: 0;
            padding: 0;
        }

        .profile-container {
            max-width: 900px;
            background: #fff;
            margin: 40px auto;
            padding: 30px 40px;
            border-radius: 20px;
            box-shadow: 0 12px 40px rgba(255, 105, 180, 0.25);
            transition: transform 0.3s, box-shadow 0.3s;
        }

        .profile-container:hover {
            transform: translateY(-5px);
            box-shadow: 0 15px 50px rgba(255, 105, 180, 0.35);
        }

        .profile-header {
            text-align: center;
            margin-bottom: 30px;
        }

        .profile-header h2 {
            font-size: 28px;
            font-weight: 600;
            color: #d63384;
            margin-bottom: 5px;
        }

        .profile-header hr {
            width: 60px;
            border-top: 3px solid #ff4da6;
            margin: 0 auto;
        }

        .info-label {
            font-weight: 600;
            color: #d63384;
            margin-top: 8px;
            display: block;
        }

        input,
        select {
            font-size: 16px;
            color: #2c3e50;
            margin-top: 4px;
            width: 100%;
            padding: 8px 12px;
            border-radius: 8px;
            border: 1px solid #ffc0cb;
            background: #fff0f5;
            transition: border 0.3s, box-shadow 0.3s;
        }

        input:focus,
        select:focus {
            border-color: #ff4da6;
            box-shadow: 0 0 8px rgba(255, 77, 166, 0.3);
            outline: none;
        }

        .btn-custom {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            border-radius: 30px;
            padding: 12px 30px;
            font-weight: 600;
            font-size: 1rem;
            transition: all 0.3s;
            cursor: pointer;
            text-decoration: none;
        }

        .btn-custom i {
            margin-right: 8px;
        }

        .btn-edit {
            background: linear-gradient(135deg, #ff80ab, #ff4da6);
            color: #fff;
            border: none;
            box-shadow: 0 5px 15px rgba(255, 105, 180, 0.4);
        }

        .btn-edit:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(255, 105, 180, 0.6);
        }

        .btn-back {
            background: #ffe4eb;
            color: #d63384;
            border: none;
        }

        .btn-back:hover {
            background: #ffd6e0;
            color: #b91d73;
        }

        .text-center.mt-4 {
            text-align: center;
            margin-top: 30px;
        }

        @media (max-width:768px) {
            .profile-container {
                padding: 20px;
            }

            .btn-custom {
                width: 100%;
                margin-bottom: 10px;
            }
        }
    </style>
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
