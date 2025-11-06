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
  action="${pageContext.request.contextPath}/submit-ticket"
  method="post"
  class="p-4 bg-white rounded"
>
  <input type="hidden" name="selectedTypeId" value="2" />

  <div class="row mb-3">
    <div class="col-md-6">
      <label class="form-label fw-bold text-primary-custom">Employee ID</label>
      <input
        type="text"
        class="form-control border-primary-custom border-2"
        name="employeeId"
        value="${userId}"
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
        value="${fullname}"
        readonly
      />
    </div>
  </div>

  <div class="row mb-3">
    <div class="col-md-6">
      <label class="form-label fw-bold text-primary-custom"
        >Overtime Date <span class="text-danger">*</span></label
      >
      <input
        type="date"
        name="overtimeDate"
        class="form-control border-primary-custom border-2"
        required
      />
    </div>
    <div class="col-md-3">
      <label class="form-label fw-bold text-primary-custom"
        >Start Time <span class="text-danger">*</span></label
      >
      <input
        type="time"
        name="startTime"
        class="form-control border-primary-custom border-2"
        required
      />
    </div>
    <div class="col-md-3">
      <label class="form-label fw-bold text-primary-custom"
        >End Time <span class="text-danger">*</span></label
      >
      <input
        type="time"
        name="endTime"
        class="form-control border-primary-custom border-2"
        required
      />
    </div>
  </div>

  <div class="mb-3">
    <label class="form-label fw-bold text-primary-custom"
      >Reason <span class="text-danger">*</span></label
    >
    <textarea
      name="reason"
      class="form-control border-primary-custom border-2"
      rows="3"
      placeholder="Enter your reason for overtime..."
      required
    ></textarea>
  </div>

  <div class="text-end">
    <button type="reset" class="btn btn-outline-secondary me-2">Reset</button>
    <button type="submit" class="btn btn-primary-custom px-4">Submit</button>
  </div>
</form>
