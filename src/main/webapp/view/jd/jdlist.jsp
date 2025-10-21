<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%@ page import="hrms.model.JobDescription" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
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
            <!-- Header -->
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2><i class="fas fa-briefcase"></i> Job Description List</h2>
            </div>


            <form class="row g-2 mb-3 align-items-end" method="get" action="<c:url value='/jdlist'/>">
                <div class="col-md-3">
                    <label class="form-label">Search</label>
                    <input type="text" name="search" value="${search}" class="form-control"
                           placeholder="Enter job title or department...">
                </div>

                <div class="col-md-2">
                    <label class="form-label">Department</label>
                    <select name="department" class="form-select">
                        <option value="">All</option>
                        <c:forEach var="dept" items="${departments}">
                            <option value="${dept}" ${department == dept ? 'selected' : ''}>${dept}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-2">
                    <label class="form-label">Status</label>
                    <select name="status" class="form-select">
                        <option value="">All</option>
                        <option value="Pending" ${status == 'Pending' ? 'selected' : ''}>Pending</option>
                        <option value="InProgress" ${status == 'InProgress' ? 'selected' : ''}>In Progress</option>
                        <option value="Completed" ${status == 'Completed' ? 'selected' : ''}>Completed</option>
                        <option value="Cancelled" ${status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
                    </select>
                </div>

                <div class="col-md-2">
                    <label class="form-label">Sort by</label>
                    <select name="sortBy" class="form-select">
                        <option value="">Default</option>
                        <option value="startDate" ${sortBy == 'startDate' ? 'selected' : ''}>Start Date</option>
                        <option value="endDate" ${sortBy == 'endDate' ? 'selected' : ''}>End Date</option>
                        <option value="status" ${sortBy == 'status' ? 'selected' : ''}>Status</option>
                        <option value="department" ${sortBy == 'department' ? 'selected' : ''}>Department</option>
                    </select>
                </div>

                <div class="col-md-1">
                    <label class="form-label">Order</label>
                    <select name="sortOrder" class="form-select">
                        <option value="ASC" ${sortOrder == 'ASC' ? 'selected' : ''}>↑ Asc</option>
                        <option value="DESC" ${sortOrder == 'DESC' ? 'selected' : ''}>↓ Desc</option>
                    </select>
                </div>

                <div class="col-md-2 d-flex gap-2">
                    <button type="submit" class="btn btn-primary w-100">
                        <i class="fas fa-search"></i> Filter
                    </button>
                    <a href="<c:url value='/jdlist'/>" class="btn btn-secondary w-100" title="Reset">
                        <i class="fas fa-rotate-right"></i> Reset
                    </a>
                </div>
            </form>


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
                                <td class="status ${jd.status}">${jd.status}</td>
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

            <!-- Items per page + Pagination -->
            <div class="d-flex justify-content-between align-items-center mt-3">
                <!-- Items per page -->
                <form method="get" action="<c:url value='/jdlist'/>" class="d-flex align-items-center gap-2">
                    <label for="pageSize" class="form-label mb-0">Items per page:</label>
                    <select id="pageSize" name="pageSize" class="form-select" style="width: 80px;"
                            onchange="this.form.submit()">
                        <option value="5" ${pageSize == 5 ? 'selected' : ''}>5</option>
                        <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
                        <option value="20" ${pageSize == 20 ? 'selected' : ''}>20</option>
                    </select>
                </form>

                <!-- Pagination -->
                <c:if test="${totalPages > 1}">
                    <nav>
                        <ul class="pagination justify-content-center mb-0">
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${i == currentPage ? 'active' : ''}">
                                    <a class="page-link"
                                       href="?page=${i}&search=${search}&department=${department}&status=${status}&sortBy=${sortBy}&sortOrder=${sortOrder}&pageSize=${pageSize}">
                                        ${i}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </c:if>
            </div>

            <c:if test="${empty jdList}">
                <div class="alert alert-info text-center mt-3">
                    <i class="fas fa-info-circle"></i> No job descriptions found.
                </div>
            </c:if>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
