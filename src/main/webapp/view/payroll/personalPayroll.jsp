<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="stylesheet" href="<c:url value='/css/personal-payroll.css'/>">
                <title>Personal Payroll</title>
            </head>

            <body>
                <h1>Personal Payroll</h1>
                <form action="<c:url value='/payroll/personal'/>" method="post"
                    style="text-align: center; margin-bottom: 20px;">
                    <label for="month">Tháng:</label>
                    <select name="month" id="month">
                        <option value="">Tất cả</option>
                        <c:forEach var="m" begin="1" end="12">
                            <option value="${m}" ${param.month==m ? 'selected' : '' }>${m}</option>
                        </c:forEach>
                    </select>

                    <label for="year">Năm:</label>
                    <input type="number" name="year" id="year" min="2000" max="2200" value="${param.year}">

                    <button type="submit">Tìm kiếm</button>
                </form>
                <c:if test="${not empty error}">
                    <p style="color: red;">${error}</p>
                </c:if>
                <table border="1" cellspacing="0" cellpadding="5">
                    <tr>
                        <th>Tháng</th>
                        <th>Năm</th>
                        <th>Ngày trả lương</th>
                        <th>Base Salary</th>
                        <th>Lương tổng</th>
                        <th>Chi tiết</th>
                    </tr>
                    <c:forEach var="payroll" items="${PersonalPayrolls}">
                        <tr>
                            <td>${payroll.month}</td>
                            <td>${payroll.year}</td>
                            <td>${payroll.payDate}</td>
                            <td>
                                <fmt:formatNumber value="${payroll.baseSalary}" type="number" groupingUsed="true"
                                    maxFractionDigits="3" />
                            </td>
                            <td>
                                <fmt:formatNumber value="${payroll.netSalary}" type="number" groupingUsed="true"
                                    maxFractionDigits="3" />
                            </td>

                            <td>
                                <form action="<c:url value='/payroll/detail'/>" method="get">
                                    <input type="hidden" name="payrollID" value="${payroll.payrollID}">
                                    <button type="submit">Chi tiết</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

            </body>

            </html>