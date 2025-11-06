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
    <link rel="stylesheet" href="<c:url value='/css/view-contract.css'/>" />
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
            const currentUserId = parseInt(this.getAttribute("data-user-id"));
            const contractUserId = parseInt(this.getAttribute("data-contract-user-id"));
            
            if (userRole !== 1 && userRole !== 2) {
              alert("Bạn không có quyền sửa ghi chú hợp đồng!");
              return;
            }

            if (currentUserId === contractUserId) {
              alert("Bạn không thể sửa hợp đồng của chính mình!");
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
            const currentUserId = parseInt(this.getAttribute("data-user-id"));
            const contractUserId = parseInt(this.getAttribute("data-contract-user-id"));
            
            if (userRole !== 1 && userRole !== 2) {
              alert("Bạn không có quyền sửa trạng thái hợp đồng!");
              return;
            }

            if (currentUserId === contractUserId) {
              alert("Bạn không thể sửa hợp đồng của chính mình!");
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
  <body>
    <div class="contract-container">
      <h1>Contract Details</h1>

      <c:if test="${not empty error}">
        <div class="error-msg">${error}</div>
      </c:if>

      <c:if test="${not empty successMessage}">
        <div class="success-msg">${successMessage}</div>
      </c:if>

      <div class="contract-detail-card">

        <c:if test="${not empty contract}">
          <div class="detail-grid">
            <!-- Contract ID -->
            <div class="detail-item">
              <label class="detail-label">Contract ID:</label>
              <div class="detail-value">${contract.contractId}</div>
            </div>

            <!-- Full Name -->
            <div class="detail-item">
              <label class="detail-label">Full Name:</label>
              <div class="detail-value">${contract.employeeName}</div>
            </div>

            <!-- User ID -->
            <div class="detail-item">
              <label class="detail-label">User ID:</label>
              <div class="detail-value">${contract.userId}</div>
            </div>

            <!-- Position -->
            <div class="detail-item">
              <label class="detail-label">Position:</label>
              <div class="detail-value">${contract.positionName}</div>
            </div>

            <!-- Start Date -->
            <div class="detail-item">
              <label class="detail-label">Start Date:</label>
              <div class="detail-value">${contract.startDate}</div>
            </div>

            <!-- End Date -->
            <div class="detail-item">
              <label class="detail-label">End Date:</label>
              <div class="detail-value">${contract.endDate}</div>
            </div>

            <!-- Sign Date -->
            <div class="detail-item">
              <label class="detail-label">Sign Date:</label>
              <div class="detail-value">${contract.signDate}</div>
            </div>

            <!-- Duration -->
            <div class="detail-item">
              <label class="detail-label">Duration:</label>
              <div class="detail-value">${contract.duration} months</div>
            </div>

            <!-- Base Salary -->
            <div class="detail-item">
              <label class="detail-label">Base Salary:</label>
              <div class="detail-value salary-highlight">
                <fmt:formatNumber value="${contract.baseSalary}" type="number" groupingUsed="true" /> VNĐ
              </div>
            </div>

            <!-- Contract Type -->
            <div class="detail-item">
              <label class="detail-label">Contract Type:</label>
              <div class="detail-value">${contract.contractTypeName}</div>
            </div>

            <!-- Signer -->
            <div class="detail-item">
              <label class="detail-label">Signer:</label>
              <div class="detail-value">${contract.signerName}</div>
            </div>

            <!-- Status Section -->
            <div class="detail-item-full">
              <label class="detail-label">
                Status:
                <c:if test="${(sessionScope.account.role == 1 || sessionScope.account.role == 2) && sessionScope.account.userId != contract.userId}">
                  <button type="button" id="editStatusBtn" class="btn-edit" 
                          data-role="${sessionScope.account.role}"
                          data-user-id="${sessionScope.account.userId}"
                          data-contract-user-id="${contract.userId}">
                    Edit
                  </button>
                </c:if>
              </label>
              <div id="statusValue" class="detail-value">
                <c:choose>
                  <c:when test="${contract.status == 'Pending'}">
                    <span class="badge bg-warning">${contract.status}</span>
                  </c:when>
                  <c:when test="${contract.status == 'Approved'}">
                    <span class="badge bg-info">${contract.status}</span>
                  </c:when>
                  <c:when test="${contract.status == 'Active'}">
                    <span class="badge bg-success">${contract.status}</span>
                  </c:when>
                  <c:when test="${contract.status == 'Expired'}">
                    <span class="badge bg-secondary">${contract.status}</span>
                  </c:when>
                  <c:when test="${contract.status == 'Archived'}">
                    <span class="badge bg-dark">${contract.status}</span>
                  </c:when>
                  <c:when test="${contract.status == 'Cancelled'}">
                    <span class="badge bg-danger">${contract.status}</span>
                  </c:when>
                  <c:otherwise>
                    <span class="badge bg-secondary">${contract.status != null ? contract.status : 'N/A'}</span>
                  </c:otherwise>
                </c:choose>
              </div>
              <div id="statusEditForm" class="edit-form" style="display: none;">
                <form method="post" action="<c:url value='/viewContracts'/>" accept-charset="UTF-8">
                  <input type="hidden" name="action" value="updateStatus" />
                  <input type="hidden" name="contractId" value="${contract.contractId}" />
                  <select id="statusSelect" name="status">
                    <option value="Pending" ${contract.status == 'Pending' ? 'selected' : ''}>Pending</option>
                    <option value="Approved" ${contract.status == 'Approved' ? 'selected' : ''}>Approved</option>
                    <option value="Active" ${contract.status == 'Active' ? 'selected' : ''}>Active</option>
                    <option value="Expired" ${contract.status == 'Expired' ? 'selected' : ''}>Expired</option>
                    <option value="Archived" ${contract.status == 'Archived' ? 'selected' : ''}>Archived</option>
                    <option value="Cancelled" ${contract.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
                  </select>
                  <div class="button-group">
                    <button type="submit" id="saveStatusBtn" class="btn-save">Save</button>
                    <button type="button" id="cancelStatusBtn" class="btn-cancel-edit">Cancel</button>
                  </div>
                </form>
              </div>
            </div>

            <!-- Note Section -->
            <div class="detail-item-full">
              <label class="detail-label">
                Note:
                <c:if test="${(sessionScope.account.role == 1 || sessionScope.account.role == 2) && sessionScope.account.userId != contract.userId}">
                  <button type="button" id="editNoteBtn" class="btn-edit" 
                          data-role="${sessionScope.account.role}"
                          data-user-id="${sessionScope.account.userId}"
                          data-contract-user-id="${contract.userId}">
                    Edit
                  </button>
                </c:if>
              </label>
              <div id="noteValue" class="detail-value note-content">
                ${contract.note}
              </div>
              <div id="noteEditForm" class="edit-form" style="display: none;">
                <form method="post" action="<c:url value='/viewContracts'/>" accept-charset="UTF-8">
                  <input type="hidden" name="action" value="updateNote" />
                  <input type="hidden" name="contractId" value="${contract.contractId}" />
                  <textarea id="noteTextarea" name="note" rows="4" placeholder="Enter note...">${contract.note}</textarea>
                  <div class="button-group">
                    <button type="submit" id="saveNoteBtn" class="btn-save">Save</button>
                    <button type="button" id="cancelNoteBtn" class="btn-cancel-edit">Cancel</button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </c:if>

        <!-- Action Buttons -->
        <div class="button-row">
          <a href="<c:url value='/myContracts'/>" class="btn-secondary">Back</a>
          <c:if test="${sessionScope.account.role == 1 || sessionScope.account.role == 2}">
            <a href="<c:url value='/viewContracts'/>" id="backToListLink" class="btn-primary" data-role="${sessionScope.account.role}">
              Back to Contract List
            </a>
          </c:if>
        </div>
      </div>
    </div>
  </body>
</html>
