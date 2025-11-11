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
    <h2><i class="fas fa-briefcase"></i> Job Description List</h2>

    <!-- Form Search + Filter -->
    <form class="row g-3 mb-3 align-items-end" method="get" action="<c:url value='/jdlist'/>">
        <div class="col-md-4">
            <label class="form-label">Search by Title</label>
            <div class="input-group">
                <input type="text" name="search" value="${search}" class="form-control" placeholder="Enter job title...">
                <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i> Search</button>
            </div>
        </div>
        <div class="col-md-3">
            <label class="form-label">Department</label>
            <select name="department" class="form-select" onchange="this.form.submit()">
                <option value="">All</option>
                <c:forEach var="d" items="${departments}">
                    <option value="${d.name}" ${departmentFilter == d.name ? 'selected' : ''}>${d.name}</option>
                </c:forEach>
            </select>
        </div>
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
        <div class="col-md-1 d-flex">
            <a href="<c:url value='/jdlist'/>" class="btn btn-secondary align-self-end w-100" title="Reset">
                <i class="fas fa-rotate-right"></i>
            </a>
        </div>

        <input type="hidden" name="itemsPerPage" value="${itemsPerPage}" />
        <input type="hidden" name="page" value="${currentPage}" />
    </form>

    <!-- JD Table -->
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
                    <td>${jd.responsibilities}</td>
                    <td>${jd.requirements}</td>
                    <td>${jd.compensation}</td>
                    <td class="status ${jd.status}">${jd.status}</td>
                    <td>
                        <a href="jd_detail?id=${jd.jobID}" class="btn btn-sm btn-primary">View</a>

                        <!-- Giữ nguyên logic nút -->
                        <c:choose>
                            <c:when test="${jd.status == 'InProgress'}">
                                <a href="pendingjd?id=${jd.jobID}" class="btn btn-sm btn-warning"
                                   onclick="return confirm('Suspend this JD?');">Suspend</a>
                            </c:when>
                            <c:when test="${jd.status == 'Pending'}">
                                <a href="reopenjd?id=${jd.jobID}" class="btn btn-sm btn-success"
                                   onclick="return confirm('Reopen this JD?');">Open</a>
                            </c:when>
                        </c:choose>

                        <c:if test="${jd.status != 'Cancelled'}">
                            <a href="canceljd?id=${jd.jobID}" class="btn btn-sm btn-danger"
                               onclick="return confirm('Cancel this JD?');">Cancel</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Pagination -->
    <div class="pagination-container d-flex justify-content-between align-items-center mt-3">
        <form action="<c:url value='/jdlist'/>" method="get" class="d-flex align-items-center">
            <label>Items per page:</label>
            <input type="number" name="itemsPerPage" value="${itemsPerPage}" min="1" max="50" class="form-control mx-2" style="width:80px;">
            <input type="hidden" name="search" value="${search}">
            <input type="hidden" name="department" value="${departmentFilter}">
            <input type="hidden" name="status" value="${statusFilter}">
            <input type="hidden" name="page" value="1">
            <button type="submit" class="btn btn-primary">Apply</button>
        </form>

        <nav>
            <ul class="pagination mb-0">
                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                    <a class="page-link"
                       href="<c:url value='/jdlist?page=${currentPage - 1}&itemsPerPage=${itemsPerPage}&search=${search}&department=${departmentFilter}&status=${statusFilter}'/>">
                        Previous
                    </a>
                </li>

                <li class="page-item disabled">
                    <span class="page-link">Page ${currentPage} / ${totalPages}</span>
                </li>

                <li class="page-item ${currentPage == totalPages || totalPages == 0 ? 'disabled' : ''}">
                    <a class="page-link"
                       href="<c:url value='/jdlist?page=${currentPage + 1}&itemsPerPage=${itemsPerPage}&search=${search}&department=${departmentFilter}&status=${statusFilter}'/>">
                        Next
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>
