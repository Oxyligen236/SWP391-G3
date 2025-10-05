package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.TicketDAO;
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
        TicketDAO TicketDAO = new TicketDAO();
        List tickets = TicketDAO.getAll();
        request.setAttribute("ticketList", tickets);
        request.getRequestDispatcher("/WEB-INF/views/ticketList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: handle POST request
    }
}
