package hrms.controller;

import hrms.dao.AccountDAO;
import hrms.dao.UserDAO;
import hrms.dto.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/userlist")
public class UserListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDAO uDAO = new UserDAO();
        AccountDAO aDAO = new AccountDAO();

        List<UserDTO> users = uDAO.getAllWithJoin();
        List<Integer> userHasAccount = new ArrayList<>();

        for (UserDTO u : users) {
            if (aDAO.getAccountByUserID(u.getUserId()) != null) {
                userHasAccount.add(u.getUserId());
            }
        }

        String accountFilter = request.getParameter("accountFilter"); // "yes", "no", null
        String keyword = request.getParameter("keyword"); // search fullname

        List<UserDTO> filteredUsers = new ArrayList<>();
        for (UserDTO u : users) {
            boolean hasAccount = userHasAccount.contains(u.getUserId());

            // Filter by Account
            if ("yes".equals(accountFilter) && !hasAccount) continue;
            if ("no".equals(accountFilter) && hasAccount) continue;

            // Filter by fullname only
            if (keyword != null && !keyword.trim().isEmpty()) {
                String k = keyword.trim().toLowerCase();
                if (!u.getFullname().toLowerCase().contains(k)) {
                    continue;
                }
            }

            filteredUsers.add(u);
        }

        request.setAttribute("users", filteredUsers);
        request.setAttribute("userHasAccount", userHasAccount);
        request.getRequestDispatcher("/view/users/userlist.jsp").forward(request, response);
    }
}
