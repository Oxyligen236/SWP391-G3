package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.UserRoleDAO;
import hrms.dto.AccountDTO;
import hrms.model.Account;
import hrms.model.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/updateRole")
public class EditUserRoleServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String accountIDParam = request.getParameter("accountID");
        
        if (accountIDParam == null || accountIDParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/account/view");
            return;
        }
        
        try {
            int accountID = Integer.parseInt(accountIDParam);
            
            // Kiểm tra xem người dùng có đang cố chỉnh sửa role của chính họ không
            Account currentUser = (Account) session.getAttribute("account");
            if (currentUser != null && currentUser.getAccountID() == accountID) {
                session.setAttribute("errorMessage", "you can not edit your own role!");
                response.sendRedirect(request.getContextPath() + "/account/view");
                return;
            }
            
            UserRoleDAO roleDAO = new UserRoleDAO();
            
            // Lấy thông tin account (AccountDTO)
            AccountDTO accountDetail = roleDAO.getAccountDetailById(accountID);
            
            if (accountDetail == null) {
                session.setAttribute("errorMessage", "Cannot find account!");
                response.sendRedirect(request.getContextPath() + "/account/view");
                return;
            }
            

            int currentRoleID = roleDAO.getCurrentRoleID(accountID);
            

            List<Role> roles = roleDAO.getAllRoles();
            
            request.setAttribute("account", accountDetail);
            request.setAttribute("currentRoleID", currentRoleID);
            request.setAttribute("roles", roles);
            request.getRequestDispatcher("/view/account/updateAccountRole.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/account/view");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        try {
            int accountID = Integer.parseInt(request.getParameter("accountID"));
            int newRoleID = Integer.parseInt(request.getParameter("newRoleID"));

            // Kiểm tra xem người dùng có đang cố chỉnh sửa role của chính họ không
            Account currentUser = (Account) session.getAttribute("account");
            if (currentUser != null && currentUser.getAccountID() == accountID) {
                session.setAttribute("errorMessage", "you can not edit your own role!");
                response.sendRedirect(request.getContextPath() + "/account/view");
                return;
            }

            UserRoleDAO roleDAO = new UserRoleDAO();
            boolean success = roleDAO.updateUserRole(accountID, newRoleID);

            if(success) {
                session.setAttribute("successMessage", "Update role successfully!");
            } else {
                session.setAttribute("errorMessage", "Update role failed!");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Invalid data!");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "System error: " + e.getMessage());
        }


        response.sendRedirect(request.getContextPath() + "/account/view");
    }
}
