<%@ page contentType="text/html; charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>

            <html>

            <head>
                <title>My Attendance</title>
                <link rel="stylesheet" href="<c:url value='/css/myattendance.css'/>">
            </head>

            <body>
                <h2>My Attendance Records</h2>

                <table border="1">
                    <thead>
                        <tr>
                            <th>Date</th>
                            <th>Day</th>
                            <th>Checkin 1</th>
                            <th>Checkout 1</th>
                            <th>Checkin 2</th>
                            <th>Checkout 2</th>
                            <th>Total Hours</th>
                            <th>OT Hours</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="a" items="${attendanceList}">
                            <tr>
                                <td>${a.date}</td>
                                <td>${a.day}</td>
                                <td>${a.checkin1}</td>
                                <td>${a.checkout1}</td>
                                <td>${a.checkin2}</td>
                                <td>${a.checkout2}</td>
                                <td>${a.totalWorkHours}</td>
                                <td>${a.otHours}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </body>

            </html>