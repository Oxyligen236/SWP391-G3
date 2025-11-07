<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Users List</title>
    <link rel="stylesheet" href="css/userlist.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        /* --- Style trực tiếp cho Search, Filter, Reset --- */
        .search-form {
            display: flex;
            flex-wrap: wrap;
            align-items: center;
            gap: 10px;
            margin-bottom: 20px;
        }
        .search-form input[type="text"] {
            padding: 8px 12px;
            border: 1px solid #cbd5e1;
            border-radius: 6px;
            font-size: 0.9rem;
            width: 220px;
            transition: border-color 0.2s, box-shadow 0.2s;
        }
        .search-form input[type="text"]:focus {
            border-color: #00a884;
            outline: none;
            box-shadow: 0 0 0 2px rgba(0, 168, 132, 0.2);
        }
        .search-form label {
            font-weight: 500;
            color: #475569;
        }
        .search-form select {
            padding: 8px 12px;
            border-radius: 6px;
            border: 1px solid #cbd5e1;
            font-size: 0.9rem;
            transition: border-color 0.2s, box-shadow 0.2s;
        }
        .search-form select:focus {
            border-color: #00a884;
            outline: none;
            box-shadow: 0 0 0 2px rgba(0, 168, 132, 0.2);
        }
        .search-form button[type="submit"] {
            padding: 8px 16px;
            border-radius: 6px;
            background-color: #00a884;
            color: #fff;
            border: none;
            font-weight: 600;
            cursor: pointer;
            transition: background-color 0.2s;
        }
        .search-form button[type="submit"]:hover {
            background-color: #008f71;
        }
        .search-form .btn-reset {
            padding: 8px 16px;
            border-radius: 6px;
            border: 1px solid #cbd5e1;
            background-color: #e2e8f0;
            color: #475569;
            text-decoration: none;
            font-weight: 500;
            cursor: pointer;
            transition: background-color 0.2s, border-color 0.2s;
        }
        .search-form .btn-reset:hover {
            background-color: #cbd5e1;
            border-color: #94a3b8;
        }
    </style>
</head>
<body>
<div class="container">
    <h3>Users List</h3>

    <c:if test="${not empty successMessage}">
        <div class="alert alert-success" id="successAlert">
            <i class="fas fa-check-circle"></i> ${successMessage}
        </div>
        <c:remove var="successMessage" scope="session"/>
    </c:if>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-error" id="errorAlert">
            <i class="fas fa-exclamation-circle"></i> ${errorMessage}
        </div>
        <c:remove var="errorMessage" scope="session"/>
    </c:if>

    <!-- Search + Filter + Reset -->
    <form method="get" action="${pageContext.request.contextPath}/userlist" class="search-form">
        <input type="text" name="keyword" placeholder="Search by fullname"
               value="${param.keyword != null ? param.keyword : ''}" />

        <label for="accountFilter">Account:</label>
        <select name="accountFilter" id="accountFilter" onchange="this.form.submit()">
            <option value="">All</option>
            <option value="yes" ${param.accountFilter == 'yes' ? 'selected' : ''}>Yes</option>
            <option value="no" ${param.accountFilter == 'no' ? 'selected' : ''}>No</option>
        </select>

        <button type="submit">Search</button>
        <a href="${pageContext.request.contextPath}/userlist" class="btn-reset">Reset</a>
    </form>

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
                <td style="text-align: center;">
                    <c:choose>
                        <c:when test="${userHasAccount.contains(u.userId)}">
                            <span class="account-status active">Yes</span>
                        </c:when>
                        <c:otherwise>
                            <span class="account-status inactive">No</span>
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
