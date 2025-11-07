<%@ page contentType="text/html; charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <title>Work History</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <link rel="stylesheet" href="<c:url value='/css/workhistory.css'/>">
    </head>
    <body>
        <div class="container">
            <header class="page-header">
                <h1>Work History</h1>
            </header>

            <form class="search-form" method="get" action="<c:url value='/workhistory'/>">
                <label for="searchType" class="form-label">Search by Type</label>
                <div class="input-group">
                    <input type="text" id="searchType" name="search" value="${search}" class="form-control"
                           placeholder="Enter job type...">
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-search"></i> Search
                    </button>
                </div>
            </form>

            <div class="table-wrap">
                <table class="workhistory-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>DoneBy</th>
                            <th>Type</th>
                            <th>Effective Date</th>
                            <th>Old Value</th>
                            <th>New Value</th>
                            <th>Description</th>
                            <th>Evaluate</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${not empty WorkHistoryList}">
                                <c:forEach var="wh" items="${WorkHistoryList}">
                                    <tr>
                                        <td>${wh.historyID}</td>
                                        <td>${UserNames[wh.userID]}</td>
                                        <td>${wh.type}</td>
                                        <td>${wh.effectiveDate}</td>
                                        <td><c:out value="${wh.oldValue}"/></td>
                                        <td><c:out value="${wh.newValue}"/></td>
                                        <td><c:out value="${wh.description}"/></td>
                                        <td><c:out value="${wh.evaluate}"/></td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="8" style="text-align: center;">No work history found.</td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>