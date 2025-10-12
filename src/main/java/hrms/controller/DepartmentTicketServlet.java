package hrms.controller;

import java.io.IOException;

import hrms.model.Account;
import hrms.service.TicketService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/department-ticket")
public class DepartmentTicketServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TicketService ticketService = new TicketService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        int userId = account.getUserID();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: handle POST request
    }
}
