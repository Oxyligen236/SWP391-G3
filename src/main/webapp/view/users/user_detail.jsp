<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Details</title>
    <link rel="stylesheet" href="<c:url value='/css/user_detail.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<div class="container">
    <h2>User Information</h2>

    <!-- Success Message -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">
            <i class="fas fa-check-circle"></i> ${successMessage}
        </div>
        <c:remove var="successMessage" scope="session"/>
    </c:if>

    <!-- Error Message -->
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error">
            <i class="fas fa-exclamation-circle"></i> ${errorMessage}
        </div>
        <c:remove var="errorMessage" scope="session"/>
    </c:if>

    <c:if test="${not empty user}">
        <table>
            <tr><td>ID:</td><td>${user.userId}</td></tr>
            <tr><td>Full Name:</td><td>${user.fullname}</td></tr>
            <tr><td>Email:</td><td>${user.email}</td></tr>
            <tr><td>Phone Number:</td><td>${user.phoneNumber}</td></tr>
            <tr><td>Birth Date:</td><td>${user.birthDate}</td></tr>
            <tr><td>Gender:</td><td>${user.gender}</td></tr>
            <tr><td>Address:</td><td>${user.address}</td></tr>
            <tr><td>Citizen ID:</td><td>${user.cccd}</td></tr>
            <tr><td>Ethnicity:</td><td>${user.ethnicity}</td></tr>
            <tr><td>Nationality:</td><td>${user.nation}</td></tr>
            <tr><td>Department:</td><td>${user.departmentName}</td></tr>
            <tr><td>Degree:</td><td>${user.degreeName}</td></tr>
            <tr><td>Position:</td><td>${user.positionName}</td></tr>

            <tr>
                <td>System Account:</td>
                <td>
                    <c:choose>
                        <c:when test="${hasAccount}">
                            <strong>${account.username}</strong>
                            <br>(Role: ${account.role},
                            Active:
                            <c:out value="${account.isIsActive() ? 'Yes' : 'No'}"/>)
                        </c:when>
                        <c:otherwise>
                            <em>This user has no system account.</em><br>
                            <a href="${pageContext.request.contextPath}/account/create?userId=${user.userId}"
                               class="btn-create">+ Create Account</a>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>

        </table>

        <div class="button-group">
            <a href="${pageContext.request.contextPath}/userlist" class="btn-back">
                <i class="fas fa-arrow-left"></i> Back to List
            </a>
            <a href="${pageContext.request.contextPath}/updateDepartment?userID=${user.userId}" class="btn-edit-dept">
                <i class="fas fa-building"></i> Edit Department
            </a>
            <a href="${pageContext.request.contextPath}/updatePosition?userID=${user.userId}" class="btn-edit-pos">
                <i class="fas fa-briefcase"></i> Edit Position
            </a>
        </div>
    </c:if>

    <c:if test="${empty user}">
        <p>User not found.</p>
        <a href="${pageContext.request.contextPath}/userlist" class="btn-back">← Back</a>
    </c:if>
</div>
</body>
</html>