<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Create Ticket</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css" />
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-primary mb-4">Create Ticket</h2>

        <!-- Error Message Display -->
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                <strong>Error!</strong> ${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <c:remove var="errorMessage" scope="session" />
        </c:if>

        <!-- Select Ticket Type -->
        <form action="${pageContext.request.contextPath}/create-ticket" method="post">
            <div class="card shadow mb-4">
                <div class="card-body">
                    <label for="ticketType" class="form-label fw-bold">
                        Select Ticket Type <span class="text-danger">*</span>
                    </label>
                    <select 
                        id="ticketType"
                        name="selectedTypeId"
                        class="form-select" 
                        onchange="this.form.submit()"
                    >
                        <option value="">-- Choose Ticket Type --</option>
                        <c:forEach var="tt" items="${ticketTypes}">
                            <option value="${tt.ticket_Type_ID}" 
                                    ${selectedTypeId == tt.ticket_Type_ID ? 'selected' : ''}>
                                ${tt.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </form>

        <!-- Form -->
        <c:if test="${not empty selectedTypeId}">
            <c:choose>
                <c:when test="${selectedTypeId == '1'}">
                    <jsp:include page="forms/leaveTicketForm.jsp" />
                </c:when>
                <c:when test="${selectedTypeId == '2'}">
                    <jsp:include page="forms/overtimeTicketForm.jsp" />
                </c:when>
                <c:when test="${selectedTypeId == '3'}">
                    <jsp:include page="forms/recruitmentTicketForm.jsp" />
                </c:when>
                <c:otherwise>
                    <jsp:include page="forms/otherTicketForm.jsp" />
                </c:otherwise>
            </c:choose>
        </c:if>
    </div>

    <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>