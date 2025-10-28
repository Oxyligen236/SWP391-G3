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

@WebServlet("/landing")
public class UserLandingServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("account") == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }
        
        Account account = (Account) session.getAttribute("account");
        int userID = account.getUserID();
        
        try {
            // Kiểm tra xem users đã có trong session chưa
            User users = (User) session.getAttribute("users");
            
            // Nếu chưa có hoặc null, lấy từ database
            if (users == null) {
                UserDAO userDAO = new UserDAO();
                users = userDAO.getUserById(userID);
                
                if (users == null) {
                    response.sendRedirect(request.getContextPath() + "/authenticate");
                    return;
                }
                
                session.setAttribute("users", users);
            }
            
            // Forward đến landing page
            request.getRequestDispatcher("/view/home/homePage_user.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
