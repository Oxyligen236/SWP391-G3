<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Ticket Details</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css"
    />
  </head>
  <body>
    <div class="container mt-5">
      <div class="row justify-content-center">
        <div class="col-md-8">
          <div class="card shadow">
            <div class="card-header bg-primary text-white">
              <h4 class="mb-0">Ticket Details</h4>
            </div>
            <div class="card-body">
              <div class="row mb-3">
                <div class="col-md-6">
                  <strong>Ticket ID:</strong> ${ticket.ticketID}
                </div>
                <div class="col-md-6">
                  <strong>Type:</strong> ${ticket.ticketTypeName}
                </div>
              </div>

              <div class="row mb-3">
                <div class="col-md-6">
                  <strong>Sender:</strong> ${ticket.userFullName != null ?
                  ticket.userFullName : 'N/A'}
                </div>
                <div class="col-md-6">
                  <strong>Created Date:</strong> ${ticket.create_Date}
                </div>
              </div>

              <div class="row mb-3">
                <div class="col-md-6">
                  <strong>Status:</strong>
                  <span
                    class="badge ${ticket.status == 'Approved' ? 'bg-success' : ticket.status == 'Rejected' ? 'bg-danger' : 'bg-warning'}"
                  >
                    ${ticket.status}
                  </span>
                </div>
                <div class="col-md-6">
                  <strong>Approve Date:</strong>
                  ${ticket.approve_Date != null ? ticket.approve_Date : 'N/A'}
                </div>
              </div>

              <div class="mb-3">
                <strong>Content:</strong>
                <div class="border p-3 bg-light" style="white-space: pre-wrap">
                  ${ticket.ticket_Content}
                </div>
              </div>

              <!-- Hiển thị comment nếu đã duyệt -->
              <c:if test="${!isPending && ticket.comment != null}">
                <div class="mb-3">
                  <strong>Comment:</strong>
                  <div
                    class="border p-3 bg-light"
                    style="white-space: pre-wrap"
                  >
                    ${ticket.comment}
                  </div>
                </div>
              </c:if>

              <hr />

              <!-- Form Approve/Reject (chỉ hiện khi Pending) -->
              <c:choose>
                <c:when test="${isPending}">
                  <h5 class="mb-3 text-primary">Approve or Reject</h5>
                  <!-- POST to cùng servlet -->
                  <form
                    action="${pageContext.request.contextPath}/ticket-detail"
                    method="post"
                  >
                    <input
                      type="hidden"
                      name="ticketId"
                      value="${ticket.ticketID}"
                    />

                    <div class="mb-3">
                      <label for="comment" class="form-label">Comment:</label>
                      <textarea
                        class="form-control"
                        id="comment"
                        name="comment"
                        rows="4"
                        placeholder="Enter your comment (optional)"
                      ></textarea>
                    </div>

                    <div class="d-flex gap-2">
                      <button
                        type="submit"
                        name="action"
                        value="approve"
                        class="btn btn-success flex-fill"
                        onclick="return confirm('Are you sure you want to APPROVE this ticket?')"
                      >
                        Approve
                      </button>
                      <button
                        type="submit"
                        name="action"
                        value="reject"
                        class="btn btn-danger flex-fill"
                        onclick="return confirm('Are you sure you want to REJECT this ticket?')"
                      >
                        Reject
                      </button>
                      <a
                        href="${pageContext.request.contextPath}/department-ticket"
                        class="btn btn-secondary flex-fill"
                      >
                        Cancel
                      </a>
                    </div>
                  </form>
                </c:when>

                <c:otherwise>
                  <!-- Nếu đã duyệt, chỉ hiện nút Back -->
                  <div class="text-center">
                    <a
                      href="${pageContext.request.contextPath}/department-ticket"
                      class="btn btn-secondary"
                    >
                      Back to List
                    </a>
                  </div>
                </c:otherwise>
              </c:choose>
            </div>
          </div>
        </div>
      </div>
    </div>

    <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
