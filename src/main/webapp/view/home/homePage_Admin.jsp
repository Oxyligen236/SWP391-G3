
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <title>HRMS - Human Resource Management System</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="HRMS Admin Dashboard" />
        <meta name="keywords" content="HR, Management, System, Dashboard" />
        <meta name="author" content="Group 3" />
        <meta name="Version" content="v1.0.0" />
        <!-- favicon -->
        <link rel="shortcut icon" href="<c:url value='/assets/img/favicon.ico.png'/>">
        <!-- Bootstrap -->
        <link href="<c:url value='/assets/img/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css" />
        <!-- simplebar -->
        <link href="<c:url value='/assets/img/css/simplebar.css'/>" rel="stylesheet" type="text/css" />
        <!-- Select2 -->
        <link href="<c:url value='/assets/img/css/select2.min.css'/>" rel="stylesheet" />
        <!-- Icons -->
        <link href="<c:url value='/assets/img/css/materialdesignicons.min.css'/>" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/assets/img/css/remixicon.css'/>" rel="stylesheet" type="text/css" />
        <link href="https://unicons.iconscout.com/release/v3.0.6/css/line.css"  rel="stylesheet">
        <!-- SLIDER -->
        <link href="<c:url value='/assets/img/css/tiny-slider.css'/>" rel="stylesheet" />
        <!-- Css -->
        <link href="<c:url value='/assets/img/css/style.min.css'/>" rel="stylesheet" type="text/css" id="theme-opt" />

    </head>
    <body>
        <div class="page-wrapper doctris-theme toggled">
            <nav id="sidebar" class="sidebar-wrapper">
                <div class="sidebar-content" data-simplebar style="height: calc(100% - 60px);">
                    <div class="sidebar-brand">
                        <a href="<c:url value='/view/home/homePage_guest.jsp'/>">
                            <img src="<c:url value='/picture/hrms-removebg-preview.png'/>" height="24" class="logo-light-mode" alt="HRMS"> HRMS
                        </a>
                    </div>
                    
                    <ul class="sidebar-menu pt-3">
                        <li class="sidebar-dropdown">
                            <a href="javascript:void(0)"><i class="uil uil-user me-2 d-inline-block"></i>Profile</a>
                            <div class="sidebar-submenu">
                                <ul>
                                    <li><a href="#">View profile</a></li>
                                    <li><a href="#">Edit Profile</a></li>
                                </ul>
                            </div>
                        </li>

                        <li class="sidebar-dropdown">
                            <a href="javascript:void(0)"><i class="uil uil-wheelchair me-2 d-inline-block"></i>Account</a>
                            <div class="sidebar-submenu">
                                <ul>
                                    <li><a href="#">View account list</a></li>
                                </ul>
                            </div>
                        </li>

                        <li class="sidebar-dropdown">
                            <a href="javascript:void(0)"><i class="uil uil-wheelchair me-2 d-inline-block"></i>Contract</a>
                            <div class="sidebar-submenu">
                                <ul>
                                    <li><a href="#">My Contract</a></li>
                                </ul>
                            </div>
                        </li>

                        <li class="sidebar-dropdown">
                            <a href="javascript:void(0)"><i class="uil uil-shopping-cart me-2 d-inline-block"></i>Payroll</a>
                            <div class="sidebar-submenu">
                                <ul>
                                    <li><a href="#">Personal Payroll</a></li>
                                </ul>
                            </div>
                        </li>

                        <li class="sidebar-dropdown">
                            <a href="javascript:void(0)"><i class="uil uil-flip-h me-2 d-inline-block"></i>Ticket</a>
                            <div class="sidebar-submenu">
                                <ul>
                                    <li><a href="#">My ticket</a></li>
                                </ul>
                            </div>
                        </li>

                    </ul>
                    <!-- sidebar-menu  -->
                </div>
            </nav>
            <!-- sidebar-wrapper  -->

            <!-- Start Page Content -->
            <main class="page-content bg-light">
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
                                                <img src="https://ui-avatars.com/api/?name=${users.fullname}&size=120&background=ff80ab&color=fff&bold=true" alt="Avatar" class="avatar avatar-ex-small rounded-circle" />
                                    </button>
                                    <div class="dropdown-menu dd-menu dropdown-menu-end bg-white shadow border-0 mt-3 py-3" style="min-width: 200px;">
                                        <a class="dropdown-item d-flex align-items-center text-dark">
                                                    <img src="https://ui-avatars.com/api/?name=${fn:escapeXml(sessionScope.users.fullname)}&size=120&background=ff80ab&color=fff&bold=true" alt="Avatar" class="avatar avatar-md-sm rounded-circle border shadow" />
                                            <div class="flex-1 ms-2">
                                                <span class="d-block mb-1">${sessionScope.users.fullname}</span>
                                                <small class="text-muted">ID: ${sessionScope.users.userId}</small>
                                            </div>
                                        </a>
                                        <a class="dropdown-item text-dark" href="<c:url value='/landing'/>"><span class="mb-0 d-inline-block me-1"><i class="uil uil-dashboard align-middle h6"></i></span>landing</a>
                                        <a class="dropdown-item text-dark" href="<c:url value='/logout'/>"><span class="mb-0 d-inline-block me-1"><i class="uil uil-sign-out-alt align-middle h6"></i></span> Logout</a>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <!-- Footer Start -->
                <footer class="bg-white shadow py-3">
                    <div class="container-fluid">
                        <div class="row align-items-center">
                            <div class="col">
                                <div class="text-sm-start text-center">
                                    <p class="mb-0 text-muted"><script>document.write(new Date().getFullYear())</script> © HRMS. Design by <a href="#" class="text-reset">Group 3</a>.</p>
                                </div>
                            </div><!--end col-->
                        </div><!--end row-->
                    </div><!--end container-->
                </footer><!--end footer-->
                <!-- End -->
            </main>
            <!--End page-content" -->
        </div>
        <!-- page-wrapper -->
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
        
    </body>

</html>