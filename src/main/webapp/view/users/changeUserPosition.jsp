<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Update User Position</title>
    <link rel="stylesheet" href="<c:url value='/css/update-position.css'/>" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
  </head>
  <body>
    <div class="popup-container">
      <div class="popup-header">
        <i class="fas fa-briefcase"></i>
        <h2>Cập Nhật Chức Vụ</h2>
      </div>

      <div class="popup-body">
        <div class="info-row">
          <label><i class="fas fa-id-badge"></i> User ID:</label>
          <span>${user.userId}</span>
        </div>

        <div class="info-row">
          <label><i class="fas fa-user"></i> Họ Tên:</label>
          <span>${user.fullname}</span>
        </div>

        <div class="info-row">
          <label><i class="fas fa-envelope"></i> Email:</label>
          <span>${user.email}</span>
        </div>

        <div class="info-row">
          <label><i class="fas fa-briefcase"></i> Chức Vụ Hiện Tại:</label>
          <span class="current-position-badge">${user.positionName}</span>
        </div>

        <form method="post" action="updatePosition">
          <input type="hidden" name="userID" value="${user.userId}" />

          <div class="form-group">
            <label for="newPositionID"><i class="fas fa-exchange-alt"></i> Chọn Chức Vụ Mới:</label>
            <select name="newPositionID" id="newPositionID" class="position-select" required>
              <c:forEach var="pos" items="${positions}">
                <option value="${pos.positionId}" ${currentPositionID == pos.positionId ? 'selected' : ''}>
                  ${pos.name}
                </option>
              </c:forEach>
            </select>
          </div>

          <div class="button-group">
            <a href="account/view" class="btn-cancel">
              <i class="fas fa-times"></i> Hủy
            </a>
            <button type="submit" class="btn-submit">
              <i class="fas fa-check"></i> Cập Nhật
            </button>
          </div>
        </form>
      </div>
    </div>
  </body>
</html>
