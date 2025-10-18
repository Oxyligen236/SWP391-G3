<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Change User department</title>
    <link rel="stylesheet" href="<c:url value='/css/update-department.css'/>" />
  </head>

  <script>
        function enableEdit(id) {
            document.getElementById("viewDept-" + id).style.display = "none";
            document.getElementById("editDept-" + id).style.display = "inline-block";
            document.getElementById("saveBtn-" + id).style.display = "inline-block";
            document.getElementById("cancelBtn-" + id).style.display = "inline-block";
            document.getElementById("editBtn-" + id).style.display = "none";
        }

        function cancelEdit(id) {
            document.getElementById("viewDept-" + id).style.display = "inline";
            document.getElementById("editDept-" + id).style.display = "none";
            document.getElementById("saveBtn-" + id).style.display = "none";
            document.getElementById("cancelBtn-" + id).style.display = "none";
            document.getElementById("editBtn-" + id).style.display = "inline-block";
        }

        document.addEventListener("submit", function(e) {
            const form = e.target;
            const idField = form.querySelector("input[name='userID']");
            if(idField) {
                const id = idField.value;
                const select = document.getElementById("editDept-" + id);
                const hidden = document.getElementById("hiddenDept-" + id);
                if(select && hidden) {
                    hidden.value = select.value;
                }
            }
        });
    </script>

    
  <body>
    <header>
      <h1>Update User Department</h1>
      <c:if test="${not empty message}">
        <div id="messageBox" style="padding: 10px; margin: 10px 0; background: #d4edda; color: #155724; border: 1px solid #c3e6cb; border-radius: 5px;">
          ${message}
        </div>
        <script>
          setTimeout(function() {
            var messageBox = document.getElementById('messageBox');
            if(messageBox) {
              messageBox.style.display = 'none';
            }
          }, 3000);
        </script>
      </c:if>
    </header>
    <div class="search-container">
    <form method="get" action="updateDepartment">
      <input
        type="text"
        id="searchValue"
        name="searchValue"
        value="${searchValue}"
        placeholder="Nhập ID hoặc tên người dùng"
      />
      <button type="submit">SEARCH</button>
      <c:if test="${not empty searchValue}">
        <a href="updateDepartment" style="margin-left: 10px;">
          <button type="button">CLEAR</button>
        </a>
      </c:if>
    </form>
    </div>

    <table>
      <thead>
        <tr>
          <th>User ID</th>
          <th>Name</th>
          <th>Current Department</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="user" items="${users}">
          <tr>
            <td>${user.userId}</td>
            <td>${user.fullname}</td>
            <td>
              <span id="viewDept-${user.userId}">${user.departmentName}</span>
              <select name="newDepartmentID" id="editDept-${user.userId}" style="display:none;">
                <c:forEach var="dept" items="${departments}">
                  <option value="${dept.departmentId}" ${user.departmentId == dept.departmentId ? 'selected' : ''}>
                    ${dept.name}
                  </option>
                </c:forEach>
              </select>
            </td>
            <td>
              <form method="post" action="updateDepartment" style="margin:0;">
                <input type="hidden" name="userID" value="${user.userId}" />
                <input type="hidden" name="newDepartmentID" id="hiddenDept-${user.userId}" />
                <button type="button" id="editBtn-${user.userId}" onclick="enableEdit('${user.userId}')">Edit</button>
                <button type="submit" id="saveBtn-${user.userId}" style="display:none;">Save</button>
                <button type="button" id="cancelBtn-${user.userId}" style="display:none;" onclick="cancelEdit('${user.userId}')">Cancel</button>
              </form>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    
    
  </body>
</html>
