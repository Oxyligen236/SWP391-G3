package hrms.controller;

import java.io.IOException;

import hrms.dto.UserDTO;
import hrms.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/view")
public class ProfileServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            // Nếu chưa login, redirect về login
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Lấy userId từ session
        int userId = (Integer) session.getAttribute("userId");

        UserDTO user = userService.getUserById(userId);
        if (user != null) {
            req.setAttribute("user", user);
            req.getRequestDispatcher("/view/profile/viewProfile.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Không tìm thấy thông tin người dùng.");
            req.getRequestDispatcher("/view/profile/error.jsp").forward(req, resp);
        }
    }
}
