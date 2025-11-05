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

        String userIDParam = request.getParameter("userID");
        if (userIDParam == null || userIDParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/userlist");
            return;
        }

        int userID = Integer.parseInt(userIDParam);
        
        ChangePositionDAO changePositionDAO = new ChangePositionDAO();
        PositionDAO positionDAO = new PositionDAO();

        UserDTO user = changePositionDAO.getUserDetailById(userID);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/userlist");
            return;
        }

        int currentPositionID = changePositionDAO.getCurrentPositionID(userID);
        List<Position> positions = positionDAO.getAll();

        request.setAttribute("user", user);
        request.setAttribute("currentPositionID", currentPositionID);
        request.setAttribute("positions", positions);

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
            session.setAttribute("successMessage", "Cập nhật chức vụ thành công!");
        }   else {
            session.setAttribute("errorMessage", "Cập nhật chức vụ thất bại!");
        }

        response.sendRedirect(request.getContextPath() + "/userlist");
    }
}
