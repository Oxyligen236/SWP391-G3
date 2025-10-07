<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form action="${pageContext.request.contextPath}/createTicket" method="post">
  <input type="hidden" name="selectedTypeId" value="1" />

  <div class="card shadow">
    <div class="card-header bg-primary text-white">
      <h5>Leave Request Form</h5>
    </div>
    <div class="card-body">
      <!-- Leave Type -->
      <div class="mb-3">
        <label for="leaveType" class="form-label"
          >Leave Type <span class="text-danger">*</span></label
        >
        <select id="leaveType" name="leaveType" class="form-select" required>
          <option value="">-- Select Leave Type --</option>
          <option value="sick">Sick Leave</option>
          <option value="annual">Annual Leave</option>
          <option value="personal">Personal Leave</option>
        </select>
      </div>

      <!-- Start Date -->
      <div class="mb-3">
        <label for="startDate" class="form-label"
          >Start Date <span class="text-danger">*</span></label
        >
        <input
          type="date"
          id="startDate"
          name="startDate"
          class="form-control"
          required
        />
      </div>

      <!-- End Date -->
      <div class="mb-3">
        <label for="endDate" class="form-label"
          >End Date <span class="text-danger">*</span></label
        >
        <input
          type="date"
          id="endDate"
          name="endDate"
          class="form-control"
          required
        />
      </div>

      <!-- Reason -->
      <div class="mb-3">
        <label for="reason" class="form-label"
          >Reason <span class="text-danger">*</span></label
        >
        <textarea
          id="reason"
          name="reason"
          class="form-control"
          rows="4"
          required
        ></textarea>
      </div>

      <!-- Submit Button -->
      <div class="d-grid gap-2">
        <button type="submit" class="btn btn-primary">
          Submit Leave Request
        </button>
        <a
          href="${pageContext.request.contextPath}/createTicket"
          class="btn btn-secondary"
          >Cancel</a
        >
      </div>
    </div>
  </div>
</form>
