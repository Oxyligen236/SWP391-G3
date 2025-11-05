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

        Account currentUser = (Account) req.getSession().getAttribute("account");
        if (currentUser == null || currentUser.getRole() != 5) {
            resp.sendRedirect(req.getContextPath() + "/view/profile/accessDenied.jsp");
            return;
        }

        req.setAttribute("roleList", roleDAO.getAllRoles());
        req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Account currentUser = (Account) req.getSession().getAttribute("account");
        if (currentUser == null || currentUser.getRole() != 5) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "❌ Bạn không có quyền thực hiện hành động này!");
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

            // Validate input
            if (username == null || username.trim().isEmpty()) {
                req.setAttribute("errorMessage", "❌ Username không được để trống!");
                req.setAttribute("roleList", roleDAO.getAllRoles());
                req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
                return;
            }

            if (password == null || password.trim().isEmpty()) {
                req.setAttribute("errorMessage", "❌ Mật khẩu không được để trống!");
                req.setAttribute("roleList", roleDAO.getAllRoles());
                req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
                return;
            }

            if (password.length() < 6) {
                req.setAttribute("errorMessage", "❌ Mật khẩu phải có ít nhất 6 ký tự!");
                req.setAttribute("roleList", roleDAO.getAllRoles());
                req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
                return;
            }

            if (!password.equals(confirmPassword)) {
                req.setAttribute("errorMessage", "❌ Mật khẩu xác nhận không khớp!");
                req.setAttribute("roleList", roleDAO.getAllRoles());
                req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
                return;
            }

            if (accountDAO.getAccountByUsername(username) != null) {
                req.setAttribute("errorMessage", "❌ Username đã tồn tại!");
                req.setAttribute("roleList", roleDAO.getAllRoles());
                req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
                return;
            }

            if (accountDAO.getAccountByUserID(userID) != null) {
                req.setAttribute("errorMessage", "❌ Người dùng này đã có tài khoản!");
                req.setAttribute("roleList", roleDAO.getAllRoles());
                req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
                return;
            }

          
            String hashedPassword = PasswordUtil.hashPassword(password);

            Account account = new Account();
            account.setUserID(userID);
            account.setUsername(username);
            account.setPassword(hashedPassword);  
            account.setRole(roleID);
            account.setIsActive(isActive);

            boolean created = accountDAO.createAccount(account);

            if (created) {
                req.setAttribute("successMessage", "✅ Tạo tài khoản thành công!");
                req.setAttribute("resetForm", true);
            } else {
                req.setAttribute("errorMessage", "❌ Tạo tài khoản thất bại!");
            }

        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "❌ Dữ liệu không hợp lệ!");
            e.printStackTrace();
        } catch (Exception e) {
            req.setAttribute("errorMessage", "💥 Lỗi hệ thống: " + e.getMessage());
            e.printStackTrace();
        }

        req.setAttribute("roleList", roleDAO.getAllRoles());
        req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
    }
}