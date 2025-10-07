
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
        
        <!-- Select Ticket Type -->
        <form action="createTicket" method="post">
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
                                    ${param.selectedTypeId == tt.ticket_Type_ID ? 'selected' : ''}>
                                ${tt.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </form>

        <!-- Form  -->
        <c:if test="${not empty param.selectedTypeId}">
            <c:choose>
                <c:when test="${param.selectedTypeId == '1'}">
                    <jsp:include page="forms/leaveTicketForm.jsp" />
                </c:when>
                <c:when test="${param.selectedTypeId == '2'}">
                    <jsp:include page="forms/overtimeTicketForm.jsp" />
                </c:when>
                <c:when test="${param.selectedTypeId == '3'}">
                    <jsp:include page="forms/complaintForm.jsp" />
                </c:when>
                <c:otherwise>
                    <jsp:include page="forms/generalTicketForm.jsp" />
                </c:otherwise>
            </c:choose>
        </c:if>
    </div>

    <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>