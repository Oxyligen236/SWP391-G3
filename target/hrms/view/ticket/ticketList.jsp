<%@page contentType="text/html" pageEncoding="UTF-8"%> <%@taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> <%@taglib
uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
  <head>
    <title>Ticket List</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" />
  </head>
  <body>
    <div class="container mt-4">
      <h4 class="text-primary">Ticket List</h4>
      <table class="table table-bordered table-hover text-center">
        <thead class="table-dark">
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
          <c:forEach var="t" items="${ticketList}">
            <tr>
              <td>${t.type}</td>
              <td
                style="
                  max-width: 150px;
                  overflow: hidden;
                  text-overflow: ellipsis;
                "
              >
                ${t.ticketContent}
              </td>
              <td>${t.createDate}</td>
              <td>${t.comment}</td>
              <td>${t.status}</td>
              <td>${t.approveDate}</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>

      <!-- Phân trang -->
      <div class="d-flex justify-content-center mt-3">
        <c:forEach begin="1" end="${totalPages}" var="i">
          <a
            href="list?page=${i}"
            class="btn btn-sm ${i == currentPage ? 'btn-primary' : 'btn-outline-secondary'} mx-1"
            >${i}</a
          >
        </c:forEach>
      </div>
    </div>
  </body>
</html>
