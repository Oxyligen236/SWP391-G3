<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đổi Mật Khẩu - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="<c:url value='/css/change-password.css'/>">
</head>
<body>
    <div class="change-password-card">
        <div class="card-header">
            <i class="fas fa-key fa-3x text-primary mb-3"></i>
            <h2>Đổi Mật Khẩu</h2>
            <p class="text-muted">
                Đăng nhập với: <strong>${sessionScope.account.username}</strong>
            </p>
        </div>


        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="fas fa-exclamation-circle me-2"></i>${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/account/change-password" 
              method="post" 
              id="changePasswordForm">
            
            <div class="form-group">
                <label for="currentPassword" class="form-label">
                    <i class="fas fa-lock"></i>
                    Mật khẩu hiện tại <span class="text-danger">*</span>
                </label>
                <div class="password-input-wrapper">
                    <input type="password" 
                           class="form-control" 
                           id="currentPassword" 
                           name="currentPassword" 
                           required 
                           placeholder="Nhập mật khẩu hiện tại">
                    <i class="fas fa-eye password-toggle" onclick="togglePassword('currentPassword', this)"></i>
                </div>
            </div>

            <div class="form-group">
                <label for="newPassword" class="form-label">
                    <i class="fas fa-key"></i>
                    Mật khẩu mới <span class="text-danger">*</span>
                </label>
                <div class="password-input-wrapper">
                    <input type="password" 
                           class="form-control" 
                           id="newPassword" 
                           name="newPassword" 
                           required 
                           minlength="6"
                           placeholder="Nhập mật khẩu mới (tối thiểu 6 ký tự)">
                    <i class="fas fa-eye password-toggle" onclick="togglePassword('newPassword', this)"></i>
                </div>
                <div id="passwordStrength" class="password-strength"></div>
            </div>

        
            <div class="form-group">
                <label for="confirmPassword" class="form-label">
                    <i class="fas fa-check-circle"></i>
                    Xác nhận mật khẩu mới <span class="text-danger">*</span>
                </label>
                <div class="password-input-wrapper">
                    <input type="password" 
                           class="form-control" 
                           id="confirmPassword" 
                           name="confirmPassword" 
                           required 
                           placeholder="Nhập lại mật khẩu mới">
                    <i class="fas fa-eye password-toggle" onclick="togglePassword('confirmPassword', this)"></i>
                </div>
                <div id="passwordMatch" class="password-strength"></div>
            </div>

            <!-- Submit Button -->
            <button type="submit" class="btn btn-change">
                <i class="fas fa-save me-2"></i>Đổi Mật Khẩu
            </button>

            <a href="<c:url value='/account/view'/>" class="btn-cancel">
                <i class="fas fa-times me-2"></i>Hủy
            </a>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Toggle password visibility
        function togglePassword(inputId, icon) {
            const input = document.getElementById(inputId);
            if (input.type === 'password') {
                input.type = 'text';
                icon.classList.remove('fa-eye');
                icon.classList.add('fa-eye-slash');
            } else {
                input.type = 'password';
                icon.classList.remove('fa-eye-slash');
                icon.classList.add('fa-eye');
            }
        }

        // Password strength indicator
        document.getElementById('newPassword').addEventListener('input', function() {
            const password = this.value;
            const strengthDiv = document.getElementById('passwordStrength');
            
            if (password.length === 0) {
                strengthDiv.textContent = '';
                return;
            }
            
            let strength = 0;
            if (password.length >= 6) strength++;
            if (password.length >= 10) strength++;
            if (/[a-z]/.test(password) && /[A-Z]/.test(password)) strength++;
            if (/\d/.test(password)) strength++;
            if (/[^a-zA-Z0-9]/.test(password)) strength++;
            
            const levels = ['Rất yếu', 'Yếu', 'Trung bình', 'Mạnh', 'Rất mạnh'];
            const colors = ['text-danger', 'text-warning', 'text-info', 'text-primary', 'text-success'];
            
            strengthDiv.textContent = '🔐 Độ mạnh: ' + levels[strength];
            strengthDiv.className = 'password-strength ' + colors[strength];
        });

        // Password match checker
        document.getElementById('confirmPassword').addEventListener('input', function() {
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = this.value;
            const matchDiv = document.getElementById('passwordMatch');
            
            if (confirmPassword.length === 0) {
                matchDiv.textContent = '';
                return;
            }
            
            if (newPassword === confirmPassword) {
                matchDiv.textContent = '✓ Mật khẩu khớp';
                matchDiv.className = 'password-strength text-success';
            } else {
                matchDiv.textContent = '✗ Mật khẩu không khớp';
                matchDiv.className = 'password-strength text-danger';
            }
        });

        // Form validation
        document.getElementById('changePasswordForm').addEventListener('submit', function(e) {
            const currentPassword = document.getElementById('currentPassword').value;
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            
            if (newPassword !== confirmPassword) {
                e.preventDefault();
                alert('Mật khẩu mới và xác nhận không khớp!');
                return false;
            }
            
            if (newPassword.length < 6) {
                e.preventDefault();
                alert('Mật khẩu mới phải có ít nhất 6 ký tự!');
                return false;
            }

            if (currentPassword === newPassword) {
                e.preventDefault();
                alert('Mật khẩu mới phải khác mật khẩu hiện tại!');
                return false;
            }
            
            return confirm('Bạn có chắc chắn muốn đổi mật khẩu?\nBạn sẽ phải đăng nhập lại sau khi đổi.');
        });
    </script>
</body>
</html>