<!-- filepath: d:\FPT\Ky_5\SWP391\Project\SWP391-G3\src\main\webapp\view\payroll\taxAndInsurance.jsp -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Quản lý Bảo hiểm và Thuế</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css" />
    <style>
        .bg-gradient-primary {
            background: linear-gradient(135deg, #4273f1 0%, #6ba3ff 100%);
        }
        .text-primary-custom {
            color: #4273f1 !important;
        }
        .border-primary-custom {
            border-color: #4273f1 !important;
        }
        .btn-primary-custom {
            background: linear-gradient(135deg, #4273f1 0%, #5a8fff 100%);
            border: none;
            color: white;
        }
        .btn-primary-custom:hover {
            background: linear-gradient(135deg, #3a63d1 0%, #4a7fef 100%);
        }
        .category-header {
            background-color: #f8f9fa;
            font-weight: bold;
            color: #4273f1;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container mt-5 mb-5">
        <div class="card shadow-lg border-0">
            <div class="card-header bg-gradient-primary text-white text-center py-3">
                <h3 class="mb-0">Quản lý Bảo hiểm và Thuế - Payroll #${payrollId}</h3>
            </div>
            <div class="card-body p-4">

                <!-- Success/Error Messages -->
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <strong>Thành công!</strong> ${successMessage}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                    <c:remove var="successMessage" scope="session" />
                </c:if>

                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>Lỗi!</strong> ${errorMessage}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                    <c:remove var="errorMessage" scope="session" />
                </c:if>

                <form action="${pageContext.request.contextPath}/tax-and-insurance" method="post">
                    <input type="hidden" name="payrollId" value="${payrollId}" />

                    <!-- Insurance Section -->
                    <div class="mb-4">
                        <div class="category-header p-2 mb-3 rounded">
                            BẢO HIỂM
                        </div>
                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <thead class="table-light">
                                    <tr>
                                        <th width="40%">Loại bảo hiểm</th>
                                        <th width="25%">Số tiền</th>
                                        <th width="25%">Loại</th>
                                        <th width="10%">Trạng thái</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="type" items="${payrollTypes}">
                                        <c:if test="${type.category == 'Insurance'}">
                                            <c:set var="currentItem" value="${null}" />
                                            <c:forEach var="item" items="${currentItems}">
                                                <c:if test="${item.typeID == type.payrollTypeID}">
                                                    <c:set var="currentItem" value="${item}" />
                                                </c:if>
                                            </c:forEach>
                                            
                                            <tr>
                                                <td>
                                                    <strong>${type.typeName}</strong>
                                                    <input type="hidden" name="typeId" value="${type.payrollTypeID}" />
                                                    <c:if test="${currentItem != null}">
                                                        <input type="hidden" name="itemId" value="${currentItem.payrollItemID}" />
                                                    </c:if>
                                                    <c:if test="${currentItem == null}">
                                                        <input type="hidden" name="itemId" value="" />
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <input type="number" 
                                                           name="amount" 
                                                           class="form-control border-primary-custom" 
                                                           step="0.01" 
                                                           min="0"
                                                           value="${currentItem != null ? currentItem.amount : 0}"
                                                           required />
                                                </td>
                                                <td>
                                                    <select name="amountType" class="form-select border-primary-custom">
                                                        <option value="fixed" ${currentItem != null && currentItem.amountType == 'fixed' ? 'selected' : ''}>
                                                            Số tiền cố định
                                                        </option>
                                                        <option value="percent" ${currentItem != null && currentItem.amountType == 'percent' ? 'selected' : ''}>
                                                            Phần trăm (%)
                                                        </option>
                                                    </select>
                                                </td>
                                                <td class="text-center">
                                                    <c:if test="${currentItem != null}">
                                                        <span class="badge bg-success">Đã thiết lập</span>
                                                    </c:if>
                                                    <c:if test="${currentItem == null}">
                                                        <span class="badge bg-secondary">Chưa có</span>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- Tax Section -->
                    <div class="mb-4">
                        <div class="category-header p-2 mb-3 rounded">
                            THUẾ
                        </div>
                        <div class="table-responsive">
                            <table class="table table-bordered">
                                <thead class="table-light">
                                    <tr>
                                        <th width="40%">Loại thuế</th>
                                        <th width="25%">Số tiền</th>
                                        <th width="25%">Loại</th>
                                        <th width="10%">Trạng thái</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="type" items="${payrollTypes}">
                                        <c:if test="${type.category == 'Tax'}">
                                            <c:set var="currentItem" value="${null}" />
                                            <c:forEach var="item" items="${currentItems}">
                                                <c:if test="${item.typeID == type.payrollTypeID}">
                                                    <c:set var="currentItem" value="${item}" />
                                                </c:if>
                                            </c:forEach>
                                            
                                            <tr>
                                                <td>
                                                    <strong>${type.typeName}</strong>
                                                    <input type="hidden" name="typeId" value="${type.payrollTypeID}" />
                                                    <c:if test="${currentItem != null}">
                                                        <input type="hidden" name="itemId" value="${currentItem.payrollItemID}" />
                                                    </c:if>
                                                    <c:if test="${currentItem == null}">
                                                        <input type="hidden" name="itemId" value="" />
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <input type="number" 
                                                           name="amount" 
                                                           class="form-control border-primary-custom" 
                                                           step="0.01" 
                                                           min="0"
                                                           value="${currentItem != null ? currentItem.amount : 0}"
                                                           required />
                                                </td>
                                                <td>
                                                    <select name="amountType" class="form-select border-primary-custom">
                                                        <option value="fixed" ${currentItem != null && currentItem.amountType == 'fixed' ? 'selected' : ''}>
                                                            Số tiền cố định
                                                        </option>
                                                        <option value="percent" ${currentItem != null && currentItem.amountType == 'percent' ? 'selected' : ''}>
                                                            Phần trăm (%)
                                                        </option>
                                                    </select>
                                                </td>
                                                <td class="text-center">
                                                    <c:if test="${currentItem != null}">
                                                        <span class="badge bg-success">Đã thiết lập</span>
                                                    </c:if>
                                                    <c:if test="${currentItem == null}">
                                                        <span class="badge bg-secondary">Chưa có</span>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- Buttons -->
                    <div class="text-end">
                        <a href="${pageContext.request.contextPath}/payroll-list" class="btn btn-secondary me-2">
                            Quay lại
                        </a>
                        <button type="submit" class="btn btn-primary-custom px-4">
                            Lưu thay đổi
                        </button>
                    </div>
                </form>

            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>