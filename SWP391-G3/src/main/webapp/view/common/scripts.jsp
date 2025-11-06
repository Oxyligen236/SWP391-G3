<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- javascript -->
<script src="<c:url value='/assets/img/js/bootstrap.bundle.min.js'/>"></script>
<!-- simplebar -->
<script src="<c:url value='/assets/img/js/simplebar.min.js'/>"></script>
<!-- Chart -->
<script src="<c:url value='/assets/img/js/apexcharts.min.js'/>"></script>
<script src="<c:url value='/assets/img/js/columnchart.init.js'/>"></script>
<!-- Icons -->
<script src="<c:url value='/assets/img/js/feather.min.js'/>"></script>
<!-- Main Js -->
<script src="<c:url value='/assets/img/js/app.js'/>"></script>

<!-- Additional Scripts từ page -->
<c:if test="${not empty additionalScripts}">
    ${additionalScripts}
</c:if>
