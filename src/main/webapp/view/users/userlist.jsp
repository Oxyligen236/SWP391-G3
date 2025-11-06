<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Users List</title>
    <link rel="stylesheet" href="css/userlist.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<div class="container">
    <h3>Users List</h3>

    <!-- Success Message -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success" id="successAlert">
            <i class="fas fa-check-circle"></i> ${successMessage}
        </div>
        <c:remove var="successMessage" scope="session"/>
    </c:if>

    <!-- Error Message -->
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error" id="errorAlert">
            <i class="fas fa-exclamation-circle"></i> ${errorMessage}
        </div>
        <c:remove var="errorMessage" scope="session"/>
    </c:if>

    <c:if test="${empty users}">
        <p>Không có dữ liệu để hiển thị.</p>
    </c:if>

    <table class="table-custom">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone number</th>
                <th>Birth date</th>
                <th>Gender</th>
                <th>Address</th>
                <th>CCCD</th>
                <th>Ethnicity</th>
                <th>Nationality</th>
                <th>Department</th>
                <th>Degree</th>
                <th>Position</th>
                <th>Account</th>
                <th>Actions</th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="u" items="${users}">
                <tr>
                    <td>${u.userId}</td>
                    <td>${u.fullname}</td>
                    <td>${u.email}</td>
                    <td>${u.phoneNumber}</td>
                    <td>${u.birthDate}</td>
                    <td>${u.gender}</td>
                    <td>${u.address}</td>
                    <td>${u.cccd}</td>
                    <td>${u.ethnicity}</td>
                    <td>${u.nation}</td>
                    <td>${u.departmentName}</td>
                    <td>${u.degreeName}</td>
                    <td>${u.positionName}</td>
<td>
    <c:choose>
        <c:when test="${userHasAccount.contains(u.userId)}">

            Yes
        </c:when>
        <c:otherwise>
            No
        </c:otherwise>
    </c:choose>
</td>

                    <td class="action-buttons">
                        <a href="${pageContext.request.contextPath}/user_detail?userID=${u.userId}" class="btn-edit">View</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<!-- Auto hide alerts after 5 seconds -->
<script>
setTimeout(function () {
    var successAlert = document.getElementById('successAlert');
    var errorAlert = document.getElementById('errorAlert');
    if (successAlert) {
        successAlert.style.transition = 'opacity 0.5s';
        successAlert.style.opacity = '0';
        setTimeout(() => successAlert.remove(), 500);
    }
    if (errorAlert) {
        errorAlert.style.transition = 'opacity 0.5s';
        errorAlert.style.opacity = '0';
        setTimeout(() => errorAlert.remove(), 500);
    }
}, 5000);
</script>
</body>
</html>
