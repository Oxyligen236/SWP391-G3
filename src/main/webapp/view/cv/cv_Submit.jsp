<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page isELIgnored="false" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>CV Submission</title>
                <link rel="stylesheet" href="<c:url value='/css/cv-submit.css'/>">
            </head>

            <body>
                <div class="cv-container">

                    <c:if test="${not empty successMessage}">
                        <script>
                            alert("${successMessage}");
                            window.location.href = "<c:url value='/view/home/home.jsp'/>";
                        </script>
                    </c:if>

                    <c:if test="${not empty errorMessage}">
                        <script>
                            alert("${errorMessage}");
                        </script>
                    </c:if>

                    <h1>Submit Your CV</h1>
                    <form action="<c:url value='/cv/submit'/>" method="post">
                        <c:if test="${not empty param.jdID and param.jdID != jobId}">
                            <p><strong>Applying for:</strong>
                                <c:out value="${not empty param.title ? param.title : 'Unknown Job'}" />
                            </p>
                            <input type="hidden" name="jdID" value="<c:out value='${param.jdID}' />">
                        </c:if>
                        <div class="form-group">
                            <label for="name">Full Name:</label>
                            <input type="text" id="name" name="name" placeholder="Enter your full name" required>
                        </div>

                        <div class="form-group">
                            <label for="email">Email Address:</label>
                            <input type="email" id="email" name="email" placeholder="Nhập địa chỉ email của bạn"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="phone">Phone Number:</label>
                            <input type="text" id="phone" name="phone" placeholder="Nhập số điện thoại của bạn"
                                required>
                        </div>

                        <div class="form-group">
                            <label for="cv_Description">CV Description:</label>
                            <textarea id="cv_Description" name="cv_Description" rows="5"
                                placeholder="Viết mô tả về kinh nghiệm của bạn..." required></textarea>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn-submit">Gửi CV</button>
                            <button type="reset" class="btn-reset">Xóa</button>
                        </div>
                    </form>
                </div>
            </body>

            </html>