package hrms.controller;

import java.io.IOException;

import hrms.dao.AccountDAO;
import hrms.dto.AccountDTO;
import hrms.model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/account/view")
public class ViewAccountServlet extends HttpServlet {
    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Account currentUser = (Account) session.getAttribute("account");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        try {
            if (currentUser.getRole() == 1) {

                request.setAttribute("accountList", accountDAO.getAllAccountsDTO());
                request.getRequestDispatcher("/view/account/viewAccountList.jsp").forward(request, response);
            } else {

                AccountDTO self = accountDAO.getAccountDTOByID(currentUser.getAccountID());
                if (self == null) {
                    request.setAttribute("errorMessage", "Không tìm thấy tài khoản của bạn!");
                    request.getRequestDispatcher("/view/error.jsp").forward(request, response);
                    return;
                }

                request.setAttribute("account", self);
                request.getRequestDispatcher("/view/account/viewAccountDetail.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi hệ thống: " + e.getMessage());
        }
    }
}
