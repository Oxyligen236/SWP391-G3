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
                            <p>Name:</p>
                            <input type="text" name="name" placeholder="Enter name" value="${param.name}">
                        </div>
                        <div class="form-group">
                            <p>Email:</p>
                            <input type="text" name="email" placeholder="Enter email" value="${param.email}">
                        </div>
                        <div class="form-group">
                            <p>Phone number:</p>
                            <input type="text" name="phone" placeholder="Enter phone number" value="${param.phone}">
                        </div>
                    </div>

                    <div class="form-row select-inputs">
                        <div class="form-group">
                            <p>Gender:</p>
                            <select name="gender">
                                <option value="">All</option>
                                <option value="male" <c:if test="${param.gender == 'male'}">selected</c:if>>Male
                                </option>
                                <option value="female" <c:if test="${param.gender == 'female'}">selected</c:if>>Female
                                </option>
                                </option>
                                <option value="other" <c:if test="${param.gender == 'other'}">selected</c:if>>Khác
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
                            </select>
                        </div>
                    </div>

                    <div class="form-row button-row">
                        <input type="hidden" name="itemsPerPage" value="${itemsPerPage}" />
                        <button type="submit">Search</button>
                        <a href="<c:url value='/cv'/>">Reset</a>
                    </div>
                </form>

                <table>
                    <thead>
                        <tr>
                            <th>No</th>
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
                        <c:if test="${empty cvs}">
                            <tr>
                                <td colspan="9" class="no-data">No CVs found</td>
                            </tr>
                        </c:if>
                        <c:forEach var="cv" items="${cvs}" varStatus="status">
                            <tr>
                                <td>${(currentPage - 1) * itemsPerPage + status.index + 1}</td>
                                <td>${cv.cvID}</td>
                                <td>${cv.jdTitle}</td>
                                <td>${cv.name}</td>
                                <td>${cv.dob}</td>
                                <td>${cv.gender}</td>
                                <td>${cv.email}</td>
                                <td>${cv.phone}</td>
                                <td>
                                    <span
                                        class="status-badge ${cv.status == 'Accepted' ? 'status-accepted' : 
                                                               cv.status == 'Rejected' ? 'status-rejected' : 
                                                               cv.status == 'Reviewed' ? 'status-reviewed' : 'status-pending'}">
                                        ${cv.status}
                                    </span>
                                </td>
                                <td>
                                    <a href="<c:url value='/cv/detail?id=${cv.cvID}'/>" class="action-link">Detail</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div class="pagination-container">
                    <form action="<c:url value='/cv'/>" method="get" class="items-per-page-form">
                        <label>Items per page:</label>
                        <input type="number" name="itemsPerPage" value="${itemsPerPage}" min="1" max="50" />
                        <button type="submit">Apply</button>

                        <input type="hidden" name="name" value="${param.name}" />
                        <input type="hidden" name="email" value="${param.email}" />
                        <input type="hidden" name="phone" value="${param.phone}" />
                        <input type="hidden" name="gender" value="${param.gender}" />
                        <input type="hidden" name="jobID" value="${param.jobID}" />
                        <input type="hidden" name="status" value="${param.status}" />
                        <input type="hidden" name="page" value="1" />
                    </form>

                    <nav class="pagination-nav">
                        <ul class="pagination">
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link"
                                    href="<c:url value='/cv?page=${currentPage - 1}&itemsPerPage=${itemsPerPage}&name=${param.name}&email=${param.email}&phone=${param.phone}&gender=${param.gender}&jobID=${param.jobID}&status=${param.status}'/>">
                                    Previous
                                </a>
                            </li>

                            <li class="page-item disabled">
                                <span class="page-link">Page ${currentPage} / ${totalPages}</span>
                            </li>

                            <li class="page-item ${currentPage == totalPages || totalPages == 0 ? 'disabled' : ''}">
                                <a class="page-link"
                                    href="<c:url value='/cv?page=${currentPage + 1}&itemsPerPage=${itemsPerPage}&name=${param.name}&email=${param.email}&phone=${param.phone}&gender=${param.gender}&jobID=${param.jobID}&status=${param.status}'/>">
                                    Next
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </body>

</html>