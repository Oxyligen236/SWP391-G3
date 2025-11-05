<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách công việc</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="<c:url value='/css/jd_guest.css'/>">
</head>

<body>
    <h2>Danh sách công việc</h2>

    <table>
        <tr>
            <th>ID</th>
            <th>Title</th>
            <th>Start</th>
            <th>End</th>
            <th>Department</th>
            <th>Vacancies</th>
            <th>Responsibilities</th>
            <th>Requirements</th>
            <th>Compensation</th>
            <th>Action</th>
        </tr>

        <c:choose>
            <c:when test="${not empty jdList}">
                <c:forEach var="jd" items="${jdList}">
                    <tr>
                        <td>${jd.jobID}</td>
                        <td>${jd.jobTitle}</td>
                        <td>${jd.startDate}</td>
                        <td>${jd.endDate}</td>
                        <td>${jd.department}</td>
                        <td>${jd.vacancies}</td>
                        <td>${jd.responsibilities}</td>
                        <td>${jd.requirements}</td>
                        <td>${jd.compensation}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/cv/submit?jdID=${jd.jobID}&title=${jd.jobTitle}" class="btn-edit">
                                Apply
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="10">Không có công việc nào</td>
                </tr>
            </c:otherwise>
        </c:choose>
    </table>
</body>
</html>
