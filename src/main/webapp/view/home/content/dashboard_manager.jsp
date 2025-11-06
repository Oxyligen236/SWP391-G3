<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>

<!-- Employee Dashboard Content -->
<div class="row">
    <div class="col-12">
        <h4 class="mb-4">Welcome, ${sessionScope.users.fullname}!</h4>
        
        <div class="row">
            <!-- Welcome Card -->
            <div class="col-xl-12 col-lg-12 mt-4">
                <div class="card border-0 shadow rounded">
                    <div class="card-body">
                        <h5 class="card-title">Welcome to HRMS Manager Dashboard</h5>
                        <p class="text-muted">Have a nice day!!!</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
