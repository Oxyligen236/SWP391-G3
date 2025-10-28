<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page isELIgnored="false" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <title>Login page</title>
                <link rel="stylesheet" href="<c:url value='/css/login.css'/>">
            </head>

            <body>
                <div class="login-container">
                    <h2>Trang đăng nhập</h2>

                    <c:if test="${not empty errorMessage}">
                        <p class="errorMessage">${errorMessage}</p>
                    </c:if>
                    <form action="<c:url value='/authenticate'/>" method="post">
                        <label>Username:</label>
                        <input type="text" name="username" value="${username}" required>
                        <label>Password:</label>
                        <input type="password" id="password" name="password" value="${password}" required>
                        <br>
                        <label class="show-password">
                            <input type="checkbox" id="showPassword">
                            Hiển thị mật khẩu
                        </label>
                        <script>
                            const passwordInput = document.getElementById('password');
                            const showPasswordCheckbox = document.getElementById('showPassword');

                            showPasswordCheckbox.addEventListener('change', () => {
                                if (showPasswordCheckbox.checked) {
                                    passwordInput.type = 'text';
                                } else {
                                    passwordInput.type = 'password';
                                }
                            });
                        </script>
                        <button type="submit">Login</button>

                        <div class="options">
                            <div class="remember-me">
                                <input type="checkbox" id="remember" name="remember" value="on" ${remember}>
                                <label for="remember">Ghi nhớ tôi</label>
                            </div>
                            <div class="forgot-password">
                                <a href="${pageContext.request.contextPath}/forgot-password">Quên mật khẩu?</a>
                            </div>
                        </div>
                    </form>

                    <div class="divider">
                        <span>HOẶC</span>
                    </div>

                    <a href="https://accounts.google.com/o/oauth2/auth?scope=email profile openid&redirect_uri=http://localhost:8080/hrms/authenticate&response_type=code&client_id=58943187648-crqjkdmm3l59d2bk2qsfvgfn23buonso.apps.googleusercontent.com&approval_prompt=force"
                        class="google-btn">
                        <i class="fab fa-google"></i>
                        Đăng nhập bằng Google
                    </a>
                    <input type="hidden" id="accessToken" value="${accessToken}">
                </div>
            </body>

            </html>