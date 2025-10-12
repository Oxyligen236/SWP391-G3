package hrms.controller;

import java.io.IOException;

import hrms.dao.PayrollDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/payroll/company")
public class CompanyPayrollServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userID = request.getParameter("userId");
        PayrollDAO payrollDAO = new PayrollDAO();
        int userId = Integer.parseInt(userID);
        request.setAttribute("payroll", payrollDAO.getAllPayrollByUserId(userId));
        request.getRequestDispatcher("/view/payroll/personal-payroll.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
