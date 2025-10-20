package hrms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import hrms.dao.TicketDAO;
import hrms.dao.Ticket_TypesDAO;
import hrms.dao.UserDAO;
import hrms.dto.TicketDTO;
import hrms.model.Ticket;
import hrms.model.Ticket_Types;
import hrms.model.User;

public class TicketService {

    private TicketDAO ticketDAO = new TicketDAO();
    private Ticket_TypesDAO ticketTypesDAO = new Ticket_TypesDAO();
    private UserDAO userDAO = new UserDAO();

    /**
     * Helper method: Chuyển Ticket sang TicketDTO với đầy đủ thông tin
     */
    private TicketDTO convertToDTO(Ticket t) {
        // Lấy Ticket Type Name
        Ticket_Types type = ticketTypesDAO.getTicketTypeById(t.getTicket_Type_ID());
        String typeName = (type != null) ? type.getName() : "Unknown";

        // Lấy User Full Name (Sender)
        String userFullName = null;
        if (t.getUserID() > 0) {  // Bỏ check null
            User user = userDAO.getUserById(t.getUserID());
            userFullName = (user != null) ? user.getFullname() : "Unknown";
        }

        // Lấy Approver Full Name
        String approverFullName = null;
        if (t.getApproverID() > 0) {  // Bỏ check null
            User approver = userDAO.getUserById(t.getApproverID());
            approverFullName = (approver != null) ? approver.getFullname() : "Unknown";
        }

        return new TicketDTO(
                t.getTicketID(),
                t.getUserID(),
                t.getTicket_Type_ID(),
                t.getCreate_Date(),
                t.getTicket_Content(),
                t.getApproverID(),
                t.getApprove_Date(),
                t.getComment(),
                t.getStatus(),
                typeName,
                userFullName,
                approverFullName
        );
    }

    public List<TicketDTO> getAllTicketsForDisplay() {
        List<TicketDTO> list = new ArrayList<>();
        List<Ticket> tickets = ticketDAO.getAll();

        for (Ticket t : tickets) {
            list.add(convertToDTO(t));
        }

        return list;
    }

    public TicketDTO getTicketById(int ticketId) {
        Ticket ticket = ticketDAO.getTicketById(ticketId);

        if (ticket == null) {
            return null;
        }

        return convertToDTO(ticket);
    }

    public List<TicketDTO> getTicketsByUserId(int userId) {
        List<TicketDTO> list = new ArrayList<>();
        List<Ticket> tickets = ticketDAO.getTicketsByUserId(userId);

        for (Ticket t : tickets) {
            list.add(convertToDTO(t));
        }

        return list;
    }

    public List<TicketDTO> getTicketsByDepartmentId(int departmentId) {
        List<TicketDTO> list = new ArrayList<>();
        List<Ticket> tickets = ticketDAO.getTicketsByDepartmentId(departmentId);

        for (Ticket t : tickets) {
            list.add(convertToDTO(t));
        }

        return list;
    }

    public int createTicket(int userId, int typeId, String content) {
        if (content == null || content.trim().isEmpty()) {
            return -1;
        }

        Ticket ticket = new Ticket();
        ticket.setUserID(userId);
        ticket.setTicket_Type_ID(typeId);
        ticket.setTicket_Content(content);
        ticket.setCreate_Date(LocalDate.now());
        ticket.setStatus("Pending");

        return ticketDAO.add(ticket);
    }
}
