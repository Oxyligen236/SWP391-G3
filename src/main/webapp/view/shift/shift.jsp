<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách ca làm</title>
    <link rel="stylesheet" href="<c:url value='/css/shift.css'/>">
</head>
<body>
<div class="shift-container">
    <h2>Shift List</h2>
    <table class="shift-table">
        <thead>
            <tr>
                <th>ID</th>
                <th>Shift Name</th>
                <th>Checkin 1</th>
                <th>Checkout 1</th>
                <th>Checkin 2</th>
                <th>Checkout 2</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="s" items="${shifts}">
                <tr>
                    <td>${s.shiftID}</td>
                    <td>${s.name}</td>
                    <td>${s.checkin1}</td>
                    <td>${s.checkout1}</td>
                    <td>${s.checkin2}</td>
                    <td>${s.checkout2}</td>
                    <td>
                        <form action="EditShiftServlet" method="get" style="display:inline;">
                            <input type="hidden" name="shiftID" value="${s.shiftID}">
                            <button type="submit">Edit</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
