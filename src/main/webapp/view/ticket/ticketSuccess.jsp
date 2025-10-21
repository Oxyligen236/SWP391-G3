<!-- filepath: d:\FPT\Ky_5\SWP391\Project\SWP391-G3\src\main\webapp\view\ticket\ticketSuccess.jsp -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Ticket Submitted Successfully</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css"
    />
    <link
      rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/ticket-success.css"
    />
  </head>
  <body>
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-lg-6 col-md-8">
          <div class="success-card">
            <div class="card-header-custom text-center">
              <h3 class="mb-0">
                <i class="bi bi-check-circle-fill me-2"></i>
                Ticket System
              </h3>
            </div>

            <div class="card-body p-5">
              <div class="text-center mb-4">
                <i class="bi bi-check-circle-fill success-icon"></i>
              </div>

              <h2 class="text-center text-success fw-bold mb-3">
                Ticket Submitted Successfully!
              </h2>

              <p class="text-center text-muted mb-4">
                Your ticket has been created and submitted for review.
              </p>

              <div class="divider"></div>

              <div class="d-grid gap-3">
                <a
                  href="${pageContext.request.contextPath}/home"
                  class="btn btn-home btn-lg"
                >
                  <i class="bi bi-house-door-fill me-2"></i>
                  Back to Home
                </a>
                <a
                  href="${pageContext.request.contextPath}/ticketList"
                  class="btn btn-tickets btn-lg"
                >
                  <i class="bi bi-file-earmark-text me-2"></i>
                  View My Tickets
                </a>
                <a
                  href="${pageContext.request.contextPath}/create-ticket"
                  class="btn btn-outline-secondary btn-lg"
                >
                  <i class="bi bi-plus-circle me-2"></i>
                  Create Another Ticket
                </a>
              </div>

              <div class="text-center mt-4">
                <small class="text-muted">
                  <i class="bi bi-info-circle me-1"></i>
                  You will receive an email notification once your ticket is
                  reviewed.
                </small>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
