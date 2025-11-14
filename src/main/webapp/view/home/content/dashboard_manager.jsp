<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<!-- Manager Dashboard Content -->
<div class="container-fluid p-4">
    <div class="row mb-4">
        <div class="col-12">
            <h4 class="mb-2">Welcome, ${sessionScope.users.fullname}!</h4>
            <p class="text-muted mb-0">Quick Access to Manager Functions</p>
        </div>
    </div>
    
    <!-- Quick Access Grid -->
    <div class="admin-grid">
        <!-- View Profile -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/view')">
            <div class="icon-box bg-soft-primary">
                <i class="uil uil-user"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-user me-2"></i>View Profile</h5>
            <p class="grid-desc">View your profile</p>
        </div>

        <!-- Edit Profile -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/edit')">
            <div class="icon-box bg-soft-success">
                <i class="uil uil-edit"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-user me-2"></i>Edit Profile</h5>
            <p class="grid-desc">Update your information</p>
        </div>

        <!-- My Account -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/account/view')">
            <div class="icon-box bg-soft-warning">
                <i class="uil uil-users-alt"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-users-alt me-2"></i>My Account</h5>
            <p class="grid-desc">View your account</p>
        </div>

        <!-- User List -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/userlist')">
            <div class="icon-box bg-soft-info">
                <i class="uil uil-list-ul"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-users-alt me-2"></i>User List</h5>
            <p class="grid-desc">Manage employee list</p>
        </div>

        <!-- Add Position -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/option/position')">
            <div class="icon-box bg-soft-danger">
                <i class="uil uil-award"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-users-alt me-2"></i>Add Position</h5>
            <p class="grid-desc">Manage positions</p>
        </div>

        <!-- Add Department -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/option/department')">
            <div class="icon-box bg-soft-secondary">
                <i class="uil uil-building"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-users-alt me-2"></i>Add Department</h5>
            <p class="grid-desc">Manage departments</p>
        </div>

        <!-- Add Degree -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/option/degree')">
            <div class="icon-box bg-soft-dark">
                <i class="uil uil-graduation-cap"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-users-alt me-2"></i>Add Degree</h5>
            <p class="grid-desc">Manage degrees</p>
        </div>

        <!-- My Contract -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/myContracts')">
            <div class="icon-box bg-soft-primary">
                <i class="uil uil-file-contract"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-file-contract me-2"></i>My Contract</h5>
            <p class="grid-desc">View your contract</p>
        </div>

        <!-- Create Contract -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/addContracts')">
            <div class="icon-box bg-soft-success">
                <i class="uil uil-file-plus"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-file-contract me-2"></i>Create Contract</h5>
            <p class="grid-desc">Add new contract</p>
        </div>

        <!-- Contract List -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/viewContracts')">
            <div class="icon-box bg-soft-warning">
                <i class="uil uil-files-landscapes"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-file-contract me-2"></i>Contract List</h5>
            <p class="grid-desc">View all contracts</p>
        </div>

        <!-- CV List -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/cv')">
            <div class="icon-box bg-soft-info">
                <i class="uil uil-file-alt"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-file-alt me-2"></i>CV List</h5>
            <p class="grid-desc">Review applications</p>
        </div>

        <!-- Job Description -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/jdlist')">
            <div class="icon-box bg-soft-danger">
                <i class="uil uil-briefcase"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-briefcase me-2"></i>Job Description</h5>
            <p class="grid-desc">Manage job postings</p>
        </div>

        <!-- Personal Payroll -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/payroll/personal')">
            <div class="icon-box bg-soft-secondary">
                <i class="uil uil-money-bill"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-money-bill me-2"></i>Personal Payroll</h5>
            <p class="grid-desc">View your payroll</p>
        </div>

        <!-- Company Payroll -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/payroll/company')">
            <div class="icon-box bg-soft-dark">
                <i class="uil uil-money-withdrawal"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-money-bill me-2"></i>Company Payroll</h5>
            <p class="grid-desc">Company payroll system</p>
        </div>

        <!-- Salary Processor -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/salary')">
            <div class="icon-box bg-soft-primary">
                <i class="uil uil-calculator"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-money-bill me-2"></i>Salary Processor</h5>
            <p class="grid-desc">Process salaries</p>
        </div>

        <!-- My Ticket -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/ticketList')">
            <div class="icon-box bg-soft-success">
                <i class="uil uil-ticket"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-ticket me-2"></i>My Ticket</h5>
            <p class="grid-desc">View your tickets</p>
        </div>

        <!-- Create Ticket -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/create-ticket')">
            <div class="icon-box bg-soft-warning">
                <i class="uil uil-plus-circle"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-ticket me-2"></i>Create Ticket</h5>
            <p class="grid-desc">Submit new ticket</p>
        </div>

        <!-- Employee Tickets -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/department-ticket')">
            <div class="icon-box bg-soft-info">
                <i class="uil uil-list-ul"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-ticket me-2"></i>Employee Tickets</h5>
            <p class="grid-desc">View employee tickets</p>
        </div>

        <!-- My Attendance -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/myattendance')">
            <div class="icon-box bg-soft-danger">
                <i class="uil uil-calendar-alt"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-calendar-alt me-2"></i>My Attendance</h5>
            <p class="grid-desc">View your attendance</p>
        </div>

        <!-- Company Attendance -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/company-attendance')">
            <div class="icon-box bg-soft-secondary">
                <i class="uil uil-calendar-slash"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-calendar-alt me-2"></i>Company Attendance</h5>
            <p class="grid-desc">View company attendance</p>
        </div>

        <!-- Shift Management -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/shift')">
            <div class="icon-box bg-soft-dark">
                <i class="uil uil-clock"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-clock me-2"></i>Shift Management</h5>
            <p class="grid-desc">Configure work shifts</p>
        </div>

        <!-- Work History -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/workhistory')">
            <div class="icon-box bg-soft-primary">
                <i class="uil uil-history"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-history me-2"></i>Work History</h5>
            <p class="grid-desc">Employee work records</p>
        </div>

        <!-- Add Option -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/option/add')">
            <div class="icon-box bg-soft-success">
                <i class="uil uil-plus-square"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-history me-2"></i>Add Option</h5>
            <p class="grid-desc">Add department options</p>
        </div>

        <!-- Edit Tax -->
        <div class="grid-item hover-card" onclick="window.parent.loadInFrame('${pageContext.request.contextPath}/tax-and-insurance')">
            <div class="icon-box bg-soft-warning">
                <i class="uil uil-percentage"></i>
            </div>
            <h5 class="grid-title"><i class="uil uil-history me-2"></i>Edit Tax</h5>
            <p class="grid-desc">Configure tax rates</p>
        </div>
    </div>
</div>

<!-- Custom CSS for Grid Layout -->
<style>
    /* Welcome Section Styling */
    .row.mb-4 h4 {
        font-size: 28px;
        font-weight: 700;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        background-clip: text;
        margin-bottom: 8px;
        animation: fadeInDown 0.6s ease-out;
    }
    
    .row.mb-4 .text-muted {
        font-size: 15px;
        color: #6c757d !important;
        font-weight: 500;
        letter-spacing: 0.3px;
        position: relative;
        padding-left: 15px;
        animation: fadeInUp 0.6s ease-out 0.2s backwards;
    }
    
    .row.mb-4 .text-muted::before {
        content: '';
        position: absolute;
        left: 0;
        top: 50%;
        transform: translateY(-50%);
        width: 4px;
        height: 20px;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        border-radius: 2px;
        animation: slideIn 0.4s ease-out 0.4s backwards;
    }
    
    @keyframes fadeInDown {
        from {
            opacity: 0;
            transform: translateY(-20px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }
    
    @keyframes fadeInUp {
        from {
            opacity: 0;
            transform: translateY(10px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }
    
    @keyframes slideIn {
        from {
            width: 0;
            opacity: 0;
        }
        to {
            width: 4px;
            opacity: 1;
        }
    }
    
    .admin-grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
        gap: 20px;
        padding: 0;
    }
    
    .grid-item {
        background: #fff;
        border-radius: 12px;
        padding: 30px 20px;
        text-align: center;
        cursor: pointer;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
        transition: all 0.3s ease;
        border: 1px solid #f0f0f0;
    }
    
    .grid-item:hover {
        transform: translateY(-8px);
        box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
    }
    
    .icon-box {
        width: 70px;
        height: 70px;
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 0 auto 20px;
        transition: all 0.3s ease;
    }
    
    .grid-item:hover .icon-box {
        transform: scale(1.1) rotate(5deg);
    }
    
    .icon-box i {
        font-size: 32px;
    }
    
    .grid-title {
        font-size: 16px;
        font-weight: 600;
        margin-bottom: 8px;
        color: #2c3e50;
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 8px;
    }
    
    .grid-title i {
        font-size: 20px;
        opacity: 1;
        flex-shrink: 0;
    }
    
    .grid-desc {
        font-size: 13px;
        color: #7f8c8d;
        margin: 0;
        line-height: 1.4;
    }
    
    /* Color schemes */
    .bg-soft-primary { 
        background: linear-gradient(135deg, rgba(47, 85, 212, 0.1), rgba(47, 85, 212, 0.05));
    }
    .bg-soft-primary i { color: #2f55d4; }
    
    .bg-soft-success { 
        background: linear-gradient(135deg, rgba(46, 202, 139, 0.1), rgba(46, 202, 139, 0.05));
    }
    .bg-soft-success i { color: #2eca8b; }
    
    .bg-soft-warning { 
        background: linear-gradient(135deg, rgba(241, 116, 37, 0.1), rgba(241, 116, 37, 0.05));
    }
    .bg-soft-warning i { color: #f17425; }
    
    .bg-soft-info { 
        background: linear-gradient(135deg, rgba(23, 162, 184, 0.1), rgba(23, 162, 184, 0.05));
    }
    .bg-soft-info i { color: #17a2b8; }
    
    .bg-soft-danger { 
        background: linear-gradient(135deg, rgba(228, 63, 82, 0.1), rgba(228, 63, 82, 0.05));
    }
    .bg-soft-danger i { color: #e43f52; }
    
    .bg-soft-secondary { 
        background: linear-gradient(135deg, rgba(108, 117, 125, 0.1), rgba(108, 117, 125, 0.05));
    }
    .bg-soft-secondary i { color: #6c757d; }
    
    .bg-soft-dark { 
        background: linear-gradient(135deg, rgba(52, 58, 64, 0.1), rgba(52, 58, 64, 0.05));
    }
    .bg-soft-dark i { color: #343a40; }
    
    /* Responsive */
    @media (max-width: 768px) {
        .admin-grid {
            grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
            gap: 15px;
        }
        
        .grid-item {
            padding: 20px 15px;
        }
        
        .icon-box {
            width: 55px;
            height: 55px;
            margin-bottom: 15px;
        }
        
        .icon-box i {
            font-size: 26px;
        }
        
        .grid-title {
            font-size: 14px;
        }
        
        .grid-desc {
            font-size: 12px;
        }
    }
</style>
