package hrms.controller;

import java.io.IOException;
import java.time.LocalDate;

import hrms.dao.TicketDAO;
import hrms.dto.TicketDTO;
import hrms.model.Account;
import hrms.model.Ticket;
import hrms.service.TicketService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/approve-ticket")
public class ApproveTicketServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TicketService ticketService = new TicketService();
    private TicketDAO ticketDAO = new TicketDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ticketIdRaw = request.getParameter("ticketId");

        if (ticketIdRaw == null || ticketIdRaw.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/department-ticket");
            return;
        }

        try {
            int ticketId = Integer.parseInt(ticketIdRaw);
            TicketDTO ticket = ticketService.getTicketById(ticketId);

            if (ticket == null) {
                request.setAttribute("errorMessage", "Ticket not found!");
                response.sendRedirect(request.getContextPath() + "/department-ticket");
                return;
            }

            request.setAttribute("ticket", ticket);
            boolean isPending = "Pending".equalsIgnoreCase(ticket.getStatus());
            request.setAttribute("isPending", isPending);


            request.getRequestDispatcher("/view/ticket/ticketDetail.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/department-ticket");
        }
    }

 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        try {
            int ticketId = Integer.parseInt(request.getParameter("ticketId"));
            String action = request.getParameter("action");
            String comment = request.getParameter("comment");

            if (action == null || (!action.equals("approve") && !action.equals("reject"))) {
                response.sendRedirect(request.getContextPath() + "/approve-ticket?ticketId=" + ticketId);
                return;
            }

            Ticket ticket = ticketDAO.getTicketById(ticketId);

            if (ticket == null || !"Pending".equalsIgnoreCase(ticket.getStatus())) {
                session.setAttribute("errorMessage", "Ticket is not available for approval!");
                response.sendRedirect(request.getContextPath() + "/department-ticket");
                return;
            }

            String newStatus = action.equals("approve") ? "Approved" : "Rejected";
            ticket.setStatus(newStatus);
            ticket.setApproverID(account.getUserID());
            ticket.setApprove_Date(LocalDate.now());
            ticket.setComment(comment != null && !comment.trim().isEmpty() ? comment : null);

            boolean success = ticketDAO.update(ticket);

            if (success) {
                session.setAttribute("successMessage",
                        "Ticket has been " + newStatus.toLowerCase() + " successfully!");
            } else {
                session.setAttribute("errorMessage", "Failed to update ticket!");
            }

            response.sendRedirect(request.getContextPath() + "/department-ticket");

        } catch (NumberFormatException e) {
            System.out.println(e);
            response.sendRedirect(request.getContextPath() + "/department-ticket");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}