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

        // --- Pagination ---
        int currentPage = 1;
        int itemsPerPage = 10;

        String pageParam = request.getParameter("page");
        String itemsParam = request.getParameter("itemsPerPage");

        if (pageParam != null) {
            try {
                currentPage = Integer.parseInt(pageParam);
                if (currentPage < 1) currentPage = 1;
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        if (itemsParam != null) {
            try {
                itemsPerPage = Integer.parseInt(itemsParam);
                if (itemsPerPage < 1) itemsPerPage = 5;
                if (itemsPerPage > 50) itemsPerPage = 50;
            } catch (NumberFormatException e) {
                itemsPerPage = 10;
            }
        }

        int totalItems = filteredUsers.size();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        if (currentPage > totalPages && totalPages > 0) currentPage = totalPages;

        int startIndex = (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

        List<UserDTO> paginatedUsers = filteredUsers.subList(startIndex, endIndex);

        request.setAttribute("users", paginatedUsers);
        request.setAttribute("userHasAccount", userHasAccount);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("accountFilter", accountFilter);
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher("/view/users/userlist.jsp").forward(request, response);
    }
}
