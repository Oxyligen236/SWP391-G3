<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>View user contract</title>
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

        if (editBtn) {
          editBtn.addEventListener("click", function() {
            const userRole = parseInt(this.getAttribute("data-role"));
            
            // Chỉ cho phép HR (roleID = 2) và Recruiter (roleID = 3) được sửa
            if (userRole !== 2 && userRole !== 3) {
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

        // Xử lý nút Back to Contract List
        if (backToListLink) {
          backToListLink.addEventListener("click", function(e) {
            const userRole = parseInt(this.getAttribute("data-role"));
            
            // Chỉ cho phép HR (roleID = 2) và Recruiter (roleID = 3)
            if (userRole !== 2 && userRole !== 3) {
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
    <div class="contract-form">
      <h2>Thông tin hợp đồng</h2>

      <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
      </c:if>

      <c:if test="${not empty successMessage}">
        <div class="success-message">${successMessage}</div>
      </c:if>

      <c:if test="${not empty contract}">
        <div class="form-grid">
          <div class="form-group">
            <label>Contract ID:</label>
            <div class="value">${contract.contractId}</div>
          </div>

          <div class="form-group">
            <label>Full Name:</label>
            <div class="value">${contract.contractName}</div>
          </div>

          <div class="form-group">
            <label>User ID:</label>
            <div class="value">${contract.userId}</div>
          </div>

          <div class="form-group">
            <label>Position:</label>
            <div class="value">${contract.positionName}</div>
          </div>

          <div class="form-group">
            <label>Start Date:</label>
            <div class="value">${contract.startDate}</div>
          </div>

          <div class="form-group">
            <label>End Date:</label>
            <div class="value">${contract.endDate}</div>
          </div>

          <div class="form-group">
            <label>Sign Date:</label>
            <div class="value">${contract.signDate}</div>
          </div>

          <div class="form-group">
            <label>Duration:</label>
            <div class="value">${contract.duration} months</div>
          </div>

          <div class="form-group">
            <label>Base Salary:</label>
            <div class="value"><fmt:formatNumber value="${contract.baseSalary}" type="number" groupingUsed="true" /> VNĐ</div>
          </div>

          <div class="form-group">
            <label>Signer:</label>
            <div class="value">${contract.signerName}</div>
          </div>

          <div class="form-group">
            <label>Contract Type:</label>
            <div class="value">${contract.typeName}</div>
          </div>

          <div class="form-group full-width">
            <label>Note:
              <button type="button" id="editNoteBtn" class="edit-note-btn" data-role="${sessionScope.account.role}">
                Sửa
              </button>
            </label>
            <div id="noteValue" class="value">${contract.note}</div>
            <div id="noteEditForm" class="note-edit-form">
              <form method="post" action="<c:url value='/viewContracts'/>" accept-charset="UTF-8">
                <input type="hidden" name="action" value="updateNote" />
                <input type="hidden" name="contractId" value="${contract.contractId}" />
                <textarea id="noteTextarea" name="note" rows="4">${contract.note}</textarea>
                <div class="note-buttons">
                  <button type="submit" id="saveNoteBtn" class="save-note-btn">Lưu</button>
                  <button type="button" id="cancelNoteBtn" class="cancel-note-btn">Hủy</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </c:if>

      <div class="form-actions">
        <a href="<c:url value='/home'/>" class="home-link">← Back to Home</a>
        <c:if test="${sessionScope.account.role == 2 || sessionScope.account.role == 3}">
          <a href="<c:url value='/viewContracts'/>" id="backToListLink" class="back-link" data-role="${sessionScope.account.role}">← Back to Contract List</a>
        </c:if>
      </div>
    </div>
  </body>
</html>
