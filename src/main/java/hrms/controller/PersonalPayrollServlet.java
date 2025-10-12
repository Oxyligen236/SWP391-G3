package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.model.Account;
import hrms.model.Payroll;
import hrms.service.PayrollService;
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
        List<Payroll> personalPayrolls = payrollService.getAllPayrollByUserId(userId);
        if (personalPayrolls == null || personalPayrolls.isEmpty()) {
            request.setAttribute("error", "Không có dữ liệu lương cá nhân.");
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

        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }
        int userId = account.getUserID();
        PayrollService payrollService = new PayrollService();
        List<Payroll> personalPayrolls = payrollService.searchPayroll(userId, monthParam, yearParam);
        if (personalPayrolls == null || personalPayrolls.isEmpty()) {
            request.setAttribute("error", "Không có dữ liệu lương cá nhân.");
            request.getRequestDispatcher("/view/payroll/personalPayroll.jsp").forward(request, response);
        }
        request.setAttribute("PersonalPayrolls", personalPayrolls);
        request.getRequestDispatcher("/view/payroll/personalPayroll.jsp").forward(request, response);
    }

}
