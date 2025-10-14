<!DOCTYPE html>
<html lang="en">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page isELIgnored="false" %>

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="stylesheet" href="<c:url value='/css/cv-list.css'/>">
                <title>CV_List</title>
            </head>

            <body>
                <h1>CV List</h1>
                <form action="<c:url value='/cv'/>" method="get">
                    <input type="text" name="name" placeholder="Tìm theo tên" value="${param.name}">
                    <input type="text" name="email" placeholder="Tìm theo email" value="${param.email}">
                    <input type="text" name="phone" placeholder="Tìm theo số điện thoại" value="${param.phone}">
                    <select name="gender">
                        <option value="">Tất cả giới tính</option>
                        <option value="male" <c:if test="${param.gender == 'male'}">selected</c:if>>Nam</option>
                        <option value="female" <c:if test="${param.gender == 'female'}">selected</c:if>>Nữ</option>
                        <option value="other" <c:if test="${param.gender == 'other'}">selected</c:if>>Khác</option>
                    </select>
                    <select name="jobID">
                        <option value="">Tất cả vị trí</option>
                        <c:forEach var="job" items="${jobs}">
                            <option value="${job.jobID}" <c:if test="${param.jobID == job.jobID}">selected</c:if>
                                >${job.jobTitle}</option>
                        </c:forEach>
                    </select>
                    <select name="status">
                        <option value="">Tất cả trạng thái</option>
                        <option value="Pending" <c:if test="${param.status == 'Pending'}">selected</c:if>>Pending
                        </option>
                        <option value="Reviewed" <c:if test="${param.status == 'Reviewed'}">selected</c:if>>Reviewed
                        </option>
                        <option value="Rejected" <c:if test="${param.status == 'Rejected'}">selected</c:if>>Rejected
                        </option>
                        <option value="Accepted" <c:if test="${param.status == 'Accepted'}">selected</c:if>>Accepted
                        </option>

                    </select>
                    <button type="submit">Tìm kiếm</button>
                    <a href="<c:url value='/cv'/>">Reset</a>
                </form>
                <table>
                    <thead>
                        <tr>
                            <th>CV ID</th>
                            <th>Tiêu đề công việc</th>
                            <th>Tên</th>
                            <th>Giới tính</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Trạng thái</th>
                            <th>Hành động</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cv" items="${cvs}">
                            <tr>
                                <td>${cv.cvID}</td>
                                <td>${cv.jdTitle}</td>
                                <td>${cv.name}</td>
                                <td>${cv.gender}</td>
                                <td>${cv.email}</td>
                                <td>${cv.phone}</td>
                                <td>${cv.status}</td>
                                <td>
                                    <a href="<c:url value='/cv/detail?id=${cv.cvID}'/>">Xem chi tiết</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>

                </table>
            </body>

</html>