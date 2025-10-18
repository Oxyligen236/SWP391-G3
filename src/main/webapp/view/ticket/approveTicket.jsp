<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Approve Ticket</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css"
    />
  </head>
  <body>
    <div class="container mt-5">
      <h2 class="text-primary mb-4">Approve Ticket</h2>

      <div class="main-table">
        <table class="table table-bordered table-hover">
          <thead class="table-light">
            <tr>
              <th scope="col">Type</th>
              <th scope="col">Sender</th>
              <th scope="col">Title</th>
              <th scope="col">Created Date</th>
              <th scope="col">Status</th>
              <th scope="col">Approve Date</th>
              <th scope="col">Actions</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="ticket" items="${tickets}">
              <tr>
                <td>${ticket.ticketTypeName}</td>
                <td>${ticket.userFullName}</td>
                <td>${ticket.ticket_Content}</td>
                <td>${ticket.create_Date}</td>
                <td>${ticket.status}</td>
                <td>${ticket.approve_Date}</td>
                <td>
                  <a
                    href="${pageContext.request.contextPath}/approve-ticket?ticketID =${ticket.ticketID}"
                    class="btn btn-primary btn-sm"
                    >View Details</a
                  >
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </body>
</html>
