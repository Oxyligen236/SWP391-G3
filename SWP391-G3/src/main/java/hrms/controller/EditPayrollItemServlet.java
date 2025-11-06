package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.PayrollDAO;
import hrms.dto.PayrollDTO;
import hrms.model.Account;
import hrms.model.PayrollType;
import hrms.service.PayrollService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/payroll/edit")
public class EditPayrollItemServlet extends HttpServlet {

    private final PayrollService payrollService = new PayrollService();
    private final PayrollDAO payrollDAO = new PayrollDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        String payrollID = request.getParameter("payrollID");
        String userID = request.getParameter("userID");

        try {
            int payrollId = Integer.parseInt(payrollID);
            int userId = Integer.parseInt(userID);

            // Get payroll detail
            PayrollDTO payrollDetail = payrollService.getPayrollByUserIdAndPayrollId(userId, payrollId);
            
            if (payrollDetail == null) {
                request.setAttribute("error", "Payroll record not found.");
                response.sendRedirect(request.getContextPath() + "/payroll/company");
                return;
            }

            // Check if payroll status is "Paid" - cannot edit paid payroll
            if ("Paid".equals(payrollDetail.getStatus())) {
                request.setAttribute("error", "Cannot edit paid payroll.");
                response.sendRedirect(request.getContextPath() + "/payroll/company/detail?payrollID=" + payrollId + "&userID=" + userId);
                return;
            }

            // Get all payroll types for dropdown (only Adjustment category)
            // Exclude Type_ID 8, 9, 10 (auto-calculated from attendance: Làm thêm giờ, Phạt đi muộn, Nghỉ không phép)
            List<PayrollType> adjustmentTypes = payrollDAO.getAllPayrollTypes()
                .stream()
                .filter(type -> "Adjustment".equals(type.getCategory()))
                .filter(type -> type.getPayrollTypeID() != 8 
                             && type.getPayrollTypeID() != 9 
                             && type.getPayrollTypeID() != 10)
                .toList();

            request.setAttribute("payrollDetail", payrollDetail);
            request.setAttribute("adjustmentTypes", adjustmentTypes);
            request.getRequestDispatcher("/view/payroll/editPayrollItem.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/error");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        String action = request.getParameter("action");
        String payrollID = request.getParameter("payrollID");
        String userID = request.getParameter("userID");

        try {
            int payrollId = Integer.parseInt(payrollID);
            int userId = Integer.parseInt(userID);

            boolean success = false;
            String message = "";

            switch (action) {
                case "add":
                    success = handleAddItem(request, payrollId);
                    message = success ? "Adjustment added successfully!" : "Failed to add adjustment.";
                    break;

                case "update":
                    success = handleUpdateItem(request);
                    message = success ? "Adjustment updated successfully!" : "Failed to update adjustment.";
                    break;

                case "delete":
                    success = handleDeleteItem(request);
                    message = success ? "Adjustment deleted successfully!" : "Failed to delete adjustment.";
                    break;

                default:
                    message = "Invalid action.";
            }

            // Recalculate NetSalary after any change
            if (success) {
                recalculateNetSalary(payrollId);
            }

            request.getSession().setAttribute(success ? "success" : "error", message);
            response.sendRedirect(request.getContextPath() + "/payroll/edit?payrollID=" + payrollId + "&userID=" + userId);

        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/payroll/company");
        }
    }

    private boolean handleAddItem(HttpServletRequest request, int payrollId) {
        try {
            int typeId = Integer.parseInt(request.getParameter("typeId"));
            double amount = Double.parseDouble(request.getParameter("amount"));
            String amountType = request.getParameter("amountType");

            // Prevent adding auto-calculated types (Type_ID 8, 9, 10)
            if (typeId == 8 || typeId == 9 || typeId == 10) {
                return false; // Cannot add auto-calculated types manually
            }

            // Validate amount
            if (amount <= 0) {
                return false;
            }

            // Check if item already exists for this payroll and type
            if (payrollDAO.getPayrollItemByPayrollIdAndTypeId(payrollId, typeId) != null) {
                return false; // Already exists
            }

            // Get type info to determine if it's positive (bonus/allowance) or negative (deduction)
            PayrollType type = payrollDAO.getPayrollTypeById(typeId);
            if (type == null) {
                return false;
            }

            // Determine isPositive based on type name
            boolean isPositive = isPositiveAdjustment(type.getTypeName());

            return payrollDAO.addPayrollItem(payrollId, typeId, amount, amountType, isPositive);

        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean handleUpdateItem(HttpServletRequest request) {
        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            double amount = Double.parseDouble(request.getParameter("amount"));
            String amountType = request.getParameter("amountType");

            // Validate amount
            if (amount <= 0) {
                return false;
            }

            return payrollDAO.updatePayrollItem(itemId, amount, amountType);

        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean handleDeleteItem(HttpServletRequest request) {
        try {
            int itemId = Integer.parseInt(request.getParameter("itemId"));
            
            
            return payrollDAO.deletePayrollItem(itemId);

        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void recalculateNetSalary(int payrollId) {
        // This method will be implemented in PayrollService
        // For now, we'll add it to PayrollDAO
        payrollDAO.recalculateNetSalary(payrollId);
    }

    private boolean isPositiveAdjustment(String typeName) {
        // Positive adjustments (additions): bonuses, allowances, overtime
        String[] positiveKeywords = {"Thưởng", "Phụ cấp", "Làm thêm", "thưởng", "phụ cấp", "làm thêm"};
        
        for (String keyword : positiveKeywords) {
            if (typeName.contains(keyword)) {
                return true;
            }
        }
        
        // Negative adjustments (deductions): penalties
        return false;
    }
}
