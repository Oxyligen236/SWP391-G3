package hrms.controller;

import java.io.IOException;

import hrms.dao.UserDAO;
import hrms.model.Account;
import hrms.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        Account account = (Account) session.getAttribute("account");
        int userID = account.getUserID();
        int roleID = account.getRole();

        try {
            UserDAO userDAO = new UserDAO();
            User users = userDAO.getUserById(userID);

            if (users == null) {
                response.sendRedirect(request.getContextPath() + "/authenticate");
                return;
            }
            session.setAttribute("users", users);

            String homePage = getHomePageByRole(roleID);

            request.getRequestDispatcher(homePage).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private String getHomePageByRole(int roleID) {
        switch (roleID) {
            case 5:
                return "/view/home/homePage_Admin.jsp";
            case 1:
                return "/view/home/homePage_HRManager.jsp";
            case 2:
                return "/view/home/homePage_HR.jsp";
            case 3:
                return "/view/home/homePage_DeptManager.jsp";
            case 4:
                return "/view/home/homePage_employee.jsp";
            default:
                return "/view/home/homePage_guest.jsp";
        }
    }
    
}
