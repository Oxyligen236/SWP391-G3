package hrms.controller;

import java.io.IOException;

import hrms.dao.UserDAO;
import hrms.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/view") 
public class ProfileServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            // Chưa đăng nhập → chuyển hướng đến login
            resp.sendRedirect(req.getContextPath() + "/authenticate"); 
            return;
        }

        int userId = (int) session.getAttribute("userId"); // Lấy từ session
        User user = userDAO.getUserById(userId);

        if (user != null) {
            req.setAttribute("user", user);
            req.getRequestDispatcher("view/profile/viewProfile.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Không tìm thấy thông tin người dùng.");
            req.getRequestDispatcher("/view/profile/error.jsp").forward(req, resp);
        }
    }
}
