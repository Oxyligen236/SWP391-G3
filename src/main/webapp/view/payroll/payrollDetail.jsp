<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <link rel="stylesheet" href="<c:url value='/css/payroll-detail.css'/>">
                <title>Payroll Detail</title>
            </head>

            <body>

                <h1>Chi tiết bảng lương</h1>
                <c:if test="${displayType == 'management'}">
                    <div class="management-info">
                        <h2>Nhân viên: ${payrollDetail.userName} (ID: ${payrollDetail.userID})</h2>
                        <h2>Phòng ban: ${payrollDetail.userDepartment}, Vị trí: ${payrollDetail.userPosition}</h2>
                    </div>

                </c:if>
                <table border="1" cellspacing="0" cellpadding="5">
                    <tr>
                        <th>Tháng</th>
                        <th>Năm</th>
                        <th>Lương cơ bản</th>
                        <th>Tổng nhận thêm</th>
                        <th>Tổng khấu trừ</th>
                        <th>Lương thực nhận</th>
                        <th>Ngày trả lương</th>
                    </tr>
                    <tr>
                        <td>${payrollDetail.month}</td>
                        <td>${payrollDetail.year}</td>
                        <td>
                            <fmt:formatNumber value="${payrollDetail.baseSalary}" type="number" groupingUsed="true"
                                maxFractionDigits="3" />
                        </td>
                        <td>
                            <fmt:formatNumber value="${payrollDetail.totalEarnings}" type="number" groupingUsed="true"
                                maxFractionDigits="3" />
                        </td>
                        <td>
                            <fmt:formatNumber value="${payrollDetail.totalDeductions}" type="number" groupingUsed="true"
                                maxFractionDigits="3" />
                        <td>
                            <fmt:formatNumber value="${payrollDetail.netSalary}" type="number" groupingUsed="true"
                                maxFractionDigits="3" />
                        </td>
                        <td>${payrollDetail.payDate}</td>
                    </tr>
                </table>

                <h1>Chi tiết các khoản:</h1>
                <table border="1" cellspacing="0" cellpadding="5">
                    <tr>
                        <th>Tên khoản</th>
                        <th>Số tiền</th>
                        <th>Loại</th>
                    </tr>
                    <c:forEach var="item" items="${payrollDetail.payrollItems}">
                        <tr>
                            <td>
                                ${item.typeName}
                            </td>
                            <td>
                                <fmt:formatNumber value="${item.amount}" type="number" groupingUsed="true"
                                    maxFractionDigits="3" />
                                <c:choose>
                                    <c:when test="${item.amountType == 'fixed'}">(₫)</c:when>
                                    <c:when test="${item.amountType == 'percent'}">(% Lương cơ bản)</c:when>
                                    <c:otherwise>Không xác định</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${item.positive}">Thu nhập (+)</c:when>
                                    <c:otherwise>Khấu trừ (-)</c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </body>

            </html>