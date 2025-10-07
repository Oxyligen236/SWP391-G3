<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ page
contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Home</title>
  </head>

  <body>
    <h1>Welcome to the Home Page</h1>
    <p>This is a simple JSP page.</p>
    <a href="<c:url value='/authenticate'/>">Go to Login Page</a><br />
    <a href="<c:url value='/logout'/>">Logout</a><br />
    <a href="<c:url value='/cv'/>">CV List</a><br />

    <!-- <c:url var="submitUrl" value="/cv/submit">
        <c:param name="jdID" value="${job.jobID}" />
        <c:param name="title" value="${job.jobTitle}" />
      </c:url>

      <a href="${submitUrl}">
        Apply for ${job.jobTitle}
      </a> -->
    <c:set var="jobId" value="1" />
    <c:set var="jobTitle" value="Tuyen nhan vien IT" />

    <c:url var="submitUrl" value="/cv/submit">
      <c:param name="jdID" value="${jobId}" />
      <c:param name="title" value="${jobTitle}" />
    </c:url>

    <a href="${submitUrl}"
      >Apply for
      <c:out value="${jobTitle}" />
    </a>
  </body>
</html>
