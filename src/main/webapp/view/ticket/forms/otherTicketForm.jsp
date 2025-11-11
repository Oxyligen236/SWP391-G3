<!-- filepath: d:\FPT\Ky_5\SWP391\Project\SWP391-G3\src\main\webapp\view\ticket\forms\otherTicketForm.jsp -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%@ page
isELIgnored="false" %>

<link
  rel="stylesheet"
  href="${pageContext.request.contextPath}/css/ticketForms.css"
/>

<form
  action="${pageContext.request.contextPath}/submit-ticket"
  method="post"
  class="ticket-form-container"
>
  <input type="hidden" name="selectedTypeId" value="4" />

  <div class="form-row">
    <div class="form-group">
      <label class="form-label">Employee ID</label>
      <input
        type="text"
        class="form-control"
        name="employeeId"
        value="${userId}"
        readonly
      />
    </div>
    <div class="form-group">
      <label class="form-label">Employee Name</label>
      <input
        type="text"
        class="form-control"
        name="employeeName"
        value="${fullname}"
        readonly
      />
    </div>
  </div>

  <div class="form-group full-width">
    <label class="form-label">
      Ticket Content <span class="required">*</span>
    </label>
    <textarea
      name="ticketContent"
      class="form-control"
      rows="4"
      placeholder="Enter your request details..."
      required
    ></textarea>
  </div>

  <div class="button-group">
    <button type="reset" class="btn btn-outline-secondary">Reset</button>
    <button type="submit" class="btn btn-primary">Submit</button>
  </div>
</form>
