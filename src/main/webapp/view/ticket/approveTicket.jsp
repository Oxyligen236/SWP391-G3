
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Approve Ticket</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" />
</head>
<body>
    <div class="container mt-4">
        <h4 class="text-primary mb-4">Department Tickets</h4>

       
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="bi bi-check-circle-fill me-2"></i>
                <strong>Success!</strong> ${successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <c:remove var="successMessage" scope="session" />
        </c:if>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                <strong>Error!</strong> ${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <c:remove var="errorMessage" scope="session" />
        </c:if>

        <form action="${pageContext.request.contextPath}/department-ticket" method="get" class="row g-3 mb-4">
            
            <!-- Search Sender -->
            <div class="col-md-3">
                <label class="form-label">Search Sender</label>
                <input type="text" name="searchSender" class="form-control" 
                       placeholder="Enter sender name..." 
                       value="${searchSender != null ? searchSender : ''}" />
            </div>

            <!-- Filter Department -->
            <div class="col-md-3">
                <label class="form-label">Department</label>
                <select name="department" class="form-select" ${userRole == 3 ? 'disabled' : ''}>
                    <c:if test="${userRole != 3}">
                        <option value="All" ${selectedDepartment == 'All' ? 'selected' : ''}>All Departments</option>
                    </c:if>
                    <c:forEach var="dept" items="${departments}">
                        <option value="${dept.name}" 
                            ${selectedDepartment eq dept.name ? 'selected' : ''}>
                            ${dept.name}
                        </option>
                    </c:forEach>
                </select>
                <c:if test="${userRole == 3}">
                    <input type="hidden" name="department" value="${selectedDepartment}" />
                </c:if>
            </div>

            <!-- Sort By -->
            <div class="col-md-2">
                <label class="form-label">Sort By</label>
                <select name="sortBy" class="form-select">
                    <option value="createDate" ${sortBy == 'createDate' ? 'selected' : ''}>Create Date</option>
                    <option value="approveDate" ${sortBy == 'approveDate' ? 'selected' : ''}>Approve Date</option>
                </select>
            </div>

            <!-- Order -->
            <div class="col-md-2">
                <label class="form-label">Order</label>
                <select name="sortOrder" class="form-select">
                    <option value="asc" ${sortOrder == 'asc' ? 'selected' : ''}>Ascending</option>
                    <option value="desc" ${sortOrder == 'desc' ? 'selected' : ''}>Descending</option>
                </select>
            </div>

            <!-- Status -->
            <div class="col-md-2">
                <label class="form-label">Status</label>
                <select name="status" class="form-select">
                    <option value="All" ${selectedStatus == 'All' ? 'selected' : ''}>All</option>
                    <option value="Pending" ${selectedStatus == 'Pending' ? 'selected' : ''}>Pending</option>
                    <option value="Approved" ${selectedStatus == 'Approved' ? 'selected' : ''}>Approved</option>
                    <option value="Rejected" ${selectedStatus == 'Rejected' ? 'selected' : ''}>Rejected</option>
                </select>
            </div>

            <!-- Type -->
            <div class="col-md-3">
                <label class="form-label">Type</label>
                <select name="type" class="form-select">
                    <option value="All" ${selectedType == 'All' ? 'selected' : ''}>All</option>
                    <c:forEach var="ticketType" items="${ticketTypes}">
                        <option value="${ticketType.ticket_Type_ID}" 
                            ${selectedType eq ticketType.ticket_Type_ID.toString() ? 'selected' : ''}>
                            ${ticketType.name}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <!-- Buttons -->
            <div class="col-md-12 d-flex gap-2">
                <input type="hidden" name="itemsPerPage" value="${itemsPerPage}" />
                
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-search"></i> Apply
                </button>
                <a href="${pageContext.request.contextPath}/department-ticket" class="btn btn-secondary">
                    <i class="bi bi-arrow-clockwise"></i> Reset
                </a>
            </div>
        </form>

        <!-- TABLE -->
        <div class="table-responsive">
            <table class="table table-bordered table-hover text-center align-middle">
                <thead class="table-primary">
                    <tr>
                        <th>No</th>
                        <th>Type</th>
                        <th>Sender</th>
                        <th>Department</th>
                        <th>Content</th>
                        <th>Create Date</th>
                        <th>Status</th>
                        <th>Approve Date</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${empty ticketList}">
                        <tr>
                            <td colspan="9" class="text-danger fw-bold">No tickets found</td>
                        </tr>
                    </c:if>

                    <c:forEach var="ticket" items="${ticketList}" varStatus="status">
                        <tr>
                            <td>${(currentPage - 1) * itemsPerPage + status.index + 1}</td>
                            <td>${ticket.ticketTypeName}</td>
                            <td>${ticket.userFullName}</td>
                            <td>${ticket.departmentName != null ? ticket.departmentName : 'N/A'}</td>
                            <td style="max-width: 250px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
                                ${ticket.ticket_Content}
                            </td>
                            <td>${ticket.create_Date}</td>
                            <td>
                                <span class="badge ${ticket.status == 'Approved' ? 'bg-success' : 
                                                   ticket.status == 'Rejected' ? 'bg-danger' : 'bg-warning'}">
                                    ${ticket.status}
                                </span>
                            </td>
                            <td>${ticket.approve_Date != null ? ticket.approve_Date : '-'}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/approve-ticket?ticketId=${ticket.ticketID}" 
                                   class="btn btn-primary btn-sm">
                                    View
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Pagination -->
        <div class="d-flex justify-content-between align-items-center mt-4">
            <form action="${pageContext.request.contextPath}/department-ticket" method="get" class="d-flex align-items-center">
                <label class="me-2">Items per page:</label>
                <input type="number" name="itemsPerPage" value="${itemsPerPage}" 
                       class="form-control w-auto me-2" min="1" max="50" style="width: 80px;" />
                <button type="submit" class="btn btn-sm btn-primary">Set</button>
                
                <input type="hidden" name="searchSender" value="${searchSender}" />
                <input type="hidden" name="status" value="${selectedStatus}" />
                <input type="hidden" name="type" value="${selectedType}" />
                <input type="hidden" name="department" value="${selectedDepartment}" />
                <input type="hidden" name="sortBy" value="${sortBy}" />
                <input type="hidden" name="sortOrder" value="${sortOrder}" />
                <input type="hidden" name="page" value="1" />
            </form>

            <nav>
                <ul class="pagination mb-0">
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" 
                           href="${pageContext.request.contextPath}/department-ticket?page=${currentPage - 1}&itemsPerPage=${itemsPerPage}&searchSender=${searchSender}&status=${selectedStatus}&type=${selectedType}&department=${selectedDepartment}&sortBy=${sortBy}&sortOrder=${sortOrder}">
                            Previous
                        </a>
                    </li>

                    <li class="page-item disabled">
                        <span class="page-link">Page ${currentPage} of ${totalPages}</span>
                    </li>

                    <li class="page-item ${currentPage == totalPages || totalPages == 0 ? 'disabled' : ''}">
                        <a class="page-link" 
                           href="${pageContext.request.contextPath}/department-ticket?page=${currentPage + 1}&itemsPerPage=${itemsPerPage}&searchSender=${searchSender}&status=${selectedStatus}&type=${selectedType}&department=${selectedDepartment}&sortBy=${sortBy}&sortOrder=${sortOrder}">
                            Next
                        </a>
                    </li>
                </ul>
            </nav>
        </div>

        <p class="text-center text-muted mt-2">Showing ${ticketList.size()} of ${totalItems} tickets</p>
    </div>

    <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>