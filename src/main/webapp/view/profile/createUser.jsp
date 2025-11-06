<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="hrms.model.Department, hrms.model.Position, hrms.model.Degree, java.util.List" %>

<c:url value="/user/create" var="createUserUrl" />
<c:url value="/cv" var="cvListUrl" />
<c:url value="/contract/create" var="createContractUrl" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create New User</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" rel="stylesheet">
    <style>
        * { margin:0; padding:0; box-sizing:border-box; }
        body { font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif; background-color:#f8faff; }
        .container { max-width:900px; margin:20px auto; padding:20px; }
        h1 { color:#0d6efd; margin-bottom:20px; font-size:28px; font-weight:700; border-bottom:3px solid #0d6efd; padding-bottom:10px; }
        .form-card { background:#fff; padding:25px; border-radius:8px; box-shadow:0 2px 8px rgba(0,0,0,0.1); }
        .form-group { display:flex; flex-direction:column; margin-bottom:15px; }
        .form-group label { font-weight:600; color:#0d3b66; margin-bottom:5px; }
        .form-group input[type="text"], .form-group input[type="email"], .form-group input[type="date"], .form-group select {
            padding:10px 12px; border:1px solid #dee2e6; border-radius:6px; font-size:14px; background-color:#fff;
        }
        .form-group input:focus, .form-group select:focus { border-color:#0d6efd; outline:none; box-shadow:0 0 0 0.2rem rgba(13,110,253,0.25); }
        .form-actions { display:flex; flex-wrap:wrap; gap:15px; justify-content:center; margin-top:20px; }
        .form-actions button, .form-actions a { padding:10px 25px; border:none; border-radius:6px; font-weight:600; font-size:14px; cursor:pointer; text-decoration:none; }
        .btn-primary { background:#a2d2ff; color:#0d3b66; }
        .btn-primary:hover { background:#7bb8ff; color:#0d3b66; transform:translateY(-1px); box-shadow:0 4px 8px rgba(162,210,255,0.3); }
        .btn-outline { background:#d3e0ff; color:#0d3b66; }
        .btn-outline:hover { background:#aebfff; color:#0d3b66; transform:translateY(-1px); box-shadow:0 4px 8px rgba(174,191,255,0.3); }
        .message { padding:12px; border-radius:10px; margin-bottom:20px; font-size:0.95rem; }
        .message.success { background:#d1e7dd; color:#0f5132; }
        .message.error { background:#f8d7da; color:#842029; }
        @media (max-width:768px) { .form-actions { flex-direction:column; } }
    </style>
</head>
<body>
<div class="container">
    <h1><i class="fas fa-user-plus"></i> Create New User</h1>
    <div class="form-card">

        <c:if test="${not empty error}">
            <div class="message error">${error}</div>
        </c:if>

        <form action="${createUserUrl}" method="post">
            <div class="row">
                <div class="col-md-6 form-group">
                    <label>Full Name:</label>
                    <input type="text" name="fullname" value="<c:out value='${formFullname}'/>" required>
                </div>
                <div class="col-md-6 form-group">
                    <label>Email:</label>
                    <input type="email" name="email" value="<c:out value='${formEmail}'/>" required>
                </div>

                <div class="col-md-6 form-group">
                    <label>Phone Number:</label>
                    <input type="text" name="phoneNumber" value="<c:out value='${formPhoneNumber}'/>">
                </div>
                <div class="col-md-6 form-group">
                    <label>Birth Date:</label>
                    <input type="date" name="birthDate" value="<c:out value='${formBirthDate}'/>">
                </div>

                <div class="col-md-6 form-group">
                    <label>Gender:</label>
                    <select name="gender" required>
                        <option value="">--Select Gender--</option>
                        <c:forEach var="g" items="${genders}">
                            <option value="${g}" <c:if test="${formGender == g}">selected</c:if>>${g}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-6 form-group">
                    <label>Address:</label>
                    <input type="text" name="address" value="<c:out value='${formAddress}'/>">
                </div>

                <div class="col-md-4 form-group">
                    <label>Ethnicity:</label>
                    <input type="text" name="ethnicity" value="<c:out value='${formEthnicity}'/>">
                </div>
                <div class="col-md-4 form-group">
                    <label>Nationality:</label>
                    <input type="text" name="nation" value="<c:out value='${formNation}'/>">
                </div>
                <div class="col-md-4 form-group">
                    <label>ID Number:</label>
                    <input type="text" name="cccd" value="<c:out value='${formCccd}'/>" required>
                </div>

                <div class="col-md-4 form-group">
                    <label>Department:</label>
                    <select name="departmentId" required>
                        <option value="">--Select Department--</option>
                        <c:forEach var="d" items="${departments}">
                            <option value="${d.departmentId}" <c:if test="${formDepartmentId != null && formDepartmentId eq d.departmentId.toString()}">selected</c:if>>${d.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4 form-group">
                    <label>Position:</label>
                    <select name="positionId" required>
                        <option value="">--Select Position--</option>
                        <c:forEach var="p" items="${positions}">
                            <option value="${p.positionId}" <c:if test="${formPositionId != null && formPositionId eq p.positionId.toString()}">selected</c:if>>${p.name}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-4 form-group">
                    <label>Degree:</label>
                    <select name="degreeId" required>
                        <option value="">--Select Degree--</option>
                        <c:forEach var="deg" items="${degrees}">
                            <option value="${deg.degreeId}" <c:if test="${formDegreeId != null && formDegreeId eq deg.degreeId.toString()}">selected</c:if>>${deg.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-primary"><i class="fas fa-user-plus"></i> Create User</button>
                <button type="reset" class="btn btn-outline"><i class="fas fa-times"></i> Reset</button>
                <a href="${cvListUrl}" class="btn btn-outline"><i class="fas fa-arrow-left"></i> Back to CV List</a>
            </div>
        </form>
    </div>
</div>

<!-- SUCCESS MODAL -->
<c:if test="${not empty success}">
<div class="modal fade" id="userCreatedModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header bg-success text-white">
                <h5 class="modal-title">
                    <i class="fas fa-check-circle"></i> Success
                </h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <p><strong>${success}</strong></p>
                <p>User ID: <code>${newUserId}</code></p>
            </div>
            <div class="modal-footer">
                <a href="${cvListUrl}" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> Back to CV List
                </a>
                <a href="${createContractUrl}?userId=${newUserId}" class="btn btn-success">
                    <i class="fas fa-file-contract"></i> Create Contract
                </a>
            </div>
        </div>
    </div>
</div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    const modal = new bootstrap.Modal(document.getElementById('userCreatedModal'));
    modal.show();
});
</script>
</c:if>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>