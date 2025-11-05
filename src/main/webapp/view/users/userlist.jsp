<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Users List</title>
        <link rel="stylesheet" href="css/userlist.css">
    </head>
    <body>
        <div class="container">
            <h3>Users List</h3>

            <c:if test="${empty users}">
                <p>Không có dữ liệu để hiển thị.</p>
            </c:if>

            <table class="table-custom">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Phone number</th>
                        <th>Birth date</th>
                        <th>Gender</th>
                        <th>Address</th>
                        <th>CCCD</th>
                        <th>Ethnicity</th>
                        <th>Nationality</th>
                        <th>Department</th>
                        <th>Degree</th>
                        <th>Position</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="u" items="${users}">
                        <tr>
                            <td>${u.userId}</td>
                            <td>${u.fullname}</td>
                            <td>${u.email}</td>
                            <td>${u.phoneNumber}</td>
                            <td>${u.birthDate}</td>
                            <td>${u.gender}</td>
                            <td>${u.address}</td>
                            <td>${u.cccd}</td>
                            <td>${u.ethnicity}</td>
                            <td>${u.nation}</td>
                            <td>${u.departmentName}</td>
                            <td>${u.degreeName}</td>
                            <td>${u.positionName}</td>
                            <td class="action-buttons">
                                <a href="${pageContext.request.contextPath}/updateDepartment?userID=${u.userId}" class="btn-edit">Department</a>
                                <a href="${pageContext.request.contextPath}/updatePosition?userID=${u.userId}" class="btn-edit">Position</a>
                                <a href="${pageContext.request.contextPath}/updateRole?accountID=${u.userId}" class="btn-edit">Role</a>


                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
