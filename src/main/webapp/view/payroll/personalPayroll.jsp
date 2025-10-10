<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Personal Payroll</title>
        </head>

        <body>
            <h1>Personal Payroll</h1>
            <c:if test="${not empty error}">
                <div style="color: red; text-align: center">${error}</div>
            </c:if>
            <c:if test="${not empty message}">
                <div style="color: green; text-align: center">${message}</div>
            </c:if>
            <table>
                <tr>
                    <th>Month</th>
                    <th>Year</th>
                    <th>Base Salary</th>
                    <th>Bonus</th>
                    <th>Deductions</th>
                    <th>Total Salary</th>
                </tr>
                <c:forEach var="payroll" items="${payroll}">
                    <tr>
                        <td>${payroll.month}</td>
                        <td>${payroll.year}</td>
                        <td>${payroll.baseSalary}</td>
                        <td>${payroll.bonus}</td>
                        <td>${payroll.deductions}</td>
                        <td>${payroll.totalSalary}</td>
                    </tr>
                </c:forEach>
            </table>

        </body>

        </html>