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

    private static final String ADMIN_EMAIL = "pqm1290@gmail.com";

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
            request.setAttribute("errorMessage", "Please enter your email address!");
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
                : "Password Reset Request from " + user.getFullname();

        String adminBody = (body != null && !body.trim().isEmpty())
                ? body
                : "I forgot my password. Please help me reset it.";

        EmailService emailService = new EmailService();
        boolean sent = emailService.sendForgotPasswordEmail(
                ADMIN_EMAIL,
                userEmail,
                user.getFullname(),
                adminSubject,
                adminBody
        );

        if (sent) {
            request.setAttribute("successMessage",
                    "Your password reset request has been sent successfully! Admin will contact you via email.");
        } else {
            request.setAttribute("errorMessage",
                    "Unable to send request. Please try again later!");
        }

        request.getRequestDispatcher("/view/authenticate/forgotPassword.jsp").forward(request, response);
    }
}
