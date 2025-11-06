<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Create Contract</title>
    <link rel="stylesheet" href="<c:url value='/css/contract-form.css'/>" />
    <!-- Flatpickr CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <!-- Flatpickr JS -->
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
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
        const durationInput = document.getElementById("duration");
        const signDateError = document.getElementById("signDateError");
        const startDateError = document.getElementById("startDateError");
        const endDateError = document.getElementById("endDateError");

        const today = new Date();
        today.setHours(0, 0, 0, 0);

        // Initialize Flatpickr for date inputs
        let signDatePicker = flatpickr(signDateInput, {
          dateFormat: "d/m/Y",
          minDate: "today",
          onChange: function(selectedDates, dateStr, instance) {
            if (selectedDates.length > 0) {
              document.getElementById("signDateISO").value = selectedDates[0].toISOString().split('T')[0];
              signDateError.textContent = "";
              
              // Update startDate minDate
              if (startDatePicker) {
                startDatePicker.set('minDate', selectedDates[0]);
              }
            }
          }
        });

        let startDatePicker = flatpickr(startDateInput, {
          dateFormat: "d/m/Y",
          minDate: "today",
          onChange: function(selectedDates, dateStr, instance) {
            if (selectedDates.length > 0) {
              document.getElementById("startDateISO").value = selectedDates[0].toISOString().split('T')[0];
              startDateError.textContent = "";
              signDateError.textContent = "";
              
              // Update endDate constraints
              const startDate = selectedDates[0];
              const minEndDate = new Date(startDate);
              minEndDate.setDate(minEndDate.getDate() + 1);
              
              const maxEndDate = new Date(startDate);
              maxEndDate.setMonth(maxEndDate.getMonth() + 36);
              
              if (endDatePicker) {
                endDatePicker.set('minDate', minEndDate);
                endDatePicker.set('maxDate', maxEndDate);
              }
              
              calculateDuration();
            }
          }
        });

        let endDatePicker = flatpickr(endDateInput, {
          dateFormat: "d/m/Y",
          onChange: function(selectedDates, dateStr, instance) {
            if (selectedDates.length > 0) {
              document.getElementById("endDateISO").value = selectedDates[0].toISOString().split('T')[0];
              endDateError.textContent = "";
              durationInput.disabled = false;
              calculateDuration();
            } else {
              document.getElementById("endDateISO").value = "";
              durationInput.disabled = true;
              durationInput.value = "";
              durationInput.placeholder = "Indefinite contract";
            }
          },
          onClose: function(selectedDates, dateStr, instance) {
            if (!dateStr) {
              document.getElementById("endDateISO").value = "";
              durationInput.disabled = true;
              durationInput.value = "";
              durationInput.placeholder = "Indefinite contract";
            }
          }
        });

        // Disable duration field initially (until endDate is filled)
        durationInput.disabled = true;
        durationInput.placeholder = "Enter endDate";

        // Function to calculate duration in months
        function calculateDuration() {
          const startDateISO = document.getElementById("startDateISO").value;
          const endDateISO = document.getElementById("endDateISO").value;
          
          if (startDateISO && endDateISO) {
            const startDate = new Date(startDateISO);
            const endDate = new Date(endDateISO);
            
            // Calculate months difference
            const months = (endDate.getFullYear() - startDate.getFullYear()) * 12 + 
                          (endDate.getMonth() - startDate.getMonth());
            
            // If there are remaining days, add 1 month
            if (endDate.getDate() > startDate.getDate()) {
              durationInput.value = months + 1;
            } else {
              durationInput.value = months;
            }
          }
        }

        findBtn.addEventListener("click", function (e) {
          e.preventDefault();
          userError.textContent = "";
          const userID = userIdInput.value.trim();
          if (!userID) {
            userError.textContent = "Enter User ID";
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
                userError.textContent = data.message || "Can not find user";
              }
            })
            .catch((err) => {
              userError.textContent = "Error occurred: " + err.message;
            });
        });

        form.addEventListener("submit", function (e) {
          signDateError.textContent = "";
          startDateError.textContent = "";
          endDateError.textContent = "";

          let hasError = false;

          const todayDate = new Date();
          todayDate.setHours(0, 0, 0, 0);
          
          const signDateISO = document.getElementById("signDateISO").value;
          const startDateISO = document.getElementById("startDateISO").value;
          const endDateISO = document.getElementById("endDateISO").value;

          // Check if signer is the same as the employee
          const selectedUserID = userIdInput.value.trim();
          const selectedSignerID = document.getElementById("signerID").value;
          
          if (selectedUserID && selectedSignerID && selectedUserID === selectedSignerID) {
            alert("Error: The signer cannot be the same person as the employee receiving the contract!");
            hasError = true;
          }

          if (!signDateISO) {
            signDateError.textContent = "Sign date is required";
            hasError = true;
          }
          
          if (!startDateISO) {
            startDateError.textContent = "Start date is required";
            hasError = true;
          }

          if (signDateISO && startDateISO && endDateISO) {
            const signDate = new Date(signDateISO);
            const startDate = new Date(startDateISO);
            const endDate = new Date(endDateISO);

            // Check if signDate is before today
            if (signDate < todayDate) {
              signDateError.textContent = "Sign date cannot be before today";
              hasError = true;
            }

            // Check if startDate is before today
            if (startDate < todayDate) {
              startDateError.textContent = "Start date cannot be before today";
              hasError = true;
            }

            // Check if signDate is after startDate
            if (signDate > startDate) {
              signDateError.textContent = "Sign date cannot be after start date";
              hasError = true;
            }

            // Check if endDate is before or equal to startDate
            if (endDate <= startDate) {
              endDateError.textContent = "End date must be after start date";
              hasError = true;
            }

            // Check if endDate is before or equal to signDate
            if (endDate <= signDate) {
              endDateError.textContent = "End date must be after sign date";
              hasError = true;
            }

            // Check if endDate is more than 36 months after startDate
            const maxEndDate = new Date(startDate);
            maxEndDate.setMonth(maxEndDate.getMonth() + 36);
            if (endDate > maxEndDate) {
              endDateError.textContent = "Contract duration cannot exceed 36 months";
              hasError = true;
            }
          }

          if (hasError) {
            e.preventDefault();
          }
        });

        // Add real-time validation when signer is selected
        const signerSelect = document.getElementById("signerID");
        signerSelect.addEventListener("change", function() {
          const selectedUserID = userIdInput.value.trim();
          const selectedSignerID = signerSelect.value;
          
          if (selectedUserID && selectedSignerID && selectedUserID === selectedSignerID) {
            alert("Warning: The signer cannot be the same person as the employee receiving the contract!");
            signerSelect.value = ""; // Clear the selection
          }
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
              <label for="userID">User ID <span class="required">*</span></label>
              <div class="search-group">
                <input type="text" id="userID" name="userID" required placeholder="Enter User ID" />
                <button type="button" id="findUserBtn" class="btn-search">Search</button>
              </div>
              <div id="userError" class="invalid-feedback"></div>
            </div>

            <!-- Full Name (readonly) -->
            <div class="field-group">
              <label for="name">Full Name</label>
              <input type="text" id="name" name="name" required readonly placeholder="Auto-filled after search" />
            </div>

            <!-- Position -->
            <div class="field-group">
              <label for="positionID">Position <span class="required">*</span></label>
              <select name="positionID" id="positionID" required>
                <option value="">-- Select Position --</option>
                <c:forEach var="p" items="${positions}">
                  <option value="${p.positionId}">${p.name}</option>
                </c:forEach>
              </select>
            </div>

            <!-- Contract Type -->
            <div class="field-group">
              <label for="typeID">Contract Type <span class="required">*</span></label>
              <select name="typeID" id="typeID" required>
                <option value="">-- Select Contract Type --</option>
                <c:forEach var="t" items="${types}">
                  <option value="${t.typeID}">${t.typeName}</option>
                </c:forEach>
              </select>
            </div>

            <!-- Sign Date -->
            <div class="field-group">
              <label for="signDate">Sign Date <span class="required">*</span></label>
              <input type="text" id="signDate" name="signDate" required placeholder="dd/mm/yyyy" />
              <input type="hidden" id="signDateISO" name="signDateISO" />
              <div id="signDateError" class="invalid-feedback"></div>
            </div>

            <!-- Start Date -->
            <div class="field-group">
              <label for="startDate">Start Date <span class="required">*</span></label>
              <input type="text" id="startDate" name="startDate" required placeholder="dd/mm/yyyy" />
              <input type="hidden" id="startDateISO" name="startDateISO" />
              <div id="startDateError" class="invalid-feedback"></div>
            </div>

            <!-- End Date -->
            <div class="field-group">
              <label for="endDate">End Date</label>
              <input type="text" id="endDate" name="endDate" placeholder="dd/mm/yyyy" />
              <input type="hidden" id="endDateISO" name="endDateISO" />
              <div id="endDateError" class="invalid-feedback"></div>
            </div>

            <!-- Duration -->
            <div class="field-group">
              <label for="duration">Contract Duration (Months)</label>
              <input type="number" id="duration" name="duration" min="1" placeholder="Number of Months" readonly />
            </div>

            <!-- Base Salary -->
            <div class="field-group">
              <label for="baseSalary">Base Salary (VNĐ) <span class="required">*</span></label>
              <input type="number" step="0.01" id="baseSalary" name="baseSalary" min="0" required placeholder="Enter Base Salary" />
            </div>

            <!-- Signer -->
            <div class="field-group">
              <label for="signerID">Signer <span class="required">*</span></label>
              <select name="signerID" id="signerID" required>
                <option value="">-- Select Signer --</option>
                <c:forEach var="s" items="${signers}">
                  <%-- Only show users with role 1 (HR Manager) or 2 (HR Staff) --%>
                  <c:set var="userRole" value="${userRoles[s.userId]}" />
                  <c:if test="${userRole == 1 || userRole == 2 || userRole == 3}">
                    <option value="${s.userId}">${s.fullname}</option>
                  </c:if>
                </c:forEach>
              </select>
            </div>
          </div>

          <!-- Note (Full Width) -->
          <div class="field-group-full">
            <label for="note">Note</label>
            <textarea id="note" name="note" rows="4" placeholder="Enter note (if any)"></textarea>
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
