<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reset Password - HRMS</title>
    <link rel="stylesheet" href="<c:url value='/css/cv-list.css'/>">

    <style>
        body {
            background-color: #f9f9f9;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        .popup-container {
            max-width: 500px;
            margin: 50px auto;
            padding: 20px 25px;
            border-radius: 10px;
            background: #ffffff;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        .popup-header {
            font-size: 1.2rem;
            font-weight: bold;
            text-align: center;
            padding-bottom: 10px;
            border-bottom: 1px solid #ddd;
            color: #333;
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
            color: #555;
        }

        .info-value {
            flex: 1;
            color: #111;
        }

        .current-role-badge {
            padding: 4px 10px;
            background: #e2e3e5;
            color: #333;
            border-radius: 6px;
            font-weight: 500;
        }

        .popup-footer {
            margin-top: 20px;
            display: flex;
            justify-content: flex-end;
            gap: 10px;
        }

        .btn-submit, .btn-cancel {
            padding: 6px 16px;
            border-radius: 6px;
            font-weight: 500;
            cursor: pointer;
            border: none;
        }

        .btn-submit {
            background-color: #42a5f5;
            color: white;
        }
        .btn-submit:hover {
            background-color: #1e88e5;
        }

        .btn-cancel {
            background-color: #f0f0f0;
            color: #333;
            border: 1px solid #ddd;
        }

        .btn-cancel:hover {
            background-color: #e0e0e0;
        }

        /* Alerts giống cv-list */
        .alert-success {
            background-color: #d4edda;
            color: #155724;
            padding: 12px 20px;
            border-radius: 6px;
            margin-bottom: 15px;
            border: 1px solid #c3e6cb;
        }

        .alert-warning {
            background-color: #fff3cd;
            color: #856404;
            padding: 12px 20px;
            border-radius: 6px;
            margin-bottom: 15px;
            border: 1px solid #ffeeba;
        }

        .alert-info {
            background-color: #cce5ff;
            color: #004085;
            padding: 12px 20px;
            border-radius: 6px;
            margin-bottom: 15px;
            border: 1px solid #b8daff;
        }
    </style>
</head>

<body>
<div class="popup-container">
    <div class="popup-header">
        <i class="fas fa-key"></i> Confirm Password Reset
    </div>

    <form action="<c:url value='/account/reset-password'/>" method="post" onsubmit="return confirmReset()">
        <div class="popup-body">
            <input type="hidden" name="accountID" value="${account.accountID}">

            <div class="info-row">
                <div class="info-label">Account ID:</div>
                <div class="info-value"><strong>${account.accountID}</strong></div>
            </div>
            <div class="info-row">
                <div class="info-label">Username:</div>
                <div class="info-value"><strong>${account.username}</strong></div>
            </div>
            <div class="info-row">
                <div class="info-label">Full Name:</div>
                <div class="info-value">${account.fullName}</div>
            </div>
            <div class="info-row">
                <div class="info-label">Current Role:</div>
                <div class="info-value"><span class="current-role-badge">${account.roleName}</span></div>
            </div>

            <!-- Success message -->
            <c:if test="${not empty successMessage}">
                <div class="alert-success">
                    <i class="fas fa-check-circle"></i> ${successMessage}
                </div>
                <c:if test="${not empty tempPassword}">
                    <div class="alert-info">
                        <i class="fas fa-key"></i> Temporary password: <strong>${tempPassword}</strong>
                    </div>
                </c:if>
            </c:if>

            <!-- Warning -->
            <c:if test="${empty successMessage}">
                <div class="alert-warning">
                    <i class="fas fa-exclamation-triangle"></i> Warning: The password will be reset and sent to the user. Make sure to notify them.
                </div>
            </c:if>
        </div>

        <div class="popup-footer">
            <button type="button" class="btn-cancel" onclick="window.location.href='<c:url value='/account/view'/>'">
                Cancel
            </button>

            <c:if test="${empty successMessage}">
                <button type="submit" class="btn-submit">Confirm Reset</button>
            </c:if>

            <c:if test="${not empty successMessage}">
                <button type="button" class="btn-submit" onclick="window.location.href='<c:url value='/account/view'/>'">
                    Back to List
                </button>
            </c:if>
        </div>
    </form>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/js/all.min.js"></script>
<script>
    function confirmReset() {
        return confirm("Are you sure you want to reset the password for this account?");
    }
</script>
</body>
</html>
