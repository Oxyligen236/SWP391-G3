<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<!-- Include Header -->
<jsp:include page="/view/common/header.jsp" />

<div class="page-wrapper doctris-theme toggled">
    <!-- Include Sidebar cho Manager -->
    <jsp:include page="/view/common/sidebar_manager.jsp" />

    <!-- Start Page Content -->
    <main class="page-content bg-light">
        <!-- Include Topbar -->
        <jsp:include page="/view/common/topbar.jsp" />

        <!-- Main Content Area - Dynamic -->
        <div class="container-fluid">
            <c:choose>
                <c:when test="${not empty contentPage}">
                    <jsp:include page="${contentPage}" />
                </c:when>
                <c:otherwise>
                    <div class="row">
                        <div class="col-12">
                            <h4 class="mb-3">Manager Dashboard</h4>
                            <p>Welcome to Department Management. Select an option from the sidebar.</p>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- Include Footer -->
        <jsp:include page="/view/common/footer.jsp" />
    </main>
</div>

<!-- Include Scripts -->
<jsp:include page="/view/common/scripts.jsp" />

</body>
</html>
