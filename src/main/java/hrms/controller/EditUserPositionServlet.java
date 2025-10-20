package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.ChangePositionDAO;
import hrms.dao.PositionDAO;
import hrms.dto.UserDTO;
import hrms.model.Position;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/updatePosition")
public class EditUserPositionServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        PositionDAO positionDAO = new PositionDAO();
        ChangePositionDAO changePositionDAO = new ChangePositionDAO();

        String searchValue = request.getParameter("searchValue");

        List<Position> positions = positionDAO.getAll();
        List<UserDTO> users = changePositionDAO.getAllUsersWithPosition();

        if (searchValue != null && !searchValue.trim().isEmpty()) {
            users = changePositionDAO.searchUsersWithPosition(searchValue.trim());
            request.setAttribute("searchValue", searchValue);
        } else {
            users = changePositionDAO.getAllUsersWithPosition();
        }

        request.setAttribute("positions", positions);
        request.setAttribute("users", users);
        
        HttpSession session = request.getSession();
        String message = (String) session.getAttribute("message");
        if(message != null) {
            request.setAttribute("message", message);
            session.removeAttribute("message"); 
        }
        request.getRequestDispatcher("view/users/changeUserPosition.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
        int userID = Integer.parseInt(request.getParameter("userID"));
        int newPositionID = Integer.parseInt(request.getParameter("newPositionID"));

        ChangePositionDAO changePositionDAO = new ChangePositionDAO();
        boolean success = changePositionDAO.updateUserPosition(userID, newPositionID);

        HttpSession session = request.getSession();
        if(success) {
            session.setAttribute("message", "User department updated successfully!");
        }   else {
            session.setAttribute("message", "Failed to update user department.");
        }

        response.sendRedirect("updatePosition");
    }
}
