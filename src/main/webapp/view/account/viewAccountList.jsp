<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh Sách Tài Khoản - HRMS</title>

    <!-- Bootstrap & Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <!-- Custom CSS -->
    <style>
        body {
            background-color: #f8faff;
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        }

        h2 {
            color: #0d6efd;
            font-weight: 600;
        }

        .btn-primary {
            background-color: #a2d2ff;
            border-color: #a2d2ff;
            color: #0d3b66;
        }

        .btn-primary:hover {
            background-color: #7bb8ff;
            border-color: #7bb8ff;
            color: #0d3b66;
        }

        .btn-secondary {
            background-color: #d3e0ff;
            border-color: #d3e0ff;
            color: #0d3b66;
        }

        .btn-secondary:hover {
            background-color: #aebfff;
            border-color: #aebfff;
            color: #0d3b66;
        }

        .btn-warning {
            background-color: #ffe5b4;
            border-color: #ffe5b4;
            color: #6b4f00;
        }

        .btn-warning:hover {
            background-color: #ffd580;
            border-color: #ffd580;
            color: #6b4f00;
        }

        .btn-success {
            background-color: #c6f7d0;
            border-color: #c6f7d0;
            color: #0b5f2c;
        }

        .btn-success:hover {
            background-color: #a0e6b8;
            border-color: #a0e6b8;
            color: #0b5f2c;
        }

        .btn-danger {
            background-color: #ffc6c6;
            border-color: #ffc6c6;
            color: #6b0000;
        }

        .btn-danger:hover {
            background-color: #ff9999;
            border-color: #ff9999;
            color: #6b0000;
        }

        .btn-info {
            background-color: #bde0fe;
            border-color: #bde0fe;
            color: #0353a4;
        }

        .btn-info:hover {
            background-color: #9ecbfc;
            border-color: #9ecbfc;
            color: #0353a4;
        }

        table {
            background-color: #ffffff;
        }

        thead {
            background-color: #e0f0ff;
        }

        .badge-success {
            background-color: #c6f7d0;
            color: #0b5f2c;
        }

        .badge-danger {
            background-color: #ffc6c6;
            color: #6b0000;
        }

        .badge-info {
            background-color: #bde0fe;
            color: #0353a4;
        }

        .table-hover tbody tr:hover {
            background-color: #f0f8ff;
        }

        .form-label {
            font-weight: 500;
            color: #0d3b66;
        }

        .form-control, .form-select {
            border-radius: 0.375rem;
        }

        .page-link {
            color: #0d6efd;
        }

        .page-item.active .page-link {
            background-color: #a2d2ff;
            border-color: #a2d2ff;
            color: #0d3b66;
        }
    </style>
</head>

<body>
    <div class="container py-4">

        <!-- Header -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2><i class="fas fa-users"></i> Quản Lý Tài Khoản</h2>
            <div class="d-flex gap-2">
                <a href="<c:url value='/account/create'/>" class="btn btn-primary">
                    <i class="fas fa-plus"></i> Add New Account
                </a>
                <a href="<c:url value='/home'/>" class="btn btn-secondary">
                    <i class="fas fa-home"></i> Back to Home
                </a>
            </div>
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

        <!-- Filter Form -->
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
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Full Name</th>
                        <th>Role</th>
                        <th>Status</th>
                        <th style="width: 300px;">Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="account" items="${accounts}">
                        <tr>
                            <td>${account.accountID}</td>
                            <td><strong>${account.username}</strong></td>
                            <td>${account.fullName}</td>
                            <td><span class="badge badge-info">${account.roleName}</span></td>
                            <td>
                                <c:choose>
                                    <c:when test="${account.active}">
                                        <span class="badge badge-success"><i class="fas fa-check"></i> Active</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge badge-danger"><i class="fas fa-times"></i> Inactive</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="<c:url value='/updateRole?accountID=${account.accountID}'/>" class="btn btn-warning btn-sm">
                                    <i class="fas fa-edit"></i> Edit
                                </a>
                                <form action="<c:url value='/account/toggle-status'/>" method="post" style="display:inline;">
                                    <input type="hidden" name="accountID" value="${account.accountID}">
                                    <button type="submit" class="btn btn-sm ${account.active ? 'btn-danger' : 'btn-success'}">
                                        <i class="fas ${account.active ? 'fa-ban' : 'fa-check'}"></i> ${account.active ? 'Disable' : 'Activate'}
                                    </button>
                                </form>
                                <a href="<c:url value='/account/reset-password?accountID=${account.accountID}'/>" class="btn btn-info btn-sm text-white">
                                    <i class="fas fa-key"></i> Reset
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Pagination + Items per page -->
        <div class="d-flex justify-content-between align-items-center mt-3">
            <form method="get" action="<c:url value='/account/view'/>" class="d-flex align-items-center gap-2">
                <label for="pageSize" class="form-label mb-0">Items per page:</label>
                <select id="pageSize" name="pageSize" class="form-select" style="width: 80px;" onchange="this.form.submit()">
                    <option value="5" ${pageSize==5 ? 'selected' : '' }>5</option>
                    <option value="10" ${pageSize==10 ? 'selected' : '' }>10</option>
                    <option value="20" ${pageSize==20 ? 'selected' : '' }>20</option>
                </select>
            </form>

            <c:if test="${totalPages > 1}">
                <nav>
                    <ul class="pagination justify-content-center mb-0">
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <li class="page-item ${i == currentPage ? 'active' : ''}">
                                <a class="page-link" href="?page=${i}&search=${search}&role=${roleFilter}&status=${statusFilter}&sortBy=${sortBy}&sortOrder=${sortOrder}&pageSize=${pageSize}">${i}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </nav>
            </c:if>
        </div>

        <!-- No results -->
        <c:if test="${empty accounts}">
            <div class="alert alert-info text-center mt-3">
                <i class="fas fa-info-circle"></i> No accounts found.
            </div>
        </c:if>

    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
