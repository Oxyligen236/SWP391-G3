<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <title>Login page</title>
            <link rel="stylesheet" href="../../css/login.css">
        </head>

        <body>
            <div class="login-container">
                <h2>Đăng nhập</h2>

                <c:if test="${not empty error}">
                    <p class="error">${error}</p>
                </c:if>

                <form action="login" method="post">
                    <label>Username:</label>
                    <input type="text" name="username" required>

                    <label>Password:</label>
                    <input type="password" name="password" required>

                    <button type="submit">Login</button>
                </form>
            </div>
        </body>

        </html>