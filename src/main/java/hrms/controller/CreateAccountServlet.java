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

        String userIDParam = req.getParameter("userId");
        if (userIDParam != null && !userIDParam.isEmpty()) {
            try {
                int userID = Integer.parseInt(userIDParam);
                req.setAttribute("userId", userID);
                System.out.println("Received userId from URL: " + userID);
            } catch (NumberFormatException e) {
                req.setAttribute("errorMessage", " Invalid userId!");
                System.out.println("Invalid userId format: " + userIDParam);
            }
        } else {
            req.setAttribute("errorMessage", " User ID is required!");
            System.out.println("No userId parameter provided");
        }

        req.setAttribute("roleList", roleDAO.getAllRoles());
        req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Account currentUser = (Account) req.getSession().getAttribute("account");
        if (currentUser == null || currentUser.getRole() != 5) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "You do not have permission to perform this action!");
            return;
        }

        req.setCharacterEncoding("UTF-8");

        try {
            String userIDParam = req.getParameter("userId");
            String roleIDParam = req.getParameter("roleID");
            
            if (userIDParam == null || userIDParam.trim().isEmpty()) {
                req.setAttribute("errorMessage", "User ID is required!");
                forwardWithRoles(req, resp);
                return;
            }

            if (roleIDParam == null || roleIDParam.trim().isEmpty()) {
                req.setAttribute("errorMessage", " Role is required!");
                req.setAttribute("userId", userIDParam);
                forwardWithRoles(req, resp);
                return;
            }

            int userID = Integer.parseInt(userIDParam);
            int roleID = Integer.parseInt(roleIDParam);
            
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            String confirmPassword = req.getParameter("confirmPassword");
            String isActiveParam = req.getParameter("isActive");
            
            boolean isActive = isActiveParam != null && isActiveParam.equals("true");

            System.out.println("Form submitted - userId: " + userID + ", username: " + username + ", roleID: " + roleID + ", isActive: " + isActive);

            if (username == null || username.trim().isEmpty()) {
                req.setAttribute("errorMessage", "Username cannot be empty!");
                req.setAttribute("userId", userID);
                forwardWithRoles(req, resp);
                return;
            }

            username = username.trim();

            if (username.length() < 3) {
                req.setAttribute("errorMessage", "Username must be at least 3 characters!");
                req.setAttribute("userId", userID);
                forwardWithRoles(req, resp);
                return;
            }

            if (password == null || password.trim().isEmpty()) {
                req.setAttribute("errorMessage", " Password cannot be empty!");
                req.setAttribute("userId", userID);
                forwardWithRoles(req, resp);
                return;
            }

            if (password.length() < 6) {
                req.setAttribute("errorMessage", "Password must be at least 6 characters!");
                req.setAttribute("userId", userID);
                forwardWithRoles(req, resp);
                return;
            }

            if (confirmPassword == null || !password.equals(confirmPassword)) {
                req.setAttribute("errorMessage", "Password confirmation does not match!");
                req.setAttribute("userId", userID);
                forwardWithRoles(req, resp);
                return;
            }

            if (accountDAO.getAccountByUsername(username) != null) {
                req.setAttribute("errorMessage", " Username already exists!");
                req.setAttribute("userId", userID);
                forwardWithRoles(req, resp);
                return;
            }

            if (accountDAO.getAccountByUserID(userID) != null) {
                req.setAttribute("errorMessage", "This user already has an account!");
                req.setAttribute("userId", userID);
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
                req.setAttribute("successMessage", " Account created successfully!");
                req.setAttribute("newUsername", username);
                req.setAttribute("newUserId", userID);
                req.setAttribute("resetForm", true);
                System.out.println("Account created - userId: " + userID + ", username: " + username);
            } else {
                req.setAttribute("errorMessage", "Failed to create account!");
                req.setAttribute("userId", userID);
                System.out.println("Account creation failed for userId: " + userID);
            }

        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Invalid input data! " + e.getMessage());
            System.out.println(" NumberFormatException: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            req.setAttribute("errorMessage", "System error: " + e.getMessage());
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }

        forwardWithRoles(req, resp);
    }

    private void forwardWithRoles(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("roleList", roleDAO.getAllRoles());
        req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
    }
}