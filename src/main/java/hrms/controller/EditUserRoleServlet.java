package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.UserRoleDAO;
import hrms.dto.AccountDTO;
import hrms.model.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/updateRole")
public class EditUserRoleServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserRoleDAO roleDAO = new UserRoleDAO();
        List<AccountDTO> accounts = roleDAO.getAllAccountWithRoles();
        List<Role> roles = roleDAO.getAllRoles();

        request.setAttribute("accounts", accounts);
        request.setAttribute("roles", roles);
        request.getRequestDispatcher("view/account/updateAccountRole.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        
        int accountID = Integer.parseInt(request.getParameter("accountID"));
        int newRoleID = Integer.parseInt(request.getParameter("newRoleID"));


        UserRoleDAO roleDAO = new UserRoleDAO();
        

        boolean success = roleDAO.updateUserRole(accountID, newRoleID);

        if(success) {
            request.setAttribute("message", "User role updated successfully!");
        }   else {
            request.setAttribute("message", "Failed to update user role.");
        }

        List<AccountDTO> accounts = roleDAO.getAllAccountWithRoles();
        List<Role> roles = roleDAO.getAllRoles();
        request.setAttribute("accounts", accounts);
        request.setAttribute("roles", roles);

        request.getRequestDispatcher("view/account/updateAccountRole.jsp").forward(request, response);
    }
}
