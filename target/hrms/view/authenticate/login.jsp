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
                    <h2>Login Page</h2>

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
                            Show Password
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
                                <label for="remember">Remember Me</label>
                            </div>
                            <div class="forgot-password">
                                <a href="<c:url value='/forgotPassword'/>">Forgot Password?</a>
                            </div>
                        </div>
                    </form>
                </div>
            </body>

            </html>