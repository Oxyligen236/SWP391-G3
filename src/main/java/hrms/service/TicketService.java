package hrms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import hrms.dao.TicketDAO;
import hrms.dao.Ticket_TypesDAO;
import hrms.dto.TicketDTO;
import hrms.model.Ticket;
import hrms.model.Ticket_Types;


public class TicketService{

    private TicketDAO ticketDAO = new TicketDAO();
    private Ticket_TypesDAO ticketTypesDAO = new Ticket_TypesDAO();

    public List<TicketDTO> getAllTicketsForDisplay() {
        List<TicketDTO> list = new ArrayList<>();       

        List<Ticket> tickets = ticketDAO.getAll();
        
     
        for (Ticket t : tickets) {
   
            Ticket_Types type = ticketTypesDAO.getTicketTypeById(t.getTicket_Type_ID());
            String typeName = type != null ? type.getName() : "Unknown";
            
   
            TicketDTO dto = new TicketDTO(
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
                null,
                null
            );
            list.add(dto);
        }
        
        return list;
    }

    public TicketDTO getTicketById(int ticketId) {
    
        Ticket ticket = ticketDAO.getTicketById(ticketId);
        
        if (ticket == null) return null;

        Ticket_Types type = ticketTypesDAO.getTicketTypeById(ticket.getTicket_Type_ID());
        String typeName = (type != null) ? type.getName() : "Unknown";
        
  
        return new TicketDTO(
            ticket.getTicketID(),
            ticket.getUserID(),
            ticket.getTicket_Type_ID(),
            ticket.getCreate_Date(),  
            ticket.getTicket_Content(),
            ticket.getApproverID(),
            ticket.getApprove_Date(), 
            ticket.getComment(),
            ticket.getStatus(),
            typeName,
            null,
            null
        );
    }
    
    public List<TicketDTO> getTicketsByUserId(int userId) {
        List<TicketDTO> list = new ArrayList<>();
        
  
        List<Ticket> tickets = ticketDAO.getTicketsByUserId(userId); 
        
    
        for (Ticket t : tickets) {
            Ticket_Types type = ticketTypesDAO.getTicketTypeById(t.getTicket_Type_ID());
            String typeName = (type != null) ? type.getName() : "Unknown";
            
            TicketDTO dto = new TicketDTO(
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
                null,
                null
            );
            list.add(dto);
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