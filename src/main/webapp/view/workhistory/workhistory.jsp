<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
            <link rel="stylesheet" href="<c:url value='/css/workhistory.css'/>">
</head>
<body>
<div class="container">
    <header class="page-header">
        <h1>Work History</h1>
    </header>

    <div class="table-wrap">
        <table class="workhistory-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>User ID</th>
                <th>Type</th>
                <th>Effective Date</th>
                <th>Old Value</th>
                <th>New Value</th>
                <th>Description</th>
                <th>Evaluate</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="wh" items="${workHistoryList}">
                <tr>
                    <td>${wh.historyID}</td>
                    <td>${wh.userID}</td>
                    <td>${wh.type}</td>
                    <td><fmt:formatDate value="${wh.effectiveDate}" pattern="yyyy-MM-dd"/></td>
                    <td><c:out value="${wh.oldValue}"/></td>
                    <td><c:out value="${wh.newValue}"/></td>
                    <td><c:out value="${wh.description}"/></td>
                    <td><c:out value="${wh.evaluate}"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
