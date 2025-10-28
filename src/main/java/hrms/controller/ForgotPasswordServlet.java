package hrms.controller;

import java.io.IOException;
import java.net.URLEncoder;

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
        request.getRequestDispatcher("/view/authenticate/forgotPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String subject = request.getParameter("subject");
        String body = request.getParameter("body");
        String type = request.getParameter("type");

        // Encode
        String encodedSubject = URLEncoder.encode(subject, "UTF-8");
        String encodedBody = URLEncoder.encode(body, "UTF-8");

        String redirectUrl = "";
        if ("mailto".equals(type)) {
            redirectUrl = "mailto:" + email + "?subject=" + encodedSubject + "&body=" + encodedBody;
        } else if ("gmail".equals(type)) {
            redirectUrl = "https://mail.google.com/mail/?view=cm&fs=1&to=" + email + "&su=" + encodedSubject + "&body=" + encodedBody;
        }

        response.sendRedirect(redirectUrl);
    }
}
