package hrms.controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import hrms.dao.Ticket_TypesDAO;
import hrms.dto.TicketDTO;
import hrms.model.Account;
import hrms.model.Ticket_Types;
import hrms.service.TicketService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ticketList")
public class TicketListServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private TicketService ticketService = new TicketService();
    private Ticket_TypesDAO ticketTypesDAO = new Ticket_TypesDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/authenticate");
            return;
        }

        int userId = account.getUserID();

        String error = (String) session.getAttribute("error");
        if (error != null) {
            request.setAttribute("error", error);
            session.removeAttribute("error");
        }

        String statusFilter = request.getParameter("status");
        String typeFilter = request.getParameter("type");

        String sortBy = request.getParameter("sortBy");
        String sortOrder = request.getParameter("sortOrder");

        if (sortBy == null || sortBy.isEmpty()) {
            sortBy = "createDate";
        }
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = "desc";
        }

        int itemsPerPage = 5;
        if (request.getParameter("itemsPerPage") != null) {
            try {
                itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage"));
            } catch (NumberFormatException e) {
                itemsPerPage = 5;
            }
        }

        int currentPage = 1;
        if (request.getParameter("page") != null) {
            try {
                currentPage = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        List<TicketDTO> tickets = ticketService.getTicketsByUserId(userId);

        if (statusFilter != null && !statusFilter.isEmpty() && !statusFilter.equals("All")) {
            tickets = tickets.stream()
                    .filter(t -> t.getStatus().equalsIgnoreCase(statusFilter))
                    .collect(Collectors.toList());
        }

        if (typeFilter != null && !typeFilter.isEmpty() && !typeFilter.equals("All")) {
            try {
                int typeId = Integer.parseInt(typeFilter);
                tickets = tickets.stream()
                        .filter(t -> t.getTicket_Type_ID() == typeId)
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
            }
        }

        if (sortBy != null && !sortBy.isEmpty()) {
            Comparator<TicketDTO> comparator = null;

            switch (sortBy) {
                case "createDate":
                    comparator = Comparator.comparing(TicketDTO::getCreate_Date,
                            Comparator.nullsLast(Comparator.naturalOrder()));
                    break;
                case "approveDate":
                    comparator = Comparator.comparing(TicketDTO::getApprove_Date,
                            Comparator.nullsLast(Comparator.naturalOrder()));
                    break;
            }

            if (comparator != null) {
                if ("desc".equalsIgnoreCase(sortOrder)) {
                    comparator = comparator.reversed();
                }
                tickets.sort(comparator);
            }
        }

        int totalItems = tickets.size();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        if (currentPage > totalPages && totalPages > 0) {
            currentPage = totalPages;
        }

        int startIndex = (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

        List<TicketDTO> paginatedTickets = tickets.subList(startIndex, endIndex);

        List<Ticket_Types> ticketTypes = ticketTypesDAO.getAllTicketTypes();

        request.setAttribute("ticketList", paginatedTickets);
        request.setAttribute("ticketTypes", ticketTypes);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("totalItems", totalItems);

        request.setAttribute("selectedStatus", statusFilter != null ? statusFilter : "All");
        request.setAttribute("selectedType", typeFilter != null ? typeFilter : "All");
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortOrder", sortOrder);

        request.getRequestDispatcher("/view/ticket/ticketList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
