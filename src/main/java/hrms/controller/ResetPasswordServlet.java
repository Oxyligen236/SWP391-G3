package hrms.controller;

import java.io.IOException;

import hrms.dao.AccountDAO;
import hrms.dto.AccountDTO;
import hrms.model.Account;
import hrms.utils.PasswordUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/account/reset-password")
public class ResetPasswordServlet extends HttpServlet {
    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("account");

        try {
            // 🔒 Check admin permission
            if (!isAdmin(currentUser)) {
                session.setAttribute("errorMessage", "❌ You do not have permission to access this page!");
                response.sendRedirect(request.getContextPath() + "/account/view");
                return;
            }

            String accountIDParam = request.getParameter("accountID");
            if (accountIDParam == null || accountIDParam.isEmpty()) {
                session.setAttribute("errorMessage", "❌ Invalid Account ID!");
                response.sendRedirect(request.getContextPath() + "/account/view");
                return;
            }

            int accountID = Integer.parseInt(accountIDParam);

            AccountDTO accountDetail = accountDAO.getAccountDTOByID(accountID);
            if (accountDetail == null) {
                session.setAttribute("errorMessage", "❌ Account not found!");
                response.sendRedirect(request.getContextPath() + "/account/view");
                return;
            }

            if (accountDetail.getAccountID() == currentUser.getAccountID()) {
                session.setAttribute("errorMessage", "❌ You cannot reset your own password!");
                response.sendRedirect(request.getContextPath() + "/account/view");
                return;
            }

            request.setAttribute("account", accountDetail);
            request.getRequestDispatcher("/view/account/resetPassword.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "💥 System error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/account/view");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("account");

        try {
            if (!isAdmin(currentUser)) {
                session.setAttribute("errorMessage", "❌ You do not have permission to perform this action!");
                response.sendRedirect(request.getContextPath() + "/account/view");
                return;
            }

            String accountIDParam = request.getParameter("accountID");
            if (accountIDParam == null || accountIDParam.isEmpty()) {
                session.setAttribute("errorMessage", "❌ Invalid Account ID!");
                response.sendRedirect(request.getContextPath() + "/account/view");
                return;
            }

            int accountID = Integer.parseInt(accountIDParam);
            AccountDTO targetAccount = accountDAO.getAccountDTOByID(accountID);

            if (targetAccount == null) {
                session.setAttribute("errorMessage", "❌ Account does not exist!");
                response.sendRedirect(request.getContextPath() + "/account/view");
                return;
            }

            if (targetAccount.getAccountID() == currentUser.getAccountID()) {
                session.setAttribute("errorMessage", "❌ You cannot reset your own password!");
                response.sendRedirect(request.getContextPath() + "/account/view");
                return;
            }

            // Generate new temporary password
            String newPassword = generateRandomPassword();
            String hashedPassword = PasswordUtil.hashPassword(newPassword);

            boolean success = accountDAO.resetPassword(accountID, hashedPassword);

            if (success) {
                request.setAttribute("account", targetAccount);
                request.setAttribute("tempPassword", newPassword); 
                request.setAttribute("successMessage",
                        "✅ Password has been successfully reset for account: " + targetAccount.getUsername());

                request.getRequestDispatcher("/view/account/resetPassword.jsp").forward(request, response);
            } else {
                session.setAttribute("errorMessage", "❌ Password reset failed!");
                response.sendRedirect(request.getContextPath() + "/account/view");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "💥 System error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/account/view");
        }
    }

    private boolean isAdmin(Account account) {
        return account != null && account.getRole() == 5;
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
        java.security.SecureRandom random = new java.security.SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
