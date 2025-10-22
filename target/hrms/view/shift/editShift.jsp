<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa ca làm</title>
    <link rel="stylesheet" href="<c:url value='/css/shift.css'/>">
</head>
<body>
<div class="shift-container">
    <h2>Chỉnh sửa ca làm việc</h2>

    <form action="EditShiftServlet" method="post">
        <input type="hidden" name="shiftID" value="${shift.shiftID}">

        <label>Tên ca:</label>
        <input type="text" name="name" value="${shift.name}" required><br>

        <label>Checkin 1:</label>
        <input type="time" name="checkin1" value="${shift.checkin1}" required><br>

        <label>Checkout 1:</label>
        <input type="time" name="checkout1" value="${shift.checkout1}" required><br>

        <label>Checkin 2:</label>
        <input type="time" name="checkin2" value="${shift.checkin2}" required><br>

        <label>Checkout 2:</label>
        <input type="time" name="checkout2" value="${shift.checkout2}" required><br>

        <button type="submit">Lưu thay đổi</button>
        <a href="/shift">Hủy</a>
    </form>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>
</div>
</body>
</html>
