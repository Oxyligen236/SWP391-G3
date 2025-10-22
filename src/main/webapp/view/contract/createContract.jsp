<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Create Contract</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" />
    <script defer>
      document.addEventListener("DOMContentLoaded", function () {
        const findBtn = document.getElementById("findUserBtn");
        const userIdInput = document.getElementById("userID");
        const nameInput = document.getElementById("name");
        const userError = document.getElementById("userError");
        
        const form = document.querySelector("form");
        const signDateInput = document.getElementById("signDate");
        const startDateInput = document.getElementById("startDate");
        const endDateInput = document.getElementById("endDate");
        const signDateError = document.getElementById("signDateError");
        const startDateError = document.getElementById("startDateError");
        const endDateError = document.getElementById("endDateError");

        findBtn.addEventListener("click", function (e) {
          e.preventDefault();
          userError.textContent = "";
          const userID = userIdInput.value.trim();
          if (!userID) {
            userError.textContent = "Vui lòng nhập mã nhân viên";
            return;
          }
          const url = '<c:url value="/getUser"/>' + "?userID=" + encodeURIComponent(userID);
          fetch(url, { method: "GET", headers: { Accept: "application/json" } })
            .then((resp) => {
              if (!resp.ok) throw new Error("Network response was not ok");
              return resp.json();
            })
            .then((data) => {
              if (data.found) {
                nameInput.value = data.name || "";
                userError.textContent = "";
              } else {
                nameInput.value = "";
                userError.textContent = data.message || "Không tìm thấy mã nhân viên";
              }
            })
            .catch((err) => {
              userError.textContent = "Lỗi khi tìm: " + err.message;
            });
        });

        form.addEventListener("submit", function (e) {
          signDateError.textContent = "";
          startDateError.textContent = "";
          endDateError.textContent = "";

          let hasError = false;

          const signDate = new Date(signDateInput.value);
          const startDate = new Date(startDateInput.value);
          const endDate = endDateInput.value ? new Date(endDateInput.value) : null;

          if (signDate >= startDate) {
            signDateError.textContent = "Ngày ký phải trước ngày bắt đầu";
            hasError = true;
          }

          if (endDate && endDate <= startDate) {
            endDateError.textContent = "Ngày kết thúc phải sau ngày bắt đầu";
            hasError = true;
          }

          if (hasError) {
            e.preventDefault();
          }
        });

        signDateInput.addEventListener("change", function() {
          signDateError.textContent = "";
        });

        startDateInput.addEventListener("change", function() {
          startDateError.textContent = "";
          signDateError.textContent = "";
        });

        endDateInput.addEventListener("change", function() {
          endDateError.textContent = "";
        });
      });
    </script>
  </head>
  <body class="bg-light">
    <div class="container mt-4 mb-5">
      <div class="card shadow-sm">
        <div class="card-header bg-primary text-white">
          <h4 class="mb-0">
            <i class="bi bi-file-earmark-plus"></i> Create New Contract
          </h4>
        </div>
        <div class="card-body">
          <c:if test="${not empty error}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <i class="bi bi-exclamation-triangle-fill"></i> ${error}
              <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
          </c:if>

          <form action="<c:url value='/viewContracts' />" method="post">
            <input type="hidden" name="action" value="insert" />
            
            <div class="row g-3">
              <!-- User ID & Find Button -->
              <div class="col-md-6">
                <label for="userID" class="form-label fw-semibold">
                  <i class="bi bi-person-badge"></i> Mã nhân viên <span class="text-danger">*</span>
                </label>
                <div class="input-group">
                  <input type="text" class="form-control" id="userID" name="userID" required placeholder="Nhập mã nhân viên" />
                  <button class="btn btn-primary" type="button" id="findUserBtn">
                    <i class="bi bi-search"></i> Tìm
                  </button>
                </div>
                <div id="userError" class="text-danger small mt-1"></div>
              </div>

              <!-- Full Name (readonly) -->
              <div class="col-md-6">
                <label for="name" class="form-label fw-semibold">
                  <i class="bi bi-person"></i> Họ và tên
                </label>
                <input type="text" class="form-control" id="name" name="name" required readonly 
                       style="background-color: #e9ecef;" placeholder="Tự động điền sau khi tìm" />
              </div>

              <!-- Position -->
              <div class="col-md-6">
                <label for="positionID" class="form-label fw-semibold">
                  <i class="bi bi-briefcase"></i> Vị trí <span class="text-danger">*</span>
                </label>
                <select class="form-select" name="positionID" id="positionID" required>
                  <option value="">-- Chọn vị trí --</option>
                  <c:forEach var="p" items="${positions}">
                    <option value="${p.positionId}">${p.name}</option>
                  </c:forEach>
                </select>
              </div>

              <!-- Contract Type -->
              <div class="col-md-6">
                <label for="typeID" class="form-label fw-semibold">
                  <i class="bi bi-file-text"></i> Loại hợp đồng <span class="text-danger">*</span>
                </label>
                <select class="form-select" name="typeID" id="typeID" required>
                  <option value="">-- Chọn loại hợp đồng --</option>
                  <c:forEach var="t" items="${types}">
                    <option value="${t.typeID}">${t.typeName}</option>
                  </c:forEach>
                </select>
              </div>

              <!-- Sign Date -->
              <div class="col-md-4">
                <label for="signDate" class="form-label fw-semibold">
                  <i class="bi bi-calendar-check"></i> Ngày ký <span class="text-danger">*</span>
                </label>
                <input type="date" class="form-control" id="signDate" name="signDate" required />
                <div id="signDateError" class="text-danger small mt-1"></div>
              </div>

              <!-- Start Date -->
              <div class="col-md-4">
                <label for="startDate" class="form-label fw-semibold">
                  <i class="bi bi-calendar-event"></i> Ngày bắt đầu <span class="text-danger">*</span>
                </label>
                <input type="date" class="form-control" id="startDate" name="startDate" required />
                <div id="startDateError" class="text-danger small mt-1"></div>
              </div>

              <!-- End Date -->
              <div class="col-md-4">
                <label for="endDate" class="form-label fw-semibold">
                  <i class="bi bi-calendar-x"></i> Ngày kết thúc
                </label>
                <input type="date" class="form-control" id="endDate" name="endDate" />
                <div id="endDateError" class="text-danger small mt-1"></div>
              </div>

              <!-- Duration -->
              <div class="col-md-6">
                <label for="duration" class="form-label fw-semibold">
                  <i class="bi bi-clock-history"></i> Hạn hợp đồng (tháng) <span class="text-danger">*</span>
                </label>
                <input type="number" class="form-control" id="duration" name="duration" min="1" required placeholder="Số tháng" />
              </div>

              <!-- Base Salary -->
              <div class="col-md-6">
                <label for="baseSalary" class="form-label fw-semibold">
                  <i class="bi bi-currency-dollar"></i> Lương cơ bản (VNĐ) <span class="text-danger">*</span>
                </label>
                <input type="number" step="0.01" class="form-control" id="baseSalary" name="baseSalary" 
                       min="0" required placeholder="Nhập lương cơ bản" />
              </div>

              <!-- Signer -->
              <div class="col-md-12">
                <label for="signerID" class="form-label fw-semibold">
                  <i class="bi bi-pen"></i> Người đại diện ký <span class="text-danger">*</span>
                </label>
                <select class="form-select" name="signerID" id="signerID" required>
                  <option value="">-- Chọn người ký --</option>
                  <c:forEach var="s" items="${signers}">
                    <option value="${s.userId}">${s.fullname}</option>
                  </c:forEach>
                </select>
              </div>

              <!-- Note -->
              <div class="col-md-12">
                <label for="note" class="form-label fw-semibold">
                  <i class="bi bi-sticky"></i> Ghi chú
                </label>
                <textarea class="form-control" id="note" name="note" rows="3" placeholder="Nhập ghi chú (nếu có)"></textarea>
              </div>

              <!-- Submit Button -->
              <div class="col-12">
                <hr class="my-3">
                <div class="d-flex gap-2 justify-content-end">
                  <a href="<c:url value='/home'/>" class="btn btn-secondary">
                    <i class="bi bi-x-circle"></i> Cancel
                  </a>
                  <button type="submit" class="btn btn-primary px-4">
                    <i class="bi bi-save"></i> Create Contract
                  </button>
                </div>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>

    <script src="${pageContext.request.contextPath}/webjars/bootstrap/5.3.3/js/bootstrap.bundle.min.js"></script>
  </body>
</html>
