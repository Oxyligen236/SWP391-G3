package hrms.controller;

import hrms.dao.AccountDAO;
import java.io.IOException;
import java.util.List;
import hrms.dao.TicketDAO;
import hrms.dao.UserDAO;
import hrms.dto.UserDTO;
import hrms.model.Account;
import hrms.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@WebServlet("/userlist")
public class UserListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO uDAO = new UserDAO();
        AccountDAO aDAO = new AccountDAO();

        List<UserDTO> users = uDAO.getAllWithJoin();


        List<Integer> userHasAccount = new ArrayList<>();

        for (UserDTO u : users) {
            if (aDAO.getAccountByUserID(u.getUserId()) != null) {
                userHasAccount.add(u.getUserId());
            }
        }

        request.setAttribute("users", users);
        request.setAttribute("userHasAccount", userHasAccount);
        request.getRequestDispatcher("/view/users/userlist.jsp").forward(request, response);

    }
}
