<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<!-- Include Header -->
<jsp:include page="/view/common/header.jsp" />

<div class="page-wrapper doctris-theme toggled">
    <!-- Include Sidebar (có thể thay đổi sidebar tùy role) -->
    <jsp:include page="/view/common/sidebar_employee.jsp" />

    <!-- Start Page Content -->
    <main class="page-content bg-light">
        <!-- Include Topbar -->
        <jsp:include page="/view/common/topbar.jsp" />

        <!-- Main Content Area - Đây là phần sẽ thay đổi -->
        <div class="container-fluid">
            <c:choose>
                <c:when test="${not empty contentPage}">
                    <!-- Include dynamic content page -->
                    <jsp:include page="${contentPage}" />
                </c:when>
                <c:otherwise>
                    <!-- Default content khi không có contentPage -->
                    <div class="row">
                        <div class="col-12">
                            <h4 class="mb-3">Welcome to HRMS</h4>
                            <p>Select an option from the sidebar to get started.</p>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Include Footer -->
        <jsp:include page="/view/common/footer.jsp" />
    </main>
    <!--End page-content" -->
</div>
<!-- page-wrapper -->

<!-- Include Scripts -->
<jsp:include page="/view/common/scripts.jsp" />

</body>
</html>
