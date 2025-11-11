<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <%@ page
isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Tax and Insurance Management</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css"
    />
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/css/taxAndInsurance.css"
    />
  </head>
  <body>
    <div class="tax-insurance-container">
      <div class="tax-insurance-card">
        <div class="tax-insurance-header">
          <h3>Tax and Insurance Policy Management</h3>
        </div>

        <div class="tax-insurance-body">
          <!-- Success Message -->
          <c:if test="${not empty successMessage}">
            <div class="alert-message alert-success">
              <strong>Success!</strong> ${successMessage}
              <button
                type="button"
                class="alert-close-btn"
                onclick="this.parentElement.remove()"
              >
                &times;
              </button>
            </div>
            <c:remove var="successMessage" scope="session" />
          </c:if>

          <!-- Error Message -->
          <c:if test="${not empty errorMessage}">
            <div class="alert-message alert-danger">
              <strong>Error!</strong> ${errorMessage}
              <button
                type="button"
                class="alert-close-btn"
                onclick="this.parentElement.remove()"
              >
                &times;
              </button>
            </div>
            <c:remove var="errorMessage" scope="session" />
          </c:if>

          <div class="table-responsive">
            <table class="tax-insurance-table">
              <thead>
                <tr>
                  <th width="30%">Item Name</th>
                  <th width="35%">Amount</th>
                  <th width="25%">Type</th>
                  <th width="10%">Action</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="type" items="${payrollTypes}">
                  <c:set var="currentItem" value="${null}" />
                  <c:forEach var="item" items="${payrollItems}">
                    <c:if test="${item.typeID == type.payrollTypeID}">
                      <c:set var="currentItem" value="${item}" />
                    </c:if>
                  </c:forEach>

                  <tr>
                    <td class="text-start fw-bold">${type.typeName}</td>
                    <td>
                      <c:choose>
                        <c:when test="${currentItem != null}">
                          <c:choose>
                            <c:when
                              test="${currentItem.amountType == 'percent'}"
                            >
                              ${currentItem.amount}% (Base Salary)
                            </c:when>
                            <c:otherwise>
                              ${currentItem.amount} vn₫
                            </c:otherwise>
                          </c:choose>
                        </c:when>
                        <c:otherwise>
                          <span class="text-muted">Not set</span>
                        </c:otherwise>
                      </c:choose>
                    </td>
                    <td>
                      <span class="deduction-badge">Deduction (-)</span>
                    </td>
                    <td>
                      <button
                        type="button"
                        class="btn-edit"
                        data-type-id="${type.payrollTypeID}"
                        data-type-name="${fn:escapeXml(type.typeName)}"
                        data-amount="${currentItem != null ? currentItem.amount : ''}"
                        data-amount-type="${currentItem != null ? currentItem.amountType : 'fixed'}"
                        onclick="openEditModal(this)"
                      >
                        Edit
                      </button>
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>

    <!-- Edit Modal -->
    <div
      class="modal fade"
      id="editModal"
      tabindex="-1"
      aria-hidden="true"
      data-bs-backdrop="static"
    >
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Edit Item</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
            ></button>
          </div>
          <form
            id="editForm"
            method="post"
            action="${pageContext.request.contextPath}/tax-and-insurance"
          >
            <div class="modal-body">
              <input type="hidden" id="editTypeId" name="typeId" />

              <div class="form-group">
                <label class="form-label">Item Name</label>
                <input
                  type="text"
                  class="form-control"
                  id="editTypeName"
                  readonly
                />
              </div>

              <div class="form-group">
                <label class="form-label">Amount Type</label>
                <select
                  class="form-select"
                  id="editAmountType"
                  name="amountType"
                  onchange="updateAmountLabel()"
                >
                  <option value="percent">Percentage (%)</option>
                  <option value="fixed">Fixed Amount (vn₫)</option>
                </select>
              </div>

              <div class="form-group">
                <label class="form-label">
                  Amount
                  <span id="amountUnit" class="amount-unit"
                    >(% Base Salary)</span
                  >
                </label>

                <input
                  type="text"
                  class="form-control"
                  id="editAmount"
                  name="amount"
                  required
                  placeholder="Enter amount"
                />
              </div>
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn-secondary"
                data-bs-dismiss="modal"
              >
                Cancel
              </button>
              <button
                type="button"
                class="btn-primary-custom"
                onclick="confirmSave()"
              >
                Save
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <div
      class="modal fade"
      id="confirmModal"
      tabindex="-1"
      aria-hidden="true"
      data-bs-backdrop="static"
    >
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Confirm</h5>
            <button
              type="button"
              class="btn-close"
              onclick="backToEdit()"
            ></button>
          </div>
          <div class="modal-body">
            <p class="mb-0">Are you sure you want to save this change?</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn-secondary" onclick="backToEdit()">
              No
            </button>
            <button
              type="button"
              class="btn-primary-custom"
              onclick="submitForm()"
            >
              Yes
            </button>
          </div>
        </div>
      </div>
    </div>

    <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
    <script>
      let editModal;
      let confirmModal;

      document.addEventListener("DOMContentLoaded", function () {
        editModal = new bootstrap.Modal(document.getElementById("editModal"));
        confirmModal = new bootstrap.Modal(
          document.getElementById("confirmModal")
        );
      });

      function openEditModal(button) {
        const typeId = button.getAttribute("data-type-id");
        const typeName = button.getAttribute("data-type-name");
        const amount = button.getAttribute("data-amount");
        const amountType = button.getAttribute("data-amount-type");

        document.getElementById("editTypeId").value = typeId;
        document.getElementById("editTypeName").value = typeName;

        document.getElementById("editAmount").value = amount;
        document.getElementById("editAmountType").value = amountType;
        updateAmountLabel();
        editModal.show();
      }

      function updateAmountLabel() {
        const amountType = document.getElementById("editAmountType").value;
        const amountUnit = document.getElementById("amountUnit");
        if (amountType === "percent") {
          amountUnit.textContent = "(% Base Salary)";
        } else {
          amountUnit.textContent = "(₫)";
        }
      }

      function confirmSave() {
        const amountInput = document.getElementById("editAmount").value.trim();

        const amount = parseFloat(amountInput);

        if (isNaN(amount) || amount <= 0) {
          alert("Amount must be a valid number greater than 0!");
          return;
        }

        editModal.hide();
        setTimeout(() => {
          confirmModal.show();
        }, 300);
      }

      function backToEdit() {
        confirmModal.hide();
        setTimeout(() => {
          editModal.show();
        }, 300);
      }

      function submitForm() {
        document.getElementById("editForm").submit();
      }
    </script>
  </body>
</html>
