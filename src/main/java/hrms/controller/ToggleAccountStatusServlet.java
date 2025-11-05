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

        if (currentUser == null || currentUser.getRole() != ADMIN_ROLE) {
            session.setAttribute("errorMessage", "You do not have permission to perform this action!");
            response.sendRedirect(request.getContextPath() + "/account/view");
            return;
        }

        String idStr = request.getParameter("accountID");
        if (idStr == null || idStr.trim().isEmpty()) {
            session.setAttribute("errorMessage", "Invalid Account ID!");
            response.sendRedirect(request.getContextPath() + "/account/view");
            return;
        }

        int accountId = Integer.parseInt(idStr);
        Account target = accountDAO.getAccountById(accountId);

        if (target == null) {
            session.setAttribute("errorMessage", "Account does not exist!");
            response.sendRedirect(request.getContextPath() + "/account/view");
            return;
        }

        // Cannot deactivate self
        if (target.getAccountID() == currentUser.getAccountID()) {
            session.setAttribute("errorMessage", "You cannot deactivate your own account!");
            response.sendRedirect(request.getContextPath() + "/account/view");
            return;
        }

        // If deactivating another admin
        if (target.getRole() == ADMIN_ROLE && target.isIsActive()) {
            try {
                int activeAdmins = accountDAO.countActiveAdmins();
                if (activeAdmins <= 1) {
                    session.setAttribute("errorMessage", "Cannot deactivate the last active admin in the system!");
                    response.sendRedirect(request.getContextPath() + "/account/view");
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                session.setAttribute("errorMessage", "Error checking active admin count: " + e.getMessage());
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
            session.setAttribute("errorMessage", "Database error: " + e.getMessage());
        }

        if (success) {
            session.setAttribute("successMessage",
                    "✅ Account " + target.getUsername() + " has been " + (newStatus ? "activated" : "deactivated") + ".");
        } else if (session.getAttribute("errorMessage") == null) {
            session.setAttribute("errorMessage", "Failed to update account status!");
        }

        response.sendRedirect(request.getContextPath() + "/account/view");
    }
}
