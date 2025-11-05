package hrms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hrms.dao.PayrollDAO;
import hrms.model.Account;
import hrms.model.PayrollItem;
import hrms.model.PayrollType;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/tax-and-insurance")
public class TaxAndInsuranceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/view/login/login.jsp");
            return;
        }

        String payrollIdStr = request.getParameter("payrollId");
        if (payrollIdStr == null || payrollIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/payroll-list");
            return;
        }

        int payrollId = Integer.parseInt(payrollIdStr);
        PayrollDAO payrollDAO = new PayrollDAO();

        // Lấy tất cả payroll types có category là Insurance hoặc Tax
        List<PayrollType> allTypes = payrollDAO.getAllPayrollTypes();
        List<PayrollType> insuranceAndTaxTypes = new ArrayList<>();
        for (PayrollType type : allTypes) {
            if ("Insurance".equals(type.getCategory()) || "Tax".equals(type.getCategory())) {
                insuranceAndTaxTypes.add(type);
            }
        }

        // Lấy các payroll items hiện tại
        List<PayrollItem> currentItems = payrollDAO.getPayrollItemsByPayrollId(payrollId);

        request.setAttribute("payrollId", payrollId);
        request.setAttribute("payrollTypes", insuranceAndTaxTypes);
        request.setAttribute("currentItems", currentItems);

        request.getRequestDispatcher("/view/payroll/taxAndInsurance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/view/login/login.jsp");
            return;
        }

        String payrollIdStr = request.getParameter("payrollId");
        if (payrollIdStr == null || payrollIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/payroll-list");
            return;
        }

        int payrollId = Integer.parseInt(payrollIdStr);
        PayrollDAO payrollDAO = new PayrollDAO();

        // Lấy danh sách các type IDs được submit
        String[] typeIds = request.getParameterValues("typeId");
        String[] amounts = request.getParameterValues("amount");
        String[] amountTypes = request.getParameterValues("amountType");
        String[] itemIds = request.getParameterValues("itemId");

        boolean success = true;

        if (typeIds != null && amounts != null && amountTypes != null) {
            for (int i = 0; i < typeIds.length; i++) {
                int typeId = Integer.parseInt(typeIds[i]);
                double amount = Double.parseDouble(amounts[i]);
                String amountType = amountTypes[i];
                boolean isPositive = false; // Insurance và Tax luôn là deduction

                if (itemIds != null && i < itemIds.length && !itemIds[i].isEmpty()) {
                    // Update existing item
                    int itemId = Integer.parseInt(itemIds[i]);
                    success = success && payrollDAO.updatePayrollItem(itemId, amount, amountType);
                } else {
                    // Add new item
                    success = success && payrollDAO.addPayrollItem(payrollId, typeId, amount, amountType, isPositive);
                }
            }
        }

        if (success) {
            session.setAttribute("successMessage", "Cập nhật bảo hiểm và thuế thành công!");
        } else {
            session.setAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật!");
        }

        response.sendRedirect(request.getContextPath() + "/tax-and-insurance?payrollId=" + payrollId);
    }
}
