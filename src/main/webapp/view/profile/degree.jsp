<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Degree Management - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/css/cv-list.css'/>">
</head>

<body>
<div class="cv-list-container">

    <!-- Header -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>Degree Management</h1>
    </div>

    <!-- Alerts -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success alert-dismissible fade show">
            ${successMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger alert-dismissible fade show">
            ${errorMessage}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <!-- Add Degree Form -->
    <div class="card mb-4">
        <div class="card-body">
            <form method="post" action="<c:url value='/option/degree'/>" class="row g-3 align-items-end">
                <div class="col-md-8">
                    <label class="form-label" for="name">Degree Name *</label>
                    <input type="text" name="name" id="name" class="form-control" 
                           placeholder="E.g., Bachelor, Master, PhD..." maxlength="100" required>
                </div>
                <div class="col-md-4">
                    <button type="submit" class="btn btn-primary w-100">Add Degree</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Degree List -->
    <div class="card">
        <div class="card-header">Degree List (${degrees.size()} items)</div>
        <div class="list-group list-group-flush">
            <c:choose>
                <c:when test="${not empty degrees}">
                    <c:forEach var="deg" items="${degrees}">
                        <div class="list-group-item d-flex justify-content-between align-items-center">
                            <span>${deg.name}</span>
                            <span class="badge bg-info rounded-pill">${deg.degreeId}</span>
                        </div>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="list-group-item text-center text-muted py-4">
                        No degrees found
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
