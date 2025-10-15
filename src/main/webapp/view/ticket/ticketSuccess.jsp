<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
  </head>
  <body>
    <div class="container mt-5">
      <div class="row justify-content-center">
        <div class="col-md-6">
          <div class="card shadow text-center">
            <div class="card-body py-5">
              <div class="mb-4">
                <i
                  class="bi bi-check-circle-fill text-success"
                  style="font-size: 5rem"
                ></i>
              </div>
              <h2 class="text-success mb-3">Ticket Submitted Successfully!</h2>
              <p class="text-muted mb-4">
                Your ticket has been submitted and is pending approval.
              </p>

              <div class="d-grid gap-2 col-8 mx-auto">
                <a href="/hrms" class="btn btn-primary btn-lg">
                  <i class="bi bi-house-door me-2"></i>Go to Home
                </a>
                <a
                  href="<c:url value='/ticketList'/>"
                  class="btn btn-outline-secondary btn-lg"
                >
                  <i class="bi bi-file-earmark-text me-2"></i>View My Tickets
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
