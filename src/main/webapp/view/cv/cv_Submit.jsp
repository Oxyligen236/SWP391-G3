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
                            <input type="hidden" name="title" value="<c:out value='${param.title}' />">
                            <input type="hidden" name="jdID" value="<c:out value='${param.jdID}' />">
                        </c:if>

                        <div class="form-group">
                            <label for="name">Full Name:</label>
                            <input type="text" id="name" name="name" placeholder="Enter your full name" required
                                pattern="[A-Za-zÀ-ỹ\s]+" title="Name must not contain special characters or numbers.">
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
                            <input type="text" id="address" name="address" placeholder="Enter your address" required
                                pattern="[A-Za-zÀ-ỹ0-9\s,.\-]+" title="Address must not contain special characters.">
                        </div>

                        <div class="form-group">
                            <label for="nationality">Nationality:</label>
                            <input type="text" id="nationality" name="nationality" placeholder="Enter your nationality"
                                required pattern="[A-Za-zÀ-ỹ\s]+"
                                title="Nationality must not contain special characters or numbers.">
                        </div>

                        <div class="form-group">
                            <label for="email">Email:</label>
                            <input type="email" id="email" name="email" placeholder="Enter your email" required>
                        </div>

                        <div class="form-group">
                            <label for="phone">Phone Number:</label>
                            <input type="tel" id="phone" name="phone" placeholder="Enter your phone number" required
                                pattern="^0\d{9}$" title="Phone number must be 10 digits and start with 0.">
                        </div>

                        <div class="form-group">
                            <label for="cv_Description">CV Description:</label>
                            <textarea id="cv_Description" name="cv_Description" rows="5"
                                placeholder="Write about your experience..." required></textarea>
                        </div>

                        <div class="form-actions">
                            <button type="submit" class="btn-submit">Submit CV</button>
                            <button type="reset" class="btn-reset">Clear</button>
                        </div>
                    </form>
                </div>
            </body>

            </html>