<!DOCTYPE html>
<html lang="en">
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page isELIgnored="false" %>

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="stylesheet" href="<c:url value='/css/cv-list.css'/>">
                <title>CV List</title>
            </head>

            <body>
                <h1>CV List</h1>
                <c:if test="${not empty jobID_error}">
                    <div class="error-message" style="color: red;">${jobID_error}</div>
                </c:if>

                <form action="<c:url value='/cv'/>" method="get">
                    <input type="text" name="name" placeholder="Search by name" value="${param.name}">
                    <input type="text" name="email" placeholder="Search by email" value="${param.email}">
                    <input type="text" name="phone" placeholder="Search by phone" value="${param.phone}">

                    <select name="gender">
                        <option value="">All Genders</option>
                        <option value="male" <c:if test="${param.gender == 'male'}">selected</c:if>>Male</option>
                        <option value="female" <c:if test="${param.gender == 'female'}">selected</c:if>>Female</option>
                        <option value="other" <c:if test="${param.gender == 'other'}">selected</c:if>>Other</option>
                    </select>

                    <select name="jobID">
                        <option value="">All Positions</option>
                        <c:forEach var="job" items="${jobs}">
                            <option value="${job.jobID}" <c:if test="${param.jobID == job.jobID.toString()}">selected
                                </c:if>>
                                ${job.jobTitle}
                            </option>
                        </c:forEach>
                    </select>

                    <select name="status">
                        <option value="">All Status</option>
                        <option value="Pending" <c:if test="${param.status == 'Pending'}">selected</c:if>>Pending
                        </option>
                        <option value="Reviewed" <c:if test="${param.status == 'Reviewed'}">selected</c:if>>Reviewed
                        </option>
                        <option value="Rejected" <c:if test="${param.status == 'Rejected'}">selected</c:if>>Rejected
                        </option>
                        <option value="Accepted" <c:if test="${param.status == 'Accepted'}">selected</c:if>>Accepted
                        </option>
                    </select>

                    <button type="submit">Search</button>
                    <a href="<c:url value='/cv'/>">Reset</a>
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
                                    <a href="<c:url value='/cv/detail'/>?id=${cv.cvID}">View Details</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div class="pagination-container">
                    <form action="<c:url value='/cv'/>" method="get">
                        <label>Items per page:</label>
                        <input type="number" name="itemsPerPage" value="${itemsPerPage}" min="1" />
                        <button type="submit">Set</button>

                        <input type="hidden" name="name" value="${param.name}" />
                        <input type="hidden" name="email" value="${param.email}" />
                        <input type="hidden" name="phone" value="${param.phone}" />
                        <input type="hidden" name="gender" value="${param.gender}" />
                        <input type="hidden" name="status" value="${param.status}" />
                        <input type="hidden" name="jobID" value="${param.jobID}" />
                        <input type="hidden" name="page" value="1" />
                    </form>

                    <nav>
                        <ul class="pagination">
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link"
                                    href="${pageContext.request.contextPath}/cv?page=${currentPage - 1}&itemsPerPage=${itemsPerPage}&name=${param.name}&email=${param.email}&phone=${param.phone}&gender=${param.gender}&status=${param.status}&jobID=${param.jobID}">
                                    Previous
                                </a>
                            </li>

                            <li class="page-item disabled">
                                <span class="page-link">Page ${currentPage} of ${totalPages}</span>
                            </li>

                            <li class="page-item ${currentPage == totalPages || totalPages == 0 ? 'disabled' : ''}">
                                <a class="page-link"
                                    href="${pageContext.request.contextPath}/cv?page=${currentPage + 1}&itemsPerPage=${itemsPerPage}&name=${param.name}&email=${param.email}&phone=${param.phone}&gender=${param.gender}&status=${param.status}&jobID=${param.jobID}">
                                    Next
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </body>

</html>