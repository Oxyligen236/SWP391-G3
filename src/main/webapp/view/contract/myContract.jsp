<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Contracts</title>
    <link rel="stylesheet" href="<c:url value='/css/contract-list.css'/>" />
</head>
<body>
    <div class="contract-container">
        <h1>My Contracts</h1>
        
        <div class="info-box">
            <p>Danh sách tất cả các hợp đồng của bạn</p>
        </div>

        <!-- Error Message -->
        <c:if test="${not empty error}">
            <div class="error-msg">${error}</div>
        </c:if>
        
        <c:choose>
            <c:when test="${empty contracts or contracts.size() == 0}">
                <div class="no-data-msg">
                    Bạn chưa có hợp đồng nào.
                </div>
            </c:when>
            <c:otherwise>
                <!-- Contracts Table -->
                <table>
                    <thead>
                        <tr>
                            <th>Contract ID</th>
                            <th>Contract Type</th>
                            <th>Start Date</th>
                            <th>End Date</th>
                            <th>Base Salary</th>
                            <th>Position</th>
                            <th>Signer</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="contract" items="${contracts}">
                            <tr>
                                <td>${contract.contractId}</td>
                                <td>${contract.contractTypeName}</td>
                                <td>${contract.startDate}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${contract.endDate != null}">
                                            ${contract.endDate}
                                        </c:when>
                                        <c:otherwise>
                                            <span class="muted">Không xác định</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <fmt:formatNumber value="${contract.baseSalary}" type="number" groupingUsed="true" /> VNĐ
                                </td>
                                <td>${contract.positionName != null ? contract.positionName : 'N/A'}</td>
                                <td>${contract.signerName != null ? contract.signerName : 'N/A'}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${contract.status == 'Pending'}">
                                            <span class="badge bg-warning">${contract.status}</span>
                                        </c:when>
                                        <c:when test="${contract.status == 'Approved'}">
                                            <span class="badge bg-info">${contract.status}</span>
                                        </c:when>
                                        <c:when test="${contract.status == 'Active'}">
                                            <span class="badge bg-success">${contract.status}</span>
                                        </c:when>
                                        <c:when test="${contract.status == 'Expired'}">
                                            <span class="badge bg-secondary">${contract.status}</span>
                                        </c:when>
                                        <c:when test="${contract.status == 'Archived'}">
                                            <span class="badge bg-dark">${contract.status}</span>
                                        </c:when>
                                        <c:when test="${contract.status == 'Cancelled'}">
                                            <span class="badge bg-danger">${contract.status}</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary">
                                                ${contract.status != null ? contract.status : 'N/A'}
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="<c:url value='/viewContracts?action=detail&id=${contract.contractId}'/>">View</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <!-- Contract Count -->
                <div class="bottom-bar">
                    <div class="muted">Tổng số: <strong>${contracts.size()}</strong> hợp đồng</div>
                </div>
            </c:otherwise>
        </c:choose>

    </div>
</body>
</html>