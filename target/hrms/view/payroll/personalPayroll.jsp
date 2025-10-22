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
                <form action="<c:url value='/payroll/personal'/>" method="get" class="search-form">
                    <label for="month">Month:</label>
                    <select name="month" id="month">
                        <option value="">All</option>
                        <c:forEach var="m" begin="1" end="12">
                            <option value="${m}" ${param.month==String.valueOf(m) ? 'selected' : '' }>${m}</option>
                        </c:forEach>
                    </select>

                    <label for="year">Year:</label>
                    <input type="number" name="year" id="year" min="2000" max="2200" value="${param.year}"
                        placeholder="Ex: 2025">

                    <button type="submit">Search</button>
                    <a href="<c:url value='/payroll/personal'/>">Reset</a>
                </form>

                <c:if test="${not empty error}">
                    <p style="color: red; text-align: center;">${error}</p>
                </c:if>

                <c:if test="${not empty PersonalPayrolls}">
                    <p style="text-align: center;">Total Records: ${totalPayrolls}</p>
                </c:if>

                <table border="1" cellspacing="0" cellpadding="5">
                    <thead>
                        <tr>
                            <th>Month</th>
                            <th>Year</th>
                            <th>Pay Date</th>
                            <th>Base Salary</th>
                            <th>Total Work Hours</th>
                            <th>Net Salary</th>
                            <th>Details</th>
                        </tr>
                    </thead>
                    <tbody>
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
                                </td>
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
                    </tbody>
                </table>

                <div class="pagination-container">
                    <form action="<c:url value='/payroll/personal'/>" method="get">
                        <label>Items per page:</label>
                        <input type="number" name="itemsPerPage" value="${itemsPerPage}" min="1" />
                        <button type="submit">Set</button>

                        <input type="hidden" name="month" value="${param.month}" />
                        <input type="hidden" name="year" value="${param.year}" />
                        <input type="hidden" name="page" value="1" />
                    </form>

                    <nav>
                        <ul class="pagination">
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link"
                                    href="${pageContext.request.contextPath}/payroll/personal?page=${currentPage - 1}&itemsPerPage=${itemsPerPage}&month=${param.month}&year=${param.year}">
                                    Previous
                                </a>
                            </li>

                            <li class="page-item disabled">
                                <span class="page-link">Page ${currentPage} of ${totalPages}</span>
                            </li>

                            <li class="page-item ${currentPage == totalPages || totalPages == 0 ? 'disabled' : ''}">
                                <a class="page-link"
                                    href="${pageContext.request.contextPath}/payroll/personal?page=${currentPage + 1}&itemsPerPage=${itemsPerPage}&month=${param.month}&year=${param.year}">
                                    Next
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </body>

            </html>