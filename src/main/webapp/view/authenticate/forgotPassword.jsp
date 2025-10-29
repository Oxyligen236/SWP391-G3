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
                    <h2>Quên mật khẩu</h2>

                    <c:if test="${not empty successMessage}">
                        <p class="successMessage" style="color: green;">${successMessage}</p>
                    </c:if>

                    <c:if test="${not empty errorMessage}">
                        <p class="errorMessage" style="color: red;">${errorMessage}</p>
                    </c:if>

                    <form action="<c:url value='/forgot-password'/>" method="post">
                        <label for="email">Email đăng ký trong hệ thống:</label>
                        <input type="email" id="email" name="email" placeholder="Nhập email của bạn" required>
                        <input type="text" name="subject" placeholder="Yêu cầu đặt lại mật khẩu (tùy chọn)">
                        <textarea name="body" rows="4"
                            placeholder="Nhập nội dung yêu cầu đặt lại mật khẩu (tùy chọn)"></textarea>

                        <button type="submit">Gửi yêu cầu đặt lại mật khẩu</button>
                    </form>

                    <div class="back-to-login">
                        <a href="${pageContext.request.contextPath}/authenticate">Quay lại đăng nhập</a>
                    </div>
                </div>
            </body>

            </html>