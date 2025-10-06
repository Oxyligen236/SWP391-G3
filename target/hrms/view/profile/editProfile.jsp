<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <%@ page isELIgnored="false" %>

         

                    <!DOCTYPE html>
                    <html lang="vi">

                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Chỉnh Sửa Thông Tin - HRMS</title>
                        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
                            rel="stylesheet">
                        <link rel="stylesheet"
                            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
                        <style>
                            /* Giữ nguyên toàn bộ CSS của bạn */
                            body {
                                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                                min-height: 100vh;
                                padding: 20px 0;
                            }

                            .edit-container {
                                max-width: 1000px;
                                margin: 0 auto;
                            }

                            .edit-card {
                                background: white;
                                border-radius: 15px;
                                box-shadow: 0 10px 40px rgba(0, 0, 0, 0.2);
                                overflow: hidden;
                            }

                            .edit-header {
                                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                                color: white;
                                padding: 30px;
                            }

                            .edit-header h3 {
                                margin: 0;
                                font-size: 1.8rem;
                                font-weight: 700;
                            }

                            .edit-body {
                                padding: 40px;
                            }

                            .form-label {
                                font-weight: 600;
                                color: #495057;
                                margin-bottom: 8px;
                                font-size: 0.95rem;
                            }

                            .required-field::after {
                                content: " *";
                                color: #dc3545;
                                font-weight: bold;
                            }

                            .form-control,
                            .form-select {
                                border-radius: 10px;
                                border: 2px solid #e0e0e0;
                                padding: 12px 18px;
                                font-size: 1rem;
                                transition: all 0.3s;
                            }

                            .form-control:focus,
                            .form-select:focus {
                                border-color: #667eea;
                                box-shadow: 0 0 0 0.25rem rgba(102, 126, 234, 0.25);
                            }

                            .form-text {
                                font-size: 0.85rem;
                                color: #6c757d;
                            }

                            .section-divider {
                                border-top: 2px solid #e9ecef;
                                margin: 35px 0;
                                position: relative;
                            }

                            .section-divider::before {
                                content: attr(data-title);
                                position: absolute;
                                top: -12px;
                                left: 20px;
                                background: white;
                                padding: 0 15px;
                                color: #667eea;
                                font-weight: 600;
                                font-size: 0.9rem;
                                text-transform: uppercase;
                            }

                            .btn-save {
                                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                                border: none;
                                padding: 14px 45px;
                                border-radius: 30px;
                                color: white;
                                font-weight: 600;
                                font-size: 1.05rem;
                                transition: all 0.3s;
                                box-shadow: 0 4px 15px rgba(102, 126, 234, 0.4);
                            }

                            .btn-save:hover {
                                transform: translateY(-3px);
                                box-shadow: 0 8px 25px rgba(102, 126, 234, 0.6);
                                color: white;
                            }

                            .btn-cancel {
                                background: #6c757d;
                                border: none;
                                padding: 14px 45px;
                                border-radius: 30px;
                                color: white;
                                font-weight: 600;
                                font-size: 1.05rem;
                                transition: all 0.3s;
                            }

                            .btn-cancel:hover {
                                background: #5a6268;
                                transform: translateY(-2px);
                                color: white;
                            }

                            .readonly-field {
                                background-color: #f8f9fa;
                                cursor: not-allowed;
                            }
                        </style>
                    </head>

                    <body>
                        <div class="container">
                            <!-- Error Message -->
                            <c:if test="${not empty error}">
                                <div class="edit-container mb-3">
                                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                        <i class="fas fa-exclamation-circle me-2"></i><strong>Lỗi!</strong> ${error}
                                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                                    </div>
                                </div>
                            </c:if>

                            <div class="edit-container">
                                <div class="edit-card">
                                    <!-- Header -->
                                    <div class="edit-header">
                                        <h3><i class="fas fa-user-edit me-3"></i>Chỉnh Sửa Thông Tin Cá Nhân</h3>
                                        <p class="mb-0 mt-2" style="opacity:0.9;">Cập nhật thông tin của bạn</p>
                                    </div>

                                    <!-- Form Body -->
                                    <div class="edit-body">
                                        <form action="<c:url value='/update' />" method="post"
                                            id="editProfileForm">

                                            <!-- Thông tin đăng nhập -->
                                            <div class="row mb-4">
                                                <div class="col-md-6">
                                                    <label class="form-label"><i class="fas fa-user-circle me-2"></i>Tên
                                                        đăng nhập</label>
                                                    <input type="text" class="form-control readonly-field"
                                                        value="${user.username}" disabled>
                                                    <div class="form-text"><i class="fas fa-info-circle me-1"></i>Không
                                                        thể thay đổi tên đăng nhập</div>
                                                </div>
                                            </div>

                                            <div class="section-divider" data-title="Thông tin cơ bản"></div>

                                            <!-- Row 1: Họ tên & Email -->
                                            <div class="row mb-4">
                                                <div class="col-md-6">
                                                    <label for="fullname" class="form-label required-field"><i
                                                            class="fas fa-user me-2"></i>Họ và tên</label>
                                                    <input type="text" class="form-control" id="fullname"
                                                        name="fullname" value="${user.fullname}" required
                                                        maxlength="100" placeholder="Nhập họ và tên đầy đủ">
                                                </div>
                                                <div class="col-md-6">
                                                    <label for="email" class="form-label required-field"><i
                                                            class="fas fa-envelope me-2"></i>Email</label>
                                                    <input type="email" class="form-control" id="email" name="email"
                                                        value="${user.email}" required maxlength="100"
                                                        placeholder="example@email.com">
                                                </div>
                                            </div>

                                            <!-- Row 2: Số điện thoại & Ngày sinh -->
                                            <div class="row mb-4">
                                                <div class="col-md-6">
                                                    <label for="phoneNumber" class="form-label"><i
                                                            class="fas fa-phone me-2"></i>Số điện thoại</label>
                                                    <input type="tel" class="form-control" id="phoneNumber"
                                                        name="phoneNumber" value="${user.phoneNumber}" maxlength="11"
                                                        pattern="[0-9]{10,11}" placeholder="0123456789">
                                                    <div class="form-text"><i class="fas fa-info-circle me-1"></i>10-11
                                                        chữ số</div>
                                                </div>
                                                <div class="col-md-6">
                                                    <label for="birthDate" class="form-label"><i
                                                            class="fas fa-birthday-cake me-2"></i>Ngày sinh</label>
                                                    <%-- Sử dụng birthDateStr từ request scope để hiển thị đúng định
                                                        dạng cho input type="date" --%>
                                                        <input type="date" class="form-control" id="birthDate"
                                                            name="birthDate" value="${birthDateStr}">
                                                </div>
                                            </div>

                                            <!-- Row 3: Giới tính & CCCD -->
                                            <div class="row mb-4">
                                                <div class="col-md-6">
                                                    <label for="gender" class="form-label"><i
                                                            class="fas fa-venus-mars me-2"></i>Giới tính</label>
                                                    <select class="form-select" id="gender" name="gender">
                                                        <option value="">-- Chọn giới tính --</option>
                                                        <option value="Nam" ${user.gender=='Nam' ? 'selected' : '' }>Nam
                                                        </option>
                                                        <option value="Nữ" ${user.gender=='Nữ' ? 'selected' : '' }>Nữ
                                                        </option>
                                                        <option value="Khác" ${user.gender=='Khác' ? 'selected' : '' }>
                                                            Khác</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-6">
                                                    <label for="cccd" class="form-label"><i
                                                            class="fas fa-id-card me-2"></i>CCCD/CMND</label>
                                                    <input type="text" class="form-control" id="cccd" name="cccd"
                                                        value="${user.cccd}" maxlength="12" pattern="[0-9]{9,12}"
                                                        placeholder="Nhập số CCCD/CMND">
                                                    <div class="form-text"><i class="fas fa-info-circle me-1"></i>9-12
                                                        chữ số</div>
                                                </div>
                                            </div>

                                            <div class="section-divider" data-title="Thông tin bổ sung"></div>

                                            <!-- Row 4: Quốc tịch & Dân tộc -->
                                            <div class="row mb-4">
                                                <div class="col-md-6">
                                                    <label for="nation" class="form-label"><i
                                                            class="fas fa-flag me-2"></i>Quốc tịch</label>
                                                    <input type="text" class="form-control" id="nation" name="nation"
                                                        value="${user.nation}" maxlength="50"
                                                        placeholder="Ví dụ: Việt Nam">
                                                </div>
                                                <div class="col-md-6">
                                                    <label for="ethnicity" class="form-label"><i
                                                            class="fas fa-users me-2"></i>Dân tộc</label>
                                                    <input type="text" class="form-control" id="ethnicity"
                                                        name="ethnicity" value="${user.ethnicity}" maxlength="50"
                                                        placeholder="Ví dụ: Kinh">
                                                </div>
                                            </div>

                                            <!-- Row 5: Địa chỉ -->
                                            <div class="row mb-4">
                                                <div class="col-12">
                                                    <label for="address" class="form-label"><i
                                                            class="fas fa-map-marker-alt me-2"></i>Địa chỉ</label>
                                                    <textarea class="form-control" id="address" name="address" rows="3"
                                                        maxlength="200"
                                                        placeholder="Nhập địa chỉ đầy đủ">${user.address}</textarea>
                                                    <div class="form-text"><i class="fas fa-info-circle me-1"></i>Tối đa
                                                        200 ký tự</div>
                                                </div>
                                            </div>

                                            <!-- Action Buttons -->
                                            <div class="text-center mt-5">
                                                <!-- Nút Lưu -->
                                                <button type="submit" class="btn btn-save me-3">
                                                    <i class="fas fa-save me-2"></i>Lưu thay đổi
                                                </button>

                                                <!-- Nút Hủy bỏ quay về trang xem profile -->
                                                <a href="<c:url value='/view' />"
                                                    class="btn btn-cancel">
                                                    <i class="fas fa-arrow-left"></i> Hủy bỏ
                                                </a>



                                            </div>

                                    </div>

                                    <div class="text-center mt-4">
                                        <small class="text-muted"><i class="fas fa-info-circle me-1"></i>Các
                                            trường có dấu <span class="text-danger">(*)</span> là bắt
                                            buộc</small>
                                    </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        </div>

                        <script
                            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                        <script>
                            // Validation giữ nguyên
                            document.getElementById('editProfileForm').addEventListener('submit', function (e) {
                                const fullname = document.getElementById('fullname').value.trim();
                                const email = document.getElementById('email').value.trim();
                                const phoneNumber = document.getElementById('phoneNumber').value.trim();
                                const cccd = document.getElementById('cccd').value.trim();
                                if (!fullname) { e.preventDefault(); alert('Vui lòng nhập họ và tên!'); document.getElementById('fullname').focus(); return false; }
                                if (fullname.length < 3) { e.preventDefault(); alert('Họ và tên phải có ít nhất 3 ký tự!'); document.getElementById('fullname').focus(); return false; }
                                if (!email) { e.preventDefault(); alert('Vui lòng nhập email!'); document.getElementById('email').focus(); return false; }
                                if (!/^[A-Za-z0-9+_.-]+@(.+)$/.test(email)) { e.preventDefault(); alert('Email không hợp lệ!'); document.getElementById('email').focus(); return false; }
                                if (phoneNumber && !/^[0-9]{10,11}$/.test(phoneNumber)) { e.preventDefault(); alert('Số điện thoại phải là 10-11 chữ số!'); document.getElementById('phoneNumber').focus(); return false; }
                                if (cccd && !/^[0-9]{9,12}$/.test(cccd)) { e.preventDefault(); alert('CCCD/CMND phải là 9-12 chữ số!'); document.getElementById('cccd').focus(); return false; }
                                return true;
                            });
                            document.getElementById('phoneNumber').addEventListener('input', function () { this.value = this.value.replace(/[^0-9]/g, ''); });
                            document.getElementById('cccd').addEventListener('input', function () { this.value = this.value.replace(/[^0-9]/g, ''); });
                        </script>
                    </body>

                    </html>