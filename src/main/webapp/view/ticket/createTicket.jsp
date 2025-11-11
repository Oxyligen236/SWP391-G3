
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/createTicket.css" />
</head>
<body>
    <div class="create-ticket-container">
        <div class="ticket-card">
          
            <div class="ticket-card-header">
                <h3>Create Ticket</h3>
            </div>

            
            <div class="ticket-card-body">
                <!-- Error Message -->
                <c:if test="${not empty errorMessage}">
                    <div class="alert-message alert-danger">
                        <strong>Error!</strong> ${errorMessage}
                        <button type="button" class="alert-close-btn" onclick="this.parentElement.remove()">
                            &times;
                        </button>
                    </div>
                    <c:remove var="errorMessage" scope="session" />
                </c:if>

                <!-- Ticket Type Selection -->
                <form action="${pageContext.request.contextPath}/create-ticket" 
                      method="post" 
                      class="ticket-type-section">
                    <div>
                        <label for="ticketType" class="form-label">
                            Select Ticket Type <span class="required">*</span>
                        </label>
                        <select id="ticketType" 
                                name="selectedTypeId" 
                                class="form-select" 
                                onchange="this.form.submit()">
                            <option value="">-- Choose Ticket Type --</option>
                            <c:forEach var="tt" items="${ticketTypes}">
                                <c:choose>
                                    <c:when test="${tt.ticket_Type_ID == 3}">
                                        <c:if test="${userRole == 2}">
                                            <option value="${tt.ticket_Type_ID}" 
                                                ${selectedTypeId == tt.ticket_Type_ID ? 'selected' : ''}>
                                                ${tt.name}
                                            </option>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${tt.ticket_Type_ID}" 
                                            ${selectedTypeId == tt.ticket_Type_ID ? 'selected' : ''}>
                                            ${tt.name}
                                        </option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </form>

                <!-- Divider -->
                <c:if test="${not empty selectedTypeId}">
                    <hr class="divider" />
                </c:if>

                <!-- Dynamic Form Display -->
                <c:if test="${not empty selectedTypeId}">
                    <c:choose>
                        <c:when test="${selectedTypeId == '1'}">
                            <%@ include file="forms/leaveTicketForm.jsp" %>
                        </c:when>
                        <c:when test="${selectedTypeId == '2'}">
                            <%@ include file="forms/overtimeTicketForm.jsp" %>
                        </c:when>
                        <c:when test="${selectedTypeId == '3'}">
                            <c:choose>
                                <c:when test="${userRole == 2}">
                                    <%@ include file="forms/recruitmentTicketForm.jsp" %>
                                </c:when>
                                <c:otherwise>
                                    <div class="access-denied-alert">
                                        <h4 class="alert-heading">Access Denied!</h4>
                                        <p>Only HR personnel can create Recruitment Tickets.</p>
                                        <hr>
                                        <p>Please contact your HR department if you need assistance.</p>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <%@ include file="forms/otherTicketForm.jsp" %>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
</body>
</html>