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

    HttpSession session = request.getSession();
    Account currentUser = (Account) session.getAttribute("account");

    if (currentUser == null) {
        response.sendRedirect(request.getContextPath() + "/authenticate");
        return;
    }

    String oldPassword = request.getParameter("currentPassword");
    String newPassword = request.getParameter("newPassword");
    String confirmPassword = request.getParameter("confirmPassword");


    System.out.println("=== DEBUG CHANGE PASSWORD ===");
    System.out.println("Old password nhập: " + oldPassword);
    System.out.println("Password trong DB: " + currentUser.getPassword());
    System.out.println("New password: " + newPassword);
    System.out.println("Confirm password: " + confirmPassword);

    try {
        if (!currentUser.getPassword().equals(oldPassword)) {
            System.out.println(" MẬT KHẨU CŨ SAI!");
            request.setAttribute("errorMessage", "Mật khẩu hiện tại không đúng!");
            request.getRequestDispatcher("/view/account/changePassword.jsp").forward(request, response);
            return;
        }

        System.out.println(" Mật khẩu cũ đúng!");

        if (!newPassword.equals(confirmPassword)) {
            System.out.println(" MẬT KHẨU MỚI KHÔNG KHỚP!");
            request.setAttribute("errorMessage", "Mật khẩu mới không khớp!");
            request.getRequestDispatcher("/view/account/changePassword.jsp").forward(request, response);
            return;
        }

        System.out.println("Mật khẩu mới khớp!");

        boolean success = accountDAO.changePassword(currentUser.getAccountID(), oldPassword, newPassword);

        System.out.println("Kết quả cập nhật: " + (success ? " THÀNH CÔNG" : " THẤT BẠI"));

        if (success) {
            request.setAttribute("message", "Bạn đã đổi mật khẩu thành công. Vui lòng đăng nhập lại!");
            session.invalidate();
            request.getRequestDispatcher("/view/account/changePasswordSuccess.jsp").forward(request, response);
        } else {
            request.setAttribute("errorMessage", "Đổi mật khẩu thất bại, vui lòng thử lại!");
            request.getRequestDispatcher("/view/account/changePassword.jsp").forward(request, response);
        }

    } catch (Exception e) {
        System.out.println(" EXCEPTION: " + e.getMessage());
        e.printStackTrace();
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi hệ thống: " + e.getMessage());
    }
}
}