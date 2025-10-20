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
            <div class="container">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <h2><i class="fas fa-users"></i> Quản Lý Tài Khoản</h2>
                    <a href="<c:url value='/account/create'/>" class="btn btn-primary">
                        <i class="fas fa-plus"></i> Thêm Tài Khoản Mới
                    </a>
                </div>

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

                <div class="table-responsive">
                    <table class="table table-hover table-bordered align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Họ Tên</th>
                                <th>Vai Trò</th>
                                <th>Trạng Thái</th>
                                <th style="width: 280px;">Thao Tác</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="account" items="${accountList}">
                                <tr>
                                    <td>${account.accountID}</td>
                                    <td><strong>${account.username}</strong></td>
                                    <td>${account.fullName}</td>
                                    <td><span class="badge bg-info">${account.roleName}</span></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${account.active}">
                                                <span class="status-badge status-active">
                                                    <i class="fas fa-check-circle"></i> Hoạt động
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status-badge status-inactive">
                                                    <i class="fas fa-times-circle"></i> Vô hiệu
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a href="<c:url value='/account/view?id=${account.accountID}'/>"
                                            class="btn btn-sm btn-info btn-action" title="Xem chi tiết">
                                            <i class="fas fa-eye"></i> Xem
                                        </a>
                                        <a href="<c:url value='/account/update-role?id=${account.accountID}'/>"
                                            class="btn btn-sm btn-warning btn-action" title="Chỉnh sửa vai trò">
                                            <i class="fas fa-edit"></i> Sửa
                                        </a>

                                        <button type="button"
                                            class="btn btn-sm ${account.active ? 'btn-danger' : 'btn-success'} btn-action"
                                            data-bs-toggle="modal" data-bs-target="#toggleModal${account.accountID}"
                                            title="${account.active ? 'Vô hiệu hóa' : 'Kích hoạt'}">
                                            <i class="fas ${account.active ? 'fa-ban' : 'fa-check'}"></i>
                                            ${account.active ? 'Disable' : 'Active'}
                                        </button>


                                        <div class="modal fade" id="toggleModal${account.accountID}" tabindex="-1">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">Xác Nhận</h5>
                                                        <button type="button" class="btn-close"
                                                            data-bs-dismiss="modal"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        Bạn có chắc chắn muốn
                                                        <strong>${account.active ? 'vô hiệu hóa (Disable)' : 'kích hoạt
                                                            (Active)'}</strong>
                                                        tài khoản <strong>${account.username}</strong>?
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">Hủy</button>
                                                        <form action="<c:url value='/account/toggle-status'/>"
                                                            method="post" style="display:inline;">
                                                            <input type="hidden" name="accountID"
                                                                value="${account.accountID}">
                                                            <button type="submit"
                                                                class="btn ${account.active ? 'btn-danger' : 'btn-success'}">
                                                                Xác nhận
                                                            </button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

                <c:if test="${empty accountList}">
                    <div class="alert alert-info text-center">
                        <i class="fas fa-info-circle"></i> Chưa có tài khoản nào trong hệ thống.
                    </div>
                </c:if>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>