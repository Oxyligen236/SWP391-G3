<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<!-- ======= Include Header ======= -->
<jsp:include page="/view/common/header.jsp" />

<div class="page-wrapper doctris-theme toggled">
    <!-- ======= Sidebar (theo role: employee) ======= -->
    <jsp:include page="/view/common/sidebar_deptManager.jsp" />

    <!-- ======= Page Content ======= -->
    <main class="page-content bg-light">
        <!-- Topbar -->
        <jsp:include page="/view/common/topbar.jsp" />

        <!-- ======= Nội dung chính qua iframe ======= -->
        <div class="container-fluid p-0" style="height: calc(100vh - 120px);">
            <iframe 
                id="mainFrame"
                name="mainFrame"
                src="<c:url value='/view/home/content/dashboard_employee.jsp'/>"
                frameborder="0"
                style="width: 100%; height: 100%; border: none; background-color: #fff;"
            ></iframe>
        </div>

        <!-- Footer -->
        <jsp:include page="/view/common/footer.jsp" />
    </main>
</div>

<!-- ======= Scripts ======= -->
<jsp:include page="/view/common/scripts.jsp" />

</body>
</html>
