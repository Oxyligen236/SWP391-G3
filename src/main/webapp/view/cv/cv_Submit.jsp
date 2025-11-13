<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page isELIgnored="false" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>CV Submission</title>
                <link rel="stylesheet" href="<c:url value='/css/cv-submit.css'/>">
            </head>

            <body>
                <div class="cv-container">

                    <c:if test="${not empty successMessage}">
                        <script>
                            alert("${successMessage}");
                            <c:if test="${not empty redirectUrl}">
                                window.location.href = "${redirectUrl}";
                            </c:if>
                        </script>
                    </c:if>

                    <c:if test="${not empty errorMessage}">
                        <script>
                            alert("${errorMessage}");
                        </script>
                    </c:if>

                    <h1>Submit Your CV</h1>
                    <form action="<c:url value='/cv/submit'/>" method="post">
                        <c:if test="${not empty param.jdID and param.jdID != jobId}">
                            <p><strong>Applying for:</strong>
                                <c:out value="${not empty param.title ? param.title : 'Unknown Job'}" />
                            </p>
                            <input type="hidden" name="jdID" value="<c:out value='${param.jdID}' />">
                        </c:if>
                        <div class="form-group">
                            <label for="name">Full Name:</label>
                            <input type="text" id="name" name="name" placeholder="Enter your full name"
                                value="${param.name}" required>
                        </div>
                        <div class="form-group">
                            <label for="dob">Date of Birth:</label>
                            <input type="date" id="dob" name="dob" value="${param.dob}" required>
                        </div>
                        <div class="form-group">
                            <label for="gender">Gender:</label>
                            <select id="gender" name="gender" required>
                                <option value="" disabled ${empty param.gender ? 'selected' : '' }>Select your gender
                                </option>
                                <option value="male" ${param.gender=='male' ? 'selected' : '' }>Male</option>
                                <option value="female" ${param.gender=='female' ? 'selected' : '' }>Female</option>
                                <option value="other" ${param.gender=='other' ? 'selected' : '' }>Other</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="CCCD">CCCD:</label>
                            <input type="text" id="CCCD" name="CCCD" placeholder="Enter your CCCD" value="${param.CCCD}"
                                required>
                        </div>
                        <div class="form-group">
                            <label for="address">Address:</label>
                            <input type="text" id="address" name="address" placeholder="Enter your address"
                                value="${param.address}" required>
                        </div>
                        <div class="form-group">
                            <label for="nationality">Nationality:</label>
                            <input type="text" id="nationality" name="nationality" placeholder="Enter your nationality"
                                value="${param.nationality}" required>
                        </div>
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="email" id="email" name="email" placeholder="Enter your email"
                                value="${param.email}" required>
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone Number:</label>
                            <input type="text" id="phone" name="phone" placeholder="Enter your phone number"
                                value="${param.phone}" required>
                        </div>
                        <div class="form-group">
                            <label for="Degree">Degree:</label>
                            <select id="degree" name="degree" required>
                                <option value="" disabled ${empty param.degree ? 'selected' : '' }>Select your degree
                                </option>
                                <c:forEach var="degree" items="${degrees}">
                                    <option value="${degree.degreeId}" ${param.degree==degree.degreeId ? 'selected' : ''
                                        }>
                                        ${degree.name}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="experience">Experience:</label>
                            <textarea id="experience" name="experience" rows="5" placeholder="Describe your experience"
                                required>${param.experience}</textarea>
                        </div>

                        <div class="form-group">
                            <label for="education">Education:</label>
                            <textarea id="education" name="education" rows="5" placeholder="Describe your education"
                                required>${param.education}</textarea>
                        </div>

                        <div class="form-group">
                            <label for="skills">Skills:</label>
                            <textarea id="skills" name="skills" rows="5" placeholder="List your skills"
                                required>${param.skills}</textarea>
                        </div>

                        <div class="form-group">
                            <label for="aboutMe">About Me:</label>
                            <textarea id="aboutMe" name="aboutMe" rows="5" placeholder="Introduce yourself"
                                required>${param.aboutMe}</textarea>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn-submit">Submit CV</button>
                            <button type="reset" class="btn-reset">Reset</button>
                        </div>
                    </form>
                </div>
            </body>

            </html>