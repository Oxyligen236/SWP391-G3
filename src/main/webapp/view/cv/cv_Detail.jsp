<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <title>CV Detail</title>
            <link rel="stylesheet" href="<c:url value='/css/cv-detail.css'/>">
        </head>

        <body>
            <div class="cv-detail">
                <h2>Chi tiết CV #${cv.cvID}</h2>
                <p><strong>JD ID:</strong> ${cv.jdID}</p>
                <p><strong>Họ tên:</strong> ${cv.name}</p>
                <p><strong>Email:</strong> ${cv.email}</p>
                <p><strong>Số điện thoại:</strong> ${cv.phone}</p>
                <p><strong>Trạng thái:</strong> ${cv.status}</p>
                <p><strong>Mô tả:</strong></p>
                <p>${cv.cv_Description}</p>
                <a href="<c:url value='/cv'/>">← Quay lại danh sách</a>
            </div>
        </body>

        </html>