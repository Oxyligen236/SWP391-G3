<!-- filepath: d:\FPT\Ky_5\SWP391\Project\SWP391-G3\src\main\webapp\view\ticket\forms\leaveTicketForm.jsp -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%@ page
isELIgnored="false" %>

<style>
  .text-primary-custom {
    color: #4273f1 !important;
  }
  .border-primary-custom {
    border-color: #4273f1 !important;
  }
  .btn-primary-custom {
    background: linear-gradient(135deg, #4273f1 0%, #5a8fff 100%);
    border: none;
    color: white;
  }
  .btn-primary-custom:hover {
    background: linear-gradient(135deg, #3a63d1 0%, #4a7fef 100%);
  }
</style>

<form
  method="post"
  action="${pageContext.request.contextPath}/submit-ticket"
  class="p-4 bg-white rounded"
>
  <input type="hidden" name="selectedTypeId" value="1" />

  <div class="row mb-3">
    <div class="col-md-6">
      <label class="form-label fw-bold text-primary-custom">Employee ID</label>
      <input
        type="text"
        class="form-control border-primary-custom border-2"
        name="employeeId"
        value="<c:out value='${userId}'/>"
        readonly
      />
    </div>
    <div class="col-md-6">
      <label class="form-label fw-bold text-primary-custom"
        >Employee Name</label
      >
      <input
        type="text"
        class="form-control border-primary-custom border-2"
        name="employeeName"
        value="<c:out value='${fullname}'/>"
        readonly
      />
    </div>
  </div>

  <div class="mb-3">
    <label class="form-label fw-bold text-primary-custom"
      >Leave Type <span class="text-danger">*</span></label
    >
    <select
      class="form-select border-primary-custom border-2"
      name="leaveTypeID"
      required
    >
      <option value="">-- Choose Leave Type --</option>
      <c:forEach var="leaveType" items="${leaveTypes}">
        <option value="<c:out value='${leaveType.leaveTypeID}'/>">
          <c:out value="${leaveType.leaveTypeName}" />
        </option>
      </c:forEach>
    </select>
  </div>

  <div class="row mb-3">
    <div class="col-md-6">
      <label class="form-label fw-bold text-primary-custom"
        >Start Date <span class="text-danger">*</span></label
      >
      <input
        type="date"
        class="form-control border-primary-custom border-2"
        name="startDate"
        required
      />
    </div>
    <div class="col-md-6">
      <label class="form-label fw-bold text-primary-custom"
        >End Date <span class="text-danger">*</span></label
      >
      <input
        type="date"
        class="form-control border-primary-custom border-2"
        name="endDate"
        required
      />
    </div>
  </div>

  <div class="mb-3">
    <label class="form-label fw-bold text-primary-custom"
      >Reason <span class="text-danger">*</span></label
    >
    <textarea
      class="form-control border-primary-custom border-2"
      name="reason"
      rows="3"
      placeholder="Enter your reason for leave..."
      required
    ></textarea>
  </div>

  <div class="text-end">
    <button type="reset" class="btn btn-outline-secondary me-2">Reset</button>
    <button type="submit" class="btn btn-primary-custom px-4">Submit</button>
  </div>
</form>
