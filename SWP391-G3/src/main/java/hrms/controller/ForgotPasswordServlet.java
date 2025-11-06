package hrms.controller;

import java.io.IOException;

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

    // Email admin nhận yêu cầu reset password
    private static final String ADMIN_EMAIL = "admin@yourcompany.com";

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

        String userEmail = request.getParameter("email");
        String subject = request.getParameter("subject");
        String body = request.getParameter("body");

        if (userEmail == null || userEmail.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Enter your email address!");
            request.getRequestDispatcher("/view/authenticate/forgotPassword.jsp").forward(request, response);
            return;
        }

        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByEmail(userEmail);
        if (user == null) {
            request.setAttribute("errorMessage", "Email does not exist in the system!");
            request.getRequestDispatcher("/view/authenticate/forgotPassword.jsp").forward(request, response);
            return;
        }

        String adminSubject = (subject != null && !subject.trim().isEmpty())
                ? subject
                : "Password Reset Request";

        String adminBody = String.format(
                "<h3>Password Reset Request</h3>"
                + "<p><strong>User Email:</strong> %s</p>"
                + "<p><strong>User Name:</strong> %s</p>"
                + "<p><strong>Message:</strong></p>"
                + "<p>%s</p>",
                userEmail,
                user.getFullname() != null ? user.getFullname() : "N/A",
                body != null && !body.trim().isEmpty() ? body : "User requested password reset."
        );

        EmailService emailService = new EmailService();
        // Gửi email ĐẾN admin, không phải đến user
        boolean sent = emailService.sendForgotPasswordEmail(ADMIN_EMAIL, adminSubject, adminBody);

        if (sent) {
            request.setAttribute("successMessage", "Your password reset request has been sent to admin!");
        } else {
            request.setAttribute("errorMessage", "Unable to send request. Please try again!");
        }

        request.getRequestDispatcher("/view/authenticate/forgotPassword.jsp").forward(request, response);
    }
}
