<%@ page import="java.sql.*, java.time.LocalDate" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page import="java.util.List" %>
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
            <h2>Create Job Description</h2>

            <c:if test="${not empty error}">
                <p style="color:red;">${error}</p>
            </c:if>

            <form action="<%= request.getContextPath() %>/createjd" method="post">

               <input type="hidden" name="ticketID" value="${not empty ticketID ? ticketID : param.ticketID}">



                <label for="jobTitle">Job Title:</label>
                <input type="text" id="jobTitle" name="jobTitle" required>

                <label for="startDate">Start Date:</label>
                <input type="date" id="startDate" name="startDate" required>

                <label for="endDate">End Date:</label>
                <input type="date" id="endDate" name="endDate" required>

                <label for="department">Department:</label>
                <select name="departmentName" id="department">
                    <c:forEach var="d" items="${departments}">
                        <option value="${d.name}">${d.name}</option>
                    </c:forEach>
                </select>




                <label for="vacancies">Vacancies:</label>
                <input type="number" id="vacancies" name="vacancies" required>

                <label for="responsibilities">Responsibilities:</label>
                <textarea id="responsibilities" name="responsibilities" rows="3" required></textarea>

                <label for="requirements">Requirements:</label>
                <textarea id="requirements" name="requirements" rows="3" required></textarea>

                <label for="compensation">Compensation:</label>
                <input type="text" id="compensation" name="compensation">

                <label for="officeAddress">OfficeAddress:</label>
                <input type="text" id="officeAddress" name="officeAddress">

                <label for="workingConditions">WorkingConditions:</label>
                <textarea id="workingConditions" name="workingConditions" rows="3"></textarea>
                <div class="button-group">
                    <button type="submit" class="btn-primary">Save</button>
                    <button type="button" class="btn-secondary" onclick="window.location.href = '/jdlist'">Cancel</button>
                </div>

            </form>
        </div>

    </body>
</html>