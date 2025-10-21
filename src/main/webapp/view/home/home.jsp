<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <html lang="en">

    <body>
      <h1>Welcome to the Home Page</h1>
      <p>This is a simple JSP page.</p>
      <a href="<c:url value='/authenticate'/>">Go to Login Page</a><br />
      <a href="<c:url value='/logout'/>">Logout</a><br />
      <a href="<c:url value='/cv'/>">CV List</a><br />
      <a href="<c:url value='/view'/>">View Profile</a><br />
      <a href="<c:url value='/edit'/>">Edit Profile</a><br />

      <a href="<c:url value='/account/create'/>">Edit Profile</a><br />

      <>
        <h1>Welcome to the Home Page</h1>
        <p>This is a simple JSP page.</p>
        <a href="<c:url value='/authenticate'/>">Go to Login Page</a>
        </br>
        <a href="<c:url value='/logout'/>">Logout</a>
        </br>
        <a href="<c:url value='/cv'/>">CV List</a>
        </br>
        <a href="<c:url value='/addContracts'/>">AddContract</a>
        </br>
        <a href="<c:url value='/updateRole'/>">updateRole</a>
        </br>
        <a href="<c:url value='/updateDepartment'/>">updateDepartment</a>
        </br>
        <a href="<c:url value='/updatePosition'/>">updatePosition</a>
        </br>
        <a href="<c:url value='/viewContracts'/>">ViewContractList</a>
        </br>
        <a href="<c:url value='/viewContracts'/>">ViewContractList</a>
        </br>
        <a href="<c:url value='/account/view'/>">Account List (View/Edit/Toggle)</a><br />


        <!-- <c:url var="submitUrl" value="/cv/submit">
        <c:param name="jdID" value="${job.jobID}" />
        <c:param name="title" value="${job.jobTitle}" />
      </c:url>

      <a href="${submitUrl}">
        Apply for ${job.jobTitle}
      </a> -->
        <a href="<c:url value='/cv/submit?jdID=1&title=Java+Developer'/>">
          Apply for Java Developer
        </a>
        </br>
        <a href="<c:url value='/payroll/personal'/>">Personal Payroll</a>
        </br>
        <a href="<c:url value='/payroll/company'/>">Company Payroll</a>

    </body>

    </html>