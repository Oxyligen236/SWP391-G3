<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="hrms.model.Department, hrms.model.Position, hrms.model.Degree, java.util.List" %>

<c:url value="/user/create" var="createUserUrl" />
<c:url value="/cv" var="cvListUrl" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create New User - HRMS</title>
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

        .form-group { margin-bottom:15px; }
        .form-group label { font-weight:500; color:#2c3e50; margin-bottom:5px; display:block; }
        .form-control, .form-select { padding:8px 12px; border:1px solid #dee2e6; border-radius:6px; font-size:14px; }
        .form-control:focus, .form-select:focus { border-color:#3498db; box-shadow:0 0 0 0.15rem rgba(52,152,219,0.2); }

        .form-actions { display:flex; flex-wrap:wrap; gap:15px; justify-content:center; margin-top:20px; }
        .btn-custom { padding:8px 20px; font-weight:500; border-radius:8px; font-size:14px; cursor:pointer; transition:all 0.2s ease; text-decoration:none; border:none; }
        .btn-save { background:#3498db; color:#fff; }
        .btn-save:hover { background:#2980b9; transform:translateY(-1px); box-shadow:0 4px 8px rgba(52,152,219,0.3); }
        .btn-reset { background:#6c757d; color:#fff; }
        .btn-reset:hover { background:#5a6268; transform:translateY(-1px); }

        .message { padding:12px; border-radius:10px; margin-bottom:20px; font-size:0.95rem; }
        .message.error { background:#f8d7da; color:#842029; }

        @media (max-width:768px) { .profile-container { padding:0 15px; } }
    </style>
</head>
<body>
<div class="container py-5">
    <div class="profile-container">
        <h2 class="page-title"><i class="fas fa-user-plus"></i> Create New User</h2>

        <!-- ERROR MESSAGE -->
        <c:if test="${not empty error}">
            <div class="message error">${error}</div>
        </c:if>

        <form action="${createUserUrl}" method="post" id="createUserForm">
            <!-- BASIC INFORMATION -->
            <div class="info-card">
                <div class="section-title"><i class="fas fa-user"></i> Basic Information</div>
                <div class="row">
                    <div class="col-md-6 form-group">
                        <label>Full Name <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" name="fullname" value="<c:out value='${formFullname}'/>" required>
                    </div>
                    <div class="col-md-6 form-group">
                        <label>Email <span class="text-danger">*</span></label>
                        <input type="email" class="form-control" name="email" value="<c:out value='${formEmail}'/>" required>
                    </div>
                    <div class="col-md-6 form-group">
                        <label>Phone Number</label>
                        <input type="text" class="form-control" name="phoneNumber" value="<c:out value='${formPhoneNumber}'/>">
                    </div>
                    <div class="col-md-6 form-group">
                        <label>Birth Date</label>
                        <input type="date" class="form-control" name="birthDate" value="<c:out value='${formBirthDate}'/>">
                    </div>
                    <div class="col-md-6 form-group">
                        <label>Gender <span class="text-danger">*</span></label>
                        <select name="gender" class="form-select" required>
                            <option value="">--Select Gender--</option>
                            <c:forEach var="g" items="${genders}">
                                <option value="${g}" <c:if test="${formGender == g}">selected</c:if>>${g}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6 form-group">
                        <label>Address</label>
                        <input type="text" class="form-control" name="address" value="<c:out value='${formAddress}'/>">
                    </div>
                </div>
            </div>

            <!-- ADDITIONAL INFORMATION -->
            <div class="info-card">
                <div class="section-title"><i class="fas fa-info-circle"></i> Additional Information</div>
                <div class="row">
                    <div class="col-md-4 form-group">
                        <label>Ethnicity</label>
                        <input type="text" class="form-control" name="ethnicity" value="<c:out value='${formEthnicity}'/>">
                    </div>
                    <div class="col-md-4 form-group">
                        <label>Nationality</label>
                        <input type="text" class="form-control" name="nation" value="<c:out value='${formNation}'/>">
                    </div>
                    <div class="col-md-4 form-group">
                        <label>ID Number/CCCD <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" name="cccd" value="<c:out value='${formCccd}'/>" required pattern="[0-9]{12}" title="CCCD must be 12 digits">
                    </div>
                </div>
            </div>

            <!-- JOB INFORMATION -->
            <div class="info-card">
                <div class="section-title"><i class="fas fa-briefcase"></i> Job Information</div>
                <div class="row">
                    <div class="col-md-4 form-group">
                        <label>Department <span class="text-danger">*</span></label>
                        <select name="departmentId" id="departmentId" class="form-select" required onchange="filterPositions()">
                            <option value="">--Select Department--</option>
                            <c:forEach var="d" items="${departments}">
                                <option value="${d.departmentId}" 
                                    <c:if test="${formDepartmentId != null && formDepartmentId eq d.departmentId.toString()}">selected</c:if>>
                                    ${d.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4 form-group">
                        <label>Position <span class="text-danger">*</span></label>
                        <select name="positionId" id="positionId" class="form-select" required>
                            <option value="">--Select Position--</option>
                        </select>
                    </div>
                    <div class="col-md-4 form-group">
                        <label>Degree <span class="text-danger">*</span></label>
                        <select name="degreeId" class="form-select" required>
                            <option value="">--Select Degree--</option>
                            <c:forEach var="deg" items="${degrees}">
                                <option value="${deg.degreeId}" 
                                    <c:if test="${formDegreeId != null && formDegreeId eq deg.degreeId.toString()}">selected</c:if>>
                                    ${deg.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <!-- FORM ACTIONS -->
            <div class="form-actions">
                <button type="submit" class="btn-save btn-custom"><i class="fas fa-user-plus"></i> Create User</button>
                <button type="reset" class="btn-reset btn-custom"><i class="fas fa-times"></i> Reset</button>
                <a href="${cvListUrl}" class="btn-reset btn-custom"><i class="fas fa-arrow-left"></i> Back to CV List</a>
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
                <h5 class="modal-title"><i class="fas fa-check-circle"></i> Success</h5>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <p><strong>${success}</strong></p>
                <p>User ID: <code>${newUserId}</code></p>
            </div>
            <div class="modal-footer">
                <a href="${cvListUrl}" class="btn btn-secondary"><i class="fas fa-arrow-left"></i> Back to CV List</a>
                <a href="<c:url value='/addContracts' />?userId=${newUserId}" class="btn btn-success">
                    <i class="fas fa-file-contract"></i> Create Contract
                </a>
            </div>
        </div>
    </div>
</div>
</c:if>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>
<script>
// POSITIONS DATA
const allPositions = [
    <c:forEach var="p" items="${positions}" varStatus="status">
        { positionId: ${p.positionId}, name: "${p.name}", departmentId: ${p.departmentId} }<c:if test="${!status.last}">,</c:if>
    </c:forEach>
];

const selectedPositionId = "${formPositionId}";

function filterPositions() {
    const departmentSelect = document.getElementById('departmentId');
    const positionSelect = document.getElementById('positionId');
    const selectedDepartmentId = departmentSelect.value;

    positionSelect.innerHTML = '<option value="">--Select Position--</option>';
    if (!selectedDepartmentId) {
        positionSelect.disabled = true;
        return;
    }

    const filteredPositions = allPositions.filter(pos => pos.departmentId == selectedDepartmentId);
    if (filteredPositions.length > 0) {
        positionSelect.disabled = false;
        filteredPositions.forEach(pos => {
            const option = document.createElement('option');
            option.value = pos.positionId;
            option.textContent = pos.name;
            if (selectedPositionId && pos.positionId == selectedPositionId) option.selected = true;
            positionSelect.appendChild(option);
        });
    } else {
        positionSelect.disabled = true;
        const option = document.createElement('option');
        option.value = '';
        option.textContent = 'No positions available for this department';
        positionSelect.appendChild(option);
    }
}

document.addEventListener('DOMContentLoaded', () => {
    const departmentSelect = document.getElementById('departmentId');
    if (departmentSelect.value) filterPositions();
    else document.getElementById('positionId').disabled = true;

    const successModal = document.getElementById('userCreatedModal');
    if (successModal) new bootstrap.Modal(successModal).show();
});

document.getElementById('createUserForm').addEventListener('reset', () => {
    setTimeout(() => {
        const positionSelect = document.getElementById('positionId');
        positionSelect.disabled = true;
        positionSelect.innerHTML = '<option value="">--Select Position--</option>';
    }, 10);
});
</script>
</body>
</html>
