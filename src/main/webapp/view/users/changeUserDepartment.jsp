<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cập Nhật Phòng Ban - HRMS</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="<c:url value='/css/update-department.css'/>">
</head>
<body>
    <div class="popup-container">
        <div class="popup-header">
            <h5><i class="fas fa-building"></i> Cập Nhật Phòng Ban</h5>
        </div>
        
        <form action="<c:url value='/updateDepartment'/>" method="post">
            <div class="popup-body">
                <input type="hidden" name="userID" value="${user.userId}">
                
                <div class="info-row">
                    <div class="info-label">
                        <i class="fas fa-id-card"></i> User ID:
                    </div>
                    <div class="info-value">
                        <strong>${user.userId}</strong>
                    </div>
                </div>
                
                <div class="info-row">
                    <div class="info-label">
                        <i class="fas fa-user"></i> Họ Tên:
                    </div>
                    <div class="info-value">
                        <strong>${user.fullname}</strong>
                    </div>
                </div>
                
                <div class="info-row">
                    <div class="info-label">
                        <i class="fas fa-envelope"></i> Email:
                    </div>
                    <div class="info-value">
                        ${user.email}
                    </div>
                </div>
                
                <div class="info-row">
                    <div class="info-label">
                        <i class="fas fa-building"></i> Phòng Ban Hiện Tại:
                    </div>
                    <div class="info-value">
                        <span class="current-department-badge">${user.departmentName}</span>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="newDepartmentID" class="select-label">
                        <i class="fas fa-exchange-alt"></i> <strong>Chọn Phòng Ban Mới:</strong>
                    </label>
                    <select class="department-select" name="newDepartmentID" id="newDepartmentID" required>
                        <option value="">-- Chọn phòng ban --</option>
                        <c:forEach var="dept" items="${departments}">
                            <option value="${dept.departmentId}" ${currentDepartmentID == dept.departmentId ? 'selected' : ''}>
                                ${dept.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            
            <div class="popup-footer">
                <button type="button" class="btn-cancel" onclick="window.location.href=''">
                    <i class="fas fa-times"></i> Hủy
                </button>
                <button type="submit" class="btn-submit">
                    <i class="fas fa-check"></i> Xác Nhận
                </button>
            </div>
        </form>
    </div>
</body>
</html>
