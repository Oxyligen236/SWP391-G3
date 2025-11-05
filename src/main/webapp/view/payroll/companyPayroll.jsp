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
                <div class="payroll-container">
                    <h1>Company Payroll Management</h1>

                    <form action="<c:url value='/payroll/company'/>" method="post" class="search-form">
                        <div class="form-row">
                            <div class="field-group">
                                <label for="month">Month:</label>
                                <select name="month" id="month">
                                    <option value="">All</option>
                                    <c:forEach var="m" begin="1" end="12">
                                        <option value="${m}" ${param.month==m ? 'selected' : '' }>${m}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="field-group">
                                <label for="year">Year:</label>
                                <input type="number" id="year" name="year" min="2000" max="2300" value="${param.year}"
                                    placeholder="e.g. 2025">
                            </div>

                            <div class="field-group">
                                <label for="status">Status:</label>
                                <select name="status" id="status">
                                    <option value="">All Statuses</option>
                                    <option value="PENDING" ${param.status=='PENDING' ? 'selected' : '' }>Pending
                                    </option>
                                    <option value="CANCELLED" ${param.status=='CANCELLED' ? 'selected' : '' }>Cancelled
                                    </option>
                                    <option value="PAID" ${param.status=='PAID' ? 'selected' : '' }>Paid</option>
                                </select>
                            </div>
                        </div>

                        <input type="hidden" name="userID" value="${userID}">

                        <div class="button-row">
                            <button type="submit">Search</button>
                        </div>
                    </form>

                    <c:if test="${not empty error}">
                        <div style="color: red;">${error}</div>
                    </c:if>

                    <table border="1">
                        <thead>
                            <tr>
                                <th>Department</th>
                                <th>Position</th>
                                <th>Month</th>
                                <th>Year</th>
                                <th>Base Salary</th>
                                <th>Total Work Hours</th>
                                <th>Total Earnings</th>
                                <th>Total Deductions</th>
                                <th>Net Salary</th>
                                <th>Status</th>
                                <th>Pay Date</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${empty payrolls}">
                                <tr>
                                    <td colspan="12" class="no-data">No payroll records found</td>
                                </tr>
                            </c:if>
                            <c:forEach var="employee" items="${payrolls}">
                                <tr>
                                    <td>${employee.userDepartment}</td>
                                    <td>${employee.userPosition}</td>
                                    <td>${employee.month}</td>
                                    <td>${employee.year}</td>
                                    <td>
                                        <fmt:formatNumber value="${employee.baseSalary}" type="number"
                                            groupingUsed="true" maxFractionDigits="3" />
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${employee.totalWorkHours.toHours() >= 1}">
                                                ${employee.totalWorkHours.toHours()} hours
                                                <c:if test="${employee.totalWorkHours.toMinutesPart() > 0}">
                                                    ${employee.totalWorkHours.toMinutesPart()} minutes
                                                </c:if>
                                            </c:when>
                                            <c:when test="${employee.totalWorkHours.toMinutesPart() > 0}">
                                                ${employee.totalWorkHours.toMinutesPart()} minutes
                                            </c:when>
                                            <c:otherwise>
                                                0 minutes
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
                                        <fmt:formatNumber value="${employee.netSalary}" type="number"
                                            groupingUsed="true" maxFractionDigits="3" />
                                    </td>
                                    <td>
                                        <span
                                            class="status-badge ${employee.status == 'PAID' ? 'status-paid' : 
                                                                     employee.status == 'CANCELLED' ? 'status-cancelled' : 'status-pending'}">
                                            ${employee.status}
                                        </span>
                                    </td>
                                    <td>${employee.payDate}</td>
                                    <td>
                                        <form action="<c:url value='/payroll/company/detail'/>" method="get">
                                            <input type="hidden" name="payrollID" value="${employee.payrollID}">
                                            <input type="hidden" name="userID" value="${employee.userID}">
                                            <button type="submit">Details</button>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </body>

            </html>