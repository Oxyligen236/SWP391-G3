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
                <h2>Search Payroll</h2>
                <form action="<c:url value='/payroll/company'/>" method="get" class="search-form">
                    <div class="field-group">
                        <label>Employee Name:</label>
                        <input type="text" name="userName" value="${param.userName}" placeholder="Enter employee name">
                    </div>

                    <div class="field-group">
                        <label>Department:</label>
                        <select name="department">
                            <option value="">--All--</option>
                            <c:forEach var="dept" items="${departments}">
                                <option value="${dept.name}" ${param.department==dept.name ? 'selected' : '' }>
                                    ${dept.name}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="field-group">
                        <label>Position:</label>
                        <select name="position">
                            <option value="">--All--</option>
                            <c:forEach var="pos" items="${positions}">
                                <option value="${pos.name}" ${param.position==pos.name ? 'selected' : '' }>${pos.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="field-group">
                        <label>Month:</label>
                        <select name="month" id="month">
                            <option value="">All</option>
                            <c:forEach var="m" begin="1" end="12">
                                <option value="${m}" ${param.month==m ? 'selected' : '' }>${m}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="field-group">
                        <label>Year:</label>
                        <input type="number" name="year" min="2000" max="2300" value="${param.year}"
                            placeholder="Ex: 2025">
                    </div>

                    <div class="field-group">
                        <label>Status:</label>
                        <select name="status">
                            <option value="">--All--</option>
                            <option value="PENDING" ${param.status=='PENDING' ? 'selected' : '' }>PENDING</option>
                            <option value="CANCELLED" ${param.status=='CANCELLED' ? 'selected' : '' }>CANCELLED</option>
                            <option value="PAID" ${param.status=='PAID' ? 'selected' : '' }>PAID</option>
                        </select>
                    </div>

                    <button type="submit">Search</button>
                    <a href="<c:url value='/payroll/company'/>">Reset</a>
                </form>
                <hr>

                <c:if test="${not empty error}">
                    <div style="color: red;">${error}</div>
                </c:if>

                <table border="1">
                    <thead>
                        <tr>
                            <th>Employee ID</th>
                            <th>Name</th>
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
                                    <fmt:formatNumber value="${employee.netSalary}" type="number" groupingUsed="true"
                                        maxFractionDigits="3" />
                                </td>
                                <td>${employee.status}</td>
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

                <!-- Pagination Section -->
                <div class="pagination-container">
                    <form action="<c:url value='/payroll/company'/>" method="get">
                        <label class="me-2">Items per page:</label>
                        <input type="number" name="itemsPerPage" value="${itemsPerPage}" min="1" />
                        <button type="submit">Set</button>

                        <!-- Hidden fields to preserve search parameters -->
                        <input type="hidden" name="userName" value="${param.userName}" />
                        <input type="hidden" name="department" value="${param.department}" />
                        <input type="hidden" name="position" value="${param.position}" />
                        <input type="hidden" name="month" value="${param.month}" />
                        <input type="hidden" name="year" value="${param.year}" />
                        <input type="hidden" name="status" value="${param.status}" />
                        <input type="hidden" name="page" value="1" />
                    </form>

                    <nav>
                        <ul class="pagination mb-0">
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link"
                                    href="${pageContext.request.contextPath}/payroll/company?page=${currentPage - 1}&itemsPerPage=${itemsPerPage}&userName=${param.userName}&department=${param.department}&position=${param.position}&month=${param.month}&year=${param.year}&status=${param.status}">
                                    Previous
                                </a>
                            </li>

                            <li class="page-item disabled">
                                <span class="page-link">Page ${currentPage} of ${totalPages}</span>
                            </li>

                            <li class="page-item ${currentPage == totalPages || totalPages == 0 ? 'disabled' : ''}">
                                <a class="page-link"
                                    href="${pageContext.request.contextPath}/payroll/company?page=${currentPage + 1}&itemsPerPage=${itemsPerPage}&userName=${param.userName}&department=${param.department}&position=${param.position}&month=${param.month}&year=${param.year}&status=${param.status}">
                                    Next
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>

            </body>

            </html>