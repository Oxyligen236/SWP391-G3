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
import jakarta.servlet.http.HttpSession;

@WebServlet("/updateRole")
public class EditUserRoleServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountIDParam = request.getParameter("accountID");
        
        if (accountIDParam == null || accountIDParam.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/account/view");
            return;
        }
        
        try {
            int accountID = Integer.parseInt(accountIDParam);
            UserRoleDAO roleDAO = new UserRoleDAO();
            
            // Lấy thông tin account (AccountDTO)
            AccountDTO accountDetail = roleDAO.getAccountDetailById(accountID);
            
            if (accountDetail == null) {
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", "Không tìm thấy tài khoản!");
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

            UserRoleDAO roleDAO = new UserRoleDAO();
            boolean success = roleDAO.updateUserRole(accountID, newRoleID);

            if(success) {
                session.setAttribute("successMessage", "Cập nhật vai trò thành công!");
            } else {
                session.setAttribute("errorMessage", "Cập nhật vai trò thất bại!");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Dữ liệu không hợp lệ!");
        } catch (Exception e) {
            session.setAttribute("errorMessage", "Lỗi hệ thống: " + e.getMessage());
        }


        response.sendRedirect(request.getContextPath() + "/account/view");
    }
}
