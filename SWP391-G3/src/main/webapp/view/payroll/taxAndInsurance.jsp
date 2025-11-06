<!-- filepath: d:\FPT\Ky_5\SWP391\Project\SWP391-G3\src\main\webapp\view\payroll\taxAndInsurance.jsp -->
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %> <%@
taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <%@ taglib
prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ page
isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Quản lý Thuế và Bảo hiểm</title>
    <link
      rel="stylesheet"
      href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css"
    />
    <style>
      .bg-gradient-primary {
        background: linear-gradient(135deg, #4273f1 0%, #6ba3ff 100%);
      }
      .text-primary-custom {
        color: #4273f1 !important;
      }
      .btn-primary-custom {
        background: linear-gradient(135deg, #4273f1 0%, #5a8fff 100%);
        border: none;
        color: white;
      }
      .btn-primary-custom:hover {
        background: linear-gradient(135deg, #3a63d1 0%, #4a7fef 100%);
      }
      .table-header-gradient {
        background: linear-gradient(135deg, #e8f0fe 0%, #f0f7ff 100%);
        font-weight: bold;
        color: #4273f1;
      }
      .deduction-badge {
        background-color: #dc3545;
        color: white;
        padding: 4px 12px;
        border-radius: 20px;
        font-size: 0.85rem;
      }
      .modal {
        z-index: 9999 !important;
      }
      .modal-backdrop {
        z-index: 9998 !important;
      }
    </style>
  </head>
  <body class="bg-light">
    <div class="container mt-5 mb-5">
      <div class="card shadow-lg border-0">
        <div
          class="card-header bg-gradient-primary text-white text-center py-3"
        >
          <h3 class="mb-0">Quản lý Chính sách Thuế và Bảo hiểm</h3>
        </div>
        <div class="card-body p-4">
          <!-- Success/Error Messages -->
          <c:if test="${not empty successMessage}">
            <div
              class="alert alert-success alert-dismissible fade show"
              role="alert"
            >
              <strong>Thành công!</strong> ${successMessage}
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="alert"
              ></button>
            </div>
            <c:remove var="successMessage" scope="session" />
          </c:if>

          <c:if test="${not empty errorMessage}">
            <div
              class="alert alert-danger alert-dismissible fade show"
              role="alert"
            >
              <strong>Lỗi!</strong> ${errorMessage}
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="alert"
              ></button>
            </div>
            <c:remove var="errorMessage" scope="session" />
          </c:if>

          <!-- Table -->
          <div class="table-responsive">
            <table class="table table-bordered text-center align-middle">
              <thead class="table-header-gradient">
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
                              ${currentItem.amount} (% Base Salary)
                            </c:when>
                            <c:otherwise>
                              <%-- Hiển thị số thập phân nếu có, ngược lại hiển
                              thị số nguyên --%>
                              <c:choose>
                                <c:when test="${currentItem.amount % 1 == 0}">
                                  <fmt:formatNumber
                                    value="${currentItem.amount}"
                                    pattern="#,##0"
                                  />
                                </c:when>
                                <c:otherwise>
                                  <fmt:formatNumber
                                    value="${currentItem.amount}"
                                    pattern="#,##0.##"
                                  />
                                </c:otherwise>
                              </c:choose>
                              (₫)
                            </c:otherwise>
                          </c:choose>
                        </c:when>
                        <c:otherwise>
                          <span class="text-muted">Chưa thiết lập</span>
                        </c:otherwise>
                      </c:choose>
                    </td>
                    <td>
                      <span class="deduction-badge">Deduction (-)</span>
                    </td>
                    <td>
                      <button
                        type="button"
                        class="btn btn-sm btn-outline-primary"
                        data-type-id="${type.payrollTypeID}"
                        data-type-name="${fn:escapeXml(type.typeName)}"
                        data-amount="${currentItem != null ? currentItem.amount : 0}"
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
          <div class="modal-header bg-gradient-primary text-white">
            <h5 class="modal-title">Chỉnh sửa</h5>
            <button
              type="button"
              class="btn-close btn-close-white"
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

              <div class="mb-3">
                <label class="form-label fw-bold text-primary-custom"
                  >Item Name</label
                >
                <input
                  type="text"
                  class="form-control"
                  id="editTypeName"
                  readonly
                />
              </div>

              <div class="mb-3">
                <label class="form-label fw-bold text-primary-custom"
                  >Amount Type</label
                >
                <select
                  class="form-select"
                  id="editAmountType"
                  name="amountType"
                  onchange="updateAmountLabel()"
                >
                  <option value="percent">Percentage (%)</option>
                  <option value="fixed">Fixed Amount (₫)</option>
                </select>
              </div>

              <div class="mb-3">
                <label class="form-label fw-bold text-primary-custom">
                  Amount <span id="amountUnit">(% Base Salary)</span>
                </label>
                <input
                  type="number"
                  class="form-control"
                  id="editAmount"
                  name="amount"
                  step="0.01"
                  min="0"
                  required
                />
              </div>
            </div>
            <div class="modal-footer">
              <button
                type="button"
                class="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                Cancel
              </button>
              <button
                type="button"
                class="btn btn-primary-custom"
                onclick="confirmSave()"
              >
                Save
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- Confirm Modal -->
    <div
      class="modal fade"
      id="confirmModal"
      tabindex="-1"
      aria-hidden="true"
      data-bs-backdrop="static"
    >
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header bg-gradient-primary text-white">
            <h5 class="modal-title">Xác nhận</h5>
            <button
              type="button"
              class="btn-close btn-close-white"
              onclick="backToEdit()"
            ></button>
          </div>
          <div class="modal-body">
            <p class="mb-0">Bạn có chắc chắn muốn lưu thay đổi này không?</p>
          </div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              onclick="backToEdit()"
            >
              No
            </button>
            <button
              type="button"
              class="btn btn-primary-custom"
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

        console.log("Edit Modal Data:", {
          typeId,
          typeName,
          amount,
          amountType,
        });

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
        const amount = document.getElementById("editAmount").value;
        if (!amount || parseFloat(amount) < 0) {
          alert("Vui lòng nhập số tiền hợp lệ!");
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
        console.log("Submitting form...");
        console.log("TypeID:", document.getElementById("editTypeId").value);
        console.log("Amount:", document.getElementById("editAmount").value);
        console.log(
          "AmountType:",
          document.getElementById("editAmountType").value
        );

        document.getElementById("editForm").submit();
      }
    </script>
  </body>
</html>
