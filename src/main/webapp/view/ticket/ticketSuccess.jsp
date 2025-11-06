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
  </head>

  <body class="bg-light">
    <div
      class="container min-vh-100 d-flex align-items-center justify-content-center"
    >
      <div class="row justify-content-center w-100">
        <div class="col-md-8 col-lg-6">
          <div class="card shadow-lg border-0 rounded-4">
            <div
              class="card-header bg-success text-white text-center py-4 rounded-top-4"
            >
              <h2 class="h3 mb-0 mt-3">Success!</h2>
            </div>

            <div class="card-body text-center p-5">
              <h3 class="card-title text-success fw-bold mb-3">
                Ticket Submitted Successfully
              </h3>

              <p class="card-text text-muted mb-4 lead">
                Your ticket has been submitted and is pending approval. You will
                be notified once it's reviewed.
              </p>
            </div>

            <div class="card-footer bg-white border-0 p-4">
              <div class="d-grid gap-3">
                <a
                  href="${pageContext.request.contextPath}/ticketList"
                  class="btn btn-outline-success btn-lg"
                >
                  View My Tickets
                </a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
