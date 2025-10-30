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

                <form action="<c:url value='/cv'/>" method="get" class="search-form">
                    <div class="form-row text-inputs">
                        <div class="form-group">
                            <p>Tên:</p>
                            <input type="text" name="name" placeholder="Enter name" value="${param.name}">
                        </div>
                        <div class="form-group">
                            <p>Email:</p>
                            <input type="text" name="email" placeholder="Enter email" value="${param.email}">
                        </div>
                        <div class="form-group">
                            <p>Số điện thoại:</p>
                            <input type="text" name="phone" placeholder="Enter phone number" value="${param.phone}">
                        </div>
                    </div>

                    <div class="form-row select-inputs">
                        <div class="form-group">
                            <p>Giới tính:</p>
                            <select name="gender">
                                <option value="">All</option>
                                <option value="male" <c:if test="${param.gender == 'male'}">selected</c:if>>Male
                                </option>
                                <option value="female" <c:if test="${param.gender == 'female'}">selected</c:if>>Female
                                </option>
                                <option value="other" <c:if test="${param.gender == 'other'}">selected</c:if>>Other
                                </option>
                            </select>
                        </div>
                        <div class="form-group">
                            <p>Job Position:</p>
                            <select name="jobID">
                                <option value="">All Positions</option>
                                <c:forEach var="job" items="${jobs}">
                                    <option value="${job.jobID}" <c:if test="${param.jobID == job.jobID}">selected
                                        </c:if>>
                                        ${job.jobTitle}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <p>Status:</p>
                            <select name="status">
                                <option value="">All Statuses</option>
                                <option value="Pending" <c:if test="${param.status == 'Pending'}">selected</c:if>
                                    >Pending</option>
                                <option value="Reviewed" <c:if test="${param.status == 'Reviewed'}">selected</c:if>
                                    >Reviewed</option>
                                <option value="Rejected" <c:if test="${param.status == 'Rejected'}">selected</c:if>
                                    >Rejected</option>
                                <option value="Accepted" <c:if test="${param.status == 'Accepted'}">selected</c:if>
                                    >Accepted</option>
                                nhận</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-row button-row">
                        <button type="submit">Search</button>
                        <a href="<c:url value='/cv'/>">Reset</a>
                    </div>
                </form>

                <table>
                    <thead>
                        <tr>
                            <th>CV ID</th>
                            <th>Job Title</th>
                            <th>Name</th>
                            <th>Gender</th>
                            <th>Email</th>
                            <th>Phone</th>
                            <th>Status</th>
                            <th>Action</th>
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
                                    <a href="<c:url value='/cv/detail?id=${cv.cvID}'/>">View Details</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </body>

</html>