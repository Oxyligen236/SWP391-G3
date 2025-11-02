<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page isELIgnored="false" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <title>Quên mật khẩu</title>
                <link rel="stylesheet" href="<c:url value='/css/forgotPassword.css'/>">
            </head>

            <body>
                <div class="login-container">
                    <h2>Forgot Password</h2>

                    <c:if test="${not empty successMessage}">
                        <p class="successMessage" style="color: green;">${successMessage}</p>
                    </c:if>

                    <c:if test="${not empty errorMessage}">
                        <p class="errorMessage" style="color: red;">${errorMessage}</p>
                    </c:if>

                    <form action="<c:url value='/forgot-password'/>" method="post">
                        <label for="email">Email registered in the system:</label>
                        <input type="email" id="email" name="email" placeholder="Enter your email" required>
                        <label for="subject">Subject:</label>
                        <input type="text" id="subject" name="subject" placeholder="Password reset request (optional)"
                            required>
                        <label for="body">Content:</label>
                        <textarea id="body" name="body" rows="4"
                            placeholder="Enter the content of the password reset request (optional)"
                            required></textarea>

                        <button type="submit">Send password reset request</button>
                    </form>

                    <div class="back-to-login">
                        <a href="<c:url value='/authenticate'/>">Back to login</a>
                    </div>
                </div>
            </body>

            </html>