<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Account - HRMS</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://kit.fontawesome.com/a2e0e6ad53.js" crossorigin="anonymous"></script>
    <style>
        /* Reset & global */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f5f5f5;
        }
        .cv-list-container {
            max-width: 700px;
            margin: 40px auto;
            padding: 20px;
        }
        h1 {
            color: #2c3e50;
            margin-bottom: 20px;
            font-size: 28px;
            font-weight: 700;
            border-bottom: 3px solid #3498db;
            padding-bottom: 10px;
            text-align: center;
        }
        .form-container {
            background: #fff;
            padding: 25px;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        .form-group {
            display: flex;
            flex-direction: column;
            margin-bottom: 15px;
        }
        .form-group p {
            font-weight: 600;
            color: #2c3e50;
            margin-bottom: 6px;
        }
        .form-group input[type="text"],
        .form-group input[type="number"],
        .form-group input[type="password"],
        .form-group select {
            padding: 10px 12px;
            border: 1px solid #dee2e6;
            border-radius: 6px;
            font-size: 14px;
            transition: all 0.2s ease;
        }
        .form-group input:focus,
        .form-group select:focus {
            border-color: #3498db;
            outline: none;
            box-shadow: 0 0 0 0.2rem rgba(52, 152, 219, 0.25);
        }
        .button-row {
            display: flex;
            justify-content: center;
            gap: 15px;
            margin-top: 20px;
        }
        .button-row button,
        .button-row a {
            padding: 10px 25px;
            font-weight: 600;
            border-radius: 6px;
            text-decoration: none;
            font-size: 14px;
            transition: all 0.2s ease;
        }
        .button-row button {
            background-color: #3498db;
            color: white;
            border: none;
        }
        .button-row button:hover {
            background-color: #2980b9;
            transform: translateY(-1px);
            box-shadow: 0 4px 8px rgba(52,152,219,0.3);
        }
        .button-row a {
            background-color: #6c757d;
            color: white;
        }
        .button-row a:hover {
            background-color: #5a6268;
            transform: translateY(-1px);
            box-shadow: 0 4px 8px rgba(108,117,125,0.3);
        }
        .alert {
            margin-bottom: 20px;
            border-radius: 6px;
        }
    </style>
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
                <input type="number" name="userID" required>
            </div>
            <div class="form-group">
                <p>Username:</p>
                <input type="text" name="username" required>
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
                        <option value="${role.roleID}">${role.name}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <p>Status:</p>
                <div class="d-flex gap-3">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="isActive" value="true" checked>
                        <label class="form-check-label">Active</label>
                    </div>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="isActive" value="false">
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

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.getElementById('createAccountForm').addEventListener('submit', function(e) {
        const password = this.password.value;
        const confirmPassword = this.confirmPassword.value;

        if(password !== confirmPassword){
            e.preventDefault();
            alert('Password and Confirm Password do not match!');
            return false;
        }
        if(password.length < 6){
            e.preventDefault();
            alert('Password must be at least 6 characters!');
            return false;
        }
    });
</script>

</body>
</html>
