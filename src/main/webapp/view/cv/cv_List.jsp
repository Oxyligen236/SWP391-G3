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
                <div class="cv-list-container">
                    <h1>CV Management</h1>

                    <form action="<c:url value='/cv'/>" method="get" class="search-form">
                        <div class="form-row text-inputs">
                            <div class="form-group">
                                <p>Name</p>
                                <input type="text" name="name" placeholder="Enter name"
                                    value="${sessionScope.searchName != null ? sessionScope.searchName : param.name}">
                            </div>
                            <div class="form-group">
                                <p>Email</p>
                                <input type="text" name="email" placeholder="Enter email"
                                    value="${sessionScope.searchEmail != null ? sessionScope.searchEmail : param.email}">
                            </div>
                            <div class="form-group">
                                <p>Phone</p>
                                <input type="text" name="phone" placeholder="Enter phone"
                                    value="${sessionScope.searchPhone != null ? sessionScope.searchPhone : param.phone}">
                            </div>
                        </div>

                        <div class="form-row select-inputs">
                            <div class="form-group">
                                <p>Job</p>
                                <select name="jobID">
                                    <option value="">All Jobs</option>
                                    <c:forEach var="job" items="${jobs}">
                                        <option value="${job.jobID}" ${(sessionScope.searchJobID !=null &&
                                            sessionScope.searchJobID==job.jobID) || (sessionScope.searchJobID==null &&
                                            param.jobID==job.jobID) ? 'selected' : '' }>
                                            ${job.jobTitle}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="form-group">
                                <p>Gender</p>
                                <select name="gender">
                                    <option value="">All</option>
                                    <option value="Male" ${(sessionScope.searchGender !=null &&
                                        sessionScope.searchGender=='Male' ) || (sessionScope.searchGender==null &&
                                        param.gender=='Male' ) ? 'selected' : '' }>
                                        Male
                                    </option>
                                    <option value="Female" ${(sessionScope.searchGender !=null &&
                                        sessionScope.searchGender=='Female' ) || (sessionScope.searchGender==null &&
                                        param.gender=='Female' ) ? 'selected' : '' }>
                                        Female
                                    </option>
                                </select>
                            </div>

                            <div class="form-group">
                                <p>Status</p>
                                <select name="status">
                                    <option value="">All</option>
                                    <option value="Pending" ${(sessionScope.searchStatus !=null &&
                                        sessionScope.searchStatus=='Pending' ) || (sessionScope.searchStatus==null &&
                                        param.status=='Pending' ) ? 'selected' : '' }>
                                        Pending
                                    </option>
                                    <option value="Reviewed" ${(sessionScope.searchStatus !=null &&
                                        sessionScope.searchStatus=='Reviewed' ) || (sessionScope.searchStatus==null &&
                                        param.status=='Reviewed' ) ? 'selected' : '' }>
                                        Reviewed
                                    </option>
                                    <option value="Rejected" ${(sessionScope.searchStatus !=null &&
                                        sessionScope.searchStatus=='Rejected' ) || (sessionScope.searchStatus==null &&
                                        param.status=='Rejected' ) ? 'selected' : '' }>
                                        Rejected
                                    </option>
                                    <option value="Accepted" ${(sessionScope.searchStatus !=null &&
                                        sessionScope.searchStatus=='Accepted' ) || (sessionScope.searchStatus==null &&
                                        param.status=='Accepted' ) ? 'selected' : '' }>
                                        Accepted
                                    </option>
                                </select>
                            </div>
                        </div>

                        <div class="button-row">
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
                                <th>Date of Birth</th>
                                <th>Gender</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:if test="${not empty message}">
                                <tr>
                                    <td colspan="10" class="no-data">${message}</td>
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
                                        <a href="<c:url value='/cv/detail?id=${cv.cvID}'/>"
                                            class="action-link">Detail</a>
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
                </div>
            </body>

</html>