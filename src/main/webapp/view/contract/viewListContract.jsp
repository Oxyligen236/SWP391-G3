<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>

<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>View user contract list</title>
    <link rel="stylesheet" href="<c:url value='/css/cv-list.css'/>" />
    <script src="" defer></script>
  </head>
  <body>
    <h2>Contract List</h2>
    <div>
      <form id="searchForm" method="get" action="<c:url value='/viewContracts'/>">
        <select id="searchField" name="searchField" onchange="onFieldChange()">
          <option value="">Tìm kiếm theo</option>
          <option value="userId" <c:if test="${searchField == 'userId'}">selected</c:if>>UserID</option>
          <option value="duration" <c:if test="${searchField == 'duration'}">selected</c:if>>Duration</option>
          <option value="baseSalary" <c:if test="${searchField == 'baseSalary'}">selected</c:if>>Salary</option>
          <option value="startDate" <c:if test="${searchField == 'startDate'}">selected</c:if>>startDate</option>
          <option value="endDate" <c:if test="${searchField == 'endDate'}">selected</c:if>>endDate</option>
          <option value="signDate" <c:if test="${searchField == 'signDate'}">selected</c:if>>signDate</option>
        </select>

        <span id="textInputWrapper">
          <input type="text" id="searchValue" name="searchValue" value="${fn:escapeXml(searchValue)}" placeholder="Nhập thông tin tìm kiếm" />
        </span>
        <span id="dateInputWrapper" style="display:none">
          Từ: <input type="date" id="fromDate" name="fromDate" value="${fromDate}" />
          Đến: <input type="date" id="toDate" name="toDate" value="${toDate}" />
        </span>

        <label for="sortField">Sắp xếp theo:</label>
        <select id="sortField" name="sortField">
          <option value="">--Chọn--</option>
          <option value="baseSalary" <c:if test="${sortField == 'baseSalary'}">selected</c:if>>Salary</option>
          <option value="duration" <c:if test="${sortField == 'duration'}">selected</c:if>>Duration</option>
        </select>

        <label for="sortOrder">Thứ tự:</label>
        <select id="sortOrder" name="sortOrder">
          <option value="asc" <c:if test="${sortOrder == 'asc'}">selected</c:if>>Tăng dần</option>
          <option value="desc" <c:if test="${sortOrder == 'desc'}">selected</c:if>>Giảm dần</option>
        </select>

        <button type="submit" onclick="">SEARCH</button>
        <button type="submit" name="action" value="sort" onclick="">SORT</button>
        
        <a href="<c:url value='/viewContracts'><c:param name='showAll' value='true' /></c:url>" class="btn">Hiển thị toàn bộ</a>
      </form>
    </div>

    <script>
      function onFieldChange() {
        var field = document.getElementById('searchField').value;
        var textInput = document.getElementById('textInputWrapper');
        var dateInput = document.getElementById('dateInputWrapper');
        if (field === 'startDate' || field === 'endDate' || field === 'signDate') {
          textInput.style.display = 'none';
          dateInput.style.display = 'inline';
        } else {
          textInput.style.display = 'inline';
          dateInput.style.display = 'none';
        }
      }

      window.addEventListener('DOMContentLoaded', function () {
        onFieldChange();
      });
    </script>

    <div>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>UserID</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Sign Date</th>
            <th>Duration</th>
            <th>Salary</th>
            <th>Type</th>
            <th>Note</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="contract" items="${contracts}">
            <tr>
              <td>${contract.contractId}</td>
              <td>${contract.userId}</td>
              <td>${contract.startDate}</td>
              <td>${contract.endDate}</td>
              <td>${contract.signDate}</td>
              <td>${contract.duration}</td>
              <td>${contract.baseSalary}</td>
              <td>${contract.typeName}</td>
              <td>${contract.note}</td>
              <td><a href="<c:url value='/viewContracts?action=detail&id=${contract.contractId}'/>">Chi tiết</a></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>

    <div class="bottom-bar">
      <div>
          <div class="pagination">
            <c:set var="cp" value="${currentPage}" />
            <c:set var="tp" value="${totalPages}" />

            <c:if test="${cp > 1}">
              <c:url var="prevUrl" value="/viewContracts">
                <c:param name="page" value="${cp - 1}" />
                <c:if test="${not empty searchField}"><c:param name="searchField" value="${searchField}" /></c:if>
                <c:if test="${not empty searchValue}"><c:param name="searchValue" value="${searchValue}" /></c:if>
                <c:if test="${not empty fromDate}"><c:param name="fromDate" value="${fromDate}" /></c:if>
                <c:if test="${not empty toDate}"><c:param name="toDate" value="${toDate}" /></c:if>
                <c:if test="${not empty sortField}"><c:param name="sortField" value="${sortField}" /></c:if>
                <c:if test="${not empty sortOrder}"><c:param name="sortOrder" value="${sortOrder}" /></c:if>
              </c:url>
              <a href="${prevUrl}">Previous</a>
            </c:if>

            <c:forEach var="i" begin="1" end="${tp}">
              <c:url var="pageUrl" value="/viewContracts">
                <c:param name="page" value="${i}" />
                <c:if test="${not empty searchField}"><c:param name="searchField" value="${searchField}" /></c:if>
                <c:if test="${not empty searchValue}"><c:param name="searchValue" value="${searchValue}" /></c:if>
                <c:if test="${not empty fromDate}"><c:param name="fromDate" value="${fromDate}" /></c:if>
                <c:if test="${not empty toDate}"><c:param name="toDate" value="${toDate}" /></c:if>
                <c:if test="${not empty sortField}"><c:param name="sortField" value="${sortField}" /></c:if>
                <c:if test="${not empty sortOrder}"><c:param name="sortOrder" value="${sortOrder}" /></c:if>
              </c:url>
              <a href="${pageUrl}" class="${i == cp ? 'active' : ''}">${i}</a>
            </c:forEach>

            <c:if test="${cp < tp}">
              <c:url var="nextUrl" value="/viewContracts">
                <c:param name="page" value="${cp + 1}" />
                <c:if test="${not empty searchField}"><c:param name="searchField" value="${searchField}" /></c:if>
                <c:if test="${not empty searchValue}"><c:param name="searchValue" value="${searchValue}" /></c:if>
                <c:if test="${not empty fromDate}"><c:param name="fromDate" value="${fromDate}" /></c:if>
                <c:if test="${not empty toDate}"><c:param name="toDate" value="${toDate}" /></c:if>
                <c:if test="${not empty sortField}"><c:param name="sortField" value="${sortField}" /></c:if>
                <c:if test="${not empty sortOrder}"><c:param name="sortOrder" value="${sortOrder}" /></c:if>
              </c:url>
              <a href="${nextUrl}">Next</a>
            </c:if>
          </div>
      </div>
      <div class="right-actions">
        <a href="<c:url value='/view/home/home.jsp'/>" class="back-link">Back to Home</a>
      </div>
    </div>

  </body>
</html>
