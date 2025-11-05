<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>View Contract Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" />
    <script defer>
      document.addEventListener("DOMContentLoaded", function() {
        const editBtn = document.getElementById("editNoteBtn");
        const noteValue = document.getElementById("noteValue");
        const noteEditForm = document.getElementById("noteEditForm");
        const cancelBtn = document.getElementById("cancelNoteBtn");
        const saveBtn = document.getElementById("saveNoteBtn");
        const noteTextarea = document.getElementById("noteTextarea");
        const backToListLink = document.getElementById("backToListLink");

        // Status edit elements
        const editStatusBtn = document.getElementById("editStatusBtn");
        const statusValue = document.getElementById("statusValue");
        const statusEditForm = document.getElementById("statusEditForm");
        const cancelStatusBtn = document.getElementById("cancelStatusBtn");
        const saveStatusBtn = document.getElementById("saveStatusBtn");
        const statusSelect = document.getElementById("statusSelect");

        if (editBtn) {
          editBtn.addEventListener("click", function() {
            const userRole = parseInt(this.getAttribute("data-role"));
            
            if (userRole !== 1 && userRole !== 2) {
              alert("Bạn không có quyền sửa ghi chú hợp đồng!");
              return;
            }

            noteValue.style.display = "none";
            noteEditForm.style.display = "block";
            editBtn.style.display = "none";
          });
        }

        if (cancelBtn) {
          cancelBtn.addEventListener("click", function() {
            noteValue.style.display = "block";
            noteEditForm.style.display = "none";
            editBtn.style.display = "inline-block";
            noteTextarea.value = noteValue.textContent.trim();
          });
        }

        // Status edit handlers
        if (editStatusBtn) {
          editStatusBtn.addEventListener("click", function() {
            const userRole = parseInt(this.getAttribute("data-role"));
            
            if (userRole !== 1 && userRole !== 2) {
              alert("Bạn không có quyền sửa trạng thái hợp đồng!");
              return;
            }

            statusValue.style.display = "none";
            statusEditForm.style.display = "block";
            editStatusBtn.style.display = "none";
          });
        }

        if (cancelStatusBtn) {
          cancelStatusBtn.addEventListener("click", function() {
            statusValue.style.display = "block";
            statusEditForm.style.display = "none";
            editStatusBtn.style.display = "inline-block";
          });
        }

        if (backToListLink) {
          backToListLink.addEventListener("click", function(e) {
            const userRole = parseInt(this.getAttribute("data-role"));
            

            if (userRole !== 1 && userRole !== 2) {
              e.preventDefault();
              alert("Bạn không có quyền truy cập danh sách hợp đồng!");
              return false;
            }
          });
        }
      });
    </script>
  </head>
  <body class="bg-light">
    <div class="container mt-4 mb-5">
      <div class="card shadow-sm">
        <div class="card-header bg-info text-white">
          <h4 class="mb-0">
            <i class="bi bi-file-earmark-text"></i> Contract Details
          </h4>
        </div>
        <div class="card-body">
          <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <i class="bi bi-exclamation-triangle-fill"></i> ${error}
              <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
          </c:if>

          <c:if test="${not empty successMessage}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
              <i class="bi bi-check-circle-fill"></i> ${successMessage}
              <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
          </c:if>

          <c:if test="${not empty contract}">
            <div class="row g-3">
              <!-- Contract ID -->
              <div class="col-md-6">
                <label class="form-label fw-bold text-secondary">
                  <i class="bi bi-hash"></i> Contract ID:
                </label>
                <div class="form-control-plaintext border rounded p-2 bg-light">
                  ${contract.contractId}
                </div>
              </div>

              <!-- Full Name -->
              <div class="col-md-6">
                <label class="form-label fw-bold text-secondary">
                  <i class="bi bi-person"></i> Full Name:
                </label>
                <div class="form-control-plaintext border rounded p-2 bg-light">
                  ${contract.employeeName}
                </div>
              </div>

              <!-- User ID -->
              <div class="col-md-6">
                <label class="form-label fw-bold text-secondary">
                  <i class="bi bi-person-badge"></i> User ID:
                </label>
                <div class="form-control-plaintext border rounded p-2 bg-light">
                  ${contract.userId}
                </div>
              </div>

              <!-- Position -->
              <div class="col-md-6">
                <label class="form-label fw-bold text-secondary">
                  <i class="bi bi-briefcase"></i> Position:
                </label>
                <div class="form-control-plaintext border rounded p-2 bg-light">
                  ${contract.positionName}
                </div>
              </div>

              <!-- Start Date -->
              <div class="col-md-4">
                <label class="form-label fw-bold text-secondary">
                  <i class="bi bi-calendar-event"></i> Start Date:
                </label>
                <div class="form-control-plaintext border rounded p-2 bg-light">
                  ${contract.startDate}
                </div>
              </div>

              <!-- End Date -->
              <div class="col-md-4">
                <label class="form-label fw-bold text-secondary">
                  <i class="bi bi-calendar-x"></i> End Date:
                </label>
                <div class="form-control-plaintext border rounded p-2 bg-light">
                  ${contract.endDate}
                </div>
              </div>

              <!-- Sign Date -->
              <div class="col-md-4">
                <label class="form-label fw-bold text-secondary">
                  <i class="bi bi-calendar-check"></i> Sign Date:
                </label>
                <div class="form-control-plaintext border rounded p-2 bg-light">
                  ${contract.signDate}
                </div>
              </div>

              <!-- Duration -->
              <div class="col-md-6">
                <label class="form-label fw-bold text-secondary">
                  <i class="bi bi-clock-history"></i> Duration:
                </label>
                <div class="form-control-plaintext border rounded p-2 bg-light">
                  ${contract.duration} months
                </div>
              </div>

              <!-- Base Salary -->
              <div class="col-md-6">
                <label class="form-label fw-bold text-secondary">
                  <i class="bi bi-currency-dollar"></i> Base Salary:
                </label>
                <div class="form-control-plaintext border rounded p-2 bg-light text-success fw-semibold">
                  <fmt:formatNumber value="${contract.baseSalary}" type="number" groupingUsed="true" /> VNĐ
                </div>
              </div>

              <!-- Signer -->
              <div class="col-md-6">
                <label class="form-label fw-bold text-secondary">
                  <i class="bi bi-pen"></i> Signer:
                </label>
                <div class="form-control-plaintext border rounded p-2 bg-light">
                  ${contract.signerName}
                </div>
              </div>

              <!-- Contract Type -->
              <div class="col-md-6">
                <label class="form-label fw-bold text-secondary">
                  <i class="bi bi-file-text"></i> Contract Type:
                </label>
                <div class="form-control-plaintext border rounded p-2 bg-light">
                  ${contract.contractTypeName}
                </div>
              </div>

              <!-- Status Section -->
              <div class="col-12">
                <label class="form-label fw-bold text-secondary">
                  <i class="bi bi-check-circle"></i> Status:
                  <c:if test="${sessionScope.account.role == 1 || sessionScope.account.role == 2}">
                    <button type="button" id="editStatusBtn" class="btn btn-sm btn-outline-primary ms-2" data-role="${sessionScope.account.role}">
                      <i class="bi bi-pencil"></i> Edit
                    </button>
                  </c:if>
                </label>
                <div id="statusValue" class="form-control-plaintext border rounded p-2 bg-light">
                  <c:choose>
                    <c:when test="${contract.status == 'Pending'}">
                      <span class="badge bg-warning text-dark fs-6">${contract.status}</span>
                    </c:when>
                    <c:when test="${contract.status == 'Approved'}">
                      <span class="badge bg-info fs-6">${contract.status}</span>
                    </c:when>
                    <c:when test="${contract.status == 'Active'}">
                      <span class="badge bg-success fs-6">${contract.status}</span>
                    </c:when>
                    <c:when test="${contract.status == 'Expired'}">
                      <span class="badge bg-secondary fs-6">${contract.status}</span>
                    </c:when>
                    <c:when test="${contract.status == 'Archived'}">
                      <span class="badge bg-dark fs-6">${contract.status}</span>
                    </c:when>
                    <c:when test="${contract.status == 'Cancelled'}">
                      <span class="badge bg-danger fs-6">${contract.status}</span>
                    </c:when>
                    <c:otherwise>
                      <span class="badge bg-secondary fs-6">${contract.status != null ? contract.status : 'N/A'}</span>
                    </c:otherwise>
                  </c:choose>
                </div>
                <div id="statusEditForm" style="display: none;">
                  <form method="post" action="<c:url value='/viewContracts'/>" accept-charset="UTF-8">
                    <input type="hidden" name="action" value="updateStatus" />
                    <input type="hidden" name="contractId" value="${contract.contractId}" />
                    <select id="statusSelect" name="status" class="form-select">
                      <option value="Pending" ${contract.status == 'Pending' ? 'selected' : ''}>Pending</option>
                      <option value="Approved" ${contract.status == 'Approved' ? 'selected' : ''}>Approved</option>
                      <option value="Active" ${contract.status == 'Active' ? 'selected' : ''}>Active</option>
                      <option value="Expired" ${contract.status == 'Expired' ? 'selected' : ''}>Expired</option>
                      <option value="Archived" ${contract.status == 'Archived' ? 'selected' : ''}>Archived</option>
                      <option value="Cancelled" ${contract.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
                    </select>
                    <div class="mt-2">
                      <button type="submit" id="saveStatusBtn" class="btn btn-sm btn-success">
                        <i class="bi bi-check-lg"></i> Save
                      </button>
                      <button type="button" id="cancelStatusBtn" class="btn btn-sm btn-secondary">
                        <i class="bi bi-x-lg"></i> Cancel
                      </button>
                    </div>
                  </form>
                </div>
              </div>

              <!-- Note Section -->
              <div class="col-12">
                <label class="form-label fw-bold text-secondary">
                  <i class="bi bi-sticky"></i> Note:
                  <button type="button" id="editNoteBtn" class="btn btn-sm btn-outline-primary ms-2" data-role="${sessionScope.account.role}">
                    <i class="bi bi-pencil"></i> Edit
                  </button>
                </label>
                <div id="noteValue" class="form-control-plaintext border rounded p-3 bg-light" style="min-height: 80px; white-space: pre-wrap;">
                  ${contract.note}
                </div>
                <div id="noteEditForm" style="display: none;">
                  <form method="post" action="<c:url value='/viewContracts'/>" accept-charset="UTF-8">
                    <input type="hidden" name="action" value="updateNote" />
                    <input type="hidden" name="contractId" value="${contract.contractId}" />
                    <textarea id="noteTextarea" name="note" class="form-control" rows="4" placeholder="Enter note...">${contract.note}</textarea>
                    <div class="mt-2">
                      <button type="submit" id="saveNoteBtn" class="btn btn-sm btn-success">
                        <i class="bi bi-check-lg"></i> Save
                      </button>
                      <button type="button" id="cancelNoteBtn" class="btn btn-sm btn-secondary">
                        <i class="bi bi-x-lg"></i> Cancel
                      </button>
                    </div>
                  </form>
                </div>
              </div>
            </div>
          </c:if>

          <!-- Action Buttons -->
          <div class="d-flex gap-2 justify-content-center mt-4 pt-3 border-top">
            <a href="<c:url value='/myContracts'/>" class="btn btn-outline-secondary">
              <i class="bi bi-house"></i> Back 
            </a>
            <c:if test="${sessionScope.account.role == 1 || sessionScope.account.role == 2}">
              <a href="<c:url value='/viewContracts'/>" id="backToListLink" class="btn btn-outline-primary" data-role="${sessionScope.account.role}">
                <i class="bi bi-list-ul"></i> Back to Contract List
              </a>
            </c:if>
          </div>
        </div>
      </div>
    </div>

    <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
