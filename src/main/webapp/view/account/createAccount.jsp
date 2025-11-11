<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Account - HRMS</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
      <link rel="stylesheet" href="<c:url value='/css/create-account.css'/>">
</head>
<body>

<div class="cv-list-container">
    <h1><i class="fas fa-user-plus"></i> Create New Account</h1>

    <div class="form-container">
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger">${errorMessage}</div>
        </c:if>

        <form action="<c:url value='/account/create'/>" method="post" id="createAccountForm">
            <div class="form-group">
                <p>User ID:</p>
                <input type="number" name="userId" required readonly
                       value="<c:out value='${userId}'/>">
            </div>

            <div class="form-group">
                <p>Username:</p>
                <input type="text" name="username" required
                       value="<c:out value='${param.username}'/>">
            </div>

            <div class="form-group">
                <p>Password:</p>
                <input type="password" name="password" required minlength="6">
            </div>

            <div class="form-group">
                <p>Confirm Password:</p>
                <input type="password" name="confirmPassword" required minlength="6">
            </div>

            <div class="form-group">
                <p>Role:</p>
                <select name="roleID" required>
                    <option value="">-- Select Role --</option>
                    <c:forEach var="role" items="${roleList}">
                        <option value="${role.roleID}"
                            <c:if test="${param.roleID == role.roleID.toString()}">selected</c:if>
                        >${role.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <p>Status:</p>
                <div class="d-flex gap-3">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="isActive" value="true"
                               <c:if test="${param.isActive != 'false'}">checked</c:if>>
                        <label class="form-check-label">Active</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="isActive" value="false"
                               <c:if test="${param.isActive == 'false'}">checked</c:if>>
                        <label class="form-check-label">Inactive</label>
                    </div>
                </div>
            </div>

            <div class="button-row">
                <button type="submit"><i class="fas fa-user-plus me-2"></i>Create Account</button>
                <a href="<c:url value='/account/view'/>"><i class="fas fa-arrow-left me-2"></i>Back</a>
            </div>
        </form>
    </div>
</div>

<script>
    document.getElementById('createAccountForm').addEventListener('submit', function(e) {
        const password = this.password.value;
        const confirmPassword = this.confirmPassword.value;
        if(password !== confirmPassword){
            e.preventDefault();
            alert('Password and Confirm Password do not match!');
        }
        if(password.length < 6){
            e.preventDefault();
            alert('Password must be at least 6 characters!');
        }
    });
</script>

</body>
</html>
