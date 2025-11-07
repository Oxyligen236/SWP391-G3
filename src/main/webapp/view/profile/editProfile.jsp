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
            <div class="alert alert-success alert-dismissible fade show text-center" role="alert">
                <i class="fas fa-check-circle me-2"></i>${successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="fas fa-exclamation-circle me-2"></i>
                <div>${errorMessage}</div>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <form action="<c:url value='/edit' />" method="post" id="editProfileForm">
            <!-- Basic Information -->
            <div class="info-card">
                <div class="section-title"><i class="fas fa-user"></i> Basic Information</div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Full Name <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" name="fullname" value="${user.fullname}" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">ID / CCCD</label>
                        <input type="text" class="form-control" name="cccd" value="${user.cccd}" 
                               pattern="[0-9]{12}" title="CCCD must be 12 digits">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Birth Date</label>
                        <input type="date" class="form-control" name="birthDate" 
                               value="<fmt:formatDate value='${user.birthDate}' pattern='yyyy-MM-dd'/>">
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Gender <span class="text-danger">*</span></label>
                        <select name="gender" class="form-select" required>
                            <option value="">-- Select Gender --</option>
                            <c:forEach var="g" items="${genders}">
                                <option value="${g}" <c:if test="${user.gender==g}">selected</c:if>>${g}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Phone Number <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" name="phoneNumber" value="${user.phoneNumber}" 
                               pattern="^(0|\+84)[0-9]{9}$" title="Phone format: 0xxxxxxxxx or +84xxxxxxxxx" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Email <span class="text-danger">*</span></label>
                        <input type="email" class="form-control" name="email" value="${user.email}" required>
                    </div>
                </div>
            </div>

            <!-- Additional Information -->
            <div class="info-card">
                <div class="section-title"><i class="fas fa-info-circle"></i> Additional Information</div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Address <span class="text-danger">*</span></label>
                        <input type="text" class="form-control" name="address" value="${user.address}" required>
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
                        <label class="form-label">
                            Department
                            <c:if test="${roleId == 2 || roleId == 3}">
                                <span class="text-danger">*</span>
                            </c:if>
                        </label>
                        <select name="departmentId" id="departmentId" class="form-select" 
                                <c:if test="${roleId != 2 && roleId != 3}">disabled</c:if>
                                onchange="filterPositions()">
                            <option value="">-- Select Department --</option>
                            <c:forEach var="dept" items="${departmentList}">
                                <option value="${dept.departmentId}" 
                                    <c:if test="${user.departmentId==dept.departmentId}">selected</c:if>>
                                    ${dept.name}
                                </option>
                            </c:forEach>
                        </select>
                        <c:if test="${roleId != 2 && roleId != 3}">
                            <small class="text-muted">Only HR can edit this field</small>
                        </c:if>
                    </div>
                    
                    <div class="col-md-4 mb-3">
                        <label class="form-label">
                            Position
                            <c:if test="${roleId == 2 || roleId == 3}">
                                <span class="text-danger">*</span>
                            </c:if>
                        </label>
                        <select name="positionId" id="positionId" class="form-select" 
                                <c:if test="${roleId != 2 && roleId != 3}">disabled</c:if>>
                            <option value="">-- Select Position --</option>
                            <!-- Positions will be loaded by JavaScript -->
                        </select>
                        <c:if test="${roleId != 2 && roleId != 3}">
                            <small class="text-muted">Only HR can edit this field</small>
                        </c:if>
                    </div>
                    
                    <div class="col-md-4 mb-3">
                        <label class="form-label">Degree</label>
                        <select name="degreeId" class="form-select">
                            <option value="">-- Select Degree --</option>
                            <c:forEach var="d" items="${degreeList}">
                                <option value="${d.degreeId}" 
                                    <c:if test="${user.degreeId==d.degreeId}">selected</c:if>>
                                    ${d.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <!-- Action Buttons -->
            <div class="form-actions">
                <button type="submit" class="btn-save btn-custom">
                    <i class="fas fa-save"></i> Save Changes
                </button>
                <a href="<c:url value='/view' />" class="btn-back btn-custom">
                    <i class="fas fa-arrow-left"></i> Back
                </a>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>

<script>
// ===== DATA FROM JSP - ALL POSITIONS =====
const allPositions = [
    <c:forEach var="p" items="${positionList}" varStatus="status">
    {
        positionId: ${p.positionId},
        name: "${p.name}",
        departmentId: ${p.departmentId}
    }<c:if test="${!status.last}">,</c:if>
    </c:forEach>
];

// Current selected values from user data
const currentDepartmentId = "${user.departmentId}";
const currentPositionId = "${user.positionId}";
const canEditJobInfo = ${roleId == 2 || roleId == 3};

console.log("Loaded " + allPositions.length + " positions");
console.log("Current department: " + currentDepartmentId);
console.log("Current position: " + currentPositionId);
console.log("Can edit job info: " + canEditJobInfo);

/**
 * Filter positions based on selected department
 */
function filterPositions() {
    const departmentSelect = document.getElementById('departmentId');
    const positionSelect = document.getElementById('positionId');
    const selectedDepartmentId = departmentSelect.value;
    
    console.log("Department changed to: " + selectedDepartmentId);
    
    // Clear position dropdown
    positionSelect.innerHTML = '<option value="">-- Select Position --</option>';
    
    if (!selectedDepartmentId) {
        console.log("No department selected");
        return;
    }
    
    // Filter positions by departmentId
    const filteredPositions = allPositions.filter(function(pos) {
        return pos.departmentId == selectedDepartmentId;
    });
    
    console.log("Filtered " + filteredPositions.length + " positions");
    
    // Add filtered options to dropdown
    if (filteredPositions.length > 0) {
        filteredPositions.forEach(function(pos) {
            const option = document.createElement('option');
            option.value = pos.positionId;
            option.textContent = pos.name;
            
            // Keep current selection if it matches
            if (pos.positionId == currentPositionId) {
                option.selected = true;
            }
            
            positionSelect.appendChild(option);
        });
    } else {
        const option = document.createElement('option');
        option.value = '';
        option.textContent = 'No positions available for this department';
        positionSelect.appendChild(option);
    }
}

/**
 * Initialize on page load
 */
document.addEventListener('DOMContentLoaded', function() {
    // If department is already selected, filter positions
    if (currentDepartmentId) {
        console.log("Initializing with department: " + currentDepartmentId);
        filterPositions();
    }
});

/**
 * Form validation
 */
document.getElementById('editProfileForm').addEventListener('submit', function(e) {
    // Validate CCCD
    const cccd = document.querySelector('input[name="cccd"]').value;
    if (cccd && !/^[0-9]{12}$/.test(cccd)) {
        alert('CCCD must be exactly 12 digits!');
        e.preventDefault();
        return false;
    }
    
    // Validate phone
    const phone = document.querySelector('input[name="phoneNumber"]').value;
    if (phone && !/^(0|\+84)[0-9]{9}$/.test(phone)) {
        alert('Invalid phone format! Use: 0xxxxxxxxx or +84xxxxxxxxx');
        e.preventDefault();
        return false;
    }
    
    // Validate age (must be 18+)
    const birthDate = document.querySelector('input[name="birthDate"]').value;
    if (birthDate) {
        const birth = new Date(birthDate);
        const today = new Date();
        const age = Math.floor((today - birth) / (365.25 * 24 * 60 * 60 * 1000));
        
        if (age < 18) {
            alert('User must be at least 18 years old!');
            e.preventDefault();
            return false;
        }
        
        if (birth > today) {
            alert('Birth date cannot be in the future!');
            e.preventDefault();
            return false;
        }
    }
    
    return true;
});
</script>
</body>
</html>