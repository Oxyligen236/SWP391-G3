package hrms.controller;

import java.io.IOException;
import hrms.dao.AccountDAO;
import hrms.dao.RoleDAO;
import hrms.model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/account/create")
public class CreateAccountServlet extends HttpServlet {

    private final RoleDAO roleDAO = new RoleDAO();
    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // Kiểm tra quyền admin
        Account currentUser = (Account) req.getSession().getAttribute("currentUser");
        if(currentUser == null || currentUser.getRole() != 1) { // roleID=1 là admin
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập!");
            return;
        }

        req.setAttribute("roleList", roleDAO.getAllRoles());
        req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Kiểm tra quyền admin
        Account currentUser = (Account) req.getSession().getAttribute("currentUser");
        if(currentUser == null || currentUser.getRole() != 1) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền thực hiện hành động này!");
            return;
        }

        req.setCharacterEncoding("UTF-8");

        int userID = Integer.parseInt(req.getParameter("userID"));
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        int roleID = Integer.parseInt(req.getParameter("roleID"));
        boolean isActive = Boolean.parseBoolean(req.getParameter("isActive"));

        // Kiểm tra mật khẩu
        if(!password.equals(confirmPassword)) {
            req.setAttribute("errorMessage", "Mật khẩu xác nhận không khớp!");
            req.setAttribute("roleList", roleDAO.getAllRoles());
            req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
            return;
        }

        // Kiểm tra username đã tồn tại chưa
        if(accountDAO.getAccountByUsername(username) != null) {
            req.setAttribute("errorMessage", "Username đã tồn tại!");
            req.setAttribute("roleList", roleDAO.getAllRoles());
            req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
            return;
        }

        // Kiểm tra user đã có tài khoản chưa
        if(accountDAO.getAccountByUserID(userID) != null) {
            req.setAttribute("errorMessage", "Người dùng này đã có tài khoản!");
            req.setAttribute("roleList", roleDAO.getAllRoles());
            req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
            return;
        }

        // Tạo account
        Account account = new Account();
        account.setUserID(userID);
        account.setUsername(username);
        account.setPassword(password); // TODO: hash password nếu muốn
        account.setRole(roleID);
        account.setIsActive(isActive);

        boolean created = accountDAO.createAccount(account);

        if(created) {
            req.setAttribute("successMessage", "Tạo tài khoản thành công!");
        } else {
            req.setAttribute("errorMessage", "Tạo tài khoản thất bại!");
        }

        req.setAttribute("roleList", roleDAO.getAllRoles());
        req.getRequestDispatcher("/view/account/createAccount.jsp").forward(req, resp);
    }
}
