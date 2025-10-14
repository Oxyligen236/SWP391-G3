package hrms.controller;

import java.io.IOException;

import hrms.service.PayrollService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/payroll/company/detail")
public class CompanyPayrollDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PayrollService payrollService = new PayrollService();
        request.setAttribute("payrolls", payrollService.getAllCompanyPayrolls());
        request.getRequestDispatcher("/view/payroll/companyPayroll.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
