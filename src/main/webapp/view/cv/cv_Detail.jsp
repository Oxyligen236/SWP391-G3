<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page isELIgnored="false" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <title>CV Detail</title>
                <link rel="stylesheet" href="<c:url value='/css/cv-detail.css'/>">
            </head>

            <body>
                <div class="cv-detail">
                    <c:if test="${not empty CV_ID_error}">
                        <div class="error-message">
                            <p>
                                <c:out value="${CV_ID_error}" />
                            </p>
                            <a href="<c:url value='/cv'/>">← Quay lại danh sách</a>
                        </div>
                    </c:if>
                    <h2>Chi tiết CV #${cvDetail.cvID}</h2>
                    <p><strong>JD ID:</strong> ${cvDetail.jdID}</p>
                    <p><strong>Tiêu đề công việc:</strong> ${cvDetail.jdTitle}</p>
                    <p><strong>Họ tên:</strong> ${cvDetail.name}</p>
                    <p><strong>Email:</strong> ${cvDetail.email}</p>
                    <p><strong>Số điện thoại:</strong> ${cvDetail.phone}</p>
                    <p><strong>Trạng thái:</strong> ${cvDetail.status}</p>
                    <p><strong>Mô tả:</strong></p>
                    <textarea readonly rows="8" cols="75">${cvDetail.cv_Description}</textarea>
                    <br />
                    <a href="<c:url value='/cv'/>">← Quay lại danh sách</a>
                </div>
            </body>

            </html>