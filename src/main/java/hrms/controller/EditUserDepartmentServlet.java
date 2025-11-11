package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.ChangeDepartmentDAO;
import hrms.dao.DepartmentDAO;
import hrms.dao.PositionDAO;
import hrms.dao.WorkHistoryDAO;
import hrms.dto.UserDTO;
import hrms.model.Account;
import hrms.model.Department;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/updateDepartment")
public class EditUserDepartmentServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIDParam = request.getParameter("userID");
        
        if (userIDParam == null || userIDParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/userlist");
            return;
        }
        
        try {
            int userID = Integer.parseInt(userIDParam);
            ChangeDepartmentDAO changeDepartmentDAO = new ChangeDepartmentDAO();
            DepartmentDAO departmentDAO = new DepartmentDAO();
            
            // Lấy thông tin user
            UserDTO userDetail = changeDepartmentDAO.getUserDetailById(userID);
            
            if (userDetail == null) {
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", "Không tìm thấy người dùng!");
                response.sendRedirect(request.getContextPath() + "/user_detail?userID=" + userID);
                return;
            }
            
            
            int currentDepartmentID = changeDepartmentDAO.getCurrentDepartmentID(userID);
            
            
            List<Department> departments = departmentDAO.getAll();
            
            request.setAttribute("user", userDetail);
            request.setAttribute("currentDepartmentID", currentDepartmentID);
            request.setAttribute("departments", departments);
            request.getRequestDispatcher("/view/users/changeUserDepartment.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/userlist");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        try {
            int userID = Integer.parseInt(request.getParameter("userID"));
            int newDepartmentID = Integer.parseInt(request.getParameter("newDepartmentID"));

            ChangeDepartmentDAO changeDepartmentDAO = new ChangeDepartmentDAO();
            DepartmentDAO departmentDAO = new DepartmentDAO();
            WorkHistoryDAO workHistoryDAO = new WorkHistoryDAO();
            
            // Get current position and department
            int currentPositionID = changeDepartmentDAO.getCurrentPositionID(userID);
            int currentDepartmentID = changeDepartmentDAO.getCurrentDepartmentID(userID);
            
            // Get department names for work history
            String oldDepartmentName = departmentDAO.getNameById(currentDepartmentID);
            String newDepartmentName = departmentDAO.getNameById(newDepartmentID);
            
            if (oldDepartmentName == null) oldDepartmentName = "None";
            if (newDepartmentName == null) newDepartmentName = "Unknown";
            
            // Get current user from session (the person making the change)
            Account currentUser = (Account) session.getAttribute("account");
            int performedByUserID = (currentUser != null) ? currentUser.getUserID() : userID;
            
            // If department changes, check if current position belongs to new department
            if (currentDepartmentID != newDepartmentID && currentPositionID > 0) {
                // Check if current position belongs to new department
                PositionDAO positionDAO = new PositionDAO();
                boolean positionBelongsToDepartment = positionDAO.checkPositionBelongsToDepartment(currentPositionID, newDepartmentID);
                
                if (!positionBelongsToDepartment) {
                    // Reset position to NULL if it doesn't belong to new department
                    boolean resetSuccess = changeDepartmentDAO.updateUserDepartmentAndResetPosition(userID, newDepartmentID);
                    if (resetSuccess) {
                        // Log to work history
                        workHistoryDAO.addWorkHistory(
                            performedByUserID,
                            "Department Change",
                            oldDepartmentName,
                            newDepartmentName,
                            "Department changed from " + oldDepartmentName + " to " + newDepartmentName + ". Position reset due to incompatibility.",
                            "Position reset required"
                        );
                        
                        session.setAttribute("successMessage", "Update department successful! Position has been reset because it does not belong to the new department.");
                    } else {
                        session.setAttribute("errorMessage", "Update department failed!");
                    }
                    response.sendRedirect(request.getContextPath() + "/user_detail?userID=" + userID);
                    return;
                }
            }
            
            // Normal update if position is compatible or NULL
            boolean success = changeDepartmentDAO.updateUserDepartment(userID, newDepartmentID);

            if(success) {
                // Log to work history
                workHistoryDAO.addWorkHistory(
                    performedByUserID,
                    "Department Change",
                    oldDepartmentName,
                    newDepartmentName,
                    "Department changed from " + oldDepartmentName + " to " + newDepartmentName,
                    "Updated successfully"
                );
                
                session.setAttribute("successMessage", "Update department successfull!");
            } else {
                session.setAttribute("errorMessage", "Update department failed!");
            }
        } catch (NumberFormatException e) {
            int userID = Integer.parseInt(request.getParameter("userID"));
            session.setAttribute("errorMessage", "Invalid data!");
            response.sendRedirect(request.getContextPath() + "/user_detail?userID=" + userID);
            return;
        } catch (Exception e) {
            int userID = Integer.parseInt(request.getParameter("userID"));
            session.setAttribute("errorMessage", "System error: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/user_detail?userID=" + userID);
            return;
        }
        
        int userID = Integer.parseInt(request.getParameter("userID"));
        response.sendRedirect(request.getContextPath() + "/user_detail?userID=" + userID);
    }
}
