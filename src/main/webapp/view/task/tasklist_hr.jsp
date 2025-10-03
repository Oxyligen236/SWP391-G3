<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>


<html>
<head>
    <title>Your Task List</title>
</head>
<body>
    <h3>Your task list </h3>

    <table>
        <thead>
            <tr>
                <th>Title</th>
                <th>Assigned to</th>
                <th>Start date</th>
                <th>End date</th>
                <th>Status</th>
                <th>Report</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="task" items="${tasks}">
                <tr>
                    <td>${task.title}</td>
                    <td>${task.assignedBy}</td>
                    <td>${task.startDate}</td>
                    <td>${task.endDate}</td>
                    <td>${task.status}</td>
                    <td><a href="report/${task.taskId}">View report</a></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
