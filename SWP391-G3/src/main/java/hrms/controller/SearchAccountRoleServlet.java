package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.UserRoleDAO;
import hrms.dto.AccountDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/searchAccountRole")
public class SearchAccountRoleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String searchValue = request.getParameter("searchValue");
        UserRoleDAO dao = new UserRoleDAO();
        List<AccountDTO> accounts;

        if (searchValue == null || searchValue.trim().isEmpty()) {
            accounts = dao.getAllAccountWithRoles(); 
        } else {
            accounts = dao.searchAccountByNameOrID(searchValue.trim());
        }

   
        request.setAttribute("searchValue", searchValue);
        request.setAttribute("accounts", accounts);
        request.setAttribute("roles", dao.getAllRoles());

        request.getRequestDispatcher("/view/account/updateAccountRole.jsp").forward(request, response);
    }
}
