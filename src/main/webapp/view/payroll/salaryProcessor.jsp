<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Salary Processing</title>
                    <link rel="stylesheet" href="<c:url value='/css/salaryProcessor.css'/>">
                </head>

                <body>
                    <div class="salary-processor-container">
                        <h1>Salary Processing System</h1>
                        <div class="header-info">
                            <p class="month-info">Month ${currentMonth}/${currentYear}</p>
                            <p class="pay-date-info">
                                <strong>Pay Date:</strong>
                                <fmt:formatDate value="${payDate}" pattern="dd/MM/yyyy" />
                            </p>
                        </div>

                        <c:if test="${not empty successMessage}">
                            <div class="alert alert-success">
                                ${successMessage}
                                <button type="button" class="btn-close"
                                    onclick="this.parentElement.style.display='none'">×</button>
                            </div>
                        </c:if>

                        <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger">
                                ${errorMessage}
                                <button type="button" class="btn-close"
                                    onclick="this.parentElement.style.display='none'">×</button>
                            </div>
                        </c:if>

                        <c:if test="${not empty successCount || not empty failCount}">
                            <div class="stats-section">
                                <div class="stats-card">
                                    <h3 class="text-success">${successCount}</h3>
                                    <p>Success</p>
                                </div>
                                <div class="stats-card">
                                    <h3 class="text-danger">${failCount}</h3>
                                    <p>Failure</p>
                                </div>
                                <div class="stats-card">
                                    <h3>${fn:length(employeesWithoutPayroll)}</h3>
                                    <p>No Payroll</p>
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${empty successCount && empty failCount}">
                            <div class="stats-section">
                                <div class="stats-card">
                                    <h3>${fn:length(employeesWithoutPayroll)}</h3>
                                    <p>Employees Without Payroll</p>
                                </div>
                            </div>
                        </c:if>

                        <div class="action-section">
                            <div class="action-card">
                                <h3>Create Payroll</h3>
                                <p>Create basic payroll for employees without payroll this month</p>
                                <p class="note-text">
                                    <strong>Note:</strong> Only create basic information (UserID, BaseSalary, Month,
                                    Year,
                                    PayDate).
                                    Salary calculation should be done after having complete attendance data.
                                </p>
                                <form action="${pageContext.request.contextPath}/salary" method="post">
                                    <input type="hidden" name="action" value="generate">
                                    <button type="submit" class="btn-generate" ${fn:length(employeesWithoutPayroll)==0
                                        ? 'disabled' : '' }>
                                        Create Payroll (${fn:length(employeesWithoutPayroll)} employees)
                                    </button>
                                </form>
                            </div>

                            <div class="action-card">
                                <h3>Calculate Salary</h3>
                                <p>Calculate salary for all employees for the current month</p>
                                <p class="note-text">
                                    <strong>Note:</strong> Calculate NetSalary, TotalWorkHours based on attendance data,
                                    OT, leave. Perform after the 25th of the month.
                                </p>
                                <form action="${pageContext.request.contextPath}/salary" method="post">
                                    <input type="hidden" name="action" value="calculate">
                                    <button type="submit" class="btn-calculate">Calculate Salary</button>
                                </form>
                            </div>
                        </div>
                        <c:if test="${not empty employeesWithoutPayroll}">
                            <div class="employee-table-section">
                                <h2>List of Employees Without Payroll (${totalEmployees} employees)</h2>
                                <table class="employee-table">
                                    <thead>
                                        <tr>
                                            <th>No</th>
                                            <th>ID</th>
                                            <th>Full Name</th>
                                            <th>Email</th>
                                            <th>Phone Number</th>
                                            <th>Department</th>
                                            <th>Position</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="employee" items="${employeesWithoutPayroll}" varStatus="status">
                                            <tr>
                                                <td>${(currentPage - 1) * itemsPerPage + status.index + 1}</td>
                                                <td>${employee.userId}</td>
                                                <td>${employee.fullname}</td>
                                                <td>${employee.email}</td>
                                                <td>${employee.phoneNumber}</td>
                                                <td>${employee.departmentName}</td>
                                                <td>${employee.positionName}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>

                                <div class="pagination-container">
                                    <form action="<c:url value='/salary'/>" method="get" class="items-per-page-form">
                                        <label>Items per page:</label>
                                        <input type="number" name="itemsPerPage" value="${itemsPerPage}" min="1"
                                            max="50" />
                                        <button type="submit">Apply</button>
                                        <input type="hidden" name="page" value="1" />
                                    </form>

                                    <nav class="pagination-nav">
                                        <ul class="pagination">
                                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                                <a class="page-link"
                                                    href="<c:url value='/salary?page=${currentPage - 1}&itemsPerPage=${itemsPerPage}'/>">
                                                    Previous
                                                </a>
                                            </li>

                                            <li class="page-item disabled">
                                                <span class="page-link">Page ${currentPage} / ${totalPages}</span>
                                            </li>

                                            <li
                                                class="page-item ${currentPage == totalPages || totalPages == 0 ? 'disabled' : ''}">
                                                <a class="page-link"
                                                    href="<c:url value='/salary?page=${currentPage + 1}&itemsPerPage=${itemsPerPage}'/>">
                                                    Next
                                                </a>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                        </c:if>

                    </div>
                </body>

                </html>