package hrms.controller;

import java.io.IOException;

import hrms.dao.AccountDAO;
import hrms.model.Account;
import hrms.utils.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/account/change-password")
public class ChangePasswordServlet extends HttpServlet {

    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("account");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        request.getRequestDispatcher("/view/account/changePassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("account");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        try {
            // ===== VALIDATION =====
            if (currentPassword == null || currentPassword.trim().isEmpty()
                    || newPassword == null || newPassword.trim().isEmpty()
                    || confirmPassword == null || confirmPassword.trim().isEmpty()) {

                request.setAttribute("errorMessage", " Please fill in all fields!");
                request.getRequestDispatcher("/view/account/changePassword.jsp").forward(request, response);
                return;
            }

            if (newPassword.length() < 6) {
                request.setAttribute("errorMessage", "New password must be at least 6 characters!");
                request.getRequestDispatcher("/view/account/changePassword.jsp").forward(request, response);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("errorMessage", " New password and confirmation do not match!");
                request.getRequestDispatcher("/view/account/changePassword.jsp").forward(request, response);
                return;
            }

            if (currentPassword.equals(newPassword)) {
                request.setAttribute("errorMessage", " New password must be different from current password!");
                request.getRequestDispatcher("/view/account/changePassword.jsp").forward(request, response);
                return;
            }

            // ===== VERIFY CURRENT PASSWORD =====
            Account account = accountDAO.getAccountById(currentUser.getAccountID());
            if (account == null || !PasswordUtil.verifyPassword(currentPassword, account.getPassword())) {
                request.setAttribute("errorMessage", " Current password is incorrect!");
                request.getRequestDispatcher("/view/account/changePassword.jsp").forward(request, response);
                System.out.println("⚠️ Wrong current password for account ID: " + currentUser.getAccountID());
                return;
            }

            // ===== HASH NEW PASSWORD =====
            String hashedNewPassword = PasswordUtil.hashPassword(newPassword);

            // ===== CHANGE PASSWORD =====
            boolean success = accountDAO.changePassword(
                    currentUser.getAccountID(),
                    hashedNewPassword
            );

            if (success) {
                session.invalidate();
                response.sendRedirect(request.getContextPath() + "/view/account/changePasswordSuccess.jsp");
                System.out.println("✅ Password changed successfully for account ID: " + currentUser.getAccountID());
            } else {
                request.setAttribute("errorMessage", "❌ Failed to change password! Please try again.");
                request.getRequestDispatcher("/view/account/changePassword.jsp").forward(request, response);
                System.out.println("❌ Password change failed for account ID: " + currentUser.getAccountID());
            }

        } catch (Exception e) {
            request.setAttribute("errorMessage", "💥 System error: " + e.getMessage());
            request.getRequestDispatcher("/view/account/changePassword.jsp").forward(request, response);
            System.out.println("💥 Exception in ChangePasswordServlet: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
