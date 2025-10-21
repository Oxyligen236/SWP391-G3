<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Create Contract page</title>
    <link rel="stylesheet" href="<c:url value='/css/contract.css'/>" />
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
          const url =
            '<c:url value="/getUser"/>' +
            "?userID=" +
            encodeURIComponent(userID);
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
                userError.textContent =
                  data.message || "Không tìm thấy mã nhân viên";
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
  <body>
    <div class="contract-form">
      <h2>Thông tin hợp đồng</h2>
      <c:if test="${not empty error}">
        <div style="color: red; text-align: center">${error}</div>
      </c:if>
      <form action="<c:url value='/viewContracts' />" method="post">
        <input type="hidden" name="action" value="insert" />
        <div class="form-group">
          <label for="name">Họ và tên:</label>
          <input type="text" id="name" name="name" required readonly style="background-color: #f0f0f0; cursor: not-allowed;" />
        </div>

        <div class="form-group">
          <label for="userID">Mã nhân viên:</label>
          <div style="display: flex; gap: 8px; align-items: center">
            <input type="text" id="userID" name="userID" required />
            <button id="findUserBtn">Tìm</button>
          </div>
          <div
            id="userError"
            style="color: red; font-size: 0.9em; margin-top: 4px"
          ></div>
        </div>
        <div class="form-group">
          <label for="positionID">Vị trí:</label>
          <select name="positionID" id="positionID" required>
            <c:forEach var="p" items="${positions}">
              <option value="${p.positionId}">${p.name}</option>
            </c:forEach>
          </select>
        </div>
        <div class="form-group">
          <label for="signDate">Ngày ký:</label>
          <input type="date" id="signDate" name="signDate" required />
          <div id="signDateError" style="color: red; font-size: 0.9em; margin-top: 4px"></div>
        </div>

        <div class="form-group">
          <label for="startDate">Ngày bắt đầu:</label>
          <input type="date" id="startDate" name="startDate" required />
          <div id="startDateError" style="color: red; font-size: 0.9em; margin-top: 4px"></div>
        </div>

        <div class="form-group">
          <label for="endDate">Ngày kết thúc:</label>
          <input type="date" id="endDate" name="endDate" />
          <div id="endDateError" style="color: red; font-size: 0.9em; margin-top: 4px"></div>
        </div>

        <div class="form-group">
          <label for="duration">Hạn hợp đồng:</label>
          <input type="number" id="duration" name="duration" min="1" required />
        </div>

        <div class="form-group">
          <label for="baseSalary">Lương cơ bản:</label>
          <input
            type="number"
            step="0.01"
            id="baseSalary"
            name="baseSalary"
            min="0"
            required
          />
        </div>
        <div class="form-group">
          <label for="signerID">Người đại diện ký:</label>
          <select name="signerID" id="signerID" required>
            <c:forEach var="s" items="${signers}">
              <option value="${s.userId}">${s.fullname}</option>
            </c:forEach>
          </select>
        </div>

        <div class="form-group">
          <label for="typeID">Loại hợp đồng:</label>
          <select name="typeID" id="typeID" required>
            <c:forEach var="t" items="${types}">
              <option value="${t.typeID}">${t.typeName}</option>
            </c:forEach>
          </select>
          <!-- <select id="type" name="type" required>
            <option value="">--Chọn loại hợp đồng--</option>
            <option value="thuviec">Thử việc</option>
            <option value="chinhthuc">Chính thức</option>
            <option value="thoivu">Thời vụ</option>
          </select> -->
        </div>

        <div class="form-group">
          <label for="note">Ghi chú:</label>
          <textarea id="note" name="note" rows="3"></textarea>
        </div>
        <button type="submit">Lưu</button>
      </form>
    </div>
  </body>
</html>
