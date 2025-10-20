package hrms.controller;

import java.io.IOException;
import hrms.dao.AccountDAO;
import hrms.model.Account;
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

        String oldPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        try {
            if (oldPassword == null || oldPassword.trim().isEmpty() ||
                newPassword == null || newPassword.trim().isEmpty() ||
                confirmPassword == null || confirmPassword.trim().isEmpty()) {

                request.setAttribute("errorMessage", "Vui lòng điền đầy đủ thông tin!");
                request.getRequestDispatcher("/view/account/changePassword.jsp").forward(request, response);
                return;
            }

            if (newPassword.length() < 6) {
                request.setAttribute("errorMessage", "Mật khẩu mới phải có ít nhất 6 ký tự!");
                request.getRequestDispatcher("/view/account/changePassword.jsp").forward(request, response);
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                request.setAttribute("errorMessage", "Mật khẩu mới và xác nhận không khớp!");
                request.getRequestDispatcher("/view/account/changePassword.jsp").forward(request, response);
                return;
            }

            if (oldPassword.equals(newPassword)) {
                request.setAttribute("errorMessage", "Mật khẩu mới phải khác mật khẩu hiện tại!");
                request.getRequestDispatcher("/view/account/changePassword.jsp").forward(request, response);
                return;
            }

            boolean success = accountDAO.changePassword(
                currentUser.getAccountID(), 
                oldPassword, 
                newPassword
            );

            if (success) {
                session.invalidate();
                response.sendRedirect(request.getContextPath() + "/view/account/changePasswordSuccess.jsp");
            } else {
                request.setAttribute("errorMessage", "Mật khẩu hiện tại không đúng hoặc có lỗi xảy ra!");
                request.getRequestDispatcher("/view/account/changePassword.jsp").forward(request, response);
            }

        } catch (Exception e) {
            request.setAttribute("errorMessage", "Lỗi hệ thống: " + e.getMessage());
            request.getRequestDispatcher("/view/account/changePassword.jsp").forward(request, response);
        }
    }
}
