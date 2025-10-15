<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>View user contract</title>
    <link rel="stylesheet" href="<c:url value='/css/view-contract.css'/>" />
    <script src="" defer></script>
  </head>
  <body>
    <div class="contract-form">
    <h2>Contract Detail</h2>
    <c:if test="${not empty error}">
        <div style="color: red; text-align: center">${error}</div>
    </c:if>
    <c:if test="${not empty contract}">
        <p><strong>Contract ID:</strong> ${contract.contractId}</p>
        <p><strong>Full Name:</strong> ${contract.contractName}
        <strong>User ID:</strong> ${contract.userId}</p>
        <p><strong>Start Date:</strong> ${contract.startDate}
        <strong>End Date:</strong> ${contract.endDate}
        <strong>Sign Date:</strong> ${contract.signDate}</p>
        <p><strong>Duration:</strong> ${contract.duration} months
        <strong>Base Salary:</strong> ${contract.baseSalary}</p>
        <p><strong>Contract Type:</strong> ${contract.typeName}</p>
        <p><strong>Note:</strong> ${contract.note}</p>
    </c:if>
    <a href="<c:url value='/viewContracts'/>" class="back-link">Back to Contract List</a>
    </div>
  </body>
</html>