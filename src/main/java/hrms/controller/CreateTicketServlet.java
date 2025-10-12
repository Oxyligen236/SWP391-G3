package hrms.controller;

import java.io.IOException;
import java.util.List;

import hrms.dao.Ticket_TypesDAO;
import hrms.dao.UserDAO;
import hrms.model.Account;
import hrms.model.Ticket_Types;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/create-ticket")
public class CreateTicketServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        UserDAO userDAO = new UserDAO();
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/view/login/login.jsp");
            return;
        }

        int userId = account.getUserID();
        String fullName = userDAO.getUserById(userId).getFullname(); 
        
        Ticket_TypesDAO ticketTypesDAO = new Ticket_TypesDAO();
        List<Ticket_Types> ticketTypes = ticketTypesDAO.getAllTicketTypes();
        
        request.setAttribute("userId", userId);
        request.setAttribute("fullname", fullName);
        request.setAttribute("ticketTypes", ticketTypes);
        
        request.getRequestDispatcher("/view/ticket/createTicket.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        UserDAO userDAO = new UserDAO();
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        int userId = account.getUserID();
        String fullName = userDAO.getUserById(userId).getFullname(); 
        String selectedTypeId = request.getParameter("selectedTypeId");
        
        Ticket_TypesDAO ticketTypesDAO = new Ticket_TypesDAO();
        List<Ticket_Types> ticketTypes = ticketTypesDAO.getAllTicketTypes();
        
        request.setAttribute("userId", userId);
        request.setAttribute("fullname", fullName);
        request.setAttribute("ticketTypes", ticketTypes);
        request.setAttribute("selectedTypeId", selectedTypeId);
        
        request.getRequestDispatcher("/view/ticket/createTicket.jsp").forward(request, response);
    }
}