<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hợp Đồng Của Tôi</title>
    <link rel="stylesheet" href="<c:url value='/css/contract-list.css'/>">
</head>
<body>
    <div class="container">
        <h1>My Contract</h1>
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
        
        <c:choose>
            <c:when test="${empty contracts or contracts.size() == 0}">
                <p class="no-data">Bạn chưa có hợp đồng nào.</p>
            </c:when>
            <c:otherwise>
                <table class="contract-table">
                    <thead>
                        <tr>
                            <th>Mã HĐ</th>
                            <th>Loại HĐ</th>
                            <th>Ngày Bắt Đầu</th>
                            <th>Ngày Kết Thúc</th>
                            <th>Lương Cơ Bản</th>
                            <th>Vị trí</th>
                            <th>Người ký</th>
                            <th>Hành Động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="contract" items="${contracts}">
                            <tr>
                                <td>${contract.contractId}</td>
                                <td>${contract.typeName}</td>
                                <td>${contract.startDate}</td>
                                <td>${contract.endDate != null ? contract.endDate : 'Không xác định'}</td>
                                <td>${contract.baseSalary} VNĐ</td>
                                <td>${contract.positionName != null ? contract.positionName : 'N/A'}</td>
                                <td>${contract.signerName != null ? contract.signerName : 'N/A'}</td>
                                <td>
                                    <a href="<c:url value='/viewContracts?action=detail&id=${contract.contractId}'/>" class="btn-view">Xem Chi Tiết</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>

        <a href="<c:url value='/home'/>" class="btn-back">Quay Lại Trang Chủ</a>
    </div>
</body>
</html>