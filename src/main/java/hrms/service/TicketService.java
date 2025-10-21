package hrms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import hrms.dao.DepartmentDAO;
import hrms.dao.LeaveDetailDAO;
import hrms.dao.OTDetailDAO;
import hrms.dao.TicketDAO;
import hrms.dao.Ticket_TypesDAO;
import hrms.dao.UserDAO;
import hrms.dto.TicketDTO;
import hrms.model.LeaveDetail;
import hrms.model.OTDetail;
import hrms.model.Ticket;
import hrms.model.Ticket_Types;
import hrms.model.User;

public class TicketService {

    private TicketDAO ticketDAO = new TicketDAO();
    private Ticket_TypesDAO ticketTypesDAO = new Ticket_TypesDAO();
    private UserDAO userDAO = new UserDAO();
    private DepartmentDAO departmentDAO = new DepartmentDAO();
    private LeaveDetailDAO leaveDetailDAO = new LeaveDetailDAO();
    private OTDetailDAO otDetailDAO = new OTDetailDAO();

    /**
     * Helper method: Chuyển Ticket sang TicketDTO với đầy đủ thông tin
     */
    private TicketDTO convertToDTO(Ticket t) {
        TicketDTO dto = new TicketDTO();

        // Set basic ticket info
        dto.setTicketID(t.getTicketID());
        dto.setUserID(t.getUserID());
        dto.setTicket_Type_ID(t.getTicket_Type_ID());
        dto.setTicket_Content(t.getTicket_Content());
        dto.setStatus(t.getStatus());
        dto.setCreate_Date(t.getCreate_Date());
        dto.setApproverID(t.getApproverID());
        dto.setApprove_Date(t.getApprove_Date());
        dto.setComment(t.getComment());

        // Lấy Ticket Type Name
        Ticket_Types type = ticketTypesDAO.getTicketTypeById(t.getTicket_Type_ID());
        dto.setTicketTypeName((type != null) ? type.getName() : "Unknown");

        // Lấy User Full Name và Department Name (Sender)
        if (t.getUserID() > 0) {
            User user = userDAO.getUserById(t.getUserID());
            if (user != null) {
                dto.setUserFullName(user.getFullname());

                Integer deptId = user.getDepartmentId();
                if (deptId != null && deptId > 0) {
                    String deptName = departmentDAO.getNameById(deptId);
                    dto.setDepartmentName((deptName != null) ? deptName : "Unknown");
                } else {
                    dto.setDepartmentName("N/A");
                }
            } else {
                dto.setUserFullName("Unknown");
                dto.setDepartmentName("N/A");
            }
        }

        // Lấy Approver Full Name
        if (t.getApproverID() > 0) {
            User approver = userDAO.getUserById(t.getApproverID());
            dto.setApproverName((approver != null) ? approver.getFullname() : "Unknown");
        }

        // Lấy thông tin chi tiết theo loại ticket
        if (t.getTicket_Type_ID() == 1) {
            // Leave Ticket
            LeaveDetail leaveDetail = leaveDetailDAO.getByTicketId(t.getTicketID());
            if (leaveDetail != null) {
                dto.setLeaveType(leaveDetail.getLeaveType());
                dto.setStartDate(leaveDetail.getStart_Date());
                dto.setEndDate(leaveDetail.getEnd_Date());
            }
        } else if (t.getTicket_Type_ID() == 2) {
            // Overtime Ticket
            List<OTDetail> otDetails = otDetailDAO.getByTicketId(t.getTicketID());
            if (otDetails != null && !otDetails.isEmpty()) {
                OTDetail firstOT = otDetails.get(0);
                dto.setOvertimeDate(firstOT.getOt_Date());
                dto.setStartTime(firstOT.getFormattedStartTime());
                dto.setEndTime(firstOT.getFormattedEndTime());
            }
        }

        return dto;
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
