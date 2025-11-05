<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<c:url value='/css/payroll-detail.css'/>">
    <title>Edit Employee Bonuses & Deductions</title>
    <style>
        .edit-container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
            background: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .alert {
            padding: 12px 20px;
            margin-bottom: 20px;
            border-radius: 4px;
            font-weight: 500;
        }
        
        .alert-success {
            background-color: #d4edda;
            border: 1px solid #c3e6cb;
            color: #155724;
        }
        
        .alert-error {
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            color: #721c24;
        }
        
        .info-section {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 6px;
            margin-bottom: 20px;
        }
        
        .info-section h3 {
            margin-top: 0;
            color: #2c3e50;
        }
        
        .items-table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        
        .items-table th,
        .items-table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        
        .items-table th {
            background-color: #34495e;
            color: white;
            font-weight: 600;
        }
        
        .items-table tr:hover {
            background-color: #f5f5f5;
        }
        
        .badge {
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: 600;
        }
        
        .badge-income {
            background-color: #d4edda;
            color: #155724;
        }
        
        .badge-deduction {
            background-color: #f8d7da;
            color: #721c24;
        }
        
        .badge-fixed {
            background-color: #cce5ff;
            color: #004085;
        }
        
        .badge-percent {
            background-color: #fff3cd;
            color: #856404;
        }
        
        .btn {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 500;
            transition: all 0.3s;
        }
        
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #0056b3;
        }
        
        .btn-success {
            background-color: #28a745;
            color: white;
        }
        
        .btn-success:hover {
            background-color: #218838;
        }
        
        .btn-danger {
            background-color: #dc3545;
            color: white;
        }
        
        .btn-danger:hover {
            background-color: #c82333;
        }
        
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        
        .btn-secondary:hover {
            background-color: #5a6268;
        }
        
        .add-form {
            background: #f8f9fa;
            padding: 20px;
            border-radius: 6px;
            margin-bottom: 30px;
        }
        
        .form-group {
            margin-bottom: 15px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: 600;
            color: #2c3e50;
        }
        
        .form-group select,
        .form-group input {
            width: 100%;
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }
        
        .form-row {
            display: grid;
            grid-template-columns: 2fr 1fr 1fr 1fr;
            gap: 15px;
        }
        
        .actions {
            display: flex;
            gap: 5px;
        }
        
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.4);
        }
        
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 400px;
            border-radius: 8px;
        }
        
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }
        
        .close:hover {
            color: #000;
        }

        .action-buttons {
            margin-top: 20px;
            display: flex;
            gap: 10px;
        }
    </style>
</head>

<body>
    <div class="edit-container">
        <h1>Edit Employee Bonuses, Deductions & Allowances</h1>

        <!-- Alert Messages -->
        <c:if test="${not empty sessionScope.success}">
            <div class="alert alert-success">
                ${sessionScope.success}
            </div>
            <c:remove var="success" scope="session"/>
        </c:if>

        <c:if test="${not empty sessionScope.error}">
            <div class="alert alert-error">
                ${sessionScope.error}
            </div>
            <c:remove var="error" scope="session"/>
        </c:if>

        <!-- Employee Info -->
        <div class="info-section">
            <h3>Employee Information</h3>
            <p><strong>Name:</strong> ${payrollDetail.userName}</p>
            <p><strong>Department:</strong> ${payrollDetail.userDepartment}</p>
            <p><strong>Position:</strong> ${payrollDetail.userPosition}</p>
            <p><strong>Period:</strong> ${payrollDetail.month}/${payrollDetail.year}</p>
            <p><strong>Base Salary:</strong> 
                <fmt:formatNumber value="${payrollDetail.baseSalary}" type="number" groupingUsed="true"/> ₫
            </p>
            <p><strong>Current Net Salary:</strong> 
                <fmt:formatNumber value="${payrollDetail.netSalary}" type="number" groupingUsed="true"/> ₫
            </p>
        </div>

        <!-- Add New Adjustment Form -->
        <div class="add-form">
            <h3>Add New Adjustment</h3>
            <form action="${pageContext.request.contextPath}/payroll/edit" method="post">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="payrollID" value="${payrollDetail.payrollID}">
                <input type="hidden" name="userID" value="${payrollDetail.userID}">
                
                <div class="form-row">
                    <div class="form-group">
                        <label for="typeId">Adjustment Type *</label>
                        <select name="typeId" id="typeId" required>
                            <option value="">-- Select Type --</option>
                            <c:forEach var="type" items="${adjustmentTypes}">
                                <option value="${type.payrollTypeID}">${type.typeName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="amount">Amount *</label>
                        <input type="number" name="amount" id="amount" 
                               min="0" step="0.01" required placeholder="Enter amount">
                    </div>
                    
                    <div class="form-group">
                        <label for="amountType">Type *</label>
                        <select name="amountType" id="amountType" required>
                            <option value="fixed">Fixed (₫)</option>
                            <option value="percent">Percent (%)</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label>&nbsp;</label>
                        <button type="submit" class="btn btn-success">Add Adjustment</button>
                    </div>
                </div>
            </form>
        </div>

        <!-- Current Adjustments Table -->
        <h3>Current Adjustments (Bonuses, Deductions & Allowances)</h3>
        <table class="items-table">
            <thead>
                <tr>
                    <th>Type Name</th>
                    <th>Category</th>
                    <th>Amount</th>
                    <th>Amount Type</th>
                    <th>Impact</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${payrollDetail.payrollItems}">
                    <c:if test="${item.category == 'Adjustment'}">
                        <tr>
                            <td>${item.typeName}</td>
                            <td>${item.category}</td>
                            <td>
                                <fmt:formatNumber value="${item.amount}" type="number" groupingUsed="true" maxFractionDigits="2"/>
                            </td>
                            <td>
                                <span class="badge ${item.amountType == 'fixed' ? 'badge-fixed' : 'badge-percent'}">
                                    ${item.amountType == 'fixed' ? 'Fixed (₫)' : 'Percent (%)'}
                                </span>
                            </td>
                            <td>
                                <span class="badge ${item.positive ? 'badge-income' : 'badge-deduction'}">
                                    ${item.positive ? 'Income (+)' : 'Deduction (-)'}
                                </span>
                            </td>
                            <td class="actions">
                                <button class="btn btn-primary btn-sm" 
                                        onclick="openEditModal(${item.payrollItemId}, '${item.typeName}', ${item.amount}, '${item.amountType}')">
                                    Edit
                                </button>
                                <form action="${pageContext.request.contextPath}/payroll/edit" method="post" 
                                      style="display:inline;" 
                                      onsubmit="return confirm('Are you sure you want to delete this adjustment?');">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="payrollID" value="${payrollDetail.payrollID}">
                                    <input type="hidden" name="userID" value="${payrollDetail.userID}">
                                    <input type="hidden" name="itemId" value="${item.payrollItemId}">
                                    <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>

        <!-- Fixed Items (Insurance & Tax - Read Only) -->
        <h3>Fixed Deductions (Insurance & Tax)</h3>
        <table class="items-table">
            <thead>
                <tr>
                    <th>Type Name</th>
                    <th>Category</th>
                    <th>Amount</th>
                    <th>Amount Type</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${payrollDetail.payrollItems}">
                    <c:if test="${item.category == 'Insurance' || item.category == 'Tax'}">
                        <tr>
                            <td>${item.typeName}</td>
                            <td>${item.category}</td>
                            <td>
                                <fmt:formatNumber value="${item.amount}" type="number" groupingUsed="true" maxFractionDigits="2"/>
                            </td>
                            <td>
                                <span class="badge ${item.amountType == 'fixed' ? 'badge-fixed' : 'badge-percent'}">
                                    ${item.amountType == 'fixed' ? 'Fixed (₫)' : 'Percent (%)'}
                                </span>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </tbody>
        </table>

        <div class="action-buttons">
            <a href="${pageContext.request.contextPath}/payroll/company/detail?payrollID=${payrollDetail.payrollID}&userID=${payrollDetail.userID}" 
               class="btn btn-secondary">Back to Detail</a>
            <a href="${pageContext.request.contextPath}/payroll/company" 
               class="btn btn-secondary">Back to Payroll List</a>
        </div>
    </div>

    <!-- Edit Modal -->
    <div id="editModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeEditModal()">&times;</span>
            <h3>Edit Adjustment</h3>
            <form action="${pageContext.request.contextPath}/payroll/edit" method="post">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="payrollID" value="${payrollDetail.payrollID}">
                <input type="hidden" name="userID" value="${payrollDetail.userID}">
                <input type="hidden" name="itemId" id="editItemId">
                
                <div class="form-group">
                    <label>Type Name</label>
                    <input type="text" id="editTypeName" readonly 
                           style="background-color: #e9ecef; cursor: not-allowed;">
                </div>
                
                <div class="form-group">
                    <label for="editAmount">Amount *</label>
                    <input type="number" name="amount" id="editAmount" 
                           min="0" step="0.01" required>
                </div>
                
                <div class="form-group">
                    <label for="editAmountType">Amount Type *</label>
                    <select name="amountType" id="editAmountType" required>
                        <option value="fixed">Fixed (₫)</option>
                        <option value="percent">Percent (%)</option>
                    </select>
                </div>
                
                <div style="margin-top: 20px; display: flex; gap: 10px;">
                    <button type="submit" class="btn btn-success">Save Changes</button>
                    <button type="button" class="btn btn-secondary" onclick="closeEditModal()">Cancel</button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function openEditModal(itemId, typeName, amount, amountType) {
            document.getElementById('editModal').style.display = 'block';
            document.getElementById('editItemId').value = itemId;
            document.getElementById('editTypeName').value = typeName;
            document.getElementById('editAmount').value = amount;
            document.getElementById('editAmountType').value = amountType;
        }

        function closeEditModal() {
            document.getElementById('editModal').style.display = 'none';
        }

        // Close modal when clicking outside
        window.onclick = function(event) {
            var modal = document.getElementById('editModal');
            if (event.target == modal) {
                closeEditModal();
            }
        }
    </script>
</body>

</html>
