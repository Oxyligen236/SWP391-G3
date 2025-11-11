<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Job Detail</title>
        <link rel="stylesheet" href="<c:url value='/css/jd-detail.css'/>">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>

        <div class="container py-4">
            <h2 class="mb-4"><i class="fas fa-file-alt"></i> Job Detail</h2>

            <c:if test="${not empty jd}">
                <div class="card shadow p-4">
                    <h4>${jd.jobTitle}</h4>
                    <p><strong>Department:</strong> ${jd.department}</p>

                    <p><strong>Start Date:</strong> ${jd.startDate}</p>
                    <p><strong>End Date:</strong> ${jd.endDate}</p>
                    <p><strong>Responsibilities:</strong></p>
                    <p>${jd.responsibilities}</p>
                    <p><strong>Requirements:</strong></p>
                    <p>${jd.requirements}</p>
                    <p><strong>Compensation:</strong></p>
                    <p>${jd.compensation}</p>
                    <p><strong>Office Address:</strong> </p>
                    <p>${jd.officeAddress}</p>
                    <p><strong>Working Conditions:</strong></p>
                    <p>${jd.workingConditions}</p>
                </div>

                <div class="action-buttons">
                    <a href="<c:url value='/jd_guest'/>" class="btn btn-secondary">
                        <i class="fas fa-arrow-left"></i> Back to List
                    </a>
                    <a href="<c:url value='/cv/submit?jdID=${jd.jobID}&title=${jd.jobTitle}'/>" class="btn btn-success">
                        <i class="fas fa-user"></i> Apply
                    </a>
                </div>

            </c:if>

            <c:if test="${empty jd}">
                <div class="alert alert-warning">No job description found.</div>
            </c:if>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>