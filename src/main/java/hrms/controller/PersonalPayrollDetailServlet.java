package hrms.controller;

import java.io.IOException;

import hrms.dto.PayrollDTO;
import hrms.model.Account;
import hrms.service.PayrollService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/payroll/personal/detail")
public class PersonalPayrollDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String payrollID = request.getParameter("payrollID");
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }
        int userId = account.getUserID();
        try {
            int payrollId = Integer.parseInt(payrollID);
            PayrollService payrollService = new PayrollService();
            PayrollDTO payrollDetail = payrollService.getPayrollByUserIdAndPayrollId(userId, payrollId);
            if (payrollDetail == null) {

                request.setAttribute("error", "Không tìm thấy chi tiết lương.");
                request.getRequestDispatcher("/view/payroll/personalPayroll.jsp").forward(request, response);
                return;
            }
            request.setAttribute("payrollDetail", payrollDetail);
            request.getRequestDispatcher("/view/payroll/payrollDetail.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/error");
        }

    }

}
