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
        int roleId = account.getRole();

        UserDTO user = userService.getUserById(userId);
        if (user != null) {
            req.setAttribute("user", user);
            
            // Set content page for layout system
            req.setAttribute("contentPage", "/view/profile/viewProfile.jsp");
            req.setAttribute("pageTitle", "Thông Tin Cá Nhân");
            
            // Determine layout based on role
            String layout = getLayoutByRole(roleId);
            req.getRequestDispatcher(layout).forward(req, resp);
        } else {
            req.setAttribute("error", "Không tìm thấy thông tin người dùng.");
            req.getRequestDispatcher("/view/profile/error.jsp").forward(req, resp);
        }
    }
    
    private String getLayoutByRole(int roleId) {
        return switch (roleId) {
            case 1 -> "/view/common/layout_admin.jsp";
            case 2, 3 -> "/view/common/layout_hr.jsp";
            case 4 -> "/view/common/layout_manager.jsp";
            case 5 -> "/view/common/layout_employee.jsp";
            default -> "/view/common/layout_employee.jsp";
        };
    }
}