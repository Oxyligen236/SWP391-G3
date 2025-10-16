<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Create Contract page</title>
    <link rel="stylesheet" href="<c:url value='/css/update-role.css'/>" />
  </head>

  <script>
    function enableEdit(id) {
      document.getElementById("viewRole-" + id).style.display = "none";
      document.getElementById("editRole-" + id).style.display = "inline-block";
      document.getElementById("saveBtn-" + id).style.display = "inline-block";
      document.getElementById("editBtn-" + id).style.display = "none";
    }

    document.addEventListener("submit", function (e) {
      const form = e.target;
      const idField = form.querySelector("input[name='accountID']");
      if (idField) {
        const id = idField.value;
        const select = document.getElementById("editRole-" + id);
        const hidden = document.getElementById("hiddenRole-" + id);
        if (select && hidden) {
          hidden.value = select.value;
        }
      }
    });
  </script>

  <body>
    <header>
      <h1>Update account Role</h1>
      <c:if test="${not empty message}">
        <div style="color: green">${message}</div>
      </c:if>
    </header>

    <form method="get" action="searchAccountRole">
      <input
        type="text"
        id="searchValue"
        name="searchValue"
        value="${searchValue}"
        placeholder="Nhập ID hoặc tên người dùng"
      />
      <button type="submit">SEARCH</button>
    </form>

    <table>
      <thead>
        <tr>
          <th>User ID</th>
          <th>Name</th>
          <th>Current Role</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="acc" items="${accounts}">
          <tr>
            <td>${acc.accountID}</td>
            <td>${acc.fullName}</td>
            <td>
              <span id="viewRole-${acc.accountID}">${acc.roleName}</span>

              <select name="newRoleID" id="editRole-${acc.accountID}" style="display:none;">
                <c:forEach var="r" items="${roles}">
                  <option value="${r.roleID}" ${acc.roleName == r.name ? 'selected' : ''}>${r.name}</option>
                </c:forEach>
              </select>
            </td>

            <td>
              <form method="post" action="updateRole" style="margin:0;">
                <input type="hidden" name="accountID" value="${acc.accountID}" />
                <input type="hidden" name="newRoleID" id="hiddenRole-${acc.accountID}" />

                <button type="button" id="editBtn-${acc.accountID}" onclick="enableEdit('${acc.accountID}')">Edit</button>
                <button type="submit" id="saveBtn-${acc.accountID}" style="display:none;">Save</button>
              </form>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </body>
</html>
