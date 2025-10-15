package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.DepartmentDAO;
import hrms.dao.PositionDAO;
import hrms.dto.PayrollDTO;
import hrms.model.Department;
import hrms.model.Position;
import hrms.service.PayrollService;
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
        PayrollService payrollService = new PayrollService();
        DepartmentDAO departmentDAO = new DepartmentDAO();
        PositionDAO positionDAO = new PositionDAO();
        List<Department> departments = departmentDAO.getAll();
        List<Position> positions = positionDAO.getAll();
        List<PayrollDTO> payrolls = payrollService.getAllCompanyPayrolls();
        if (payrolls == null || payrolls.isEmpty()) {
            request.setAttribute("error", "No payroll records found.");
            request.getRequestDispatcher("/view/payroll/companyPayroll.jsp").forward(request, response);
        }
        request.setAttribute("payrolls", payrolls);
        request.setAttribute("departments", departments);
        request.setAttribute("positions", positions);
        request.getRequestDispatcher("/view/payroll/companyPayroll.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String monthStr = request.getParameter("month");
        String yearStr = request.getParameter("year");
        String status = request.getParameter("status");
        String department = request.getParameter("department");
        String position = request.getParameter("position");

        int month;
        int year;
        PayrollService payrollService = new PayrollService();
        DepartmentDAO departmentDAO = new DepartmentDAO();
        PositionDAO positionDAO = new PositionDAO();
        List<Department> departments = departmentDAO.getAll();
        List<Position> positions = positionDAO.getAll();
        try {
            month = (monthStr == null || monthStr.isEmpty()) ? 0 : Integer.parseInt(monthStr);
            year = (yearStr == null || yearStr.isEmpty()) ? 0 : Integer.parseInt(yearStr);
            request.setAttribute("departments", departments);
            request.setAttribute("positions", positions);
            List<PayrollDTO> payrolls = payrollService.searchPayroll(userName, department, position, month, year, status);
            if (payrolls == null || payrolls.isEmpty()) {
                request.setAttribute("error", "No payroll records found for the given criteria.");
                request.getRequestDispatcher("/view/payroll/companyPayroll.jsp").forward(request, response);
                return;
            }
            request.setAttribute("payrolls", payrolls);
            request.getRequestDispatcher("/view/payroll/companyPayroll.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid month or year format.");
            request.getRequestDispatcher("/view/payroll/companyPayroll.jsp").forward(request, response);
        }

    }

}
