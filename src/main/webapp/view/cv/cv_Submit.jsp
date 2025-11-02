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
                            window.location.href = "<c:url value='/view/home/home.jsp'/>";
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
                                pattern="[a-zA-ZÀ-ỹ\s]+" title="Tên không được chứa số hoặc ký tự đặc biệt" required>
                        </div>
                        <div class="form-group">
                            <label for="dob">Date of Birth:</label>
                            <input type="date" id="dob" name="dob" required>
                        </div>
                        <div class="form-group">
                            <label for="gender">Gender:</label>
                            <select id="gender" name="gender" required>
                                <option value="" disabled selected>Select your gender</option>
                                <option value="male">Male</option>
                                <option value="female">Female</option>
                                <option value="other">Other</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="address">Address:</label>
                            <input type="text" id="address" name="address" placeholder="Enter your address"
                                pattern="[a-zA-Z0-9À-ỹ\s,.-]+"
                                title="Address cannot contain special characters other than commas, periods, and hyphens"
                                required>
                        </div>
                        <div class="form-group">
                            <label for="nationality">Nationality:</label>
                            <input type="text" id="nationality" name="nationality" placeholder="Enter your nationality"
                                pattern="[a-zA-ZÀ-ỹ\s]+"
                                title="Nationality cannot contain numbers or special characters" required>
                        </div>
                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="email" id="email" name="email" placeholder="Enter your email" required>
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone Number:</label>
                            <input type="text" id="phone" name="phone" placeholder="Enter your phone number"
                                pattern="\d{10}" title="Phone number must be exactly 10 digits" required>
                        </div>

                        <div class="form-group">
                            <label for="experience">Experience:</label>
                            <textarea id="experience" name="experience" rows="5" placeholder="Describe your experience"
                                required></textarea>
                        </div>

                        <div class="form-group">
                            <label for="education">Education:</label>
                            <textarea id="education" name="education" rows="5" placeholder="Describe your education"
                                required></textarea>
                        </div>

                        <div class="form-group">
                            <label for="skills">Skills:</label>
                            <textarea id="skills" name="skills" rows="5" placeholder="List your skills"
                                required></textarea>
                        </div>

                        <div class="form-group">
                            <label for="aboutMe">About Me:</label>
                            <textarea id="aboutMe" name="aboutMe" rows="5" placeholder="Introduce yourself"
                                required></textarea>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn-submit">Submit CV</button>
                            <button type="reset" class="btn-reset">Reset</button>
                        </div>
                    </form>
                </div>
            </body>

            </html>