<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
isELIgnored="false" %>

<!DOCTYPE html>

<html>
  <head>
    <title>Ticket List</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css"
    />
  </head>
  <body>
    <div class="container mt-4">
      <h4 class="text-primary">Ticket List</h4>
      <table class="table table-bordered table-hover text-center">
        <thead class="table-primary">
          <tr>
            <th>TYPE</th>
            <th>TICKET_CONTENT</th>
            <th>CREATE_DATE</th>
            <th>COMMENT</th>
            <th>STATUS</th>
            <th>APPROVE_DATE</th>
          </tr>
        </thead>
        <tbody>
          <c:if test="${empty ticketList}">
            <tr>
              <td colspan="6" class="text-danger fw-bold text-center">
                Không có ticket nào
              </td>
            </tr>
          </c:if>

          <c:forEach var="t" items="${ticketList}">
            <tr>
              <td>${t.ticketTypeName}</td>
              <td
                style="
                  max-width: 150px;
                  overflow: hidden;
                  text-overflow: ellipsis;
                "
              >
                ${t.ticket_Content}
              </td>
              <td>${t.create_Date}</td>
              <td>${t.comment}</td>
              <td class="text-success">${t.status}</td>
              <td>${t.approve_Date}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </body>
</html>
