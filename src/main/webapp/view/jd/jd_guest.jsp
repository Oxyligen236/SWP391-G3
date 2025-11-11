<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách công việc</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="<c:url value='/css/jd_guest.css'/>">
</head>
<body>
<div class="container-fluid">
    <a href="<c:url value='/view/home/homePage_guest.jsp'/>" class="btn btn-secondary mt-3">
        <i class="fas fa-home"></i> Back to Home Page
    </a>
    <h2>Open Positions</h2>

    <form class="row g-3 mb-4 align-items-end filter-form" method="get" action="<c:url value='/jd_guest'/>">
        <div class="col-md-4">
            <label class="form-label">Search by Title</label>
            <div class="input-group">
                <input type="text" name="search" value="${search}" class="form-control"
                       placeholder="Enter job title...">
                <button type="submit" class="btn btn-primary"><i class="fas fa-search"></i> Search</button>
            </div>
        </div>
        <div class="col-md-3">
            <label class="form-label">Department</label>
            <select name="department" id="department" class="form-select" onchange="this.form.submit()">
                <option value="" ${departmentFilter == '' ? 'selected' : ''}>All</option>
                <c:forEach var="d" items="${departments}">
                    <option value="${d.name}" ${departmentFilter == d.name ? 'selected' : ''}>${d.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-1 d-flex">
            <a href="<c:url value='/jd_guest'/>" class="btn btn-secondary align-self-end w-100" title="Reset Filters">
                <i class="fas fa-rotate-right"></i>
            </a>
        </div>

        <input type="hidden" name="page" value="${currentPage}" />
        <input type="hidden" name="itemsPerPage" value="${itemsPerPage}" />
    </form>

    <table class="table table-bordered table-hover align-middle">
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
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
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

                        <td>
                            <c:choose>
                                <c:when test="${fn:length(jd.responsibilities) > 50}">
                                    ${fn:substring(jd.responsibilities, 0, 50)}...
                                </c:when>
                                <c:otherwise>${jd.responsibilities}</c:otherwise>
                            </c:choose>
                        </td>

                        <td>
                            <c:choose>
                                <c:when test="${fn:length(jd.requirements) > 50}">
                                    ${fn:substring(jd.requirements, 0, 50)}...
                                </c:when>
                                <c:otherwise>${jd.requirements}</c:otherwise>
                            </c:choose>
                        </td>

                        <td>
                            <c:choose>
                                <c:when test="${fn:length(jd.compensation) > 50}">
                                    ${fn:substring(jd.compensation, 0, 50)}...
                                </c:when>
                                <c:otherwise>${jd.compensation}</c:otherwise>
                            </c:choose>
                        </td>

                        <td>
                            <a href="jd_guestDetail?id=${jd.jobID}" class="btn-edit">Apply</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr>
                    <td colspan="10">No Job available now!</td>
                </tr>
            </c:otherwise>
        </c:choose>
        </tbody>
    </table>

    <!-- Pagination -->
    <div class="pagination-container d-flex justify-content-between align-items-center mt-3">
        <form action="<c:url value='/jd_guest'/>" method="get" class="d-flex align-items-center">
            <label>Items per page:</label>
            <input type="number" name="itemsPerPage" value="${itemsPerPage}" min="1" max="50" class="form-control mx-2" style="width:80px;">
            <input type="hidden" name="search" value="${search}">
            <input type="hidden" name="department" value="${departmentFilter}">
            <input type="hidden" name="page" value="1">
            <button type="submit" class="btn btn-primary">Apply</button>
        </form>

        <nav>
            <ul class="pagination mb-0">
                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                    <a class="page-link"
                       href="<c:url value='/jd_guest?page=${currentPage - 1}&itemsPerPage=${itemsPerPage}&search=${search}&department=${departmentFilter}'/>">
                        Previous
                    </a>
                </li>

                <li class="page-item disabled">
                    <span class="page-link">Page ${currentPage} / ${totalPages}</span>
                </li>

                <li class="page-item ${currentPage == totalPages || totalPages == 0 ? 'disabled' : ''}">
                    <a class="page-link"
                       href="<c:url value='/jd_guest?page=${currentPage + 1}&itemsPerPage=${itemsPerPage}&search=${search}&department=${departmentFilter}'/>">
                        Next
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>
