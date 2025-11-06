<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page isELIgnored="false" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <title>CV Detail</title>
                <link rel="stylesheet" href="<c:url value='/css/cv-detail.css'/>">
            </head>

            <body>
                <div class="cv-detail">
                    <c:if test="${not empty CV_ID_error}">
                        <div class="error-message">
                            <p>
                                <c:out value="${CV_ID_error}" />
                            </p>
                        </div>
                    </c:if>
                    <c:if test="${not empty successMessage}">
                        <div class="success-message">
                            <p>
                                <c:out value="${successMessage}" />
                            </p>
                        </div>
                    </c:if>
                    <h2>CV Detail #${cvDetail.cvID}</h2>
                    <p><strong>JD ID:</strong> ${cvDetail.jdID}</p>
                    <p><strong>Job Title:</strong> ${cvDetail.jdTitle}</p>
                    <p><strong>Name:</strong> ${cvDetail.name}</p>
                    <p><strong>Date of Birth:</strong> ${cvDetail.dob}</p>
                    <p><strong>Gender:</strong> ${cvDetail.gender}</p>
                    <p><strong>CCCD:</strong> ${cvDetail.CCCD}</p>
                    <p><strong>Address:</strong> ${cvDetail.address}</p>
                    <p><strong>Nationality:</strong> ${cvDetail.nationality}</p>
                    <p><strong>Email:</strong> ${cvDetail.email}</p>
                    <p><strong>Phone:</strong> ${cvDetail.phone}</p>
                    <p><strong>Degree:</strong> ${cvDetail.degree}</p>
                    <p><strong>Experience:</strong></p>
                    <textarea readonly rows="8" cols="75">${cvDetail.experience}</textarea>
                    <br />
                    <p><strong>Education:</strong></p>
                    <textarea readonly rows="8" cols="75">${cvDetail.education}</textarea>
                    <br />
                    <p><strong>Skills:</strong></p>
                    <textarea readonly rows="8" cols="75">${cvDetail.skills}</textarea>
                    <br />
                    <p><strong>About Me:</strong></p>
                    <textarea readonly rows="8" cols="75">${cvDetail.aboutMe}</textarea>
                    <br />
                    <div class="status-container">
                        <p><strong>Status:</strong></p>
                        <div class="button-group">
                            <form action="<c:url value='/cv/updateCvStatus'/>" method="post" class="status-update-form">
                                <input type="hidden" name="cvID" value="${cvDetail.cvID}">
                                <select name="status">
                                    <option value="Pending" <c:if test="${cvDetail.status eq 'Pending'}">selected</c:if>
                                        >Pending</option>
                                    <option value="Reviewed" <c:if test="${cvDetail.status eq 'Reviewed'}">selected
                                        </c:if>>Reviewed</option>
                                    <option value="Rejected" <c:if test="${cvDetail.status eq 'Rejected'}">selected
                                        </c:if>>Rejected</option>
                                    <option value="Accepted" <c:if test="${cvDetail.status eq 'Accepted'}">selected
                                        </c:if>>Accepted</option>
                                </select>
                                <button type="submit" class="btn-update">Update Status</button>
                            </form>

                            <!-- Nút Create User chỉ hiện khi status là Accepted -->
                            <c:if test="${cvDetail.status eq 'Accepted'}">
                                <form action="<c:url value='/user/create'/>" method="get" class="create-user-form">
                                    <input type="hidden" name="fullname" value="${cvDetail.name}">
                                    <input type="hidden" name="email" value="${cvDetail.email}">
                                    <input type="hidden" name="phoneNumber" value="${cvDetail.phone}">
                                    <input type="hidden" name="birthDate" value="${cvDetail.dob}">
                                    <input type="hidden" name="gender" value="${cvDetail.gender}">
                                    <input type="hidden" name="address" value="${cvDetail.address}">
                                    <input type="hidden" name="nation" value="${cvDetail.nationality}">
                                    <input type="hidden" name="cccd" value="${cvDetail.CCCD}">
                                    <input type="hidden" name="degreeId" value="${cvDetail.degree}">
                                    <button type="submit" class="btn-create-user">
                                        Create User
                                    </button>
                                </form>
                            </c:if>
                        </div>
                    </div>

                    <div class="nav-buttons">
                        <a href="<c:url value='/cv'/>">Back to CV List</a>
                        <c:if test="${not empty prevCV}">
                            <a href="<c:url value='/cv/detail?id=${prevCV.cvID}'/>">Previous CV</a>
                        </c:if>
                        <c:if test="${not empty nextCV}">
                            <a href="<c:url value='/cv/detail?id=${nextCV.cvID}'/>">Next CV</a>
                        </c:if>
                    </div>

                </div>
            </body>

            </html>