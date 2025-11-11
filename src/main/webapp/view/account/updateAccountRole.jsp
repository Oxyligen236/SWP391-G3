<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Role - HRMS</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="<c:url value='/css/update-role.css'/>?v=2.0">
</head>
<body>
    <div class="popup-container">
        <div class="popup-header">
            <h5><i class="fas fa-user-tag"></i> Update Role</h5>
        </div>
        
        <form action="<c:url value='/updateRole'/>" method="post">
            <div class="popup-body">
                <input type="hidden" name="accountID" value="${account.accountID}">
                
                <div class="info-row">
                    <div class="info-label">
                        <i class="fas fa-id-card"></i> Account ID:
                    </div>
                    <div class="info-value">
                        <strong>${account.accountID}</strong>
                    </div>
                </div>
                
                <div class="info-row">
                    <div class="info-label">
                        <i class="fas fa-user"></i> Username:
                    </div>
                    <div class="info-value">
                        <strong>${account.username}</strong>
                    </div>
                </div>
                
                <div class="info-row">
                    <div class="info-label">
                        <i class="fas fa-signature"></i> Full Name:
                    </div>
                    <div class="info-value">
                        ${account.fullName}
                    </div>
                </div>
                
                <div class="info-row">
                    <div class="info-label">
                        <i class="fas fa-user-shield"></i> Current Role:
                    </div>
                    <div class="info-value">
                        <span class="current-role-badge">${account.roleName}</span>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="newRoleID" class="select-label">
                        <i class="fas fa-exchange-alt"></i> <strong>Select New Role:</strong>
                    </label>
                    <select class="role-select" name="newRoleID" id="newRoleID" required>
                        <option value="">-- Select Role --</option>
                        <c:forEach var="role" items="${roles}">
                            <option value="${role.roleID}" ${currentRoleID == role.roleID ? 'selected' : ''}>
                                ${role.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            
            <div class="popup-footer">
                <button type="button" class="btn-cancel" onclick="window.location.href='<c:url value='/account/view'/>'">
                    <i class="fas fa-times"></i> Cancel
                </button>
                <button type="submit" class="btn-submit">
                    <i class="fas fa-check"></i> Confirm
                </button>
            </div>
        </form>
    </div>
</body>
</html>
