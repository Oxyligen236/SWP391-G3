<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="HRMS - Human Resource Management System" />
        <meta name="author" content="Group 3" />
        <title>HRMS - Home Page</title>
        <!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="${pageContext.request.contextPath}/assets/favicon.ico" />
        <!-- Font Awesome icons (free version)-->
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        <!-- Google fonts-->
        <link href="https://fonts.googleapis.com/css?family=Montserrat:400,700" rel="stylesheet" type="text/css" />
        <link href="https://fonts.googleapis.com/css?family=Roboto+Slab:400,100,300,700" rel="stylesheet" type="text/css" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet" />
    </head>
    <body id="page-top">
        <!-- Navigation-->
        <nav class="navbar navbar-expand-lg navbar-dark fixed-top" id="mainNav">
            <div class="container">
                <a class="navbar-brand" href="#page-top">
                    <img src="${pageContext.request.contextPath}/picture/hrms-removebg-preview.png" alt="HRMS Logo" />
                </a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
                    Menu
                    <i class="fas fa-bars ms-1"></i>
                </button>
                <div class="collapse navbar-collapse" id="navbarResponsive">
                    <ul class="navbar-nav text-uppercase ms-auto py-4 py-lg-0">
                        <li class="nav-item"><a class="nav-link" href="#services">Dịch vụ</a></li>
                        <li class="nav-item"><a class="nav-link" href="#about">Về chúng tôi</a></li>
                        <li class="nav-item"><a class="nav-link" href="#team">Đội ngũ</a></li>
                        <li class="nav-item"><a class="nav-link" href="#contact">Liên hệ</a></li>
                        <li class="nav-item"><a class="nav-link" href="<c:url value='/authenticate'/>">Đăng nhập</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        <!-- Masthead-->
        <header class="masthead">
            <div class="container">
                <div class="masthead-subheading">Chào mừng đến với hệ thống HRMS!</div>
                <div class="masthead-heading text-uppercase">Quản lý nguồn nhân lực hiệu quả</div>
                <a class="btn btn-primary btn-xl text-uppercase" href="#services">Tìm hiểu thêm</a>
            </div>
        </header>
        <!-- Services-->
        <section class="page-section" id="services">
            <div class="container">
                <div class="text-center">
                    <h2 class="section-heading text-uppercase">Dịch vụ</h2>
                    <h3 class="section-subheading text-muted">Các tính năng nổi bật của hệ thống HRMS</h3>
                </div>
                <div class="row text-center">
                    <div class="col-md-4">
                        <span class="fa-stack fa-4x">
                            <i class="fas fa-circle fa-stack-2x text-primary"></i>
                            <i class="fas fa-users fa-stack-1x fa-inverse"></i>
                        </span>
                        <h4 class="my-3">Quản lý nhân sự</h4>
                        <p class="text-muted">Quản lý thông tin nhân viên, phòng ban, chức vụ một cách hiệu quả và chuyên nghiệp.</p>
                    </div>
                    <div class="col-md-4">
                        <span class="fa-stack fa-4x">
                            <i class="fas fa-circle fa-stack-2x text-primary"></i>
                            <i class="fas fa-clock fa-stack-1x fa-inverse"></i>
                        </span>
                        <h4 class="my-3">Chấm công</h4>
                        <p class="text-muted">Hệ thống chấm công tự động, theo dõi giờ làm việc và quản lý ca làm việc linh hoạt.</p>
                    </div>
                    <div class="col-md-4">
                        <span class="fa-stack fa-4x">
                            <i class="fas fa-circle fa-stack-2x text-primary"></i>
                            <i class="fas fa-money-bill-wave fa-stack-1x fa-inverse"></i>
                        </span>
                        <h4 class="my-3">Tính lương</h4>
                        <p class="text-muted">Tự động tính toán lương, thưởng, phụ cấp và các khoản khấu trừ một cách chính xác.</p>
                    </div>
                </div>
                <div class="row text-center mt-5">
                    <div class="col-md-4">
                        <span class="fa-stack fa-4x">
                            <i class="fas fa-circle fa-stack-2x text-primary"></i>
                            <i class="fas fa-briefcase fa-stack-1x fa-inverse"></i>
                        </span>
                        <h4 class="my-3">Tuyển dụng</h4>
                        <p class="text-muted">Quản lý quy trình tuyển dụng, đăng tin tuyển dụng và tiếp nhận hồ sơ ứng viên.</p>
                    </div>
                    <div class="col-md-4">
                        <span class="fa-stack fa-4x">
                            <i class="fas fa-circle fa-stack-2x text-primary"></i>
                            <i class="fas fa-file-contract fa-stack-1x fa-inverse"></i>
                        </span>
                        <h4 class="my-3">Hợp đồng</h4>
                        <p class="text-muted">Quản lý hợp đồng lao động, theo dõi thời hạn và gia hạn hợp đồng tự động.</p>
                    </div>
                    <div class="col-md-4">
                        <span class="fa-stack fa-4x">
                            <i class="fas fa-circle fa-stack-2x text-primary"></i>
                            <i class="fas fa-ticket-alt fa-stack-1x fa-inverse"></i>
                        </span>
                        <h4 class="my-3">Quản lý đơn từ</h4>
                        <p class="text-muted">Xử lý các đơn xin nghỉ phép, đơn từ và yêu cầu của nhân viên nhanh chóng.</p>
                    </div>
                </div>
            </div>
        </section>
        <!-- About-->
        <section class="page-section" id="about">
            <div class="container">
                <div class="text-center">
                    <h2 class="section-heading text-uppercase">Về chúng tôi</h2>
                    <h3 class="section-subheading text-muted">Hành trình phát triển của HRMS</h3>
                </div>
                <ul class="timeline">
                    <li>
                        <div class="timeline-image"><img class="rounded-circle img-fluid" src="${pageContext.request.contextPath}/assets/img/about/1.jpg" alt="..." /></div>
                        <div class="timeline-panel">
                            <div class="timeline-heading">
                                <h4>2020</h4>
                                <h4 class="subheading">Khởi đầu dự án</h4>
                            </div>
                            <div class="timeline-body"><p class="text-muted">Dự án HRMS được khởi động với mục tiêu tạo ra một hệ thống quản lý nhân sự toàn diện, hiện đại và dễ sử dụng cho các doanh nghiệp Việt Nam.</p></div>
                        </div>
                    </li>
                    <li class="timeline-inverted">
                        <div class="timeline-image"><img class="rounded-circle img-fluid" src="${pageContext.request.contextPath}/assets/img/about/2.jpg" alt="..." /></div>
                        <div class="timeline-panel">
                            <div class="timeline-heading">
                                <h4>2021</h4>
                                <h4 class="subheading">Phát triển tính năng cốt lõi</h4>
                            </div>
                            <div class="timeline-body"><p class="text-muted">Hoàn thiện các tính năng cơ bản: quản lý nhân sự, chấm công, tính lương. Hệ thống được thử nghiệm tại các doanh nghiệp vừa và nhỏ.</p></div>
                        </div>
                    </li>
                    <li>
                        <div class="timeline-image"><img class="rounded-circle img-fluid" src="${pageContext.request.contextPath}/assets/img/about/3.jpg" alt="..." /></div>
                        <div class="timeline-panel">
                            <div class="timeline-heading">
                                <h4>2023</h4>
                                <h4 class="subheading">Mở rộng hệ thống</h4>
                            </div>
                            <div class="timeline-body"><p class="text-muted">Bổ sung các module tuyển dụng, quản lý hợp đồng, xử lý đơn từ. Tích hợp công nghệ AI để tự động hóa các quy trình.</p></div>
                        </div>
                    </li>
                    <li class="timeline-inverted">
                        <div class="timeline-image"><img class="rounded-circle img-fluid" src="${pageContext.request.contextPath}/assets/img/about/4.jpg" alt="..." /></div>
                        <div class="timeline-panel">
                            <div class="timeline-heading">
                                <h4>2025</h4>
                                <h4 class="subheading">Phiên bản hoàn thiện</h4>
                            </div>
                            <div class="timeline-body"><p class="text-muted">Ra mắt phiên bản chính thức với đầy đủ tính năng, giao diện thân thiện và hỗ trợ đa nền tảng. Sẵn sàng phục vụ hàng nghìn doanh nghiệp.</p></div>
                        </div>
                    </li>
                    <li class="timeline-inverted">
                        <div class="timeline-image">
                            <h4>
                                Hãy tham gia
                                <br />
                                cùng
                                <br />
                                chúng tôi!
                            </h4>
                        </div>
                    </li>
                </ul>
            </div>
        </section>
        <!-- Team-->
        <section class="page-section bg-light" id="team">
            <div class="container">
                <div class="text-center">
                    <h2 class="section-heading text-uppercase">Đội ngũ phát triển</h2>
                    <h3 class="section-subheading text-muted">Nhóm 3 - Khoa Công nghệ Phần mềm</h3>
                </div>
                <div class="row">
                    <div class="col-lg-4">
                        <div class="team-member">
                            <img class="mx-auto rounded-circle" src="${pageContext.request.contextPath}/assets/img/team/1.jpg" alt="..." />
                            <h4>Nguyễn Văn A</h4>
                            <p class="text-muted">Trưởng nhóm</p>
                            <a class="btn btn-dark btn-social mx-2" href="#!" aria-label="Facebook Profile"><i class="fab fa-facebook-f"></i></a>
                            <a class="btn btn-dark btn-social mx-2" href="#!" aria-label="LinkedIn Profile"><i class="fab fa-linkedin-in"></i></a>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="team-member">
                            <img class="mx-auto rounded-circle" src="${pageContext.request.contextPath}/assets/img/team/2.jpg" alt="..." />
                            <h4>Trần Thị B</h4>
                            <p class="text-muted">Thành viên</p>
                            <a class="btn btn-dark btn-social mx-2" href="#!" aria-label="Facebook Profile"><i class="fab fa-facebook-f"></i></a>
                            <a class="btn btn-dark btn-social mx-2" href="#!" aria-label="LinkedIn Profile"><i class="fab fa-linkedin-in"></i></a>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="team-member">
                            <img class="mx-auto rounded-circle" src="${pageContext.request.contextPath}/assets/img/team/3.jpg" alt="..." />
                            <h4>Lê Văn C</h4>
                            <p class="text-muted">Thành viên</p>
                            <a class="btn btn-dark btn-social mx-2" href="#!" aria-label="Facebook Profile"><i class="fab fa-facebook-f"></i></a>
                            <a class="btn btn-dark btn-social mx-2" href="#!" aria-label="LinkedIn Profile"><i class="fab fa-linkedin-in"></i></a>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-8 mx-auto text-center">
                        <p class="large text-muted">
                            Dự án được thực hiện bởi nhóm sinh viên năm cuối ngành Công nghệ Phần mềm. 
                            Với sự hướng dẫn tận tình của ThS. Phạm Thị D, chúng tôi đã tạo ra một hệ thống 
                            quản lý nhân sự hiện đại, đáp ứng nhu cầu thực tế của doanh nghiệp.
                        </p>
                    </div>
                </div>
            </div>
        </section>
        <!-- Contact-->
        <section class="page-section" id="contact">
            <div class="container">
                <div class="text-center">
                    <h2 class="section-heading text-uppercase">Liên hệ với chúng tôi</h2>
                    <h3 class="section-subheading text-muted">Để lại thông tin, chúng tôi sẽ liên hệ lại sớm nhất</h3>
                </div>
                <!-- Contact Form -->
                <form id="contactForm" action="${pageContext.request.contextPath}/contact" method="post">
                    <div class="row align-items-stretch mb-5">
                        <div class="col-md-6">
                            <div class="form-group">
                                <!-- Name input-->
                                <input class="form-control" id="name" name="name" type="text" placeholder="Họ và tên *" required />
                                <div class="invalid-feedback">Vui lòng nhập họ tên.</div>
                            </div>
                            <div class="form-group">
                                <!-- Email address input-->
                                <input class="form-control" id="email" name="email" type="email" placeholder="Email *" required />
                                <div class="invalid-feedback">Vui lòng nhập email hợp lệ.</div>
                            </div>
                            <div class="form-group mb-md-0">
                                <!-- Phone number input-->
                                <input class="form-control" id="phone" name="phone" type="tel" placeholder="Số điện thoại *" required />
                                <div class="invalid-feedback">Vui lòng nhập số điện thoại.</div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="form-group form-group-textarea mb-md-0">
                                <!-- Message input-->
                                <textarea class="form-control" id="message" name="message" placeholder="Nội dung tin nhắn *" required></textarea>
                                <div class="invalid-feedback">Vui lòng nhập nội dung tin nhắn.</div>
                            </div>
                        </div>
                    </div>
                    <!-- Submit success message-->
                    <c:if test="${not empty successMessage}">
                        <div class="alert alert-success text-center mb-3">
                            ${successMessage}
                        </div>
                    </c:if>
                    <!-- Submit error message-->
                    <c:if test="${not empty errorMessage}">
                        <div class="alert alert-danger text-center mb-3">
                            ${errorMessage}
                        </div>
                    </c:if>
                    <!-- Submit Button-->
                    <div class="text-center"><button class="btn btn-primary btn-xl text-uppercase" id="submitButton" type="submit">Gửi tin nhắn</button></div>
                </form>
            </div>
        </section>
        <!-- Footer-->
        <footer class="footer py-4">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-lg-4 text-lg-start">
                        Copyright &copy; HRMS 2025<br>
                        Thiết kế bởi Nhóm 3
                    </div>
                    <div class="col-lg-4 my-3 my-lg-0">
                        <a class="btn btn-dark btn-social mx-2" href="#!" aria-label="Facebook"><i class="fab fa-facebook-f"></i></a>
                        <a class="btn btn-dark btn-social mx-2" href="#!" aria-label="LinkedIn"><i class="fab fa-linkedin-in"></i></a>
                        <a class="btn btn-dark btn-social mx-2" href="#!" aria-label="Twitter"><i class="fab fa-twitter"></i></a>
                    </div>
                    <div class="col-lg-4 text-lg-end">
                        <strong>Liên hệ:</strong><br>
                        Địa chỉ: 123 Đường ABC, Quận 1, TP.HCM<br>
                        Điện thoại: (0123) 456-789<br>
                        Email: support@hrms.com
                    </div>
                </div>
            </div>
        </footer>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    </body>
</html>
