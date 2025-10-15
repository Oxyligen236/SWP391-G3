<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>View user contract</title>
    <link rel="stylesheet" href="<c:url value='/css/view-contract.css'/>" />
  </head>
  <body>
    <div class="contract-form">
      <h2>Thông tin hợp đồng</h2>

      <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
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
            <div class="value">${contract.baseSalary}</div>
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
            <label>Note:</label>
            <div class="value">${contract.note}</div>
          </div>
        </div>
      </c:if>

      <div class="form-actions">
        <a href="<c:url value='/viewContracts'/>" class="back-link">← Back to Contract List</a>
      </div>
    </div>
  </body>
</html>
