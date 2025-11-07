package hrms.controller;

import java.io.IOException;

import hrms.dao.AccountDAO;
import hrms.dao.RoleDAO;
import hrms.model.Account;
import hrms.utils.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/account/create")
public class CreateAccountServlet extends HttpServlet {

    private final RoleDAO roleDAO = new RoleDAO();
    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Kiểm tra quyền admin (role = 5)
        Account currentUser = (Account) req.getSession().getAttribute("account");
        if (currentUser == null || currentUser.getRole() != 5) {
            resp.sendRedirect(req.getContextPath() + "/view/profile/accessDenied.jsp");
            return;
        }

        // Lấy userID từ query parameter (nếu có)
        String userIDParam = req.getParameter("userID");
        if (userIDParam != null && !userIDParam.isEmpty()) {
            try {
                int userID = Integer.parseInt(userIDParam);
                req.setAttribute("userID", userID);
            } catch (NumberFormatException e) {
                req.setAttribute("errorMessage", "❌ Invalid userID!");
            }
        }

        req.setAttribute("roleList", roleDAO.getAllRoles());
        req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Kiểm tra quyền admin (role = 5)
        Account currentUser = (Account) req.getSession().getAttribute("account");
        if (currentUser == null || currentUser.getRole() != 5) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "❌ You do not have permission to perform this action!");
            return;
        }

        req.setCharacterEncoding("UTF-8");

        try {
            int userID = Integer.parseInt(req.getParameter("userID"));
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String confirmPassword = req.getParameter("confirmPassword");
            int roleID = Integer.parseInt(req.getParameter("roleID"));
            boolean isActive = Boolean.parseBoolean(req.getParameter("isActive"));

            // VALIDATE
            if (username == null || username.trim().isEmpty()) {
                req.setAttribute("errorMessage", "❌ Username cannot be empty!");
                forwardWithRoles(req, resp);
                return;
            }

            if (password == null || password.trim().isEmpty()) {
                req.setAttribute("errorMessage", "❌ Password cannot be empty!");
                forwardWithRoles(req, resp);
                return;
            }

            if (password.length() < 6) {
                req.setAttribute("errorMessage", "❌ Password must be at least 6 characters!");
                forwardWithRoles(req, resp);
                return;
            }

            if (!password.equals(confirmPassword)) {
                req.setAttribute("errorMessage", "❌ Password confirmation does not match!");
                forwardWithRoles(req, resp);
                return;
            }

            if (accountDAO.getAccountByUsername(username) != null) {
                req.setAttribute("errorMessage", "❌ Username already exists!");
                forwardWithRoles(req, resp);
                return;
            }

            if (accountDAO.getAccountByUserID(userID) != null) {
                req.setAttribute("errorMessage", "❌ This user already has an account!");
                forwardWithRoles(req, resp);
                return;
            }

            // HASH PASSWORD
            String hashedPassword = PasswordUtil.hashPassword(password);

            // Tạo account mới
            Account account = new Account();
            account.setUserID(userID);
            account.setUsername(username);
            account.setPassword(hashedPassword);
            account.setRole(roleID);
            account.setIsActive(isActive);

            boolean created = accountDAO.createAccount(account);

            if (created) {
                req.setAttribute("successMessage", "✅ Account created successfully!");
                req.setAttribute("resetForm", true);
            } else {
                req.setAttribute("errorMessage", "❌ Failed to create account!");
            }

        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "❌ Invalid input data!");
            e.printStackTrace();
        } catch (Exception e) {
            req.setAttribute("errorMessage", "💥 System error: " + e.getMessage());
            e.printStackTrace();
        }

        forwardWithRoles(req, resp);
    }

    // Helper method để forward kèm danh sách role
    private void forwardWithRoles(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("roleList", roleDAO.getAllRoles());
        req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
    }
}
