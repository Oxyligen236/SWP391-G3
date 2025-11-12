<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Password - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="<c:url value='/css/change-password.css'/>">
</head>
<body>
    <div class="change-password-card">
        <div class="card-header">
            <i class="fas fa-key fa-3x mb-3"></i>
            <h2 class="fw-bold text-primary">Change Password</h2>
            <p class="text-muted">Logged in as: <strong>${sessionScope.account.username}</strong></p>
        </div>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="fas fa-exclamation-circle me-2"></i>${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/account/change-password" method="post" id="changePasswordForm">
            <div class="mb-3">
                <label for="currentPassword" class="form-label">
                    <i class="fas fa-lock"></i> Current Password <span class="text-danger">*</span>
                </label>
                <div class="password-input-wrapper">
                    <input type="password" class="form-control" id="currentPassword" name="currentPassword" required placeholder="Enter current password">
                    <i class="fas fa-eye password-toggle" onclick="togglePassword('currentPassword', this)"></i>
                </div>
            </div>

            <div class="mb-3">
                <label for="newPassword" class="form-label">
                    <i class="fas fa-key"></i> New Password <span class="text-danger">*</span>
                </label>
                <div class="password-input-wrapper">
                    <input type="password" class="form-control" id="newPassword" name="newPassword" required minlength="6" placeholder="Enter new password">
                    <i class="fas fa-eye password-toggle" onclick="togglePassword('newPassword', this)"></i>
                </div>
                <div id="passwordStrength" class="password-strength"></div>
            </div>

            <div class="mb-3">
                <label for="confirmPassword" class="form-label">
                    <i class="fas fa-check-circle"></i> Confirm New Password <span class="text-danger">*</span>
                </label>
                <div class="password-input-wrapper">
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required placeholder="Re-enter new password">
                    <i class="fas fa-eye password-toggle" onclick="togglePassword('confirmPassword', this)"></i>
                </div>
                <div id="passwordMatch" class="password-strength"></div>
            </div>

            <div class="d-flex justify-content-center gap-3 mt-4">
                <button type="submit" class="btn-change btn-custom">
                    <i class="fas fa-save me-2"></i>Change Password
                </button>
                <a href="<c:url value='/account/view'/>" class="btn-cancel btn-custom">
                    <i class="fas fa-times me-2"></i>Cancel
                </a>
            </div>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Toggle password visibility
        function togglePassword(inputId, icon) {
            const input = document.getElementById(inputId);
            if (input.type === 'password') {
                input.type = 'text';
                icon.classList.replace('fa-eye', 'fa-eye-slash');
            } else {
                input.type = 'password';
                icon.classList.replace('fa-eye-slash', 'fa-eye');
            }
        }

        // Password strength indicator
        document.getElementById('newPassword').addEventListener('input', function() {
            const password = this.value;
            const strengthDiv = document.getElementById('passwordStrength');
            if (!password) { strengthDiv.textContent=''; return; }

            let strength = 0;
            if (password.length >= 6) strength++;
            if (password.length >= 10) strength++;
            if (/[a-z]/.test(password) && /[A-Z]/.test(password)) strength++;
            if (/\d/.test(password)) strength++;
            if (/[^a-zA-Z0-9]/.test(password)) strength++;

            const levels = ['Very Weak', 'Weak', 'Fair', 'Strong', 'Very Strong'];
            const colors = ['text-danger', 'text-warning', 'text-info', 'text-primary', 'text-success'];

            strengthDiv.textContent = '🔐 Strength: ' + levels[strength];
            strengthDiv.className = 'password-strength ' + colors[strength];
        });

        // Password match checker
        document.getElementById('confirmPassword').addEventListener('input', function() {
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = this.value;
            const matchDiv = document.getElementById('passwordMatch');

            if (!confirmPassword) { matchDiv.textContent=''; return; }

            if (newPassword === confirmPassword) {
                matchDiv.textContent = '✓ Passwords match';
                matchDiv.className = 'password-strength text-success';
            } else {
                matchDiv.textContent = '✗ Passwords do not match';
                matchDiv.className = 'password-strength text-danger';
            }
        });

        // Form validation before submit
        document.getElementById('changePasswordForm').addEventListener('submit', function(e) {
            const currentPassword = document.getElementById('currentPassword').value;
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;

            if (newPassword !== confirmPassword) {
                e.preventDefault();
                alert('New password and confirmation do not match!');
                return false;
            }
            if (newPassword.length < 6) {
                e.preventDefault();
                alert('New password must be at least 6 characters!');
                return false;
            }
            if (currentPassword === newPassword) {
                e.preventDefault();
                alert(' New password must be different from current password!');
                return false;
            }
            return confirm('Are you sure you want to change your password?\nYou will need to log in again after changing.');
        });
    </script>
</body>
</html>