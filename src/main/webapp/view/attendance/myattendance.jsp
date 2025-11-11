<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>My Attendance</title>
    <link rel="stylesheet" href="<c:url value='/css/myattendance.css'/>">
    <%-- Loại bỏ style tag cũ vì đã chuyển vào file CSS --%>
</head>
<body>
    <%-- Thêm container bao bọc để áp dụng max-width và padding --%>
    <div class="attendance-container"> 
        
        <h2>My Attendance Records</h2>

        <%-- Form Lọc (Điều chỉnh cấu trúc form để khớp với CSS Grid/Flexbox mới) --%>
        <form method="get" action="${pageContext.request.contextPath}/myattendance" class="filter-form-container">
            <div class="form-row">
                
                <div class="filter-group">
                    <label for="startDate">From:</label>
                    <input type="date" id="startDate" name="startDate" value="${param.startDate}">
                </div>
                
                <div class="filter-group">
                    <label for="endDate">To:</label>
                    <input type="date" id="endDate" name="endDate" value="${param.endDate}">
                </div>

                <div class="filter-group">
                    <label for="dayOfWeek">Day:</label>
                    <select id="dayOfWeek" name="dayOfWeek">
                        <option value="">All</option>
                        <option value="Monday" ${param.dayOfWeek == 'Monday' ? 'selected' : ''}>Monday</option>
                        <option value="Tuesday" ${param.dayOfWeek == 'Tuesday' ? 'selected' : ''}>Tuesday</option>
                        <option value="Wednesday" ${param.dayOfWeek == 'Wednesday' ? 'selected' : ''}>Wednesday</option>
                        <option value="Thursday" ${param.dayOfWeek == 'Thursday' ? 'selected' : ''}>Thursday</option>
                        <option value="Friday" ${param.dayOfWeek == 'Friday' ? 'selected' : ''}>Friday</option>
                        <option value="Saturday" ${param.dayOfWeek == 'Saturday' ? 'selected' : ''}>Saturday</option>
                        <option value="Sunday" ${param.dayOfWeek == 'Sunday' ? 'selected' : ''}>Sunday</option>
                    </select>
                </div>
                
                <%-- Để nút bấm nằm ngoài form-row để có thể căn chỉnh flex-end cho đẹp hơn nếu cần --%>
                <%-- Tuy nhiên, trong CSS mới, tôi đã điều chỉnh .button-group để căn chỉnh tự động --%>

            </div>
            
            <input type="hidden" name="page" value="1" />
            <input type="hidden" name="limit" value="${limit}" />
            
            <%-- Đặt nút bấm ra ngoài form-row nhưng vẫn trong form --%>
            <div class="button-group">
                <button type="submit" class="btn primary">Filter</button>
                <a href="${pageContext.request.contextPath}/myattendance">
                    <button type="button" class="btn secondary">Reset</button>
                </a>
            </div>
        </form>

        <table cellspacing="0" cellpadding="6">
            <thead>
                <tr>
                    <th class="sort-header">Date <span class="sort-icon">▼</span></th>
                    <th>Day</th>
                    <th>Checkin 1</th>
                    <th>Checkout 1</th>
                    <th>Checkin 2</th>
                    <th>Checkout 2</th>
                    <th>Total Hours</th>
                    <th>OT Hours</th>
                </tr>
            </thead>
            <tbody>
        <c:choose>
        <c:when test="${not empty attendanceList}">
            <c:forEach var="a" items="${attendanceList}">
                <tr>
                    <td>${a.date}</td>
                    <td>${a.day}</td>
                    <td>${a.checkin1}</td>
                    <td>${a.checkout1}</td>
                    <td>${a.checkin2}</td>
                    <td>${a.checkout2}</td>
                    <td>${a.totalWorkHours}</td>
                    <td>${a.otHours}</td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr><td colspan="8" class="no-data-found">No attendance records found.</td></tr>
        </c:otherwise>
        </c:choose>
            </tbody>
        </table>

        <%-- Phần Phân trang (Paging) --%>
        <c:set var="currentPage" value="${currentPage != null ? currentPage : 1}"/>
        <c:set var="totalPages" value="${totalPages != null ? totalPages : 0}"/>
        <c:set var="limit" value="${limit != null ? limit : 10}"/>

        <c:if test="${totalPages > 0 || not empty param.startDate || not empty param.endDate || not empty param.dayOfWeek}"> 
            <div class="pagination-container">
                
                <%-- Form chọn số mục trên mỗi trang (Items per page) --%>
                <form action="${pageContext.request.contextPath}/myattendance" method="get" class="items-per-page-form">
                    <label>Items per page:</label>
                    <input type="number" name="limit" value="${limit}" min="1" max="50" class="input-limit" />
                    <button type="submit" class="btn primary small">Apply</button>
                    <%-- Note: Đổi nút Apply thành primary và small để đồng bộ với style mới --%>

                    <%-- Truyền lại các tham số lọc hiện tại --%>
                    <input type="hidden" name="startDate" value="${param.startDate}" />
                    <input type="hidden" name="endDate" value="${param.endDate}" />
                    <input type="hidden" name="dayOfWeek" value="${param.dayOfWeek}" />
                    <input type="hidden" name="page" value="1" />
                </form>

                <nav class="pagination-nav">
                    <ul class="pagination">
                        
                        <%-- Nút Previous --%>
                        <li class="page-item ${currentPage == 1 || totalPages == 0 ? 'disabled' : ''}">
                            <c:url var="prevUrl" value="/myattendance">
                                <c:param name="page" value="${currentPage - 1}"/>
                                <c:param name="limit" value="${limit}"/>
                                <c:param name="startDate" value="${param.startDate}"/>
                                <c:param name="endDate" value="${param.endDate}"/>
                                <c:param name="dayOfWeek" value="${param.dayOfWeek}"/>
                            </c:url>
                            <a class="page-link" href="${prevUrl}">Previous</a>
                        </li>

                        <%-- Hiển thị Trang hiện tại / Tổng số trang --%>
                        <li class="page-item page-status disabled">
                            <span class="page-link">Page ${currentPage} / ${totalPages}</span>
                        </li>

                        <%-- Nút Next --%>
                        <li class="page-item ${currentPage == totalPages || totalPages == 0 ? 'disabled' : ''}">
                            <c:url var="nextUrl" value="/myattendance">
                                <c:param name="page" value="${currentPage + 1}"/>
                                <c:param name="limit" value="${limit}"/>
                                <c:param name="startDate" value="${param.startDate}"/>
                                <c:param name="endDate" value="${param.endDate}"/>
                                <c:param name="dayOfWeek" value="${param.dayOfWeek}"/>
                            </c:url>
                            <a class="page-link" href="${nextUrl}">Next</a>
                        </li>
                    </ul>
                </nav>
            </div>
        </c:if>

    </div> <%-- Kết thúc attendance-container --%>
</body>
</html>