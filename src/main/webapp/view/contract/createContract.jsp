<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Create Contract</title>
    <link rel="stylesheet" href="<c:url value='/css/contract-form.css'/>" />
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
    <div class="contract-container">
      <h1>Create New Contract</h1>
      
      <c:if test="${not empty error}">
        <div class="error-msg">${error}</div>
      </c:if>

      <div class="contract-form">
        <form action="<c:url value='/viewContracts' />" method="post">
          <input type="hidden" name="action" value="insert" />
          
          <div class="form-row">
            <!-- User ID & Find Button -->
            <div class="field-group">
              <label for="userID">Mã nhân viên <span class="required">*</span></label>
              <div class="search-group">
                <input type="text" id="userID" name="userID" required placeholder="Nhập mã nhân viên" />
                <button type="button" id="findUserBtn" class="btn-search">Tìm</button>
              </div>
              <div id="userError" class="invalid-feedback"></div>
            </div>

            <!-- Full Name (readonly) -->
            <div class="field-group">
              <label for="name">Họ và tên</label>
              <input type="text" id="name" name="name" required readonly placeholder="Tự động điền sau khi tìm" />
            </div>

            <!-- Position -->
            <div class="field-group">
              <label for="positionID">Vị trí <span class="required">*</span></label>
              <select name="positionID" id="positionID" required>
                <option value="">-- Chọn vị trí --</option>
                <c:forEach var="p" items="${positions}">
                  <option value="${p.positionId}">${p.name}</option>
                </c:forEach>
              </select>
            </div>

            <!-- Contract Type -->
            <div class="field-group">
              <label for="typeID">Loại hợp đồng <span class="required">*</span></label>
              <select name="typeID" id="typeID" required>
                <option value="">-- Chọn loại hợp đồng --</option>
                <c:forEach var="t" items="${types}">
                  <option value="${t.typeID}">${t.typeName}</option>
                </c:forEach>
              </select>
            </div>

            <!-- Sign Date -->
            <div class="field-group">
              <label for="signDate">Ngày ký <span class="required">*</span></label>
              <input type="date" id="signDate" name="signDate" required />
              <div id="signDateError" class="invalid-feedback"></div>
            </div>

            <!-- Start Date -->
            <div class="field-group">
              <label for="startDate">Ngày bắt đầu <span class="required">*</span></label>
              <input type="date" id="startDate" name="startDate" required />
              <div id="startDateError" class="invalid-feedback"></div>
            </div>

            <!-- End Date -->
            <div class="field-group">
              <label for="endDate">Ngày kết thúc</label>
              <input type="date" id="endDate" name="endDate" />
              <div id="endDateError" class="invalid-feedback"></div>
            </div>

            <!-- Duration -->
            <div class="field-group">
              <label for="duration">Hạn hợp đồng (tháng) <span class="required">*</span></label>
              <input type="number" id="duration" name="duration" min="1" required placeholder="Số tháng" />
            </div>

            <!-- Base Salary -->
            <div class="field-group">
              <label for="baseSalary">Lương cơ bản (VNĐ) <span class="required">*</span></label>
              <input type="number" step="0.01" id="baseSalary" name="baseSalary" min="0" required placeholder="Nhập lương cơ bản" />
            </div>

            <!-- Signer -->
            <div class="field-group">
              <label for="signerID">Người đại diện ký <span class="required">*</span></label>
              <select name="signerID" id="signerID" required>
                <option value="">-- Chọn người ký --</option>
                <c:forEach var="s" items="${signers}">
                  <option value="${s.userId}">${s.fullname}</option>
                </c:forEach>
              </select>
            </div>
          </div>

          <!-- Note (Full Width) -->
          <div class="field-group-full">
            <label for="note">Ghi chú</label>
            <textarea id="note" name="note" rows="4" placeholder="Nhập ghi chú (nếu có)"></textarea>
          </div>

          <!-- Submit Buttons -->
          <div class="button-row">
            <a href="<c:url value='/home'/>" class="btn-cancel">Cancel</a>
            <button type="submit" class="btn-submit">Create Contract</button>
          </div>
        </form>
      </div>
    </div>
  </body>
</html>
