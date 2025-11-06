<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Profile - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a2e0e6ad53.js" crossorigin="anonymous"></script>
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body { font-family:"Segoe UI", Tahoma, Geneva, Verdana, sans-serif; background-color:#f5f5f5; }

        .profile-container { max-width:950px; margin:40px auto; padding:20px; }
        h2.page-title { color:#2c3e50; font-weight:700; text-align:center; margin-bottom:25px; border-bottom:3px solid #3498db; padding-bottom:10px; }

        .info-card {
            background:#fff;
            border-radius:12px;
            padding:25px 30px;
            margin-bottom:20px;
            box-shadow:0 2px 8px rgba(0,0,0,0.1);
        }

        .section-title { font-weight:600; color:#2c3e50; margin-bottom:15px; font-size:18px; border-bottom:2px solid #3498db; padding-bottom:5px; }

        .form-control, .form-select { padding:8px 12px; border:1px solid #dee2e6; border-radius:6px; font-size:14px; }
        .form-control:focus, .form-select:focus { border-color:#3498db; box-shadow:0 0 0 0.15rem rgba(52,152,219,0.2); }

        .form-actions { display:flex; flex-wrap:wrap; gap:15px; justify-content:center; margin-top:20px; }
        .btn-custom { padding:8px 20px; font-weight:500; border-radius:8px; font-size:14px; cursor:pointer; transition:all 0.2s ease; text-decoration:none; }
        .btn-save { background:#3498db; color:#fff; border:none; }
        .btn-save:hover { background:#2980b9; transform:translateY(-1px); box-shadow:0 4px 8px rgba(52,152,219,0.3); }
        .btn-back { background:#6c757d; color:#fff; border:none; text-decoration:none; }
        .btn-back:hover { background:#5a6268; transform:translateY(-1px); }

        @media (max-width:768px) {
            .profile-container { padding:0 15px; }
        }
    </style>
</head>
<body>
<div class="container py-5">
    <div class="profile-container">
        <h2 class="page-title"><i class="fas fa-user-edit"></i> Edit Profile</h2>

        <c:if test="${not empty successMessage}">
            <div class="alert alert-success text-center">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger text-center">${errorMessage}</div>
        </c:if>

        <form action="<c:url value='/edit' />" method="post">
            <!-- Basic Information -->
            <div class="info-card">
                <div class="section-title"><i class="fas fa-user"></i> Basic Information</div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Full Name</label>
                        <input type="text" class="form-control" name="fullname" value="${user.fullname}" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">ID / CCCD</label>
                        <input type="text" class="form-control" name="cccd" value="${user.cccd}">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Birth Date</label>
                        <input type="date" class="form-control" name="birthDate" value="<fmt:formatDate value='${user.birthDate}' pattern='yyyy-MM-dd'/>">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Gender</label>
                        <select name="gender" class="form-select">
                            <c:forEach var="g" items="${genders}">
                                <option value="${g}" <c:if test="${user.gender==g}">selected</c:if>>${g}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Phone Number</label>
                        <input type="text" class="form-control" name="phoneNumber" value="${user.phoneNumber}">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Email</label>
                        <input type="email" class="form-control" name="email" value="${user.email}">
                    </div>
                </div>
            </div>

            <!-- Additional Information -->
            <div class="info-card">
                <div class="section-title"><i class="fas fa-info-circle"></i> Additional Information</div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Address</label>
                        <input type="text" class="form-control" name="address" value="${user.address}">
                    </div>
                    <div class="col-md-3 mb-3">
                        <label class="form-label">Ethnicity</label>
                        <input type="text" class="form-control" name="ethnicity" value="${user.ethnicity}">
                    </div>
                    <div class="col-md-3 mb-3">
                        <label class="form-label">Nationality</label>
                        <input type="text" class="form-control" name="nation" value="${user.nation}">
                    </div>
                </div>
            </div>

            <!-- Job Information -->
            <div class="info-card">
                <div class="section-title"><i class="fas fa-briefcase"></i> Job Information</div>
                <div class="row">
                    <div class="col-md-4 mb-3">
                        <label class="form-label">Department</label>
                        <select name="departmentId" class="form-select" <c:if test="${roleId != 2 && roleId != 3}">disabled</c:if>>
                            <option value="">-- Select Department --</option>
                            <c:forEach var="dept" items="${departmentList}">
                                <option value="${dept.departmentId}" <c:if test="${user.departmentId==dept.departmentId}">selected</c:if>>${dept.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label class="form-label">Position</label>
                        <select name="positionId" class="form-select" <c:if test="${roleId != 2 && roleId != 3}">disabled</c:if>>
                            <option value="">-- Select Position --</option>
                            <c:forEach var="pos" items="${positionList}">
                                <option value="${pos.positionId}" <c:if test="${user.positionId==pos.positionId}">selected</c:if>>${pos.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4 mb-3">
                        <label class="form-label">Degree</label>
                        <select name="degreeId" class="form-select">
                            <option value="">-- Select Degree --</option>
                            <c:forEach var="d" items="${degreeList}">
                                <option value="${d.degreeId}" <c:if test="${user.degreeId==d.degreeId}">selected</c:if>>${d.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <!-- Action Buttons -->
            <div class="form-actions">
                <button type="submit" class="btn-save btn-custom"><i class="fas fa-save"></i> Save Changes</button>
                <a href="<c:url value='/view' />" class="btn-back btn-custom"><i class="fas fa-arrow-left"></i> Back</a>
            </div>
        </form>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
