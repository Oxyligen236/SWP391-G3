<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Users List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="<c:url value='/css/userlist.css'/>">
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
            <th style="text-align: center;">ID</th> <th>Name</th>
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
                <td> <c:choose>
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

    <div class="items-per-page mt-3 mb-3">
        <form method="get" action="${pageContext.request.contextPath}/userlist" class="d-inline">
            <label for="itemsPerPage">Items per page:</label>
            <select name="itemsPerPage" id="itemsPerPage" onchange="this.form.submit()">
                <option value="5" ${itemsPerPage == 5 ? 'selected' : ''}>5</option>
                <option value="10" ${itemsPerPage == 10 ? 'selected' : ''}>10</option>
                <option value="20" ${itemsPerPage == 20 ? 'selected' : ''}>20</option>
            </select>
            <input type="hidden" name="keyword" value="${param.keyword}">
            <input type="hidden" name="accountFilter" value="${param.accountFilter}">
        </form>
    </div>

    <div class="pagination-container mb-4">
        <ul class="pagination justify-content-center">
            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                <a class="page-link" href="?page=${currentPage - 1}&itemsPerPage=${itemsPerPage}&keyword=${param.keyword}&accountFilter=${param.accountFilter}">Previous</a>
            </li>
            <li class="page-item disabled">
                <span class="page-link">Page ${currentPage} / ${totalPages}</span>
            </li>
            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                <a class="page-link" href="?page=${currentPage + 1}&itemsPerPage=${itemsPerPage}&keyword=${param.keyword}&accountFilter=${param.accountFilter}">Next</a>
            </li>
        </ul>
    </div>
</div>

<script>
    setTimeout(function () {
        const successAlert = document.getElementById('successAlert');
        const errorAlert = document.getElementById('errorAlert');
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