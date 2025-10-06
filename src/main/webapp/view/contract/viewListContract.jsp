<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>View user contract list</title>
    <link rel="stylesheet" href="">
    <script src="" defer></script>
  </head>
  <body>
    <h2>Contract List</h2>
    <a href="contracts?action=add">+ Add Contract</a>
    <table border="1">
       <tr>
          <th>ID</th>
          <th>UserID</th>
          <th>Start Date</th>
          <th>End Date</th>
          <th>Sign Date</th>
          <th>Duration</th>
          <th>Salary</th>
          <th>Note</th>
          <th>Type</th>
       </tr>
       <c:forEach var="contract" items="${contracts}">
          <tr>
              <td>${contract.contractID}</td>
              <td>${contract.userID}</td>
              <td>${contract.startDate}</td>
              <td>${contract.endDate}</td>
              <td>${contract.signDate}</td>
              <td>${contract.duration}</td>
              <td>${contract.salary}</td>
              <td>${contract.note}</td>
              <td>${contract.typeName}</td>
          </tr>
       </c:forEach>
    </table>
  </body>
</html>

