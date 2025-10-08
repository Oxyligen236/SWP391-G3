package hrms.controller;

import java.io.IOException;

import hrms.dto.UserDTO;
import hrms.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/view")
public class ProfileServlet extends HttpServlet {

    private final UserService userService = new UserService();

    @Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

    // Lấy user từ session
    UserDTO sessionUser = (UserDTO) req.getSession().getAttribute("currentUser");
    if (sessionUser == null) {
        resp.sendRedirect(req.getContextPath() + "/login"); // nếu chưa đăng nhập
        return;
    }

    int userId = sessionUser.getUserId();
    UserDTO user = userService.getUserById(userId);

    if (user != null) {
        req.setAttribute("user", user);
        req.getRequestDispatcher("/view/profile/viewProfile.jsp").forward(req, resp);
    } else {
        req.setAttribute("error", "Không tìm thấy thông tin người dùng.");
        req.getRequestDispatcher("/view/profile/error.jsp").forward(req, resp);
    }
}
