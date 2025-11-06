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

        // Get pagination parameters
        int page = 1;
        int itemsPerPage = 5;

        String pageParam = request.getParameter("page");
        String itemsPerPageParam = request.getParameter("itemsPerPage");

        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        if (itemsPerPageParam != null && !itemsPerPageParam.isEmpty()) {
            try {
                itemsPerPage = Integer.parseInt(itemsPerPageParam);
                if (itemsPerPage < 1) {
                    itemsPerPage = 5;
                }
                if (itemsPerPage > 50) {
                    itemsPerPage = 50;
                }
            } catch (NumberFormatException e) {
                itemsPerPage = 5;
            }
        }

        List<PayrollDTO> allPayrolls = payrollService.getAllPayrollByUserId(userId);

        if (allPayrolls == null || allPayrolls.isEmpty()) {
            request.setAttribute("error", "No personal payroll data found.");
            request.setAttribute("PersonalPayrolls", List.of());
            request.setAttribute("currentPage", 1);
            request.setAttribute("totalPages", 0);
            request.setAttribute("itemsPerPage", itemsPerPage);
        } else {
            int totalItems = allPayrolls.size();
            int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

            if (page > totalPages && totalPages > 0) {
                page = totalPages;
            }

            int startIndex = (page - 1) * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

            List<PayrollDTO> paginatedPayrolls = allPayrolls.subList(startIndex, endIndex);

            request.setAttribute("PersonalPayrolls", paginatedPayrolls);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("itemsPerPage", itemsPerPage);
            request.setAttribute("totalItems", totalItems);
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
        UserService userService = new UserService();
        if (userService.getUserById(userId) == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        // Get pagination parameters
        int page = 1;
        int itemsPerPage = 5;

        String pageParam = request.getParameter("page");
        String itemsPerPageParam = request.getParameter("itemsPerPage");

        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
                if (page < 1) {
                    page = 1;
                }
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        if (itemsPerPageParam != null && !itemsPerPageParam.isEmpty()) {
            try {
                itemsPerPage = Integer.parseInt(itemsPerPageParam);
                if (itemsPerPage < 1) {
                    itemsPerPage = 5;
                }
                if (itemsPerPage > 50) {
                    itemsPerPage = 50;
                }
            } catch (NumberFormatException e) {
                itemsPerPage = 5;
            }
        }

        PayrollService payrollService = new PayrollService();
        try {
            int month = (monthParam == null || monthParam.isEmpty()) ? 0 : Integer.parseInt(monthParam);
            int year = (yearParam == null || yearParam.isEmpty()) ? 0 : Integer.parseInt(yearParam);

            List<PayrollDTO> allPayrolls = payrollService.searchPayroll(userId, month, year, "");

            if (allPayrolls == null || allPayrolls.isEmpty()) {
                request.setAttribute("error", "No personal payroll data found.");
                request.setAttribute("PersonalPayrolls", List.of());
                request.setAttribute("currentPage", 1);
                request.setAttribute("totalPages", 0);
                request.setAttribute("itemsPerPage", itemsPerPage);
            } else {
                int totalItems = allPayrolls.size();
                int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

                if (page > totalPages && totalPages > 0) {
                    page = totalPages;
                }

                int startIndex = (page - 1) * itemsPerPage;
                int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

                List<PayrollDTO> paginatedPayrolls = allPayrolls.subList(startIndex, endIndex);

                request.setAttribute("PersonalPayrolls", paginatedPayrolls);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("itemsPerPage", itemsPerPage);
                request.setAttribute("totalItems", totalItems);
            }
            request.getRequestDispatcher("/view/payroll/personalPayroll.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Month and year must be integers.");
            request.setAttribute("currentPage", 1);
            request.setAttribute("totalPages", 0);
            request.setAttribute("itemsPerPage", itemsPerPage);
            request.getRequestDispatcher("/view/payroll/personalPayroll.jsp").forward(request, response);
        }
    }
}
