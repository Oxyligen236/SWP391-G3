<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="hrms.model.Department, hrms.model.Position, hrms.model.Degree, java.util.List" %>

<c:url value="/user/create" var="createUserUrl" />
<c:url value="/userlist" var="userListUrl" />

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Tạo User Mới - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f0f5ff;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        }

        .card {
            border-radius: 15px;
            box-shadow: 0 6px 20px rgba(0,0,0,0.08);
            padding: 35px;
            background-color: #ffffff;
        }

        h3 {
            font-weight: 600;
            color: #0d3b66;
            margin-bottom: 30px;
            text-align: center;
        }

        .form-label {
            font-weight: 500;
            color: #0d3b66;
        }

        .add-option-input {
            margin-top: 5px;
            font-style: italic;
            background-color: #e6f0ff;
        }

        .message {
            padding: 12px;
            border-radius: 10px;
            margin-bottom: 20px;
            font-size: 0.95rem;
        }

        .message.success {
            background-color: #d1e7dd;
            color: #0f5132;
        }

        .message.error {
            background-color: #f8d7da;
            color: #842029;
        }

        .btn-primary {
            background-color: #0d6efd;
            border: none;
            font-weight: 500;
        }

        .btn-primary:hover {
            background-color: #0b5ed7;
        }

        .btn-outline-primary {
            color: #0d3b66;
            border-color: #0d3b66;
            font-weight: 500;
        }

        .btn-outline-primary:hover {
            background-color: #0d6efd;
            color: #fff;
        }

        .row.g-3 > .col-md-4, .row.g-3 > .col-md-6 {
            margin-bottom: 15px;
        }

        .form-actions {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            justify-content: center;
            margin-top: 25px;
        }

        @media (max-width: 768px) {
            .card {
                padding: 25px;
            }
            .form-actions {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-lg-10 col-xl-8">
            <div class="card">
                <h3><i class="fas fa-user-plus"></i> Tạo User Mới</h3>

                <!-- Thông báo -->
                <c:if test="${not empty success}">
                    <div class="message success">${success}</div>
                </c:if>
                <c:if test="${not empty error}">
                    <div class="message error">${error}</div>
                </c:if>

                <form action="${createUserUrl}" method="post">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label">Họ và Tên:</label>
                            <input type="text" name="fullname" class="form-control" required value="<c:out value='${formFullname}'/>">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Email:</label>
                            <input type="email" name="email" class="form-control" required value="<c:out value='${formEmail}'/>">
                        </div>

                        <div class="col-md-6">
                            <label class="form-label">Số điện thoại:</label>
                            <input type="text" name="phoneNumber" class="form-control" value="<c:out value='${formPhoneNumber}'/>">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Ngày sinh:</label>
                            <input type="date" name="birthDate" class="form-control" value="<c:out value='${formBirthDate}'/>">
                        </div>

                        <div class="col-md-6">
                            <label class="form-label">Giới tính:</label>
                            <select name="gender" class="form-select" required>
                                <option value="">--Chọn giới tính--</option>
                                <option value="Male" <c:if test="${formGender == 'Male'}">selected</c:if>>Nam</option>
                                <option value="Female" <c:if test="${formGender == 'Female'}">selected</c:if>>Nữ</option>
                                <option value="Other" <c:if test="${formGender == 'Other'}">selected</c:if>>Khác</option>
                            </select>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Địa chỉ:</label>
                            <input type="text" name="address" class="form-control" value="<c:out value='${formAddress}'/>">
                        </div>

                        <div class="col-md-4">
                            <label class="form-label">Dân tộc:</label>
                            <input type="text" name="ethnicity" class="form-control" value="<c:out value='${formEthnicity}'/>">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Quốc tịch:</label>
                            <input type="text" name="nation" class="form-control" value="<c:out value='${formNation}'/>">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">CCCD/CMND:</label>
                            <input type="text" name="cccd" class="form-control" value="<c:out value='${formCccd}'/>">
                        </div>

                        <!-- Department -->
                        <div class="col-md-4">
                            <label class="form-label">Phòng ban:</label>
                            <select name="departmentId" class="form-select">
                                <option value="">--Chọn phòng ban--</option>
                                <c:forEach var="d" items="${departments}">
                                    <option value="${d.departmentId}" <c:if test="${formDepartmentId == d.departmentId}">selected</c:if>>${d.name}</option>
                                </c:forEach>
                            </select>
                            <input type="text" name="newDepartment" class="form-control add-option-input" placeholder="Thêm phòng ban mới nếu cần">
                        </div>

                        <!-- Position -->
                        <div class="col-md-4">
                            <label class="form-label">Chức vụ:</label>
                            <select name="positionId" class="form-select">
                                <option value="">--Chọn chức vụ--</option>
                                <c:forEach var="p" items="${positions}">
                                    <option value="${p.positionId}" <c:if test="${formPositionId == p.positionId}">selected</c:if>>${p.name}</option>
                                </c:forEach>
                            </select>
                            <input type="text" name="newPosition" class="form-control add-option-input" placeholder="Thêm chức vụ mới nếu cần">
                        </div>

                        <!-- Degree -->
                        <div class="col-md-4">
                            <label class="form-label">Bằng cấp:</label>
                            <select name="degreeId" class="form-select">
                                <option value="">--Chọn bằng cấp--</option>
                                <c:forEach var="deg" items="${degrees}">
                                    <option value="${deg.degreeId}" <c:if test="${formDegreeId == deg.degreeId}">selected</c:if>>${deg.name}</option>
                                </c:forEach>
                            </select>
                            <input type="text" name="newDegree" class="form-control add-option-input" placeholder="Thêm bằng cấp mới nếu cần">
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-user-plus"></i> Tạo User
                        </button>
                        <button type="reset" class="btn btn-outline-primary">
                            <i class="fas fa-times"></i> Hủy
                        </button>
                        <a href="${userListUrl}" class="btn btn-outline-primary">
                            <i class="fas fa-list"></i> Danh sách User
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
