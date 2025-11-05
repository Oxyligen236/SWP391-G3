package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.ChangeDepartmentDAO;
import hrms.dao.DepartmentDAO;
import hrms.dto.UserDTO;
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
                response.sendRedirect(request.getContextPath() + "/userlist");
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
            boolean success = changeDepartmentDAO.updateUserDepartment(userID, newDepartmentID);

            if(success) {
                session.setAttribute("successMessage", "Cập nhật phòng ban thành công!");
            } else {
                session.setAttribute("errorMessage", "Cập nhật phòng ban thất bại!");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Dữ liệu không hợp lệ!");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Lỗi hệ thống: " + e.getMessage());
        }


        response.sendRedirect(request.getContextPath() + "/userlist");
    }
}
