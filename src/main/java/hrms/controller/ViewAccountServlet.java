package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.AccountDAO;
import hrms.dao.RoleDAO;
import hrms.dto.AccountDTO;
import hrms.model.Account;
import hrms.model.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/account/view")
public class ViewAccountServlet extends HttpServlet {
    private final AccountDAO accountDAO = new AccountDAO();
    private final RoleDAO roleDAO = new RoleDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("account");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        try {
            // === Nhận tham số filter/sort/search/pagination ===
            String search = request.getParameter("search"); // tìm theo tên hoặc username
            String roleFilter = request.getParameter("role"); // lọc theo role (string)
            String statusFilter = request.getParameter("status"); // lọc theo trạng thái
            String sortBy = request.getParameter("sortBy"); // cột sắp xếp
            String sortOrder = request.getParameter("sortOrder"); // ASC/DESC
            int page = parseIntOrDefault(request.getParameter("page"), 1);
            int pageSize = parseIntOrDefault(request.getParameter("pageSize"), 10);

            // Lấy danh sách role từ DB
            List<Role> roleList = roleDAO.getAllRoles();
            request.setAttribute("roleList", roleList); // <--- đồng bộ với JSP

            if (currentUser.getRole() == 1) { // Admin
                // Lấy danh sách tài khoản theo các điều kiện lọc, tìm kiếm, phân trang
                List<AccountDTO> accounts = accountDAO.getFilteredAccounts(
                        search, roleFilter, statusFilter, sortBy, sortOrder, page, pageSize);

                int totalRecords = accountDAO.countFilteredAccounts(search, roleFilter, statusFilter);
                int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

                // Gửi dữ liệu tới JSP
                request.setAttribute("accounts", accounts);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);
                request.setAttribute("pageSize", pageSize);

                // Giữ lại filter/sort để giao diện không bị mất
                request.setAttribute("search", search);
                request.setAttribute("roleFilter", roleFilter);
                request.setAttribute("statusFilter", statusFilter);
                request.setAttribute("sortBy", sortBy);
                request.setAttribute("sortOrder", sortOrder);

                // Chuyển đến JSP
                request.getRequestDispatcher("/view/account/viewAccountList.jsp").forward(request, response);
            } else {
                // Người dùng thường chỉ được xem tài khoản của chính mình
                AccountDTO self = accountDAO.getAccountDTOByID(currentUser.getAccountID());
                request.setAttribute("account", self);
                request.getRequestDispatcher("/view/account/viewAccountDetail.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi hệ thống: " + e.getMessage());
        }
    }

    private int parseIntOrDefault(String value, int defaultVal) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultVal;
        }
    }
}
