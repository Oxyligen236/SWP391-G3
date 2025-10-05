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
                    <form action="<c:url value='/login'/>" method="post">
                        <label>Username:</label>
                        <input type="text" name="username" required>
                        <label>Password:</label>
                        <input type="password" name="password" required>
                        <button type="submit">Login</button>
                        <div class="remember-me">
                            <input type="checkbox" id="remember" name="remember">
                            <label for="remember">Ghi nhớ tôi</label>
                        </div>

                    </form>
                </div>
            </body>

            </html>