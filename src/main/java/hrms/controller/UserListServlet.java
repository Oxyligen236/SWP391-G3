package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.UserDAO;
import hrms.dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/userlist")
public class UserListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    UserDAO userDAO = new UserDAO();
      List<UserDTO> users = userDAO.getAllWithJoin();

    request.setAttribute ("users", userDAO.getAll());

    request.getRequestDispatcher ("/view/users/userlist.jsp").forward(request, response);
    }
}
