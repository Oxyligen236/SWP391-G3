<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                <p class="month-info">Tháng ${currentMonth}/${currentYear}</p>

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
                            <p>Thành công</p>
                        </div>
                        <div class="stats-card">
                            <h3 class="text-danger">${failCount}</h3>
                            <p>Thất bại</p>
                        </div>
                        <div class="stats-card">
                            <h3>${employeesWithoutPayroll}</h3>
                            <p>Chưa có bảng lương</p>
                        </div>
                    </div>
                </c:if>

                <c:if test="${empty successCount && empty failCount}">
                    <div class="stats-section">
                        <div class="stats-card">
                            <h3>${employeesWithoutPayroll}</h3>
                            <p>Nhân viên chưa có bảng lương</p>
                        </div>
                    </div>
                </c:if>

                <div class="action-section">
                    <div class="action-card">
                        <h3>Tạo bảng lương</h3>
                        <p>Tạo bảng lương cho nhân viên chưa có bảng lương tháng này</p>
                        <form action="${pageContext.request.contextPath}/salary" method="post">
                            <input type="hidden" name="action" value="generate">
                            <button type="submit" class="btn-generate" ${employeesWithoutPayroll==0 ? 'disabled' : '' }>
                                Tạo bảng lương (${employeesWithoutPayroll} nhân viên)
                            </button>
                        </form>
                    </div>

                    <div class="action-card">
                        <h3>Tính lương</h3>
                        <p>Tính lương cho tất cả nhân viên tháng hiện tại</p>
                        <form action="${pageContext.request.contextPath}/salary" method="post">
                            <input type="hidden" name="action" value="calculate">
                            <button type="submit" class="btn-calculate">Tính lương</button>
                        </form>
                    </div>
                </div>
            </div>
        </body>

        </html>