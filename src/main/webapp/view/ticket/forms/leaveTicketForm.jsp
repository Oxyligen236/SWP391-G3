<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
isELIgnored="false" %>

<form action="${pageContext.request.contextPath}/submit-ticket" method="post">
  <input type="hidden" name="selectedTypeId" value="1" />

  <div class="card shadow">
    <div class="card-header bg-primary text-white">
      <h5>Leave Request</h5>
    </div>
     <div class="">
        <label class="form-label">Employee ID</label>
        <input type="text" class="form-control" value="${userId}" readonly />
      </div>
      <div class="">
        <label class="form-label">Employee Name</label>
        <input type="text" class="form-control" value="${fullname}" readonly />
      </div>
      <!-- Leave Type -->
      <div class="mb-3">
        <label for="leaveType" class="form-label"
          >Leave Type <span class="text-danger">*</span></label
        >
        <select id="leaveType" name="leaveType" class="form-select" required>
          <option value="">-- Select Leave Type --</option>
          <option value="Sick">Sick Leave</option>
          <option value="Annual">Annual Leave</option>
          <option value="Personal">Personal Leave</option>
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
          href="${pageContext.request.contextPath}/create-ticket"
          class="btn btn-secondary"
          >Cancel</a
        >
      </div>
    </div>
  </div>
</form>
