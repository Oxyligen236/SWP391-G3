<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%@ page import="hrms.model.JobDescription" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Job Description List</title>
        <link rel="stylesheet" href="<c:url value='/css/jd-list.css'/>">
    </head>
    <body>

        <div class="container">
            <h2>Job Description List</h2>

            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Status</th>
                        <th>Start</th>
                        <th>End</th>
                        <th>Department</th>
                        <th>Vacancies</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="jd" items="${jdList}">
                        <tr>
                            <td>${jd.jobID}</td>
                            <td>${jd.jobTitle}</td>
                            <td class="status ${jd.status}">${jd.status}</td>
                            <td>${jd.startDate}</td>
                            <td>${jd.endDate}</td>
                            <td>${jd.department}</td>
                            <td>${jd.vacancies}</td>
                            <td class="actions">
                                <a href="viewjd?id=${jd.jobID}" class="btn btn-view">View</a>
                                <a href="pendingjd?id=${jd.jobID}" class="btn btn-view"
                                   onclick="return confirm('Are you sure to Suspend this JD?');">Suspend</a>
                                <a href="canceljd?id=${jd.jobID}" class="btn btn-cancel"
                                   onclick="return confirm('Are you sure to cancel this JD?');">Cancel</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </body>
</html>
