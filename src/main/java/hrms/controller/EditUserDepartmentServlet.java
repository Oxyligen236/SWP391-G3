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

        DepartmentDAO departmentDAO = new DepartmentDAO();
        ChangeDepartmentDAO changeDepartmentDAO = new ChangeDepartmentDAO();

        String searchValue = request.getParameter("searchValue");

        List<Department> departments = departmentDAO.getAll();
        List<UserDTO> users = changeDepartmentDAO.getAllUsersWithDepartment();

        if (searchValue != null && !searchValue.trim().isEmpty()) {
            users = changeDepartmentDAO.searchUsersWithDepartment(searchValue.trim());
            request.setAttribute("searchValue", searchValue);
        } else {
            users = changeDepartmentDAO.getAllUsersWithDepartment();
        }

        request.setAttribute("departments", departments);
        request.setAttribute("users", users);
        
        HttpSession session = request.getSession();
        String message = (String) session.getAttribute("message");
        if(message != null) {
            request.setAttribute("message", message);
            session.removeAttribute("message"); 
        }
        request.getRequestDispatcher("view/users/changeUserDepartment.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
        int userID = Integer.parseInt(request.getParameter("userID"));
        int newDepartmentID = Integer.parseInt(request.getParameter("newDepartmentID"));

        ChangeDepartmentDAO changeDepartmentDAO = new ChangeDepartmentDAO();
        boolean success = changeDepartmentDAO.updateUserDepartment(userID, newDepartmentID);

        HttpSession session = request.getSession();
        if(success) {
            session.setAttribute("message", "User department updated successfully!");
        }   else {
            session.setAttribute("message", "Failed to update user department.");
        }

        response.sendRedirect("updateDepartment");
    }
}
