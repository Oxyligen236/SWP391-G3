<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <title>Đổi Mật Khẩu Thành Công</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            <style>
                body {
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    min-height: 100vh;
                    background-color: #f0f2f5;
                }

                .success-box {
                    background-color: #fff;
                    padding: 40px;
                    border-radius: 15px;
                    text-align: center;
                    max-width: 450px;
                    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
                }

                h2 {
                    color: #198754;
                    margin-bottom: 20px;
                }
            </style>
        </head>

        <body>
            <div class="success-box">
                <h2>✅ Đổi mật khẩu thành công!</h2>
                <p>Vui lòng đăng nhập lại để tiếp tục sử dụng hệ thống.</p>
                <a href="<c:url value='/authenticate'/>" class="btn btn-primary mt-3">Đăng nhập lại</a>
            </div>
        </body>

        </html>