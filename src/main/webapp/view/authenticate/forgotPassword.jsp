<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page isELIgnored="false" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <title>Forgot password</title>
            </head>

            <body>
                <div>
                    <h2>Quên mật khẩu</h2>
                    <form action="<c:url value='/forgot-password'/>" method="post">
                        <input type="hidden" name="email" value="pqm1290@gmail.com">
                        <input type="hidden" name="subject" value="Thông báo">
                        <input type="hidden" name="body" value="Xin chào, đây là nội dung email.">

                        <button type="submit" name="type" value="mailto">Gửi bằng mail client</button>
                        <button type="submit" name="type" value="gmail">Gửi bằng Gmail web</button>
                    </form>
                </div>
            </body>

            </html>