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
                <form action="<c:url value='/cv'/>" method="get" style="margin-bottom: 20px;">
                    <input type="text" name="name" placeholder="Tìm theo tên" value="${param.name}">
                    <input type="text" name="email" placeholder="Tìm theo email" value="${param.email}">
                    <input type="text" name="phone" placeholder="Tìm theo số điện thoại" value="${param.phone}">
                    <button type="submit">Tìm kiếm</button>
                    <a href="<c:url value='/cv'/>">Reset</a>
                </form>
                <table>
                    <thead>
                        <tr>
                            <th>CV ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cv" items="${cvs}">
                            <tr>
                                <td>${cv.cvID}</td>
                                <td>${cv.name}</td>
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