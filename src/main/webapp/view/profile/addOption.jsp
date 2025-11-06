<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Option - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { background-color: #f8faff; font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif; }
        h2 { color: #0d6efd; font-weight: 600; }
        .btn-primary { background-color: #a2d2ff; border-color: #a2d2ff; color: #0d3b66; }
        .btn-primary:hover { background-color: #7bb8ff; border-color: #7bb8ff; color: #0d3b66; }
        .btn-secondary { background-color: #d3e0ff; border-color: #d3e0ff; color: #0d3b66; }
        .btn-secondary:hover { background-color: #aebfff; border-color: #aebfff; color: #0d3b66; }
        .alert { border-radius: 0.375rem; }
        table { background-color: #ffffff; }
        th { background-color: #e0f0ff; }
    </style>
</head>
<body>
<div class="container py-4">

    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2><i class="fas fa-plus-circle"></i> Thêm Option</h2>
        <a href="<c:url value='/home'/>" class="btn btn-secondary"><i class="fas fa-home"></i> Back to Home</a>
    </div>

    <!-- Alerts -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger">${errorMessage}</div>
    </c:if>

    <!-- Add Form -->
    <form method="post" action="<c:url value='/option/add'/>" class="row g-3 mb-4">
        <div class="col-md-3">
            <label class="form-label">Type</label>
            <select name="type" class="form-select">
                <option value="position" <c:if test="${type=='position'}">selected</c:if>>Position</option>
                <option value="department" <c:if test="${type=='department'}">selected</c:if>>Department</option>
                <option value="degree" <c:if test="${type=='degree'}">selected</c:if>>Degree</option>
            </select>
        </div>
        <div class="col-md-6">
            <label class="form-label">Name</label>
            <input type="text" name="name" value="" class="form-control" placeholder="Nhập tên...">
        </div>
        <div class="col-md-3 d-flex align-items-end">
            <button type="submit" class="btn btn-primary w-100"><i class="fas fa-plus"></i> Thêm</button>
        </div>
    </form>

    <!-- Existing Lists -->
    <div class="row">
        <div class="col-md-4">
            <h5>Positions</h5>
            <ul class="list-group mb-3">
                <c:forEach var="pos" items="${positions}">
                    <li class="list-group-item">${pos.name}</li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-md-4">
            <h5>Departments</h5>
            <ul class="list-group mb-3">
                <c:forEach var="dep" items="${departments}">
                    <li class="list-group-item">${dep.name}</li>
                </c:forEach>
            </ul>
        </div>
        <div class="col-md-4">
            <h5>Degrees</h5>
            <ul class="list-group mb-3">
                <c:forEach var="deg" items="${degrees}">
                    <li class="list-group-item">${deg.name}</li>
                </c:forEach>
            </ul>
        </div>
    </div>

</div>
</body>
</html>
