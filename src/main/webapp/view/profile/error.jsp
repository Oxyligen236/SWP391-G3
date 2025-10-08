<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lỗi - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
                               <link rel="stylesheet" href="<c:url value='/assets/css/error.css' />">
</head>
<body>
    <div class="error-container">
        <div class="error-icon">
            <i class="fas fa-exclamation-circle"></i>
        </div>
        <h1 class="error-title">Đã xảy ra lỗi!</h1>
        <p class="error-message">
            <c:choose>
                <c:when test="${not empty error}">
                    ${error}
                </c:when>
                <c:when test="${not empty requestScope['javax.servlet.error.message']}">
                    ${requestScope['javax.servlet.error.message']}
                </c:when>
                <c:otherwise>
                    Xin lỗi, đã có lỗi xảy ra trong quá trình xử lý yêu cầu của bạn.
                </c:otherwise>
            </c:choose>
        </p>
        <a href="<c:url value='/authenticate' />" class="btn-home">
            <i class="fas fa-home me-2"></i>Về trang chủ
        </a>
    </div>
</body>
</html>