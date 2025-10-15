package hrms.controller;

import java.io.IOException;

import hrms.dao.AccountDAO;
import hrms.model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/account/toggle-status")
public class ToggleStatusServlet extends HttpServlet {
    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("account");

        if (currentUser == null || currentUser.getRole() != 1) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền thực hiện hành động này!");
            return;
        }

        try {
            int accountID = Integer.parseInt(request.getParameter("accountID"));
            boolean result = accountDAO.toggleAccountStatus(accountID);

            if (!result) {
                session.setAttribute("errorMessage", "Thao tác thất bại!");
            } else {
                session.setAttribute("successMessage", "Thay đổi trạng thái thành công!");
            }
        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "ID tài khoản không hợp lệ!");
        }

        response.sendRedirect(request.getContextPath() + "/account/view");
    }
}
