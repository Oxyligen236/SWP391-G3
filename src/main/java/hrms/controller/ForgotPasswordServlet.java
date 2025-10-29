package hrms.controller;

import java.io.IOException;

import hrms.dao.AccountDAO;
import hrms.dao.UserDAO;
import hrms.model.User;
import hrms.service.EmailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        request.getRequestDispatcher("/view/authenticate/forgotPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String body = request.getParameter("body");
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Vui lòng nhập email!");
            request.getRequestDispatcher("/view/authenticate/forgotPassword.jsp").forward(request, response);
            return;
        }
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByEmail(email);
        if (user == null) {
            request.setAttribute("errorMessage", "Email không tồn tại trong hệ thống!");
            request.getRequestDispatcher("/view/authenticate/forgotPassword.jsp").forward(request, response);
            return;
        }
        EmailService emailService = new EmailService();
        boolean sent = emailService.sendForgotPasswordEmail(email, subject, body);

        if (sent) {
            request.setAttribute("successMessage", "Email đặt lại mật khẩu đã được gửi!");
        } else {
            request.setAttribute("errorMessage", "Không thể gửi email. Vui lòng thử lại!");
        }

        request.getRequestDispatcher("/view/authenticate/forgotPassword.jsp").forward(request, response);
    }
}
