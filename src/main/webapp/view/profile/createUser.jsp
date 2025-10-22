<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="hrms.model.Department, hrms.model.Position, hrms.model.Degree, java.util.List" %>

<c:url value="/user/create" var="createUserUrl" />
<c:url value="/user/list" var="userListUrl" />

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Tạo User</title>
    <link rel="stylesheet" href="<c:url value='/css/create-user.css'/>">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<div class="container">
    <h2>Tạo User Mới</h2>

    <!-- Hiển thị lỗi nếu có -->
    <c:if test="${not empty error}">
        <div class="message error">
            <i class="fas fa-exclamation-circle"></i> ${error}
        </div>
    </c:if>

    <form action="${createUserUrl}" method="post">
        <label>Họ và Tên:</label>
        <input type="text" name="fullname" required value="<c:out value='${formFullname}'/>">

        <label>Email:</label>
        <input type="email" name="email" required value="<c:out value='${formEmail}'/>">

        <label>Số điện thoại:</label>
        <input type="text" name="phoneNumber" value="<c:out value='${formPhoneNumber}'/>">

        <label>Ngày sinh:</label>
        <input type="date" name="birthDate" value="<c:out value='${formBirthDate}'/>">

        <label>Giới tính:</label>
        <select name="gender" required>
            <option value="">--Chọn giới tính--</option>
            <option value="Male" <c:if test="${formGender == 'Male'}">selected</c:if>>Nam</option>
            <option value="Female" <c:if test="${formGender == 'Female'}">selected</c:if>>Nữ</option>
            <option value="Other" <c:if test="${formGender == 'Other'}">selected</c:if>>Khác</option>
        </select>

        <label>Địa chỉ:</label>
        <input type="text" name="address" value="<c:out value='${formAddress}'/>">

        <label>Dân tộc:</label>
        <input type="text" name="ethnicity" value="<c:out value='${formEthnicity}'/>">

        <label>Quốc tịch:</label>
        <input type="text" name="nation" value="<c:out value='${formNation}'/>">

        <label>CCCD/CMND:</label>
        <input type="text" name="cccd" value="<c:out value='${formCccd}'/>">

        <label>Phòng ban:</label>
        <select name="departmentId">
            <option value="">--Chọn phòng ban--</option>
            <c:forEach var="d" items="${departments}">
                <option value="${d.departmentId}" <c:if test="${formDepartmentId == d.departmentId}">selected</c:if>>
                    ${d.name}
                </option>
            </c:forEach>
        </select>

        <label>Chức vụ:</label>
        <select name="positionId">
            <option value="">--Chọn chức vụ--</option>
            <c:forEach var="p" items="${positions}">
                <option value="${p.positionId}" <c:if test="${formPositionId == p.positionId}">selected</c:if>>
                    ${p.name}
                </option>
            </c:forEach>
        </select>

        <label>Bằng cấp:</label>
        <select name="degreeId">
            <option value="">--Chọn bằng cấp--</option>
            <c:forEach var="deg" items="${degrees}">
                <option value="${deg.degreeId}" <c:if test="${formDegreeId == deg.degreeId}">selected</c:if>>
                    ${deg.name}
                </option>
            </c:forEach>
        </select>

        <div class="buttons">
            <button type="submit"><i class="fas fa-user-plus"></i> Tạo User</button>
            <a href="<c:url value='/home'/>"><i class="fas fa-times"></i> Hủy</a>
        </div>
    </form>

    <!-- Popup khi tạo user thành công -->
    <c:if test="${not empty success}">
        <script>
            Swal.fire({
                icon: 'success',
                title: 'Tạo User Thành Công!',
                text: '${success}',
                confirmButtonText: 'OK'
            });
        </script>
    </c:if>
</div>
</body>
</html>
