package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dto.PayrollDTO;
import hrms.model.Account;
import hrms.service.PayrollService;
import hrms.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/payroll/personal")
public class PersonalPayrollServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        PayrollService payrollService = new PayrollService();
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }
        int userId = account.getUserID();
        List<PayrollDTO> personalPayrolls = payrollService.getAllPayrollByUserId(userId);
        if (personalPayrolls == null || personalPayrolls.isEmpty()) {
            request.setAttribute("error", "No personal payroll data found.");
        } else {
            request.setAttribute("PersonalPayrolls", personalPayrolls);
        }
        request.getRequestDispatcher("/view/payroll/personalPayroll.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String monthParam = request.getParameter("month");
        String yearParam = request.getParameter("year");
        int month;
        int year;
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }
        int userId = account.getUserID();
        UserService userService = new UserService();
        if (userService.getUserById(userId) == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }
        PayrollService payrollService = new PayrollService();
        try {
            month = Integer.parseInt(monthParam);
            year = Integer.parseInt(yearParam);
            List<PayrollDTO> personalPayrolls = payrollService.searchPayroll(userId, month, year, "");
            if (personalPayrolls == null || personalPayrolls.isEmpty()) {
                request.setAttribute("error", "No personal payroll data found.");
            } else {
                request.setAttribute("PersonalPayrolls", personalPayrolls);
            }
            request.getRequestDispatcher("/view/payroll/personalPayroll.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Month and year must be integers.");
            request.getRequestDispatcher("/view/payroll/personalPayroll.jsp").forward(request, response);
        }

    }

}
