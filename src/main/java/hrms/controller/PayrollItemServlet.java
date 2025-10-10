package hrms.controller;

import java.io.IOException;

import hrms.model.Account;
import hrms.service.PayrollService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/payroll/item")
public class PayrollItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String userID = String.valueOf(account.getUserID());
        PayrollService payrollService = new PayrollService();
        try {
            int userId = Integer.parseInt(userID);
            request.setAttribute("payrollItems", payrollService.getAllPayrollItemsByUserID(userId));
            request.getRequestDispatcher("/view/payroll/personalPayroll.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid user ID");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
