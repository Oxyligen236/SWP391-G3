package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dto.TicketDTO;
import hrms.service.TicketService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ticketList")
public class TicketListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TicketService ticketService = new TicketService();
        List<TicketDTO> tickets = ticketService.getAllTicketsForDisplay();
        request.setAttribute("ticketList", tickets);
        request.getRequestDispatcher("/view/ticket/ticketList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}