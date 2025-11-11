<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Account Management</title>
    <link rel="stylesheet" href="<c:url value='/css/cv-list.css'/>">
     <link rel="stylesheet" href="<c:url value='/css/view-accountlist.css'/>">
    <script>
        function confirmToggle(button, message) {
            if(confirm(message)) {
                button.closest('form').submit();
            }
            return false;
        }
    </script>
</head>
<body>
<div class="cv-list-container">
    <h1>Account Management</h1>

    <!-- Notification Messages -->
    <c:if test="${not empty sessionScope.successMessage}">
        <div class="alert-success">
            ${sessionScope.successMessage}
        </div>
        <c:remove var="successMessage" scope="session"/>
    </c:if>

    <c:if test="${not empty sessionScope.errorMessage}">
        <div class="alert-error">
            ${sessionScope.errorMessage}
        </div>
        <c:remove var="errorMessage" scope="session"/>
    </c:if>

    <!-- Filter Form -->
    <form action="<c:url value='/account/view'/>" method="get" class="search-form">
        <div class="form-row text-inputs">
            <div class="form-group">
                <p>Search:</p>
                <input type="text" name="search" placeholder="Enter name or username..." value="${search}">
            </div>
            <div class="form-group">
                <p>Role:</p>
                <select name="role">
                    <option value="">All</option>
                    <c:forEach var="role" items="${roleList}">
                        <option value="${role.roleID}" <c:if test="${roleFilter==role.roleID}">selected</c:if>>
                            ${role.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <p>Status:</p>
                <select name="status">
                    <option value="">All</option>
                    <option value="active" <c:if test="${statusFilter=='active'}">selected</c:if>>Active</option>
                    <option value="inactive" <c:if test="${statusFilter=='inactive'}">selected</c:if>>Inactive</option>
                </select>
            </div>
        </div>

        <div class="form-row select-inputs">
            <div class="form-group">
                <p>Sort by:</p>
                <select name="sortBy">
                    <option value="">Default</option>
                    <option value="full_name" <c:if test="${sortBy=='full_name'}">selected</c:if>>Full Name</option>
                    <option value="role_id" <c:if test="${sortBy=='role_id'}">selected</c:if>>Role</option>
                    <option value="active" <c:if test="${sortBy=='active'}">selected</c:if>>Status</option>
                </select>
            </div>
            <div class="form-group">
                <p>Order:</p>
                <select name="sortOrder">
                    <option value="ASC" <c:if test="${sortOrder=='ASC'}">selected</c:if>>↑ Asc</option>
                    <option value="DESC" <c:if test="${sortOrder=='DESC'}">selected</c:if>>↓ Desc</option>
                </select>
            </div>
        </div>

        <div class="button-row">
            <button type="submit">Filter</button>
            <a href="<c:url value='/account/view'/>">Reset</a>
        </div>
    </form>

    <!-- Account Table -->
    <table>
        <thead>
        <tr>
            <th>No</th>
            <th>Account ID</th>
            <th>Username</th>
            <th>Full Name</th>
            <th>Role</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:if test="${empty accounts}">
            <tr>
                <td colspan="7" class="no-data">No accounts found</td>
            </tr>
        </c:if>
        <c:forEach var="account" items="${accounts}" varStatus="status">
            <tr>
                <td>${(currentPage - 1) * pageSize + status.index + 1}</td>
                <td>${account.accountID}</td>
                <td>${account.username}</td>
                <td>${account.fullName}</td>
                <td>${account.roleName}</td>
                <td>
                    <span class="status-badge ${account.active ? 'status-accepted' : 'status-rejected'}">
                        ${account.active ? 'Active' : 'Inactive'}
                    </span>
                </td>
                <td>
                    <a href="<c:url value='/updateRole?accountID=${account.accountID}'/>" class="action-link" title="Edit role">Edit</a>

                    <form action="<c:url value='/account/toggle-status'/>" method="post" style="display:inline;">
                        <input type="hidden" name="accountID" value="${account.accountID}">
                        <button type="button" class="action-link"
                                onclick="return confirmToggle(this, '${account.active ? "Are you sure you want to deactivate this account?" : "Are you sure you want to activate this account?"}')"
                                title="${account.active ? 'Deactivate this account' : 'Activate this account'}">
                            ${account.active ? 'Deactivate' : 'Activate'}
                        </button>
                    </form>

                    <a href="<c:url value='/account/reset-password?accountID=${account.accountID}'/>" class="action-link" title="Reset password">Reset</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- Pagination -->
    <div class="pagination-container">
        <form action="<c:url value='/account/view'/>" method="get" class="items-per-page-form">
            <label>Items per page:</label>
            <input type="number" name="pageSize" value="${pageSize}" min="1" max="50"/>
            <button type="submit">Apply</button>

            <!-- Keep filter and sort -->
            <input type="hidden" name="search" value="${search}"/>
            <input type="hidden" name="role" value="${roleFilter}"/>
            <input type="hidden" name="status" value="${statusFilter}"/>
            <input type="hidden" name="sortBy" value="${sortBy}"/>
            <input type="hidden" name="sortOrder" value="${sortOrder}"/>
            <input type="hidden" name="page" value="1"/>
        </form>

        <nav class="pagination-nav">
            <ul class="pagination">
                <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                    <a class="page-link"
                       href="<c:url value='/account/view?page=${currentPage - 1}&pageSize=${pageSize}&search=${search}&role=${roleFilter}&status=${statusFilter}&sortBy=${sortBy}&sortOrder=${sortOrder}'/>">
                        Previous
                    </a>
                </li>

                <li class="page-item disabled">
                    <span class="page-link">Page ${currentPage} / ${totalPages}</span>
                </li>

                <li class="page-item ${currentPage == totalPages || totalPages == 0 ? 'disabled' : ''}">
                    <a class="page-link"
                       href="<c:url value='/account/view?page=${currentPage + 1}&pageSize=${pageSize}&search=${search}&role=${roleFilter}&status=${statusFilter}&sortBy=${sortBy}&sortOrder=${sortOrder}'/>">
                        Next
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>
