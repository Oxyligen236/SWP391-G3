<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Danh Sách Tài Khoản - HRMS</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
            <link rel="stylesheet" href="<c:url value='/css/view-accountlist.css'/>">
        </head>

        <body>
            <div class="container py-4">
                <!-- Header -->
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2><i class="fas fa-users"></i> Quản Lý Tài Khoản</h2>
                    <a href="<c:url value='/account/create'/>" class="btn btn-primary">
                        <i class="fas fa-plus"></i> Add New Account
                    </a>
                    <a href="<c:url value='/home'/>" class="btn btn-secondary">
                        <i class="fas fa-home"></i> Back to Home
                    </a>
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

                <!-- Filter & Search Form -->
                <form class="row g-2 mb-3 align-items-end" method="get" action="<c:url value='/account/view'/>">
                    <div class="col-md-3">
                        <label class="form-label">Search</label>
                        <input type="text" name="search" value="${search}" class="form-control"
                            placeholder="Enter name or username...">
                    </div>

                    <div class="col-md-2">
                        <label class="form-label">Role</label>
                        <select name="role" class="form-select">
                            <option value="">All</option>
                            <!-- Lặp qua danh sách role từ server -->
                            <c:forEach var="role" items="${roleList}">
                                <option value="${role.roleID}" ${roleFilter==role.roleID ? 'selected' : '' }>
                                    ${role.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-2">
                        <label class="form-label">Status</label>
                        <select name="status" class="form-select">
                            <option value="">All</option>
                            <option value="active" ${statusFilter=='active' ? 'selected' : '' }>Active</option>
                            <option value="inactive" ${statusFilter=='inactive' ? 'selected' : '' }>Inactive</option>
                        </select>
                    </div>

                    <div class="col-md-2">
                        <label class="form-label">Sort by</label>
                        <select name="sortBy" class="form-select">
                            <option value="">Default</option>
                            <option value="fullName" ${sortBy=='fullName' ? 'selected' : '' }>Full Name</option>
                            <option value="role" ${sortBy=='role' ? 'selected' : '' }>Role</option>
                            <option value="status" ${sortBy=='status' ? 'selected' : '' }>Status</option>
                        </select>
                    </div>

                    <div class="col-md-1">
                        <label class="form-label">Order</label>
                        <select name="sortOrder" class="form-select">
                            <option value="ASC" ${sortOrder=='ASC' ? 'selected' : '' }>↑ Asc</option>
                            <option value="DESC" ${sortOrder=='DESC' ? 'selected' : '' }>↓ Desc</option>
                        </select>
                    </div>

                    <div class="col-md-2 d-flex gap-2">
                        <button type="submit" class="btn btn-primary w-100">
                            <i class="fas fa-search"></i> Filter
                        </button>
                        <a href="<c:url value='/account/view'/>" class="btn btn-secondary w-100" title="Reset">
                            <i class="fas fa-rotate-right"></i> Reset
                        </a>
                    </div>
                </form>

                <!-- Table -->
                <div class="table-responsive">
                    <table class="table table-hover table-bordered align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Full Name</th>
                                <th>Role</th>
                                <th>Status</th>
                                <th style="width: 250px;">Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="account" items="${accounts}">
                                <tr>
                                    <td>${account.accountID}</td>
                                    <td><strong>${account.username}</strong></td>
                                    <td>${account.fullName}</td>
                                    <td><span class="badge bg-info">${account.roleName}</span></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${account.active}">
                                                <span class="badge bg-success"><i class="fas fa-check"></i>
                                                    Active</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-danger"><i class="fas fa-times"></i>
                                                    Inactive</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>

                                        <a href="<c:url value='/account/updateRole?id=${account.accountID}'/>"
                                            class="btn btn-sm btn-warning"><i class="fas fa-edit"></i> Edit</a>
                                        <form action="<c:url value='/account/toggle-status'/>" method="post"
                                            style="display:inline;">
                                            <input type="hidden" name="accountID" value="${account.accountID}">
                                            <button type="submit"
                                                class="btn btn-sm ${account.active ? 'btn-danger' : 'btn-success'}">
                                                <i class="fas ${account.active ? 'fa-ban' : 'fa-check'}"></i>
                                                ${account.active ? 'Disable' : 'Activate'}
                                            </button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <!-- Items per page + Pagination -->
                <div class="d-flex justify-content-between align-items-center mt-3">
                    <!-- Items per page -->
                    <form method="get" action="<c:url value='/account/view'/>" class="d-flex align-items-center gap-2">
                        <label for="pageSize" class="form-label mb-0">Items per page:</label>
                        <select id="pageSize" name="pageSize" class="form-select" style="width: 80px;"
                            onchange="this.form.submit()">
                            <option value="5" ${pageSize==5 ? 'selected' : '' }>5</option>
                            <option value="10" ${pageSize==10 ? 'selected' : '' }>10</option>
                            <option value="20" ${pageSize==20 ? 'selected' : '' }>20</option>
                        </select>
                    </form>

                    <!-- Pagination -->
                    <c:if test="${totalPages > 1}">
                        <nav>
                            <ul class="pagination justify-content-center mb-0">
                                <c:forEach begin="1" end="${totalPages}" var="i">
                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                        <a class="page-link"
                                            href="?page=${i}&search=${search}&role=${roleFilter}&status=${statusFilter}&sortBy=${sortBy}&sortOrder=${sortOrder}&pageSize=${pageSize}">
                                            ${i}
                                        </a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </nav>
                    </c:if>
                </div>

                <c:if test="${empty accounts}">
                    <div class="alert alert-info text-center mt-3">
                        <i class="fas fa-info-circle"></i> No accounts found.
                    </div>
                </c:if>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>