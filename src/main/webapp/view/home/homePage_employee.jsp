
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<%-- Set content page to display dashboard ---%>
<c:set var="contentPage" value="/view/home/content/dashboard_employee.jsp" scope="request"/>
<c:set var="pageTitle" value="Employee Dashboard - HRMS" scope="request"/>

<%-- Include layout with the content ---%>
<jsp:include page="/view/common/layout_employee.jsp" />