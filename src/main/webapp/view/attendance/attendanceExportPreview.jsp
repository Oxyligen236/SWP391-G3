<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Attendance Export Preview - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .summary-section {
            background-color: #f8f9fa;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
        }
        .filter-item {
            margin-bottom: 8px;
        }
        .table-container {
            margin-top: 20px;
        }
        .action-buttons {
            margin-top: 20px;
            margin-bottom: 20px;
        }
        /* Custom table header style */
        .table thead th {
            background-color: #f8f9fa !important;
            color: #2c3e50 !important;
            font-weight: 600;
            border-bottom: 2px solid #e9ecef !important;
        }
    </style>
</head>
<body>
    <div class="container-fluid mt-4">
        <!-- Header Section -->
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2><i class="bi bi-file-earmark-spreadsheet"></i> Attendance Export Preview</h2>
        </div>

        <!-- Summary Section -->
        <div class="summary-section">
            <h5 class="mb-3"><i class="bi bi-info-circle"></i> Export Summary</h5>
            <div class="row">
                <div class="col-md-4">
                    <strong>Total Records:</strong> ${totalRecords}
                </div>
                <div class="col-md-4">
                    <strong>Export Timestamp:</strong> 
                    <%
                        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String timestamp = sdf.format(new java.util.Date());
                    %>
                    <%= timestamp %>
                </div>
            </div>
            
            <!-- Applied Filters -->
            <div class="mt-3">
                <strong>Applied Filters:</strong>
                <c:choose>
                    <c:when test="${empty appliedFilters}">
                        <span class="text-muted">None</span>
                    </c:when>
                    <c:otherwise>
                        <div class="mt-2">
                            <c:forEach var="filter" items="${appliedFilters}">
                                <div class="filter-item">
                                    <span class="badge bg-primary">${filter.key}:</span> ${filter.value}
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- Action Buttons -->
        <div class="action-buttons d-flex gap-3 justify-content-center">
            <!-- Back Button -->
            <c:url var="backUrl" value="/company-attendance">
                <c:if test="${not empty searchUserName}">
                    <c:param name="userName" value="${searchUserName}"/>
                </c:if>
                <c:if test="${not empty searchDepartment}">
                    <c:param name="department" value="${searchDepartment}"/>
                </c:if>
                <c:if test="${not empty searchPosition}">
                    <c:param name="position" value="${searchPosition}"/>
                </c:if>
                <c:if test="${not empty selectedShift}">
                    <c:param name="shiftId" value="${selectedShift}"/>
                </c:if>
                <c:if test="${not empty fromDate}">
                    <c:param name="fromDate" value="${fromDate}"/>
                </c:if>
                <c:if test="${not empty toDate}">
                    <c:param name="toDate" value="${toDate}"/>
                </c:if>
                <c:if test="${hasCheckout3}">
                    <c:param name="hasCheckout3" value="true"/>
                </c:if>
                <c:if test="${hasLate}">
                    <c:param name="hasLate" value="true"/>
                </c:if>
                <c:if test="${hasEarlyLeave}">
                    <c:param name="hasEarlyLeave" value="true"/>
                </c:if>
                <c:if test="${hasOT}">
                    <c:param name="hasOT" value="true"/>
                </c:if>
            </c:url>
            <a href="${backUrl}" class="btn btn-secondary">
                <i class="bi bi-arrow-left"></i> Back
            </a>

            <!-- Export to Excel Form -->
            <form action="${pageContext.request.contextPath}/export-attendance" method="get" style="display: inline;">
                <c:if test="${not empty searchUserName}">
                    <input type="hidden" name="userName" value="${searchUserName}"/>
                </c:if>
                <c:if test="${not empty searchDepartment}">
                    <input type="hidden" name="department" value="${searchDepartment}"/>
                </c:if>
                <c:if test="${not empty searchPosition}">
                    <input type="hidden" name="position" value="${searchPosition}"/>
                </c:if>
                <c:if test="${not empty selectedShift}">
                    <input type="hidden" name="shiftId" value="${selectedShift}"/>
                </c:if>
                <c:if test="${not empty fromDate}">
                    <input type="hidden" name="fromDate" value="${fromDate}"/>
                </c:if>
                <c:if test="${not empty toDate}">
                    <input type="hidden" name="toDate" value="${toDate}"/>
                </c:if>
                <c:if test="${hasCheckout3}">
                    <input type="hidden" name="hasCheckout3" value="true"/>
                </c:if>
                <c:if test="${hasLate}">
                    <input type="hidden" name="hasLate" value="true"/>
                </c:if>
                <c:if test="${hasEarlyLeave}">
                    <input type="hidden" name="hasEarlyLeave" value="true"/>
                </c:if>
                <c:if test="${hasOT}">
                    <input type="hidden" name="hasOT" value="true"/>
                </c:if>
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-file-earmark-excel"></i> Export to Excel
                </button>
            </form>
        </div>

        <!-- Data Table -->
        <div class="table-container">
            <div class="table-responsive">
                <table class="table table-bordered table-hover text-center">
                    <thead>
                        <tr>
                            <th>Employee ID</th>
                            <th>Employee Name</th>
                            <th>Department</th>
                            <th>Position</th>
                            <th>Date</th>
                            <th>Day</th>
                            <th>Shift</th>
                            <th>Check-in 1</th>
                            <th>Check-out 1</th>
                            <th>Check-in 2</th>
                            <th>Check-out 2</th>
                            <th>Check-in 3</th>
                            <th>Check-out 3</th>
                            <th>Late Minutes</th>
                            <th>Early Leave Minutes</th>
                            <th>Total Work Hours</th>
                            <th>OT Hours</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="a" items="${attendances}">
                            <tr>
                                <td>${a.userID}</td>
                                <td>${a.userName}</td>
                                <td>${a.departmentName}</td>
                                <td>${a.positionName}</td>
                                <td>${a.date}</td>
                                <td>${a.day}</td>
                                <td>${a.shiftName}</td>
                                <td><c:if test="${not empty a.checkin1}">${a.checkin1}</c:if></td>
                                <td><c:if test="${not empty a.checkout1}">${a.checkout1}</c:if></td>
                                <td><c:if test="${not empty a.checkin2}">${a.checkin2}</c:if></td>
                                <td><c:if test="${not empty a.checkout2}">${a.checkout2}</c:if></td>
                                <td><c:if test="${not empty a.checkin3}">${a.checkin3}</c:if></td>
                                <td><c:if test="${not empty a.checkout3}">${a.checkout3}</c:if></td>
                                <td><c:if test="${not empty a.lateMinutes}">${a.lateMinutes}</c:if></td>
                                <td><c:if test="${not empty a.earlyLeaveMinutes}">${a.earlyLeaveMinutes}</c:if></td>
                                <td><c:if test="${not empty a.totalWorkHours}">${a.totalWorkHours}</c:if></td>
                                <td><c:if test="${not empty a.otHours}">${a.otHours}</c:if></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
