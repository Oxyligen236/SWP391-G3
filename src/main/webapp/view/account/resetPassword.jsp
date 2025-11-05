<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Mật Khẩu - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">

    <style>
        body {
            background-color: #e3f2fd; /* xanh dương pastel nhạt */
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        .popup-container {
            max-width: 500px;
            margin: 60px auto;
            padding: 25px;
            border-radius: 12px;
            background: #ffffff;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
        }
        .popup-header {
            padding: 15px;
            border-radius: 10px 10px 0 0;
            background-color: #90caf9; /* xanh dương pastel */
            color: #0d47a1;
            text-align: center;
            font-weight: bold;
            font-size: 1.2rem;
        }
        .popup-body {
            margin-top: 15px;
        }
        .info-row {
            display: flex;
            margin-bottom: 12px;
        }
        .info-label {
            width: 140px;
            font-weight: 600;
            color: #1976d2;
        }
        .info-value {
            flex: 1;
            color: #0d47a1;
        }
        .popup-footer {
            margin-top: 25px;
            display: flex;
            justify-content: flex-end;
            gap: 12px;
        }
        .btn-submit {
            padding: 6px 18px;
            color: #fff;
            border: none;
            cursor: pointer;
            border-radius: 8px;
            font-weight: 500;
        }
        .btn-cancel {
            padding: 6px 18px;
            border-radius: 8px;
            border: 1px solid #90caf9;
            background: #e3f2fd;
            color: #0d47a1;
            cursor: pointer;
            font-weight: 500;
        }
        .current-role-badge {
            padding: 4px 10px;
            background: #bbdefb; /* xanh pastel nhạt */
            color: #0d47a1;
            border-radius: 8px;
            font-weight: 500;
        }
        .alert-info {
            background-color: #bbdefb;
            color: #0d47a1;
        }
        .alert-success {
            background-color: #90caf9;
            color: #0d47a1;
        }
        .alert-warning {
            background-color: #e3f2fd;
            color: #1565c0;
            border: 1px solid #90caf9;
        }
        .btn-submit.bg-primary { background-color: #42a5f5; }
        .btn-submit.bg-primary:hover { background-color: #1e88e5; }
        .btn-submit.bg-secondary { background-color: #90caf9; color: #0d47a1; }
        .btn-submit.bg-secondary:hover { background-color: #64b5f6; }
    </style>
</head>

<body>
    <div class="popup-container">
        <div class="popup-header">
            <i class="fas fa-key"></i> Xác Nhận Reset Mật Khẩu
        </div>

        <form action="<c:url value='/account/reset-password'/>" method="post" onsubmit="return confirmReset()">
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

                <!-- Thông báo thành công -->
                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success alert-dismissible fade show mt-3" role="alert">
                        <i class="fas fa-check-circle"></i> ${successMessage}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                    <c:if test="${not empty tempPassword}">
                        <div class="alert alert-info alert-dismissible fade show mt-2" role="alert">
                            <i class="fas fa-key"></i> Mật khẩu tạm thời: <strong>${tempPassword}</strong>
                            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                        </div>
                    </c:if>
                </c:if>

                <!-- Cảnh báo reset -->
                <c:if test="${empty successMessage}">
                    <div class="alert alert-warning mt-3">
                        <i class="fas fa-exclamation-triangle"></i>
                        <strong>Cảnh báo:</strong> Mật khẩu sẽ được đặt lại và gửi cho người dùng. Hãy đảm bảo bạn đã thông báo cho họ.
                    </div>
                </c:if>
            </div>

            <div class="popup-footer">
                <!-- Nút Hủy luôn hiển thị -->
                <button type="button" class="btn-cancel" onclick="window.location.href='<c:url value='/account/view'/>'">
                    <i class="fas fa-times"></i> Hủy
                </button>

                <!-- Nút Xác Nhận Reset -->
                <c:if test="${empty successMessage}">
                    <button type="submit" class="btn-submit bg-primary">
                        <i class="fas fa-check"></i> Xác Nhận Reset
                    </button>
                </c:if>

                <!-- Nút Quay lại danh sách -->
                <c:if test="${not empty successMessage}">
                    <button type="button" class="btn-submit bg-secondary" onclick="window.location.href='<c:url value='/account/view'/>'">
                        <i class="fas fa-arrow-left"></i> Quay lại danh sách
                    </button>
                </c:if>
            </div>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function confirmReset() {
            return confirm("Bạn có chắc chắn muốn reset mật khẩu cho tài khoản này?");
        }
    </script>
</body>
</html>
