<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Contract List</title>
    <link rel="stylesheet" href="<c:url value='/css/contract-list.css'/>" />
</head>
<body>
    <div class="contract-container">
        <h1>Contract List</h1>

        <!-- Filter and Sort Section -->
        <form action="${pageContext.request.contextPath}/viewContracts" method="get" id="searchForm">
            <!-- Search Field -->
            <select id="searchField" name="searchField" onchange="onFieldChange()">
                <option value="">All</option>
                <option value="userId" ${searchField == 'userId' ? 'selected' : ''}>User ID</option>
                <option value="duration" ${searchField == 'duration' ? 'selected' : ''}>Duration</option>
                <option value="baseSalary" ${searchField == 'baseSalary' ? 'selected' : ''}>Salary</option>
                <option value="startDate" ${searchField == 'startDate' ? 'selected' : ''}>Start Date</option>
                <option value="endDate" ${searchField == 'endDate' ? 'selected' : ''}>End Date</option>
                <option value="signDate" ${searchField == 'signDate' ? 'selected' : ''}>Sign Date</option>
            </select>

            <!-- Text Input for non-date fields -->
            <input type="text" id="searchValue" name="searchValue" 
                   value="${fn:escapeXml(searchValue)}" 
                   placeholder="Enter search value"
                   style="flex: 1; min-width: 180px;" />

            <!-- Date Range Input for date fields -->
            <div id="dateInputWrapper" style="display:none; flex: 1;">
                <input type="date" id="fromDate" name="fromDate" 
                       value="${fromDate}" placeholder="From" />
                <input type="date" id="toDate" name="toDate" 
                       value="${toDate}" placeholder="To" />
            </div>

            <!-- Sort Field -->
            <select id="sortField" name="sortField">
                <option value="">Sort: None</option>
                <option value="baseSalary" ${sortField == 'baseSalary' ? 'selected' : ''}>Sort: Salary</option>
                <option value="duration" ${sortField == 'duration' ? 'selected' : ''}>Sort: Duration</option>
            </select>

            <!-- Sort Order -->
            <select id="sortOrder" name="sortOrder">
                <option value="asc" ${sortOrder == 'asc' ? 'selected' : ''}>Ascending</option>
                <option value="desc" ${sortOrder == 'desc' ? 'selected' : ''}>Descending</option>
            </select>

            <!-- Apply and Reset Buttons -->
            <button type="submit">Apply</button>
            <a href="${pageContext.request.contextPath}/viewContracts" class="btn">Reset</a>
        </form>

        <script>
            function onFieldChange() {
                var field = document.getElementById('searchField').value;
                var textInput = document.getElementById('searchValue');
                var dateInput = document.getElementById('dateInputWrapper');
                if (field === 'startDate' || field === 'endDate' || field === 'signDate') {
                    textInput.style.display = 'none';
                    dateInput.style.display = 'flex';
                } else {
                    textInput.style.display = 'block';
                    dateInput.style.display = 'none';
                }
            }

            window.addEventListener('DOMContentLoaded', function () {
                onFieldChange();
            });
        </script>

        <!-- Table -->
        <table>
            <thead>
                <tr>
                    <th>No</th>
                    <th>Full Name</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Duration</th>
                    <th>Salary</th>
                    <th>Type</th>
                    <th>Status</th>
                    <th>Note</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty contracts}">
                    <tr>
                        <td colspan="10" class="text-danger fw-bold">No contracts found</td>
                    </tr>
                </c:if>

                <c:forEach var="contract" items="${contracts}" varStatus="status">
                    <tr>
                        <td>${(currentPage - 1) * 10 + status.index + 1}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty userFullNames[contract.userId]}">
                                    ${fn:escapeXml(userFullNames[contract.userId])}
                                </c:when>
                                <c:otherwise>
                                    User ${contract.userId}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>${contract.startDate}</td>
                        <td>${contract.endDate}</td>
                        <td>${contract.duration}</td>
                        <td><fmt:formatNumber value="${contract.baseSalary}" type="number" groupingUsed="true" /> VNĐ</td>
                        <td>${contract.contractTypeName}</td>
                        <td>
                            <c:choose>
                                <c:when test="${contract.status == 'Pending'}">
                                    <span cl
                                    ass="badge bg-warning text-dark">${contract.status}</span>
                                </c:when>
                                <c:when test="${contract.status == 'Approved'}">
                                    <span class="badge bg-info">${contract.status}</span>
                                </c:when>
                                <c:when test="${contract.status == 'Active'}">
                                    <span class="badge bg-success">${contract.status}</span>
                                </c:when>
                                <c:when test="${contract.status == 'Expired'}">
                                    <span class="badge bg-secondary">${contract.status}</span>
                                </c:when>
                                <c:when test="${contract.status == 'Archived'}">
                                    <span class="badge bg-dark">${contract.status}</span>
                                </c:when>
                                <c:when test="${contract.status == 'Cancelled'}">
                                    <span class="badge bg-danger">${contract.status}</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary">${contract.status != null ? contract.status : 'N/A'}</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td style="max-width: 200px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
                            ${contract.note != null ? contract.note : '-'}
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/viewContracts?action=detail&id=${contract.contractId}">View</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Pagination -->
        <div class="bottom-bar">
            <div class="muted">Showing ${contracts.size()} contracts | Page ${currentPage} of ${totalPages > 0 ? totalPages : 1}</div>

            <div class="pagination">
                <c:url var="prevUrl" value="/viewContracts">
                    <c:param name="page" value="${currentPage - 1}" />
                    <c:if test="${not empty searchField}"><c:param name="searchField" value="${searchField}" /></c:if>
                    <c:if test="${not empty searchValue}"><c:param name="searchValue" value="${searchValue}" /></c:if>
                    <c:if test="${not empty fromDate}"><c:param name="fromDate" value="${fromDate}" /></c:if>
                    <c:if test="${not empty toDate}"><c:param name="toDate" value="${toDate}" /></c:if>
                    <c:if test="${not empty sortField}"><c:param name="sortField" value="${sortField}" /></c:if>
                    <c:if test="${not empty sortOrder}"><c:param name="sortOrder" value="${sortOrder}" /></c:if>
                </c:url>
                
                <c:url var="nextUrl" value="/viewContracts">
                    <c:param name="page" value="${currentPage + 1}" />
                    <c:if test="${not empty searchField}"><c:param name="searchField" value="${searchField}" /></c:if>
                    <c:if test="${not empty searchValue}"><c:param name="searchValue" value="${searchValue}" /></c:if>
                    <c:if test="${not empty fromDate}"><c:param name="fromDate" value="${fromDate}" /></c:if>
                    <c:if test="${not empty toDate}"><c:param name="toDate" value="${toDate}" /></c:if>
                    <c:if test="${not empty sortField}"><c:param name="sortField" value="${sortField}" /></c:if>
                    <c:if test="${not empty sortOrder}"><c:param name="sortOrder" value="${sortOrder}" /></c:if>
                </c:url>

                <c:if test="${currentPage > 1}">
                    <a href="${prevUrl}">Previous</a>
                </c:if>
                
                <c:if test="${totalPages > 0}">
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <c:url var="pageUrl" value="/viewContracts">
                            <c:param name="page" value="${i}" />
                            <c:if test="${not empty searchField}"><c:param name="searchField" value="${searchField}" /></c:if>
                            <c:if test="${not empty searchValue}"><c:param name="searchValue" value="${searchValue}" /></c:if>
                            <c:if test="${not empty fromDate}"><c:param name="fromDate" value="${fromDate}" /></c:if>
                            <c:if test="${not empty toDate}"><c:param name="toDate" value="${toDate}" /></c:if>
                            <c:if test="${not empty sortField}"><c:param name="sortField" value="${sortField}" /></c:if>
                            <c:if test="${not empty sortOrder}"><c:param name="sortOrder" value="${sortOrder}" /></c:if>
                        </c:url>
                        <a href="${pageUrl}" class="${i == currentPage ? 'active' : ''}">${i}</a>
                    </c:forEach>
                </c:if>

                <c:if test="${currentPage < totalPages && totalPages > 0}">
                    <a href="${nextUrl}">Next</a>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>
