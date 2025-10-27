<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="stylesheet" href="<c:url value='/css/company-payroll.css'/>">
                <title>Company Payroll</title>
            </head>

            <body>
                <h1>Company Payroll</h1>

                <div class="employee-list-section">
                    <h2>Nhân viên</h2>
                    <div class="employee-grid">
                        <div class="employee-card">
                            <div class="employee-id">ID: ${userID}</div>
                            <div class="employee-name">${userName}</div>
                        </div>
                    </div>
                </div>

                <h2>Tìm kiếm bảng lương</h2>
                <form action="<c:url value='/payroll/company'/>" method="post" class="search-form">
                    <div class="field-group">
                        <label>Tháng:</label>
                        <select name="month" id="month">
                            <option value="">Tất cả</option>
                            <c:forEach var="m" begin="1" end="12">
                                <option value="${m}" ${param.month==m ? 'selected' : '' }>${m}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="field-group">
                        <label>Năm:</label>
                        <input type="number" name="year" min="2000" max="2300" value="${param.year}"
                            placeholder="VD: 2025">
                    </div>

                    <div class="field-group">
                        <label>Trạng thái:</label>
                        <select name="status">
                            <option value="">--Tất cả--</option>
                            <option value="PENDING" ${param.status=='PENDING' ? 'selected' : '' }>PENDING</option>
                            <option value="CANCELLED" ${param.status=='CANCELLED' ? 'selected' : '' }>CANCELLED</option>
                            <option value="PAID" ${param.status=='PAID' ? 'selected' : '' }>PAID</option>
                        </select>
                    </div>
                    <input type="hidden" name="userID" value="${userID}">
                    <button type="submit">Tìm kiếm</button>
                </form>
                <hr>

                <c:if test="${not empty error}">
                    <div style="color: red;">${error}</div>
                </c:if>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Phòng ban</th>
                            <th>Vị trí</th>
                            <th>Tháng</th>
                            <th>Năm</th>
                            <th>Lương cơ bản</th>
                            <th>Tổng giờ làm việc</th>
                            <th>Tổng nhận thêm</th>
                            <th>Tổng khấu trừ</th>
                            <th>Tổng lương</th>
                            <th>Trạng thái</th>
                            <th>Ngày trả lương</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="employee" items="${payrolls}">
                            <tr>
                                <td>${employee.userDepartment}</td>
                                <td>${employee.userPosition}</td>
                                <td>${employee.month}</td>
                                <td>${employee.year}</td>
                                <td>
                                    <fmt:formatNumber value="${employee.baseSalary}" type="number" groupingUsed="true"
                                        maxFractionDigits="3" />
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${employee.totalWorkHours.toHours() >= 1}">
                                            ${employee.totalWorkHours.toHours()} giờ
                                            <c:if test="${employee.totalWorkHours.toMinutesPart() > 0}">
                                                ${employee.totalWorkHours.toMinutesPart()} phút
                                            </c:if>
                                        </c:when>
                                        <c:when test="${employee.totalWorkHours.toMinutesPart() > 0}">
                                            ${employee.totalWorkHours.toMinutesPart()} phút
                                        </c:when>
                                        <c:otherwise>
                                            0 phút
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <fmt:formatNumber value="${employee.totalEarnings}" type="number"
                                        groupingUsed="true" maxFractionDigits="3" />
                                </td>
                                <td>
                                    <fmt:formatNumber value="${employee.totalDeductions}" type="number"
                                        groupingUsed="true" maxFractionDigits="3" />
                                </td>
                                <td>
                                    <fmt:formatNumber value="${employee.netSalary}" type="number" groupingUsed="true"
                                        maxFractionDigits="3" />
                                </td>
                                <td>${employee.status}</td>
                                <td>${employee.payDate}</td>
                                <td>
                                    <form action="<c:url value='/payroll/company/detail'/>" method="get">
                                        <input type="hidden" name="payrollID" value="${employee.payrollID}">
                                        <input type="hidden" name="userID" value="${employee.userID}">
                                        <button type="submit">Chi tiết</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </body>

            </html>