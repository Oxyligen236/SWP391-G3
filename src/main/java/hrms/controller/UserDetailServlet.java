package hrms.controller;

import hrms.dao.UserDAO;
import hrms.dao.AccountDAO;
import hrms.dto.UserDTO;
import hrms.model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user_detail")
public class UserDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userIdParam = request.getParameter("userID");
        int userID = Integer.parseInt(userIdParam);

        UserDAO uDAO = new UserDAO();
        AccountDAO aDAO = new AccountDAO();


        UserDTO user = uDAO.getUserDetailById(userID);
        Account acc = aDAO.getAccountByUserID(userID);


        boolean hasAccount = (acc != null);

        request.setAttribute("user", user);
        request.setAttribute("account", acc);
        request.setAttribute("hasAccount", hasAccount);

        request.getRequestDispatcher("/view/users/user_detail.jsp").forward(request, response);
    }
}
