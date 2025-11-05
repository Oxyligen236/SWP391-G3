package hrms.controller;

import java.io.IOException;
import java.sql.SQLException;

import hrms.dao.AccountDAO;
import hrms.model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/account/toggle-status")
public class ToggleAccountStatusServlet extends HttpServlet {
    private final AccountDAO accountDAO = new AccountDAO();
    private static final int ADMIN_ROLE = 5;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("account");

        if (currentUser == null || currentUser.getRole() != 5) {
            session.setAttribute("errorMessage", " Bạn không có quyền thực hiện hành động này!");
            response.sendRedirect(request.getContextPath() + "/account/view");
            return;
        }

        String idStr = request.getParameter("accountID");
        if (idStr == null || idStr.trim().isEmpty()) {
            session.setAttribute("errorMessage", " Account ID không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/account/view");
            return;
        }

        int accountId = Integer.parseInt(idStr);
        Account target = accountDAO.getAccountById(accountId);

        if (target == null) {
            session.setAttribute("errorMessage", " Tài khoản không tồn tại!");
            response.sendRedirect(request.getContextPath() + "/account/view");
            return;
        }

        // Không thể tự disable chính mình
        if (target.getAccountID() == currentUser.getAccountID()) {
            session.setAttribute("errorMessage", " Bạn không thể vô hiệu hóa chính mình!");
            response.sendRedirect(request.getContextPath() + "/account/view");
            return;
        }

        // ⚠️ Nếu đang vô hiệu hóa một admin khác
        if (target.getRole() == ADMIN_ROLE && target.isIsActive()) {
            try {
                int activeAdmins = accountDAO.countActiveAdmins();
                if (activeAdmins <= 1) {
                    session.setAttribute("errorMessage", " Không thể vô hiệu hóa admin cuối cùng trong hệ thống!");
                    response.sendRedirect(request.getContextPath() + "/account/view");
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                session.setAttribute("errorMessage", " Lỗi khi kiểm tra số lượng admin: " + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/account/view");
                return;
            }
        }

        boolean success = false;
        boolean newStatus = !target.isIsActive(); 
        try {
            success = accountDAO.updateStatus(accountId, newStatus);
        } catch (SQLException e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", " Database error: " + e.getMessage());
        }

        if (success) {
            session.setAttribute("successMessage",
                    "✅ " + (newStatus ? "Đã kích hoạt" : "Đã vô hiệu hóa") + " tài khoản: " + target.getUsername());
        } else if (session.getAttribute("errorMessage") == null) {
            session.setAttribute("errorMessage", "Cập nhật trạng thái thất bại!");
        }

        response.sendRedirect(request.getContextPath() + "/account/view");
    }
}
