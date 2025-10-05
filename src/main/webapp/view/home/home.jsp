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
    <a href="<c:url value='/login'/>">Go to Login Page</a>
    <a href="<c:url value='/ticketList'/>">Go to Ticket List Page</a>
  </body>
</html>
