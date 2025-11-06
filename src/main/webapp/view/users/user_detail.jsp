<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Details</title>
    <link rel="stylesheet" href="<c:url value='/css/user_detail.css'/>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .container {
            width: 70%;
            margin: 30px auto;
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
            padding: 30px;
            font-family: "Segoe UI", sans-serif;
        }
        h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #2b5ca8;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        td {
            padding: 10px;
            border-bottom: 1px solid #eee;
        }
        td:first-child {
            font-weight: bold;
            width: 30%;
            color: #333;
        }
        .btn-back, .btn-create {
            display: inline-block;
            margin-top: 20px;
            background: #2b5ca8;
            color: white;
            padding: 10px 20px;
            border-radius: 8px;
            text-decoration: none;
        }
        .btn-back:hover, .btn-create:hover {
            background: #1e4787;
        }
        .status-yes {
            color: green;
            font-weight: bold;
        }
        .status-no {
            color: red;
            font-weight: bold;
        }
        .button-group {
            margin-top: 25px;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>User Information</h2>

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

            <<tr>
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
                <a href="${pageContext.request.contextPath}/account/create" 
                   class="btn-back" 
                   style="background:#28a745;">+ Create Account</a>
            </c:otherwise>
        </c:choose>
    </td>
</tr>

        </table>

        <div class="button-group">
            <a href="${pageContext.request.contextPath}/userlist" class="btn-back">
                ← Back to List
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
