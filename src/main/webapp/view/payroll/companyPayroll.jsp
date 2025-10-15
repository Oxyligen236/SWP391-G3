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
                <h2>Tìm kiếm bảng lương</h2>
                <form action="<c:url value='/payroll/company'/>" method="post">
                    <label>Tên nhân viên:</label>
                    <input type="text" name="userName" value="${param.userName}" placeholder="Nhập tên nhân viên">

                    <label>Phòng ban:</label>
                    <select name="department">
                        <option value="">--Tất cả--</option>
                        <c:forEach var="dept" items="${departments}">
                            <option value="${dept.name}" ${param.department==dept.name ? 'selected' : '' }>${dept.name}
                            </option>

                        </c:forEach>
                    </select>

                    <label>Vị trí:</label>
                    <select name="position">
                        <option value="">--Tất cả--</option>
                        <c:forEach var="pos" items="${positions}">
                            <option value="${pos.name}" ${param.position==pos.name ? 'selected' : '' }>${pos.name}
                            </option>
                        </c:forEach>
                    </select>
                    <label>Tháng:</label>
                    <input type="number" name="month" min="1" max="12" value="${param.month}" placeholder="1-12">

                    <label>Năm:</label>
                    <input type="number" name="year" min="2000" max="2300" value="${param.year}" placeholder="VD: 2025">

                    <label>Trạng thái:</label>
                    <select name="status">
                        <option value="">--Tất cả--</option>
                        <option value="PENDING" ${param.status=='PENDING' ? 'selected' : '' }>PENDING</option>
                        <option value="CANCELLED" ${param.status=='CANCELLED' ? 'selected' : '' }>CANCELLED
                        </option>
                        <option value="PAID" ${param.status=='PAID' ? 'selected' : '' }>PAID</option>
                    </select>

                    <button type="submit">Tìm kiếm</button>
                </form>
                <hr>

                <c:if test="${not empty error}">
                    <div style="color: red;">${error}</div>
                </c:if>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Employee ID</th>
                            <th>Tên</th>
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
                                <td>${employee.userID}</td>
                                <td>${employee.userName}</td>
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
                                <td><a href="/payroll/company/detail/${employee.userID}">Chi tiết</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </body>

            </html>