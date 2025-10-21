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

        String userName = request.getParameter("userName");
        String monthStr = request.getParameter("month");
        String yearStr = request.getParameter("year");
        String status = request.getParameter("status");
        String department = request.getParameter("department");
        String position = request.getParameter("position");

        String pageStr = request.getParameter("page");
        String itemsPerPageStr = request.getParameter("itemsPerPage");

        int itemsPerPage = 5;
        if (itemsPerPageStr != null && !itemsPerPageStr.trim().isEmpty()) {
            try {
                itemsPerPage = Integer.parseInt(itemsPerPageStr);
                if (itemsPerPage <= 0) {
                    itemsPerPage = 5;
                }
            } catch (NumberFormatException e) {
                itemsPerPage = 5;
            }
        }

        int currentPage = 1;
        if (pageStr != null && !pageStr.trim().isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageStr);
                if (currentPage < 1) {
                    currentPage = 1;
                }
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        PayrollService payrollService = new PayrollService();
        DepartmentDAO departmentDAO = new DepartmentDAO();
        PositionDAO positionDAO = new PositionDAO();

        List<Department> departments = departmentDAO.getAll();
        List<Position> positions = positionDAO.getAll();

        request.setAttribute("departments", departments);
        request.setAttribute("positions", positions);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("userName", userName);
        request.setAttribute("month", monthStr);
        request.setAttribute("year", yearStr);
        request.setAttribute("status", status);
        request.setAttribute("department", department);
        request.setAttribute("position", position);

        try {
            List<PayrollDTO> payrolls;

            if ((userName != null && !userName.trim().isEmpty())
                    || (department != null && !department.trim().isEmpty())
                    || (position != null && !position.trim().isEmpty())
                    || (monthStr != null && !monthStr.trim().isEmpty())
                    || (yearStr != null && !yearStr.trim().isEmpty())
                    || (status != null && !status.trim().isEmpty())) {

                int month = 0;
                int year = 0;

                if (monthStr != null && !monthStr.trim().isEmpty()) {
                    month = Integer.parseInt(monthStr);
                }
                if (yearStr != null && !yearStr.trim().isEmpty()) {
                    year = Integer.parseInt(yearStr);
                }

                payrolls = payrollService.searchPayroll(userName, department, position, month, year, status);
            } else {
                payrolls = payrollService.getAllCompanyPayrolls();
            }

            if (payrolls == null || payrolls.isEmpty()) {
                request.setAttribute("error", "No payroll data found.");
                request.setAttribute("totalPages", 0);
                request.setAttribute("totalPayrolls", 0);
                request.getRequestDispatcher("/view/payroll/companyPayroll.jsp").forward(request, response);
                return;
            }

            int totalPayrolls = payrolls.size();
            int totalPages = (int) Math.ceil((double) totalPayrolls / itemsPerPage);

            if (currentPage > totalPages && totalPages > 0) {
                currentPage = totalPages;
            }
            if (currentPage < 1) {
                currentPage = 1;
            }

            int startIndex = (currentPage - 1) * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, totalPayrolls);
            List<PayrollDTO> paginatedPayrolls = payrolls.subList(startIndex, endIndex);

            request.setAttribute("payrolls", paginatedPayrolls);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalPayrolls", totalPayrolls);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid month or year format.");
            request.setAttribute("totalPages", 0);
            request.setAttribute("totalPayrolls", 0);
        }

        request.getRequestDispatcher("/view/payroll/companyPayroll.jsp").forward(request, response);
    }
}
