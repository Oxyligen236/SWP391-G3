<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
isELIgnored="false" %>

<form action="${pageContext.request.contextPath}/submit-ticket" method="post">
  <input type="hidden" name="selectedTypeId" value="4" />

  <div class="card shadow">
    <div class="card-header bg-warning text-dark">
      <h5>Other Request</h5>
    </div>
    <div class="card-body">
      <!-- User Information Display -->

      <div class="">
        <label class="form-label">Employee ID</label>
        <input type="text" class="form-control" value="${userId}" readonly />
      </div>
      <div class="">
        <label class="form-label">Employee Name</label>
        <input type="text" class="form-control" value="${fullname}" readonly />
      </div>

      <!-- Ticket Content -->
      <div class="mb-3">
        <label for="ticketContent" class="form-label">
          Request Content <span class="text-danger">*</span>
        </label>
        <textarea
          id="ticketContent"
          name="ticketContent"
          class="form-control"
          rows="6"
          placeholder="Please describe your request in detail..."
          required
        ></textarea>
      </div>

      <!-- Submit Button -->
      <div class="d-grid gap-2">
        <button type="submit" class="btn btn-warning text-dark">
          Submit Request
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
