<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%@ page
isELIgnored="false" %>

<link
  rel="stylesheet"
  href="${pageContext.request.contextPath}/css/ticketForms.css"
/>

<form
  method="post"
  action="${pageContext.request.contextPath}/submit-ticket"
  class="ticket-form-container"
>
  <input type="hidden" name="selectedTypeId" value="1" />

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
      Leave Type <span class="required">*</span>
    </label>
    <select class="form-select" name="leaveTypeID" required>
      <option value="">-- Choose Leave Type --</option>
      <c:forEach var="leaveType" items="${leaveTypes}">
        <option value="${leaveType.leaveTypeID}">
          ${leaveType.leaveTypeName}
        </option>
      </c:forEach>
    </select>
  </div>

  <div class="form-row">
    <div class="form-group">
      <label class="form-label">
        Start Date <span class="required">*</span>
      </label>
      <input type="date" class="form-control" name="startDate" required />
    </div>
    <div class="form-group">
      <label class="form-label">
        End Date <span class="required">*</span>
      </label>
      <input type="date" class="form-control" name="endDate" required />
    </div>
  </div>

  <div class="form-group full-width">
    <label class="form-label"> Reason <span class="required">*</span> </label>
    <textarea
      class="form-control"
      name="reason"
      rows="4"
      placeholder="Enter your reason for leave..."
      required
    ></textarea>
  </div>

  <div class="button-group">
    <button type="reset" class="btn btn-outline-secondary">Reset</button>
    <button type="submit" class="btn btn-primary">Submit</button>
  </div>
</form>
