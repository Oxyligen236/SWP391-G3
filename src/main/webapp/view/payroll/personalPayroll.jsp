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
                    <label for="month">Month:</label>
                    <select name="month" id="month">
                        <option value="">All</option>
                        <c:forEach var="m" begin="1" end="12">
                            <option value="${m}" ${param.month==m ? 'selected' : '' }>${m}</option>
                        </c:forEach>
                    </select>

                    <label for="year">Year:</label>
                    <input type="number" name="year" id="year" min="2000" max="2200" value="${param.year}">

                    <button type="submit">Search</button>
                </form>
                <c:if test="${not empty error}">
                    <p style="color: red;">${error}</p>
                </c:if>
                <table border="1" cellspacing="0" cellpadding="5">
                    <tr>
                        <th>Month</th>
                        <th>Year</th>
                        <th>Pay Date</th>
                        <th>Base Salary</th>
                        <th>Total Work Hours</th>
                        <th>Total Salary</th>
                        <th>Details</th>
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
                                <c:choose>
                                    <c:when test="${payroll.totalWorkHours.toHours() >= 1}">
                                        ${payroll.totalWorkHours.toHours()} hours
                                        <c:if test="${payroll.totalWorkHours.toMinutesPart() > 0}">
                                            ${payroll.totalWorkHours.toMinutesPart()} minutes
                                        </c:if>
                                    </c:when>
                                    <c:when test="${payroll.totalWorkHours.toMinutesPart() > 0}">
                                        ${payroll.totalWorkHours.toMinutesPart()} minutes
                                    </c:when>
                                    <c:otherwise>
                                        0 minutes
                                    </c:otherwise>
                                </c:choose>
                            <td>
                                <fmt:formatNumber value="${payroll.netSalary}" type="number" groupingUsed="true"
                                    maxFractionDigits="3" />
                            </td>

                            <td>
                                <form action="<c:url value='/payroll/personal/detail'/>" method="get">
                                    <input type="hidden" name="payrollID" value="${payroll.payrollID}">
                                    <button type="submit">Details</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>

            </body>

            </html>