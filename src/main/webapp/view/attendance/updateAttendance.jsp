<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%@ page import="hrms.model.JobDescription" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Attendance</title>
        <link rel="stylesheet" href="<c:url value='/css/update-attendance.css'/>">
</head>
<body>
<div class="container">
    <h3>Update Attendance Record</h3>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/updateattendance" method="post" class="form-custom">
        <input type="hidden" name="attendanceID" value="${attendance.attendanceID}"/>

        <label>User ID:</label>
        <input type="number" name="userID" value="${attendance.userID}" required/>

        <label>Date:</label>
        <input type="date" name="date" value="${attendance.date}" required/>

        <label>Day:</label>
        <input type="text" name="day" value="${attendance.day}" required/>

        <label>Checkin 1:</label>
        <input type="time" name="checkin1" value="${attendance.checkin1}"/>

        <label>Checkout 1:</label>
        <input type="time" name="checkout1" value="${attendance.checkout1}"/>

        <label>Checkin 2:</label>
        <input type="time" name="checkin2" value="${attendance.checkin2}"/>

        <label>Checkout 2:</label>
        <input type="time" name="checkout2" value="${attendance.checkout2}"/>

        <label>Checkin 3:</label>
        <input type="time" name="checkin3" value="${attendance.checkin3}"/>

        <label>Checkout 3:</label>
        <input type="time" name="checkout3" value="${attendance.checkout3}"/>

        <label>Shift ID:</label>
        <input type="number" name="shiftID" value="${attendance.shiftID}" required/>

        <label>Late Minutes:</label>
        <input type="time" name="lateMinutes" value="${attendance.lateMinutes}"/>

        <label>Early Leave Minutes:</label>
        <input type="time" name="earlyLeaveMinutes" value="${attendance.earlyLeaveMinutes}"/>

        <label>Total Work Hours:</label>
        <input type="time" name="totalWorkHours" value="${attendance.totalWorkHours}"/>

        <label>OT Hours:</label>
        <input type="time" name="otHours" value="${attendance.otHours}"/>

        <div class="actions">
            <button type="submit">Update</button>
            <a href="${pageContext.request.contextPath}/company-attendance">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>
