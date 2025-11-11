<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="HRMS - Human Resource Management System" />
        <meta name="author" content="Group 3" />
        <title>HRMS - Home Page</title>
        <link rel="icon" type="image/x-icon" href="<c:url value='/assets/favicon.ico'/>" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" type="text/css" />
        <link href="<c:url value='/css/styles.css'/>" rel="stylesheet" />
    </head>
    <body id="page-top">
        <!-- Navigation-->
        <nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
            <div class="container">
                <a class="navbar-brand" href="#page-top">
                    <img src="<c:url value='/picture/hrms-removebg-preview.png'/>" alt="HRMS Logo" />
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    Menu
                    <i class="fas fa-bars ms-1"></i>
                </button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle p-0" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <img src="https://ui-avatars.com/api/?name=${fn:escapeXml(sessionScope.users.fullname)}&size=40&background=b5d3ff&color=0d3b66&bold=true" 
                                     alt="Avatar" 
                                     class="rounded-circle" 
                                     style="width: 40px; height: 40px; object-fit: cover; border: 2px solid #fff;" />
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end shadow border-0" style="min-width: 250px;">
                                <li>
                                    <div class="dropdown-item-text d-flex align-items-center py-3">
                                        <img src="https://ui-avatars.com/api/?name=${fn:escapeXml(sessionScope.users.fullname)}&size=60&background=b5d3ff&color=0d3b66&bold=true" 
                                             alt="Avatar" 
                                             class="rounded-circle me-3" 
                                             style="width: 60px; height: 60px; object-fit: cover;" />
                                        <div>
                                            <div class="fw-bold">${sessionScope.users.fullname}</div>
                                            <small class="text-muted">ID: ${sessionScope.users.userId}</small>
                                        </div>
                                    </div>
                                </li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="<c:url value='/home'/>"><i class="fas fa-home me-2"></i>Dashboard</a></li>
                                <li><a class="dropdown-item" href="<c:url value='/logout'/>"><i class="fas fa-sign-out-alt me-2"></i> Logout</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
        <header class="masthead">
            <div class="container">
                <div class="masthead-subheading">WELCOME back, ${users.fullname}</div>
                <div class="masthead-heading text-uppercase">We are the best</div>
                <a class="btn btn-primary btn-xl text-uppercase" href="#services">More</a>
            </div>
        </header>
        <footer class="footer py-4">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-lg-4 text-lg-start">
                        Copyright &copy; HRMS 2025<br>
                        Design by Group 3
                    </div>
                    <div class="col-lg-4 my-3 my-lg-0">
                        <a class="btn btn-dark btn-social mx-2" href="#!" aria-label="Facebook"><i class="fab fa-facebook-f"></i></a>
                        <a class="btn btn-dark btn-social mx-2" href="#!" aria-label="LinkedIn"><i class="fab fa-linkedin-in"></i></a>
                        <a class="btn btn-dark btn-social mx-2" href="#!" aria-label="Twitter"><i class="fab fa-twitter"></i></a>
                    </div>
                    <div class="col-lg-4 text-lg-end">
                        <strong>Contact:</strong><br>
                        Location: FPT University<br>
                        Group member<br>
                        Lê Anh Ngọc<br>
                        Phạm Quang Minh<br>
                        Nguyễn Thanh Minh<br>
                        Lương Thị Vân Ly<br>
                        Vũ Hoàng Linh<br>
                        Lecture: HienNM
                    </div>
                </div>
            </div>
        </footer>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="<c:url value='/js/scripts.js'/>"></script>
    </body>
</html>
