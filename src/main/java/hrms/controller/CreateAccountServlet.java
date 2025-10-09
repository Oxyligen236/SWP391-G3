package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.AccountDAO;
import hrms.dao.RoleDAO;
import hrms.model.Account;
import hrms.model.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/account/create")
public class CreateAccountServlet extends HttpServlet {

    private final AccountDAO accountDAO = new AccountDAO();
    private final RoleDAO roleDAO = new RoleDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        List<Role> roleList = roleDAO.getAllRoles();
        req.setAttribute("roleList", roleList);
        req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        int userID = Integer.parseInt(req.getParameter("userID"));
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        int roleID = Integer.parseInt(req.getParameter("roleID"));

        String errorMessage = null;

        // 1. Kiểm tra user đã có account chưa
        if (accountDAO.getAccountByUserID(userID) != null) {
            errorMessage = "Người dùng này đã có tài khoản!";
        } 
        // 2. Kiểm tra username đã tồn tại chưa
        else if (accountDAO.getAccountByUsername(username) != null) {
            errorMessage = "Tên đăng nhập đã tồn tại, vui lòng chọn tên khác!";
        } 
        // 3. Nếu hợp lệ, tạo account
        else {
            Account account = new Account();
            account.setUserID(userID);
            account.setUsername(username);
            account.setPassword(password); // TODO: hash password
            account.setRole(roleID);
            account.setIsActive(true);

            if (accountDAO.createAccount(account)) {
                req.setAttribute("successMessage", "Tạo tài khoản thành công!");
            } else {
                errorMessage = "Tạo tài khoản thất bại!";
            }
        }

        if (errorMessage != null) {
            req.setAttribute("errorMessage", errorMessage);
        }

        req.setAttribute("roleList", roleDAO.getAllRoles());
        req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
    }
}
