package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.model.Payroll;
import hrms.service.PayrollService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/payroll/personal")
public class PersonalPayrollServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userID = request.getParameter("userId");
        PayrollService payrollService = new PayrollService();
        List<Payroll> personalPayroll = payrollService.getPayrollByUserId(Integer.parseInt(userID));
        request.setAttribute("PersonalPayrolls", personalPayroll);
        request.getRequestDispatcher("/view/payroll/personal-payroll.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
