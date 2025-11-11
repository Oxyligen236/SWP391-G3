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

        int itemsPerPage = 5;
        if (request.getParameter("itemsPerPage") != null) {
            try {
                itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage"));
            } catch (NumberFormatException e) {
                itemsPerPage = 5;
            }
        }

        int currentPage = 1;
        if (request.getParameter("page") != null) {
            try {
                currentPage = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        PayrollService payrollService = new PayrollService();
        List<PayrollDTO> payrollList = payrollService.getAllCompanyPayrollDetails();

        if (payrollList == null || payrollList.isEmpty()) {
            request.setAttribute("message", "No payroll records found.");
            request.getRequestDispatcher("/view/payroll/companyPayroll.jsp").forward(request, response);
            return;
        }

        int totalItems = payrollList.size();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        if (currentPage > totalPages && totalPages > 0) {
            currentPage = totalPages;
        }

        int startIndex = (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

        List<PayrollDTO> paginatedPayrolls = payrollList.subList(startIndex, endIndex);

        request.setAttribute("payrolls", paginatedPayrolls);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("itemsPerPage", itemsPerPage);

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

        int itemsPerPage = 5;
        if (request.getParameter("itemsPerPage") != null) {
            try {
                itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage"));
            } catch (NumberFormatException e) {
                itemsPerPage = 5;
            }
        }

        int currentPage = 1;
        if (request.getParameter("page") != null) {
            try {
                currentPage = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        int month;
        int year;
        int userId;
        PayrollService payrollService = new PayrollService();

        try {
            month = (monthStr == null || monthStr.isEmpty()) ? 0 : Integer.parseInt(monthStr);
            year = (yearStr == null || yearStr.isEmpty()) ? 0 : Integer.parseInt(yearStr);
            userId = (userID == null || userID.isEmpty()) ? 0 : Integer.parseInt(userID);

            List<PayrollDTO> payrollList = payrollService.searchPayroll(userId, month, year, status);

            if (payrollList == null || payrollList.isEmpty()) {
                request.setAttribute("message", "No payroll records found for the given criteria.");
                request.getRequestDispatcher("/view/payroll/companyPayroll.jsp").forward(request, response);
                return;
            }

            int totalItems = payrollList.size();
            int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

            if (currentPage > totalPages && totalPages > 0) {
                currentPage = totalPages;
            }

            int startIndex = (currentPage - 1) * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

            List<PayrollDTO> paginatedPayrolls = payrollList.subList(startIndex, endIndex);

            request.setAttribute("payrolls", paginatedPayrolls);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("itemsPerPage", itemsPerPage);

            request.getRequestDispatcher("/view/payroll/companyPayroll.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid month or year format.");
            request.getRequestDispatcher("/view/payroll/companyPayroll.jsp").forward(request, response);
        }
    }
}
