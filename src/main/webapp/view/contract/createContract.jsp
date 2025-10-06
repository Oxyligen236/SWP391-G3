<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Create Contract page</title>
    <link rel="stylesheet" href="../../css/contract.css" />
    <script src="" defer></script>
  </head>
  <body>
    <div class="contract-form">
      <h2>Thông tin hợp đồng</h2>
      <form action="contracts" method="post">
        <input type="hidden" name="action" value="insert" />
        <div class="form-group">
          <label for="name">Họ và tên:</label>
          <input type="text" id="name" name="name" required />
        </div>

        <div class="form-group">
          <label for="userID">Mã nhân viên:</label>
          <input type="text" id="userID" name="userID" required />
        </div>

        <div class="form-group">
          <label for="signDate">Ngày ký:</label>
          <input type="date" id="signDate" name="signDate" required />
        </div>

        <div class="form-group">
          <label for="startDate">Ngày bắt đầu:</label>
          <input type="date" id="startDate" name="startDate" required />
        </div>

        <div class="form-group">
          <label for="endDate">Ngày kết thúc:</label>
          <input type="date" id="endDate" name="endDate" />
        </div>

        <div class="form-group">
          <label for="duration">Hạn hợp đồng:</label>
          <input type="text" id="duration" name="duration" />
        </div>

        <div class="form-group">
          <label for="basicSalary">Lương cơ bản:</label>
          <input type="text" id="basicSalary" name="basicSalary" />
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

        <div class="form-group">
          <label for="file">Upload hợp đồng (PDF/Ảnh):</label>
          <input
            type="file"
            id="file"
            name="file"
            accept=".pdf,.jpg,.jpeg,.png"
          />
        </div>

        <button type="submit">Lưu</button>
      </form>
    </div>
  </body>
</html>
