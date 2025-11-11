<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
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
        <input type="number" name="userID" value="${attendance.userID}" readonly style="background-color:#e2e3e5;"/>

        <label>Date:</label>
        <input type="date" name="date" value="${attendance.date}" readonly style="background-color:#e2e3e5;"/>

        <label>Day:</label>
        <input type="text" name="day" value="${attendance.day}" readonly style="background-color:#e2e3e5;"/>

        <label>Checkin 1:</label>
        <input type="time" name="checkin1" value="${attendance.checkin1 != null ? attendance.checkin1 : ''}"/>

        <label>Checkout 1:</label>
        <input type="time" name="checkout1" value="${attendance.checkout1 != null ? attendance.checkout1 : ''}"/>

        <label>Checkin 2:</label>
        <input type="time" name="checkin2" value="${attendance.checkin2 != null ? attendance.checkin2 : ''}"/>

        <label>Checkout 2:</label>
        <input type="time" name="checkout2" value="${attendance.checkout2 != null ? attendance.checkout2 : ''}"/>

        <label>Checkin 3:</label>
        <input type="time" name="checkin3" value="${attendance.checkin3 != null ? attendance.checkin3 : ''}"/>

        <label>Checkout 3:</label>
        <input type="time" name="checkout3" value="${attendance.checkout3 != null ? attendance.checkout3 : ''}"/>

        <label>Shift ID:</label>
        <input type="number" name="shiftID" value="${attendance.shiftID}" readonly style="background-color:#e2e3e5;"/>

        <label>Late Minutes:</label>
        <input type="text" name="lateMinutes" readonly style="background-color:#e2e3e5;"
               value="${attendance.lateMinutes != null ? attendance.lateMinutes : ''}"/>

        <label>Early Leave Minutes:</label>
        <input type="text" name="earlyLeaveMinutes" readonly style="background-color:#e2e3e5;"
               value="${attendance.earlyLeaveMinutes != null ? attendance.earlyLeaveMinutes : ''}"/>

        <label>Total Work Hours:</label>
        <input type="text" name="totalWorkHours" readonly style="background-color:#e2e3e5;"
               value="${attendance.totalWorkHours != null ? attendance.totalWorkHours : ''}"/>

        <label>OT Hours:</label>
        <input type="text" name="otHours" readonly style="background-color:#e2e3e5;"
               value="${attendance.otHours != null ? attendance.otHours : ''}"/>

        <div class="actions">
            <button type="submit">Update</button>
            <a href="${pageContext.request.contextPath}/company-attendance">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>
