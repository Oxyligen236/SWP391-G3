<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>

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
                <th>Họ tên</th>
                <th>Email</th>
                <th>Điện thoại</th>
                <th>Ngày sinh</th>
                <th>Giới tính</th>
                <th>Địa chỉ</th>
                <th>CCCD</th>
                <th>Dân tộc</th>
                <th>Quốc tịch</th>
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
                </tr>
            </c:forEach>
            </tbody>
        </table>

</div>
</body>
</html>
