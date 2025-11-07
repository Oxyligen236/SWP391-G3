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

        // Get user's current department
        int currentDepartmentID = changePositionDAO.getCurrentDepartmentID(userID);
        
        if (currentDepartmentID <= 0) {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Người dùng chưa có phòng ban! Vui lòng cập nhật phòng ban trước.");
            response.sendRedirect(request.getContextPath() + "/userlist");
            return;
        }
        
        int currentPositionID = changePositionDAO.getCurrentPositionID(userID);
        
        // Only get positions that belong to user's current department
        List<Position> positions = positionDAO.getByDepartmentId(currentDepartmentID);
        
        if (positions.isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Phòng ban hiện tại chưa có chức vụ nào! Vui lòng thêm chức vụ cho phòng ban.");
            response.sendRedirect(request.getContextPath() + "/userlist");
            return;
        }

        request.setAttribute("user", user);
        request.setAttribute("currentPositionID", currentPositionID);
        request.setAttribute("positions", positions);
        request.setAttribute("currentDepartmentID", currentDepartmentID);

        request.getRequestDispatcher("view/users/changeUserPosition.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        try {
            int userID = Integer.parseInt(request.getParameter("userID"));
            int newPositionID = Integer.parseInt(request.getParameter("newPositionID"));

            ChangePositionDAO changePositionDAO = new ChangePositionDAO();
            PositionDAO positionDAO = new PositionDAO();
            
            // Get user's current department
            int currentDepartmentID = changePositionDAO.getCurrentDepartmentID(userID);
            
            if (currentDepartmentID <= 0) {
                session.setAttribute("errorMessage", "Người dùng chưa có phòng ban! Vui lòng cập nhật phòng ban trước.");
                response.sendRedirect(request.getContextPath() + "/userlist");
                return;
            }
            
            // Check if the new position belongs to user's department
            boolean positionBelongsToDepartment = positionDAO.checkPositionBelongsToDepartment(newPositionID, currentDepartmentID);
            
            if (!positionBelongsToDepartment) {
                session.setAttribute("errorMessage", "Chức vụ này không thuộc phòng ban hiện tại của người dùng!");
                response.sendRedirect(request.getContextPath() + "/userlist");
                return;
            }
            
            // Update position
            boolean success = changePositionDAO.updateUserPosition(userID, newPositionID);

            if(success) {
                session.setAttribute("successMessage", "Cập nhật chức vụ thành công!");
            } else {
                session.setAttribute("errorMessage", "Cập nhật chức vụ thất bại!");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Dữ liệu không hợp lệ!");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Lỗi hệ thống: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/userlist");
    }
}
