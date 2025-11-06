package hrms.controller;

import java.io.IOException;

import hrms.dto.UserDTO;
import hrms.model.Account;
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
        Account account = (Account) session.getAttribute("account");
        if (account == null) {

            resp.sendRedirect(req.getContextPath() + "/authenticate");
            return;
        }

        int userId = account.getUserID();

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