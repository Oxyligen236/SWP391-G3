<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Company Attendance</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/company-attendance.css" />
    <style>
       
        .table-responsive {
            overflow-x: auto;
        }
        th, td {
            white-space: nowrap;
        }
    </style>
</head>
<body>
    <div class="container mt-4">
      
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h4 class="text-primary mb-0">Company Attendance Records</h4>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-outline-primary">
                <i class="bi bi-house-door-fill me-1"></i>Back to Home
            </a>
        </div>

  
        <c:if test="${not empty dateError}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>${dateError}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <!-- Error message for empty export -->
        <c:if test="${not empty sessionScope.errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>${sessionScope.errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <c:remove var="errorMessage" scope="session" />
        </c:if>

     
        <form action="${pageContext.request.contextPath}/company-attendance" method="get" class="row g-3 mb-4">
           
            <div class="col-md-3">
                <label class="form-label">Employee Name</label>
                <input type="text" name="userName" value="${searchUserName}" 
                       class="form-control" placeholder="Enter name">
            </div>
            <div class="col-md-3">
                <label class="form-label">Department</label>
                <select name="department" class="form-select">
                    <option value="">--All--</option>
                    <c:forEach var="dept" items="${departments}">
                        <option value="${dept.name}" <c:if test="${dept.name eq searchDepartment}">selected</c:if>>
                            ${dept.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-3">
                <label class="form-label">Position</label>
                <select name="position" class="form-select">
                    <option value="">--All--</option>
                    <c:forEach var="pos" items="${positions}">
                        <option value="${pos.name}" <c:if test="${pos.name eq searchPosition}">selected</c:if>>
                            ${pos.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-3">
                <label class="form-label">Shift</label>
                <select name="shiftId" class="form-select">
                    <option value="All" <c:if test="${selectedShift eq 'All'}">selected</c:if>>--All--</option>
                    <c:forEach var="shift" items="${shifts}">
                        <option value="${shift.shiftID}" 
                                <c:if test="${selectedShift eq shift.shiftID.toString()}">selected</c:if>>
                            ${shift.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
        
            <div class="col-md-6">
                <label class="form-label">From Date</label>
                <input type="date" name="fromDate" value="${fromDate}" class="form-control">
            </div>
            <div class="col-md-6">
                <label class="form-label">To Date</label>
                <input type="date" name="toDate" value="${toDate}" class="form-control">
            </div>
         
            <div class="col-md-12">
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" name="hasCheckout3" 
                           value="true" <c:if test="${hasCheckout3}">checked</c:if>>
                    <label class="form-check-label">Has Checkout 3</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" name="hasLate" 
                           value="true" <c:if test="${hasLate}">checked</c:if>>
                    <label class="form-check-label">Has Late</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" name="hasEarlyLeave" 
                           value="true" <c:if test="${hasEarlyLeave}">checked</c:if>>
                    <label class="form-check-label">Has Early Leave</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="checkbox" name="hasOT" 
                           value="true" <c:if test="${hasOT}">checked</c:if>>
                    <label class="form-check-label">Has OT</label>
                </div>
            </div>
       
            <div class="col-md-12 d-flex gap-2">
                <input type="hidden" name="itemsPerPage" value="${itemsPerPage}" />
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-search me-1"></i>Search
                </button>
                <a href="${pageContext.request.contextPath}/company-attendance" class="btn btn-secondary">
                    <i class="bi bi-arrow-clockwise me-1"></i>Reset
                </a>
            </div>
        </form>

        <!-- Export to Excel Form -->
        <form action="${pageContext.request.contextPath}/preview-attendance-export" method="get" id="exportForm" class="mb-3">
            <!-- Hidden inputs to pass all current filter values -->
            <input type="hidden" name="userName" value="${searchUserName}">
            <input type="hidden" name="department" value="${searchDepartment}">
            <input type="hidden" name="position" value="${searchPosition}">
            <input type="hidden" name="shiftId" value="${selectedShift}">
            <input type="hidden" name="fromDate" value="${fromDate}">
            <input type="hidden" name="toDate" value="${toDate}">
            <input type="hidden" name="hasCheckout3" value="${hasCheckout3}">
            <input type="hidden" name="hasLate" value="${hasLate}">
            <input type="hidden" name="hasEarlyLeave" value="${hasEarlyLeave}">
            <input type="hidden" name="hasOT" value="${hasOT}">
            
            <button type="submit" class="btn btn-success" id="exportButton">
                <i class="bi bi-file-earmark-excel me-1"></i>Preview Export
            </button>
            <span id="exportLoadingMessage" style="display: none; margin-left: 10px;">
                <span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>
                Generating Excel file...
            </span>
        </form>

   
        <div class="table-responsive">
            <table class="table table-bordered table-hover text-center align-middle">
                <thead class="table-primary">
                    <tr>
                        <th>No</th>
                        <th>User Name</th>
                        <th>Department</th>
                        <th>Position</th>
                        <th>Date</th>
                        <th>Day</th>
                        <th>Shift</th>
                        <th>Checkin 1</th>
                        <th>Checkout 1</th>
                        <th>Checkin 2</th>
                        <th>Checkout 2</th>
                        <th>Checkin 3</th>
                        <th>Checkout 3</th>
                        <th>Late</th>
                        <th>Early Leave</th>
                        <th>Total Hours</th>
                        <th>OT Hours</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty attendances}">
                            <tr>
                                <td colspan="18" class="text-danger fw-bold">No data available</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="a" items="${attendances}" varStatus="status">
                                <tr>
                                    <td>${(currentPage - 1) * itemsPerPage + status.index + 1}</td>
                                    <td>${a.userName}</td>
                                    <td>${a.departmentName}</td>
                                    <td>${a.positionName}</td>
                                    <td>${a.date}</td>
                                    <td>${a.day}</td>
                                    <td>${a.shiftName}</td>
                                    <td>${a.checkin1 != null ? a.checkin1 : '-'}</td>
                                    <td>${a.checkout1 != null ? a.checkout1 : '-'}</td>
                                    <td>${a.checkin2 != null ? a.checkin2 : '-'}</td>
                                    <td>${a.checkout2 != null ? a.checkout2 : '-'}</td>
                                    <td>${a.checkin3 != null ? a.checkin3 : '-'}</td>
                                    <td>${a.checkout3 != null ? a.checkout3 : '-'}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${a.lateMinutes != null && a.lateMinutes.toString() ne '00:00' && a.lateMinutes.toString() ne '00:00:00'}">
                                                ${a.lateMinutes}
                                            </c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${a.earlyLeaveMinutes != null && a.earlyLeaveMinutes.toString() ne '00:00' && a.earlyLeaveMinutes.toString() ne '00:00:00'}">
                                                ${a.earlyLeaveMinutes}
                                            </c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>${a.totalWorkHours != null ? a.totalWorkHours : '-'}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${a.otHours != null && a.otHours.toString() ne '00:00' && a.otHours.toString() ne '00:00:00'}">
                                                ${a.otHours}
                                            </c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/updateattendance?id=${a.attendanceID}" class="btn btn-sm btn-warning">
                                            <i class="bi bi-pencil-square"></i> Edit
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>


        <div class="d-flex justify-content-between align-items-center mt-4">
            <form action="${pageContext.request.contextPath}/company-attendance" method="get" class="d-flex align-items-center">
                <label class="me-2">Items per page:</label>
                <input type="number" name="itemsPerPage" value="${itemsPerPage}" 
                       class="form-control w-auto me-2" min="1" max="100" style="width: 80px;" />
                <button type="submit" class="btn btn-sm btn-primary">Set</button>
                <input type="hidden" name="userName" value="${searchUserName}" />
                <input type="hidden" name="department" value="${searchDepartment}" />
                <input type="hidden" name="position" value="${searchPosition}" />
                <input type="hidden" name="shiftId" value="${selectedShift}" />
                <input type="hidden" name="fromDate" value="${fromDate}" />
                <input type="hidden" name="toDate" value="${toDate}" />
                <input type="hidden" name="hasCheckout3" value="${hasCheckout3}" />
                <input type="hidden" name="hasLate" value="${hasLate}" />
                <input type="hidden" name="hasEarlyLeave" value="${hasEarlyLeave}" />
                <input type="hidden" name="hasOT" value="${hasOT}" />
                <input type="hidden" name="page" value="1" />
            </form>
            <nav>
                <ul class="pagination mb-0">
                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                        <a class="page-link" 
                           href="${pageContext.request.contextPath}/company-attendance?page=${currentPage - 1}&itemsPerPage=${itemsPerPage}&userName=${searchUserName}&department=${searchDepartment}&position=${searchPosition}&shiftId=${selectedShift}&fromDate=${fromDate}&toDate=${toDate}&hasCheckout3=${hasCheckout3}&hasLate=${hasLate}&hasEarlyLeave=${hasEarlyLeave}&hasOT=${hasOT}">
                            Previous
                        </a>
                    </li>
                    <li class="page-item disabled">
                        <span class="page-link">Page ${currentPage} of ${totalPages}</span>
                    </li>
                    <li class="page-item ${currentPage == totalPages || totalPages == 0 ? 'disabled' : ''}">
                        <a class="page-link" 
                           href="${pageContext.request.contextPath}/company-attendance?page=${currentPage + 1}&itemsPerPage=${itemsPerPage}&userName=${searchUserName}&department=${searchDepartment}&position=${searchPosition}&shiftId=${selectedShift}&fromDate=${fromDate}&toDate=${toDate}&hasCheckout3=${hasCheckout3}&hasLate=${hasLate}&hasEarlyLeave=${hasEarlyLeave}&hasOT=${hasOT}">
                            Next
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
        <p class="text-center text-muted mt-2">Showing ${attendances.size()} of ${totalItems} records</p>
    </div>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
    <script>
        // Export button loading indicator
        document.getElementById('exportForm').addEventListener('submit', function(e) {
            var exportButton = document.getElementById('exportButton');
            var loadingMessage = document.getElementById('exportLoadingMessage');
            
            // Disable button to prevent double-clicks
            exportButton.disabled = true;
            
            // Show loading message
            loadingMessage.style.display = 'inline';
            
            // Re-enable button after 5 seconds (in case of error or completion)
            setTimeout(function() {
                exportButton.disabled = false;
                loadingMessage.style.display = 'none';
            }, 5000);
        });
    </script>
</body>
</html>