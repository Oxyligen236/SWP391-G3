<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <title>Ticket List</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" />
</head>
<body>
    <div class="container mt-4">
        <h4 class="text-primary mb-4">My Tickets</h4>

        <!-- Filter and Sort Section -->
        <form action="${pageContext.request.contextPath}/ticketList" method="get" class="row g-3 mb-4">
            
            <!-- Sort by Date -->
            <div class="col-md-2">
                <label class="form-label">Sort By</label>
                <select name="sortBy" class="form-select">
                    <option value="createDate" ${sortBy == 'createDate' ? 'selected' : ''}>Create Date</option>
                    <option value="approveDate" ${sortBy == 'approveDate' ? 'selected' : ''}>Approve Date</option>
                </select>
            </div>

            <div class="col-md-2">
                <label class="form-label">Order</label>
                <select name="sortOrder" class="form-select">
                    <option value="asc" ${sortOrder == 'asc' ? 'selected' : ''}>Ascending</option>
                    <option value="desc" ${sortOrder == 'desc' ? 'selected' : ''}>Descending</option>
                </select>
            </div>

            <!-- Filter by Status -->
            <div class="col-md-2">
                <label class="form-label">Status</label>
                <select name="status" class="form-select">
                    <option value="All" ${selectedStatus == 'All' ? 'selected' : ''}>All</option>
                    <option value="Pending" ${selectedStatus == 'Pending' ? 'selected' : ''}>Pending</option>
                    <option value="Approved" ${selectedStatus == 'Approved' ? 'selected' : ''}>Approved</option>
                    <option value="Rejected" ${selectedStatus == 'Rejected' ? 'selected' : ''}>Rejected</option>
                </select>
            </div>

            <!-- Filter by Type -->
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

            <!-- Apply and Reset Buttons -->
            <div class="col-md-3 d-flex align-items-end gap-2">
                <!-- Hidden field để giữ itemsPerPage khi Apply -->
                <input type="hidden" name="itemsPerPage" value="${itemsPerPage}" />
                
                <button type="submit" class="btn btn-primary flex-fill">
                    Apply
                </button>
                <a href="${pageContext.request.contextPath}/ticketList" class="btn btn-secondary flex-fill">
                    Reset
                </a>
            </div>
        </form>

        <!-- Table -->
        <table class="table table-bordered table-hover text-center">
            <thead class="table-primary">
                <tr>
                    <th>No</th>
                    <th>Type</th>
                    <th>Content</th>
                    <th>Create Date</th>
                    <th>Status</th>
                    <th>Approve Date</th>
                    <th>Comment</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty ticketList}">
                    <tr>
                        <td colspan="7" class="text-danger fw-bold">No tickets found</td>
                    </tr>
                </c:if>

                <c:forEach var="t" items="${ticketList}" varStatus="status">
                    <tr>
                        <td>${(currentPage - 1) * itemsPerPage + status.index + 1}</td>
                        <td>${t.ticketTypeName}</td>
                        <td style="max-width: 250px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
                            ${t.ticket_Content}
                        </td>
                        <td>${t.create_Date}</td>
                        <td>
                            <span class="badge ${t.status == 'Approved' ? 'bg-success' : 
                                               t.status == 'Rejected' ? 'bg-danger' : 'bg-warning'}">
                                ${t.status}
                            </span>
                        </td>
                        <td>${t.approve_Date != null ? t.approve_Date : '-'}</td>
                        <td>${t.comment != null ? t.comment : '-'}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Pagination -->
        <div class="d-flex justify-content-between align-items-center mt-4">
            <!-- Items per page -->
            <form action="${pageContext.request.contextPath}/ticketList" method="get" class="d-flex align-items-center">
                <label class="me-2">Items per page:</label>
                <input type="number" name="itemsPerPage" value="${itemsPerPage}" 
                       class="form-control w-auto me-2" min="1" max="50" style="width: 80px;" />
                <button type="submit" class="btn btn-sm btn-primary">Set</button>
                
                <!-- Preserve filters -->
                <input type="hidden" name="status" value="${selectedStatus}" />
                <input type="hidden" name="type" value="${selectedType}" />
                <input type="hidden" name="sortBy" value="${sortBy}" />
                <input type="hidden" name="sortOrder" value="${sortOrder}" />
                <input type="hidden" name="page" value="1" />
            </form>

            <!-- Pagination buttons -->
            <nav>
                <ul class="pagination mb-0">
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" 
                           href="${pageContext.request.contextPath}/ticketList?page=${currentPage - 1}&itemsPerPage=${itemsPerPage}&status=${selectedStatus}&type=${selectedType}&sortBy=${sortBy}&sortOrder=${sortOrder}">
                            Previous
                        </a>
                    </li>

                    <li class="page-item disabled">
                        <span class="page-link">Page ${currentPage} of ${totalPages}</span>
                    </li>

                    <li class="page-item ${currentPage == totalPages || totalPages == 0 ? 'disabled' : ''}">
                        <a class="page-link" 
                           href="${pageContext.request.contextPath}/ticketList?page=${currentPage + 1}&itemsPerPage=${itemsPerPage}&status=${selectedStatus}&type=${selectedType}&sortBy=${sortBy}&sortOrder=${sortOrder}">
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