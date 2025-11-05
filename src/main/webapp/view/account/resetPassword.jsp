<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Mật Khẩu - HRMS</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="<c:url value='/css/update-role.css'/>">
    <style>
        .popup-container { max-width: 500px; margin: 50px auto; border: 1px solid #ddd; padding: 20px; border-radius: 8px; background: #fff; box-shadow: 0 2px 10px rgba(0,0,0,0.2);}
        .popup-header { padding: 10px; border-radius: 5px 5px 0 0; }
        .popup-body { margin-top: 10px; }
        .info-row { display: flex; margin-bottom: 10px; }
        .info-label { width: 150px; font-weight: bold; }
        .popup-footer { margin-top: 20px; display: flex; justify-content: flex-end; gap: 10px; }
        .btn-submit { padding: 5px 15px; color: #fff; border: none; cursor: pointer; border-radius: 5px;}
        .btn-cancel { padding: 5px 15px; border-radius: 5px; border: 1px solid #ccc; background: #f8f9fa; cursor: pointer;}
        .current-role-badge { padding: 3px 8px; background: #17a2b8; color: #fff; border-radius: 5px; }
    </style>
</head>
<body>
    <div class="popup-container">
        <div class="popup-header bg-danger text-white">
            <h5><i class="fas fa-key"></i> Xác Nhận Reset Mật Khẩu</h5>
        </div>

        <!-- Thông báo reset thành công -->
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success mt-2">
                <i class="fas fa-check-circle"></i> ${successMessage}
            </div>

            <c:if test="${not empty tempPassword}">
                <div class="alert alert-info mt-2">
                    <i class="fas fa-key"></i> Mật khẩu tạm thời: <strong>${tempPassword}</strong>
                </div>
            </c:if>
        </c:if>

        <!-- Form gửi yêu cầu reset mật khẩu -->
        <form action="<c:url value='/account/reset-password'/>" method="post">
            <div class="popup-body">
                <input type="hidden" name="accountID" value="${account.accountID}">

                <div class="info-row">
                    <div class="info-label"><i class="fas fa-id-card"></i> Account ID:</div>
                    <div class="info-value"><strong>${account.accountID}</strong></div>
                </div>

                <div class="info-row">
                    <div class="info-label"><i class="fas fa-user"></i> Username:</div>
                    <div class="info-value"><strong>${account.username}</strong></div>
                </div>

                <div class="info-row">
                    <div class="info-label"><i class="fas fa-signature"></i> Họ Tên:</div>
                    <div class="info-value">${account.fullName}</div>
                </div>

                <div class="info-row">
                    <div class="info-label"><i class="fas fa-user-shield"></i> Role Hiện Tại:</div>
                    <div class="info-value"><span class="current-role-badge">${account.roleName}</span></div>
                </div>

                <c:if test="${empty successMessage}">
                    <div class="alert alert-warning">
                        <i class="fas fa-exclamation-triangle"></i>
                        <strong>Cảnh báo:</strong> Mật khẩu sẽ được đặt lại và gửi cho người dùng. 
                        Hãy đảm bảo bạn đã thông báo cho họ trước khi thực hiện hành động này.
                    </div>
                </c:if>
            </div>

            <div class="popup-footer">
                <button type="button" class="btn-cancel" 
                        onclick="window.location.href='<c:url value='/account/view'/>'">
                    <i class="fas fa-times"></i> Hủy
                </button>
                <c:if test="${empty successMessage}">
                    <button type="submit" class="btn-submit bg-danger">
                        <i class="fas fa-check"></i> Xác Nhận Reset
                    </button>
                </c:if>
                <c:if test="${not empty successMessage}">
                    <button type="button" class="btn-submit bg-secondary"
                            onclick="window.location.href='<c:url value='/account/view'/>'">
                        <i class="fas fa-arrow-left"></i> Quay lại danh sách
                    </button>
                </c:if>
            </div>
        </form>
    </div>
</body>
</html>
