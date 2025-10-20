package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dto.PayrollDTO;
import hrms.model.Account;
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
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        String monthStr = request.getParameter("month");
        String yearStr = request.getParameter("year");
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

        int userId = account.getUserID();
        PayrollService payrollService = new PayrollService();

        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("month", monthStr);
        request.setAttribute("year", yearStr);

        try {
            List<PayrollDTO> personalPayrolls;

            if ((monthStr != null && !monthStr.trim().isEmpty())
                    || (yearStr != null && !yearStr.trim().isEmpty())) {

                int month = 0;
                int year = 0;

                if (monthStr != null && !monthStr.trim().isEmpty()) {
                    month = Integer.parseInt(monthStr);
                }
                if (yearStr != null && !yearStr.trim().isEmpty()) {
                    year = Integer.parseInt(yearStr);
                }

                personalPayrolls = payrollService.searchPersonalPayroll(userId, month, year);
            } else {
                personalPayrolls = payrollService.getAllPayrollByUserId(userId);
            }

            if (personalPayrolls == null || personalPayrolls.isEmpty()) {
                request.setAttribute("error", "Không có dữ liệu lương cá nhân.");
                request.setAttribute("totalPages", 0);
                request.setAttribute("totalPayrolls", 0);
                request.getRequestDispatcher("/view/payroll/personalPayroll.jsp").forward(request, response);
                return;
            }

            int totalPayrolls = personalPayrolls.size();
            int totalPages = (int) Math.ceil((double) totalPayrolls / itemsPerPage);

            if (currentPage > totalPages && totalPages > 0) {
                currentPage = totalPages;
            }
            if (currentPage < 1) {
                currentPage = 1;
            }

            int startIndex = (currentPage - 1) * itemsPerPage;
            int endIndex = Math.min(startIndex + itemsPerPage, totalPayrolls);
            List<PayrollDTO> paginatedPayrolls = personalPayrolls.subList(startIndex, endIndex);

            request.setAttribute("PersonalPayrolls", paginatedPayrolls);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalPayrolls", totalPayrolls);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Định dạng tháng hoặc năm không hợp lệ.");
            request.setAttribute("totalPages", 0);
            request.setAttribute("totalPayrolls", 0);
        }

        request.getRequestDispatcher("/view/payroll/personalPayroll.jsp").forward(request, response);
    }

}
