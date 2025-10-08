<%@ page import="java.sql.*, java.time.LocalDate" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Tạo Job Description</title>
        <link rel="stylesheet" href="<c:url value='/css/createjd.css'/>">
    </head>
    <body>
        <div class="container">
            <h2>Tạo Job Description</h2>
            <form action="createjd" method="post">
                <input type="hidden" name="ticketID" value="${param.ticketID}">
                <label for="jobTitle">Tên công việc:</label>
                <input type="text" id="jobTitle" name="jobTitle" required>

                <label for="startDate">Ngày bắt đầu:</label>
                <input type="date" id="startDate" name="startDate" required>

                <label for="endDate">Ngày kết thúc:</label>
                <input type="date" id="endDate" name="endDate" required>

                <label for="department">Phòng ban:</label>
                <input type="text" id="department" name="department" required>

                <label for="vacancies">Số lượng tuyển:</label>
                <input type="number" id="vacancies" name="vacancies" required>

                <label for="responsibilities">Trách nhiệm:</label>
                <textarea id="responsibilities" name="responsibilities" rows="3" required></textarea>

                <label for="requirements">Yêu cầu:</label>
                <textarea id="requirements" name="requirements" rows="3" required></textarea>

                <label for="compensation">Mức lương:</label>
                <input type="text" id="compensation" name="compensation">

                <label for="officeAddress">Địa chỉ làm việc:</label>
                <input type="text" id="officeAddress" name="officeAddress">

                <label for="workingConditions">Điều kiện làm việc:</label>
                <textarea id="workingConditions" name="workingConditions" rows="3"></textarea>

                <button type="submit" class="btn-primary">Tạo Job Description</button>
            </form>
        </div>

    </body>
</html>