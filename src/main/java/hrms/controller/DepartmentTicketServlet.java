package hrms.controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import hrms.dao.Ticket_TypesDAO;
import hrms.dto.TicketDTO;
import hrms.dto.UserDTO;
import hrms.model.Account;
import hrms.model.Ticket_Types;
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
        UserDTO userDTO = userService.getUserById(userId);

        String statusParam = request.getParameter("status");
        String typeParam = request.getParameter("type");
        String searchSender = request.getParameter("searchSender");
        String sortByParam = request.getParameter("sortBy");
        String sortOrderParam = request.getParameter("sortOrder");

        String statusFilter;
        if (statusParam == null || statusParam.isEmpty()) {
            statusFilter = "Pending";
        } else {
            statusFilter = statusParam;
        }

        String typeFilter;
        if (typeParam == null || typeParam.isEmpty()) {
            typeFilter = "All";
        } else {
            typeFilter = typeParam;
        }

        String sortBy;
        if (sortByParam == null || sortByParam.isEmpty()) {
            sortBy = "createDate";
        } else {
            sortBy = sortByParam;
        }

        String sortOrder;
        if (sortOrderParam == null || sortOrderParam.isEmpty()) {
            sortOrder = "desc";
        } else {
            sortOrder = sortOrderParam;
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

        List<TicketDTO> tickets = ticketService.getTicketsByDepartmentId(userDTO.getDepartmentId());

        if (searchSender != null && !searchSender.trim().isEmpty()) {
            String searchLower = searchSender.toLowerCase().trim();
            tickets = tickets.stream()
                    .filter(t -> t.getUserFullName() != null
                    && t.getUserFullName().toLowerCase().contains(searchLower))
                    .collect(Collectors.toList());
        }

        if (!statusFilter.equals("All")) {
            tickets = tickets.stream()
                    .filter(t -> t.getStatus() != null && t.getStatus().equalsIgnoreCase(statusFilter))
                    .collect(Collectors.toList());
        }

        if (!typeFilter.equals("All")) {
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

            if (sortBy.equals("createDate")) {
                comparator = Comparator.comparing(TicketDTO::getCreate_Date,
                        Comparator.nullsLast(Comparator.naturalOrder()));
            } else if (sortBy.equals("approveDate")) {
                comparator = Comparator.comparing(TicketDTO::getApprove_Date,
                        Comparator.nullsLast(Comparator.naturalOrder()));
            }

            if (comparator != null) {
                if (sortOrder.equalsIgnoreCase("desc")) {
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

        if (searchSender == null) {
            request.setAttribute("searchSender", "");
        } else {
            request.setAttribute("searchSender", searchSender);
        }

        request.setAttribute("selectedStatus", statusFilter);
        request.setAttribute("selectedType", typeFilter);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortOrder", sortOrder);

        request.getRequestDispatcher("/view/ticket/approveTicket.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: handle POST request
    }
}
