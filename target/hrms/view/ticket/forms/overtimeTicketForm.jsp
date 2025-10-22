<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
isELIgnored="false" %>

<form action="${pageContext.request.contextPath}/submit-ticket" method="post">
  <input type="hidden" name="selectedTypeId" value="2" />

  <div class="card shadow">
    <div class="card-header bg-success text-white">
      <h5>Overtime Request</h5>
    </div>
     <div class="">
        <label class="form-label">Employee ID</label>
        <input type="text" class="form-control" value="${userId}" readonly />
      </div>
      <div class="">
        <label class="form-label">Employee Name</label>
        <input type="text" class="form-control" value="${fullname}" readonly />
      </div>
      <!-- Date -->
      <div class="mb-3">
        <label for="overtimeDate" class="form-label"
          >Overtime Date <span class="text-danger">*</span></label
        >
        <input
          type="date"
          id="overtimeDate"
          name="overtimeDate"
          class="form-control"
          required
        />
      </div>

      <!-- Time Range -->
      <div class="row">
        <div class="col-md-6 mb-3">
          <label for="startTime" class="form-label"
            >Start Time <span class="text-danger">*</span></label
          >
          <input
            type="time"
            id="startTime"
            name="startTime"
            class="form-control"
            required
          />
        </div>
        <div class="col-md-6 mb-3">
          <label for="endTime" class="form-label"
            >End Time <span class="text-danger">*</span></label
          >
          <input
            type="time"
            id="endTime"
            name="endTime"
            class="form-control"
            required
          />
        </div>
      </div>

      <!-- Description -->
      <div class="mb-3">
        <label for="description" class="form-label"
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
        <button type="submit" class="btn btn-success">
          Submit Overtime Request
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
