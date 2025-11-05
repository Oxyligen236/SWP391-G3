<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Contracts</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" />
</head>
<body class="bg-light">
    <div class="container mt-4 mb-5">
        <!-- Header -->
        <div class="card shadow-sm mb-4">
            <div class="card-header bg-success text-white">
                <h4 class="mb-0">
                    <i class="bi bi-file-earmark-person"></i> My Contracts
                </h4>
            </div>
            <div class="card-body">
                <p class="mb-0 text-muted">
                    <i class="bi bi-info-circle"></i> Danh sách tất cả các hợp đồng của bạn
                </p>
            </div>
        </div>

        <!-- Error Message -->
        <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill"></i> ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        
        <c:choose>
            <c:when test="${empty contracts or contracts.size() == 0}">
                <div class="alert alert-info text-center" role="alert">
                    <i class="bi bi-inbox"></i> Bạn chưa có hợp đồng nào.
                </div>
            </c:when>
            <c:otherwise>
                <!-- Contracts Table -->
                <div class="card shadow-sm">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover align-middle mb-0">
                                <thead class="table-success">
                                    <tr>
                                        <th class="text-center">Contract ID</th>
                                        <th>Contract Type</th>
                                        <th class="text-center">Start Date</th>
                                        <th class="text-center">End Date</th>
                                        <th class="text-end">Base Salary</th>
                                        <th>Position</th>
                                        <th>Signer</th>
                                        <th class="text-center">Status</th>
                                        <th class="text-center">Action</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="contract" items="${contracts}">
                                        <tr>
                                            <td class="text-center fw-semibold">${contract.contractId}</td>
                                            <td>
                                                <i class="bi bi-file-text text-primary"></i> ${contract.contractTypeName}
                                            </td>
                                            <td class="text-center">
                                                <i class="bi bi-calendar-check text-success"></i> ${contract.startDate}
                                            </td>
                                            <td class="text-center">
                                                <c:choose>
                                                    <c:when test="${contract.endDate != null}">
                                                        <i class="bi bi-calendar-x text-danger"></i> ${contract.endDate}
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted fst-italic">Không xác định</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="text-end">
                                                <i class="bi bi-currency-dollar text-success"></i> 
                                                <fmt:formatNumber value="${contract.baseSalary}" type="number" groupingUsed="true" /> VNĐ
                                            </td>
                                            <td>
                                                <i class="bi bi-briefcase text-info"></i> 
                                                ${contract.positionName != null ? contract.positionName : 'N/A'}
                                            </td>
                                            <td>
                                                <i class="bi bi-pen text-secondary"></i> 
                                                ${contract.signerName != null ? contract.signerName : 'N/A'}
                                            </td>
                                            <td class="text-center">
                                                <c:choose>
                                                    <c:when test="${contract.status == 'Pending'}">
                                                        <span class="badge bg-warning text-dark">
                                                            <i class="bi bi-hourglass-split"></i> ${contract.status}
                                                        </span>
                                                    </c:when>
                                                    <c:when test="${contract.status == 'Approved'}">
                                                        <span class="badge bg-info">
                                                            <i class="bi bi-check-circle"></i> ${contract.status}
                                                        </span>
                                                    </c:when>
                                                    <c:when test="${contract.status == 'Active'}">
                                                        <span class="badge bg-success">
                                                            <i class="bi bi-check-circle-fill"></i> ${contract.status}
                                                        </span>
                                                    </c:when>
                                                    <c:when test="${contract.status == 'Expired'}">
                                                        <span class="badge bg-secondary">
                                                            <i class="bi bi-clock-history"></i> ${contract.status}
                                                        </span>
                                                    </c:when>
                                                    <c:when test="${contract.status == 'Archived'}">
                                                        <span class="badge bg-dark">
                                                            <i class="bi bi-archive"></i> ${contract.status}
                                                        </span>
                                                    </c:when>
                                                    <c:when test="${contract.status == 'Cancelled'}">
                                                        <span class="badge bg-danger">
                                                            <i class="bi bi-x-circle"></i> ${contract.status}
                                                        </span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="badge bg-secondary">
                                                            ${contract.status != null ? contract.status : 'N/A'}
                                                        </span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="text-center">
                                                <a href="<c:url value='/viewContracts?action=detail&id=${contract.contractId}'/>" 
                                                   class="btn btn-sm btn-info">
                                                    <i class="bi bi-eye"></i> View
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <!-- Contract Count -->
                <div class="mt-3">
                    <p class="text-muted small mb-0">
                        <i class="bi bi-list-check"></i> Tổng số: <strong>${contracts.size()}</strong> hợp đồng
                    </p>
                </div>
            </c:otherwise>
        </c:choose>

    </div>

    <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>