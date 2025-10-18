package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dto.TicketDTO;
import hrms.dto.UserDTO;
import hrms.model.Account;
import hrms.service.TicketService;
import hrms.service.UserService;
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
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        int userId = account.getUserID();
        UserDTO userDTO = userService.getUserById(userId);

        List<TicketDTO> tickets = ticketService.getTicketsByDepartmentId(userDTO.getDepartmentId());
        request.setAttribute("tickets", tickets);
        request.getRequestDispatcher("/view/ticket/approveTicket.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: handle POST request
    }
}
