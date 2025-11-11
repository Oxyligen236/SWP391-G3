<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%@ page
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
  <input type="hidden" name="selectedTypeId" value="2" />

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
      Overtime Date <span class="required">*</span>
    </label>
    <input
      type="date"
      id="overtimeDate"
      name="overtimeDate"
      class="form-control"
      min="${currentDate}"
      value="${selectedOvertimeDate}"
      onchange="this.form.action='${pageContext.request.contextPath}/create-ticket?selectedTypeId=2'; this.form.method='post'; this.form.submit();"
      required
    />
    <small class="form-text">Cannot select past dates</small>
  </div>

  <c:if test="${not empty dayType}">
    <div class="ot-info-box">
      <div class="row">
        <div>
          <strong>Day Type:</strong>
          <span class="badge badge-${fn:toLowerCase(dayType)}">${dayType}</span>
        </div>
        <div>
          <strong>OT Salary Rate:</strong>
          <span class="ot-info-value">${otSalaryPer}%</span>
        </div>
      </div>
    </div>
  </c:if>

  <div class="form-row">
    <div class="form-group">
      <label class="form-label">
        Start Time <span class="required">*</span>
      </label>
      <input type="time" name="startTime" class="form-control" required />
    </div>
    <div class="form-group">
      <label class="form-label">
        End Time <span class="required">*</span>
      </label>
      <input type="time" name="endTime" class="form-control" required />
    </div>
  </div>

  <div class="form-group full-width">
    <label class="form-label"> Reason <span class="required">*</span> </label>
    <textarea
      name="reason"
      class="form-control"
      rows="4"
      placeholder="Enter your reason for overtime..."
      required
    ></textarea>
  </div>

  <div class="button-group">
    <button type="reset" class="btn btn-outline-secondary">Reset</button>
    <button type="submit" class="btn btn-primary">Submit</button>
  </div>
</form>
