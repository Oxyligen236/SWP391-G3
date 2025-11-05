<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%@ page import="hrms.model.JobDescription" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Job Description List</title>
    <link rel="stylesheet" href="<c:url value='/css/jd-list.css'/>">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>

<div class="container py-4">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2><i class="fas fa-briefcase"></i> Job Description List</h2>
    </div>

    <!-- Thông báo -->
    <c:if test="${not empty sessionScope.message}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="fas fa-check-circle"></i> ${sessionScope.message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
        <c:remove var="message" scope="session"/>
    </c:if>

    <!-- Form Search + Filter -->
    <form class="row g-3 mb-3 align-items-end" method="get" action="<c:url value='/jdlist'/>">

        <!-- Search -->
        <div class="col-md-4">
            <label class="form-label">Search by Title</label>
            <div class="input-group">
                <input type="text" name="search" value="${search}" class="form-control"
                       placeholder="Enter job title...">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-search"></i> Search
                </button>
            </div>
        </div>

        <!-- Department -->
        <div class="col-md-3">
            <label class="form-label">Department</label>
            <select name="department" class="form-select" onchange="this.form.submit()">
                <option value="">All</option>
                <option value="HR Department" ${departmentFilter == 'HR Department' ? 'selected' : ''}>HR Department</option>
                <option value="IT" ${departmentFilter == 'IT' ? 'selected' : ''}>IT</option>
                <option value="Marketing" ${departmentFilter == 'Marketing' ? 'selected' : ''}>Marketing</option>
                <option value="Operate" ${departmentFilter == 'Operate' ? 'selected' : ''}>Operate</option>
                <option value="Business" ${departmentFilter == 'Business' ? 'selected' : ''}>Business</option>
            </select>
        </div>

        <!-- Status -->
        <div class="col-md-3">
            <label class="form-label">Status</label>
            <select name="status" class="form-select" onchange="this.form.submit()">
                <option value="">All</option>
                <option value="Pending" ${statusFilter == 'Pending' ? 'selected' : ''}>Pending</option>
                <option value="InProgress" ${statusFilter == 'InProgress' ? 'selected' : ''}>In Progress</option>
                <option value="Completed" ${statusFilter == 'Completed' ? 'selected' : ''}>Completed</option>
                <option value="Cancelled" ${statusFilter == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
            </select>
        </div>



        <!-- Reset -->
        <div class="col-md-1 d-flex">
            <a href="<c:url value='/jdlist'/>" class="btn btn-secondary align-self-end w-100" title="Reset">
                <i class="fas fa-rotate-right"></i>
            </a>
        </div>
    </form>

    <!-- Bảng JD -->
    <div class="table-responsive">
        <table class="table table-hover table-bordered align-middle">
            <thead class="table-dark">
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
                <th>Status</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="jd" items="${jdList}">
                <tr>
                    <td>${jd.jobID}</td>
                    <td>${jd.jobTitle}</td>
                    <td>${jd.startDate}</td>
                    <td>${jd.endDate}</td>
                    <td>${jd.department}</td>
                    <td>${jd.vacancies}</td>
                    <td >${jd.responsibilities}</td>
                    <td>${jd.requirements}</td>
                    <td>${jd.compensation}</td>
                    <td class="status ${jd.status}">${jd.status}</td>
                    <td>
                        <a href="viewjd?id=${jd.jobID}" class="btn btn-sm btn-primary">View</a>
                        <a href="pendingjd?id=${jd.jobID}" class="btn btn-sm btn-warning"
                           onclick="return confirm('Suspend this JD?');">Suspend</a>
                        <a href="canceljd?id=${jd.jobID}" class="btn btn-sm btn-danger"
                           onclick="return confirm('Cancel this JD?');">Cancel</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Không có JD -->
    <c:if test="${empty jdList}">
        <div class="alert alert-info text-center mt-3">
            <i class="fas fa-info-circle"></i> No job descriptions found.
        </div>
    </c:if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
