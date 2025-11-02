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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" />
</head>
<body>
    <div class="container mt-4">
        <h4 class="text-primary mb-4">Contract List</h4>

        <!-- Filter and Sort Section -->
        <form action="${pageContext.request.contextPath}/viewContracts" method="get" class="row g-3 mb-4">
            
            <!-- Search Field -->
            <div class="col-md-2">
                <label class="form-label">Search By</label>
                <select id="searchField" name="searchField" class="form-select" onchange="onFieldChange()">
                    <option value="">All</option>
                    <option value="userId" ${searchField == 'userId' ? 'selected' : ''}>User ID</option>
                    <option value="duration" ${searchField == 'duration' ? 'selected' : ''}>Duration</option>
                    <option value="baseSalary" ${searchField == 'baseSalary' ? 'selected' : ''}>Salary</option>
                    <option value="startDate" ${searchField == 'startDate' ? 'selected' : ''}>Start Date</option>
                    <option value="endDate" ${searchField == 'endDate' ? 'selected' : ''}>End Date</option>
                    <option value="signDate" ${searchField == 'signDate' ? 'selected' : ''}>Sign Date</option>
                </select>
            </div>

            <!-- Text Input for non-date fields -->
            <div class="col-md-3" id="textInputWrapper">
                <label class="form-label">Search Value</label>
                <input type="text" id="searchValue" name="searchValue" 
                       value="${fn:escapeXml(searchValue)}" 
                       class="form-control" placeholder="Enter search value" />
            </div>

            <!-- Date Range Input for date fields -->
            <div class="col-md-4" id="dateInputWrapper" style="display:none">
                <label class="form-label">Date Range</label>
                <div class="d-flex gap-2">
                    <input type="date" id="fromDate" name="fromDate" 
                           value="${fromDate}" class="form-control" placeholder="From" />
                    <input type="date" id="toDate" name="toDate" 
                           value="${toDate}" class="form-control" placeholder="To" />
                </div>
            </div>

            <!-- Sort Field -->
            <div class="col-md-2">
                <label class="form-label">Sort By</label>
                <select id="sortField" name="sortField" class="form-select">
                    <option value="">None</option>
                    <option value="baseSalary" ${sortField == 'baseSalary' ? 'selected' : ''}>Salary</option>
                    <option value="duration" ${sortField == 'duration' ? 'selected' : ''}>Duration</option>
                </select>
            </div>

            <!-- Sort Order -->
            <div class="col-md-2">
                <label class="form-label">Order</label>
                <select id="sortOrder" name="sortOrder" class="form-select">
                    <option value="asc" ${sortOrder == 'asc' ? 'selected' : ''}>Ascending</option>
                    <option value="desc" ${sortOrder == 'desc' ? 'selected' : ''}>Descending</option>
                </select>
            </div>

            <!-- Apply and Reset Buttons -->
            <div class="col-md-3 d-flex align-items-end gap-2">
                <button type="submit" class="btn btn-primary flex-fill">
                    Apply
                </button>
                <a href="${pageContext.request.contextPath}/viewContracts" class="btn btn-secondary flex-fill">
                    Reset
                </a>
            </div>
        </form>

        <script>
            function onFieldChange() {
                var field = document.getElementById('searchField').value;
                var textInput = document.getElementById('textInputWrapper');
                var dateInput = document.getElementById('dateInputWrapper');
                if (field === 'startDate' || field === 'endDate' || field === 'signDate') {
                    textInput.style.display = 'none';
                    dateInput.style.display = 'block';
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
        <table class="table table-bordered table-hover text-center">
            <thead class="table-primary">
                <tr>
                    <th>No</th>
                    <th>Full Name</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Duration</th>
                    <th>Salary</th>
                    <th>Type</th>
                    <th>Note</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty contracts}">
                    <tr>
                        <td colspan="9" class="text-danger fw-bold">No contracts found</td>
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
                        <td style="max-width: 200px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap;">
                            ${contract.note != null ? contract.note : '-'}
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/viewContracts?action=detail&id=${contract.contractId}" 
                               class="btn btn-sm btn-info">
                                <i class="bi bi-eye"></i> View
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Pagination -->
        <div class="d-flex justify-content-between align-items-center mt-4">
            <!-- Page info -->
            <div>
                <span class="text-muted">Page ${currentPage} of ${totalPages > 0 ? totalPages : 1}</span>
            </div>

            <!-- Pagination buttons -->
            <nav>
                <ul class="pagination mb-0">
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <c:url var="prevUrl" value="/viewContracts">
                            <c:param name="page" value="${currentPage - 1}" />
                            <c:if test="${not empty searchField}"><c:param name="searchField" value="${searchField}" /></c:if>
                            <c:if test="${not empty searchValue}"><c:param name="searchValue" value="${searchValue}" /></c:if>
                            <c:if test="${not empty fromDate}"><c:param name="fromDate" value="${fromDate}" /></c:if>
                            <c:if test="${not empty toDate}"><c:param name="toDate" value="${toDate}" /></c:if>
                            <c:if test="${not empty sortField}"><c:param name="sortField" value="${sortField}" /></c:if>
                            <c:if test="${not empty sortOrder}"><c:param name="sortOrder" value="${sortOrder}" /></c:if>
                        </c:url>
                        <a class="page-link" href="${prevUrl}">Previous</a>
                    </li>

                    <li class="page-item disabled">
                        <span class="page-link">Page ${currentPage} of ${totalPages > 0 ? totalPages : 1}</span>
                    </li>

                    <li class="page-item ${currentPage == totalPages || totalPages == 0 ? 'disabled' : ''}">
                        <c:url var="nextUrl" value="/viewContracts">
                            <c:param name="page" value="${currentPage + 1}" />
                            <c:if test="${not empty searchField}"><c:param name="searchField" value="${searchField}" /></c:if>
                            <c:if test="${not empty searchValue}"><c:param name="searchValue" value="${searchValue}" /></c:if>
                            <c:if test="${not empty fromDate}"><c:param name="fromDate" value="${fromDate}" /></c:if>
                            <c:if test="${not empty toDate}"><c:param name="toDate" value="${toDate}" /></c:if>
                            <c:if test="${not empty sortField}"><c:param name="sortField" value="${sortField}" /></c:if>
                            <c:if test="${not empty sortOrder}"><c:param name="sortOrder" value="${sortOrder}" /></c:if>
                        </c:url>
                        <a class="page-link" href="${nextUrl}">Next</a>
                    </li>
                </ul>
            </nav>
        </div>

        <p class="text-center text-muted mt-2">Showing ${contracts.size()} contracts on this page</p>
    </div>

    <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>
