<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="top-header">
    <div class="header-bar d-flex justify-content-between border-bottom">
        <div class="d-flex align-items-center">
            <a href="#" class="logo-icon">
                <img src="<c:url value='/picture/hrms-removebg-preview.png'/>" height="30" class="small" alt="HRMS">
                <span class="big">
                    <img src="<c:url value='/picture/hrms-removebg-preview.png'/>" height="24" class="logo-light-mode" alt="HRMS"> 
                </span>
            </a>
            <a id="close-sidebar" class="btn btn-icon btn-pills btn-soft-primary ms-2" href="#">
                <i class="uil uil-bars"></i>
            </a>
        </div>

        <ul class="list-unstyled mb-0">
            <li class="list-inline-item mb-0 ms-1">
                <div class="dropdown dropdown-primary">
                    <button type="button" class="btn btn-pills btn-soft-primary dropdown-toggle p-0" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        <img src="https://ui-avatars.com/api/?name=${fn:escapeXml(sessionScope.users.fullname)}&size=120&background=ff80ab&color=fff&bold=true" alt="Avatar" class="avatar avatar-ex-small rounded-circle" />
                    </button>
                    <div class="dropdown-menu dd-menu dropdown-menu-end bg-white shadow border-0 mt-3 py-3" style="min-width: 200px;">
                        <a class="dropdown-item d-flex align-items-center text-dark">
                            <img src="https://ui-avatars.com/api/?name=${fn:escapeXml(sessionScope.users.fullname)}&size=120&background=ff80ab&color=fff&bold=true" alt="Avatar" class="avatar avatar-md-sm rounded-circle border shadow" />
                            <div class="flex-1 ms-2">
                                <span class="d-block mb-1">${sessionScope.users.fullname}</span>
                                <small class="text-muted">ID: ${sessionScope.users.userId}</small>
                            </div>
                        </a>
                        <a class="dropdown-item text-dark" href="<c:url value='/landing'/>">
                            <span class="mb-0 d-inline-block me-1"><i class="uil uil-dashboard align-middle h6"></i></span>Landing
                        </a>
                        <a class="dropdown-item text-dark" href="<c:url value='/logout'/>">
                            <span class="mb-0 d-inline-block me-1"><i class="uil uil-sign-out-alt align-middle h6"></i></span> Logout
                        </a>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</div>
