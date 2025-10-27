package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dto.PayrollDTO;
import hrms.service.PayrollService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/payroll/company")
public class CompanyPayrollServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }
        PayrollService payrollService = new PayrollService();
        List<PayrollDTO> payrolls = payrollService.getAllPayrollByUserId(1);//Integer.parseInt(request.getParameter("userID"))
        if (payrolls == null || payrolls.isEmpty()) {
            request.setAttribute("error", "No payroll records found.");
            request.getRequestDispatcher("/view/payroll/companyPayroll.jsp").forward(request, response);
            return;
        }
        request.setAttribute("payrolls", payrolls);
        request.setAttribute("userID", payrolls.get(0).getUserID());
        request.setAttribute("userName", payrolls.get(0).getUserName());
        request.getRequestDispatcher("/view/payroll/companyPayroll.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        String userID = request.getParameter("userID");
        String monthStr = request.getParameter("month");
        String yearStr = request.getParameter("year");
        String status = request.getParameter("status");

        int month;
        int year;
        int userId;
        PayrollService payrollService = new PayrollService();

        try {
            month = (monthStr == null || monthStr.isEmpty()) ? 0 : Integer.parseInt(monthStr);
            year = (yearStr == null || yearStr.isEmpty()) ? 0 : Integer.parseInt(yearStr);
            userId = (userID == null || userID.isEmpty()) ? 0 : Integer.parseInt(userID);
            List<PayrollDTO> payrolls = payrollService.searchPayroll(userId, month, year, status);
            if (payrolls == null || payrolls.isEmpty()) {
                request.setAttribute("error", "không tìm thấy dữ liệu lương.");
                request.getRequestDispatcher("/view/payroll/companyPayroll.jsp").forward(request, response);
                return;
            }
            request.setAttribute("payrolls", payrolls);
            request.setAttribute("userID", payrolls.get(0).getUserID());
            request.setAttribute("userName", payrolls.get(0).getUserName());
            request.getRequestDispatcher("/view/payroll/companyPayroll.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid month or year format.");
            request.getRequestDispatcher("/view/payroll/companyPayroll.jsp").forward(request, response);
        }

    }

}
