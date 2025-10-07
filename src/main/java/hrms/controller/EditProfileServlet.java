package hrms.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;

import hrms.dao.UserDAO;
import hrms.model.Account;
import hrms.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/edit")
public class EditProfileServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session != null) {
            Account account = (Account) session.getAttribute("account");
            if (account != null) {
                int userId = account.getUserID(); // Lấy userId từ account
                User user = userDAO.getUserById(userId);

                if (user != null) {
                    req.setAttribute("user", user);
                    if (user.getBirthDate() != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        req.setAttribute("birthDateStr", sdf.format(user.getBirthDate()));
                    }
                    req.getRequestDispatcher("/view/profile/editProfile.jsp").forward(req, resp);
                    return;
                }
            }
        }

        // Nếu không có session hoặc user không tìm thấy
        req.setAttribute("error", "Bạn chưa đăng nhập hoặc không tìm thấy thông tin người dùng để chỉnh sửa.");
        req.getRequestDispatcher("/view/profile/error.jsp").forward(req, resp);
    }
}
