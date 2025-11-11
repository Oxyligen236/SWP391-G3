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

        PayrollDAO payrollDAO = new PayrollDAO();

        List<PayrollType> allTypes = payrollDAO.getAllPayrollTypes();
        List<PayrollType> taxAndInsuranceTypes = new ArrayList<>();

        for (PayrollType type : allTypes) {
            if ("Insurance".equals(type.getCategory()) || "Tax".equals(type.getCategory())) {
                taxAndInsuranceTypes.add(type);
            }
        }

        List<PayrollItem> defaultItems = payrollDAO.getPayrollItemsByPayrollId(1);

        request.setAttribute("payrollTypes", taxAndInsuranceTypes);
        request.setAttribute("payrollItems", defaultItems);

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

        try {
            int typeId = Integer.parseInt(request.getParameter("typeId"));
            double amount = Double.parseDouble(request.getParameter("amount"));
            String amountType = request.getParameter("amountType");

            // ✅ VALIDATION: amount phải > 0
            if (amount <= 0) {
                session.setAttribute("errorMessage", "Amount must be greater than 0!");
                response.sendRedirect(request.getContextPath() + "/tax-and-insurance");
                return;
            }

            PayrollDAO payrollDAO = new PayrollDAO();
            PayrollItem existingItem = payrollDAO.getPayrollItemByPayrollIdAndTypeId(1, typeId);

            boolean success;
            if (existingItem != null) {
                success = payrollDAO.updatePayrollItem(existingItem.getPayrollItemID(), amount, amountType);
            } else {
                success = payrollDAO.addPayrollItem(1, typeId, amount, amountType, false);
            }

            if (success) {
                session.setAttribute("successMessage", "Updated successfully!");
            } else {
                session.setAttribute("errorMessage", "Update failed!");
            }

        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Invalid number format!");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Error: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/tax-and-insurance");
    }
}
