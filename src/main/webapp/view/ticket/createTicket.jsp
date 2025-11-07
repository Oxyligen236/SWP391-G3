
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
    <style>
        .bg-gradient-primary {
            background: linear-gradient(135deg, #4273f1 0%, #6ba3ff 100%);
        }
        .text-primary-custom {
            color: #4273f1 !important;
        }
        .border-primary-custom {
            border-color: #4273f1 !important;
        }
        .btn-primary-custom {
            background: linear-gradient(135deg, #4273f1 0%, #5a8fff 100%);
            border: none;
            color: white;
        }
        .btn-primary-custom:hover {
            background: linear-gradient(135deg, #3a63d1 0%, #4a7fef 100%);
        }
    </style>
</head>
<body class="bg-light">
    <div class="container mt-5 mb-5">
        <div class="card shadow-lg border-0">
            <div class="card-header bg-gradient-primary text-white text-center py-3">
                <h3 class="mb-0">Create Ticket</h3>
            </div>
            <div class="card-body p-4">

                <!-- Error Message -->
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>Error!</strong> ${errorMessage}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <c:remove var="errorMessage" scope="session" />
                </c:if>

                <!-- Ticket Type Selection -->
                <form action="${pageContext.request.contextPath}/create-ticket" method="post" class="mb-4">
                    <div class="mb-3">
                        <label for="ticketType" class="form-label fw-bold text-primary-custom">
                            Select Ticket Type <span class="text-danger">*</span>
                        </label>
                        <select id="ticketType" name="selectedTypeId" class="form-select border-primary-custom border-2" onchange="this.form.submit()">
                            <option value="">-- Choose Ticket Type --</option>
                            <c:forEach var="tt" items="${ticketTypes}">
                                <c:choose>
                                    <c:when test="${tt.ticket_Type_ID == 3}">
                                        <c:if test="${userRole == 2}">
                                            <option value="${tt.ticket_Type_ID}" ${selectedTypeId == tt.ticket_Type_ID ? 'selected' : ''}>
                                                ${tt.name}
                                            </option>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${tt.ticket_Type_ID}" ${selectedTypeId == tt.ticket_Type_ID ? 'selected' : ''}>
                                            ${tt.name}
                                        </option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </form>

                <!-- Horizontal divider -->
                <c:if test="${not empty selectedTypeId}">
                    <hr class="my-4" style="border-top: 2px solid #4273f1; opacity: 0.3;" />
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
                                    <div class="alert alert-danger text-center" role="alert">
                                        <h4 class="alert-heading">Access Denied!</h4>
                                        <p>Only HR personnel can create Recruitment Tickets.</p>
                                        <hr>
                                        <p class="mb-0">Please contact your HR department if you need assistance.</p>
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