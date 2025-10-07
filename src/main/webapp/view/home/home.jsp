<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Home</title>
  </head>

    <body>
        <h1>Welcome to the Home Page</h1>
        <p>This is a simple JSP page.</p>
        <a href="<c:url value='/authenticate'/>">Go to Login Page</a>
        </br>
        <a href="<c:url value='/logout'/>">Logout</a>
        </br>
        <a href="<c:url value='/view'/>">View Profile</a>
        </br>
        <a href="<c:url value='/edit'/>">Edit Profile</a>
        </br>
        <a href="<c:url value='/cv'/>">CV List</a>
    </body>

    </html>