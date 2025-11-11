<!-- filepath: d:\FPT\Ky_5\SWP391\Project\SWP391-G3\src\main\webapp\view\ticket\ticketDetail.jsp -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Ticket Detail</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/ticketDetail.css"
    />
  </head>
  <body>
    <div class="ticket-detail-container">
      <div class="ticket-card">
        <div class="ticket-header ticket-type-${ticket.ticket_Type_ID}">
          <h2 class="ticket-title">
            <c:choose>
              <c:when test="${ticket.ticket_Type_ID == 1}"
                >Leave Request</c:when
              >
              <c:when test="${ticket.ticket_Type_ID == 2}"
                >Overtime Request</c:when
              >
              <c:when test="${ticket.ticket_Type_ID == 3}"
                >Recruitment Request</c:when
              >
              <c:otherwise>Other Request</c:otherwise>
            </c:choose>
          </h2>
          <span class="ticket-id">#${ticket.ticketID}</span>
        </div>

        <div class="ticket-body">
          <!-- Employee Information -->
          <section class="employee-info-section">
            <h3 class="section-title">Employee Information</h3>
            <div class="info-grid">
              <div class="info-item">
                <span class="info-label">Employee ID</span>
                <span class="info-value">${ticket.userID}</span>
              </div>
              <div class="info-item">
                <span class="info-label">Employee Name</span>
                <span class="info-value">${ticket.userFullName}</span>
              </div>
              <div class="info-item">
                <span class="info-label">Department</span>
                <span class="info-value">${ticket.departmentName}</span>
              </div>
              <div class="info-item">
                <span class="info-label">Created Date</span>
                <span class="info-value">${ticket.create_Date}</span>
              </div>
            </div>
          </section>

          <!-- Status -->
          <section class="status-section">
            <div class="status-badge status-${ticket.status.toLowerCase()}">
              ${ticket.status}
            </div>
          </section>

          <!-- Request Details -->
          <section class="content-section">
            <h3 class="section-title">Request Details</h3>

            <c:choose>
              <%-- Leave Ticket --%>
              <c:when test="${ticket.ticket_Type_ID == 1}">
                <div class="ticket-details ticket-details-leave">
                  <div class="detail-row">
                    <span class="detail-label">Leave Type</span>
                    <span class="detail-value">${ticket.leaveTypeName}</span>
                  </div>
                  <div class="detail-row">
                    <span class="detail-label">Start Date</span>
                    <span class="detail-value">${ticket.startDate}</span>
                  </div>
                  <div class="detail-row">
                    <span class="detail-label">End Date</span>
                    <span class="detail-value">${ticket.endDate}</span>
                  </div>
                  <div class="detail-row detail-full">
                    <span class="detail-label">Reason</span>
                    <p class="detail-content">${ticket.ticket_Content}</p>
                  </div>
                </div>
              </c:when>

              <%-- Overtime Ticket --%>
              <c:when test="${ticket.ticket_Type_ID == 2}">
                <c:if test="${not empty dayType}">
                  <div class="ot-info-box">
                    <div class="row">
                      <div class="col-md-6">
                        <strong>Day Type:</strong>
                        <span class="badge ms-2 badge-${dayType}"
                          >${dayType}</span
                        >
                      </div>
                      <div class="col-md-6">
                        <strong>OT Salary Rate:</strong>
                        <span class="text-primary-custom fw-bold"
                          >${otSalaryPer}%</span
                        >
                      </div>
                    </div>
                  </div>
                </c:if>

                <div class="ticket-details ticket-details-overtime">
                  <div class="detail-row">
                    <span class="detail-label">Overtime Date</span>
                    <span class="detail-value">${ticket.overtimeDate}</span>
                  </div>
                  <div class="detail-row">
                    <span class="detail-label">Start Time</span>
                    <span class="detail-value">${ticket.startTime}</span>
                  </div>
                  <div class="detail-row">
                    <span class="detail-label">End Time</span>
                    <span class="detail-value">${ticket.endTime}</span>
                  </div>
                  <div class="detail-row detail-full">
                    <span class="detail-label">Reason</span>
                    <p class="detail-content">${ticket.ticket_Content}</p>
                  </div>
                </div>
              </c:when>

              <%-- Recruitment/Other Tickets --%>
              <c:otherwise>
                <div class="ticket-details ticket-details-general">
                  <div class="detail-row detail-full">
                    <span class="detail-label">Request Content</span>
                    <p class="detail-content">${ticket.ticket_Content}</p>
                  </div>
                </div>
              </c:otherwise>
            </c:choose>
          </section>

          <!-- Approval Form (if Pending) -->
          <c:if test="${isPending}">
            <section class="approval-section">
              <h3 class="section-title">Manager Action</h3>
              <form
                class="approval-form"
                action="${pageContext.request.contextPath}/approve-ticket"
                method="post"
              >
                <input
                  type="hidden"
                  name="ticketId"
                  value="${ticket.ticketID}"
                />

                <div class="form-group">
                  <label class="form-label" for="comment">
                    Comment <span class="text-danger">*</span>
                  </label>
                  <textarea
                    id="comment"
                    name="comment"
                    class="form-textarea"
                    rows="4"
                    placeholder="Add your feedback here ..."
                    required
                    minlength="1"
                  ></textarea>
                </div>

                <div class="action-buttons">
                  <button
                    type="submit"
                    name="action"
                    value="approve"
                    class="btn btn-approve"
                  >
                    Approve
                  </button>
                  <button
                    type="submit"
                    name="action"
                    value="reject"
                    class="btn btn-reject"
                  >
                    Reject
                  </button>
                  <a
                    href="${pageContext.request.contextPath}/department-ticket"
                    class="btn btn-cancel"
                  >
                    Back to List
                  </a>
                </div>
              </form>
            </section>
          </c:if>

          <!-- Manager Comment (if not Pending) -->
          <c:if test="${!isPending && not empty ticket.comment}">
            <section class="manager-comment-section">
              <h3 class="section-title">Manager Feedback</h3>
              <div class="manager-comment">
                <div class="comment-header">
                  <span class="comment-author"
                    >Approved by: ${ticket.approverName}</span
                  >
                  <span class="comment-date">${ticket.approve_Date}</span>
                </div>
                <p class="comment-content">${ticket.comment}</p>
              </div>
            </section>
          </c:if>

          <!-- Back Button (if not Pending) -->
          <c:if test="${!isPending}">
            <div class="back-button-wrapper">
              <a
                href="${pageContext.request.contextPath}/department-ticket"
                class="btn btn-secondary"
              >
                Back to List
              </a>
            </div>
          </c:if>
        </div>
      </div>
    </div>

    <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
