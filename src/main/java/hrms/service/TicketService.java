package hrms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import hrms.dao.DepartmentDAO;
import hrms.dao.LeaveDetailDAO;
import hrms.dao.Leave_TypesDAO;
import hrms.dao.OTDetailDAO;
import hrms.dao.TicketDAO;
import hrms.dao.Ticket_TypesDAO;
import hrms.dao.UserDAO;
import hrms.dto.TicketDTO;
import hrms.model.LeaveDetail;
import hrms.model.Leave_Types;
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
    private Leave_TypesDAO leave_TypesDAO = new Leave_TypesDAO();

    private TicketDTO convertToDTO(Ticket t) {
        TicketDTO dto = new TicketDTO();

        dto.setTicketID(t.getTicketID());
        dto.setUserID(t.getUserID());
        dto.setTicket_Type_ID(t.getTicket_Type_ID());
        dto.setTicket_Content(t.getTicket_Content());
        dto.setStatus(t.getStatus());
        dto.setCreate_Date(t.getCreate_Date());
        dto.setApproverID(t.getApproverID());
        dto.setApprove_Date(t.getApprove_Date());
        dto.setComment(t.getComment());

        Ticket_Types type = ticketTypesDAO.getTicketTypeById(t.getTicket_Type_ID());
        dto.setTicketTypeName(type != null ? type.getName() : "Unknown");

        User user = userDAO.getUserById(t.getUserID());
        if (user != null) {
            dto.setUserFullName(user.getFullname());
            Integer deptId = user.getDepartmentId();
            if (deptId != null && deptId > 0) {
                String deptName = departmentDAO.getNameById(deptId);
                dto.setDepartmentName(deptName != null ? deptName : "Unknown");
            } else {
                dto.setDepartmentName("N/A");
            }
        } else {
            dto.setUserFullName("Unknown");
            dto.setDepartmentName("N/A");
        }

        if (t.getApproverID() > 0) {
            User approver = userDAO.getUserById(t.getApproverID());
            dto.setApproverName(approver != null ? approver.getFullname() : "Unknown");
        } else {
            dto.setApproverName("N/A");
        }

        if (t.getTicket_Type_ID() == 1) {

            LeaveDetail detail = leaveDetailDAO.getByTicketId(t.getTicketID());
            if (detail != null) {
                dto.setLeaveTypeID(detail.getLeaveTypeID());
                dto.setStartDate(detail.getStart_Date());
                dto.setEndDate(detail.getEnd_Date());

                Leave_Types leaveType = leave_TypesDAO.getLeaveTypeById(detail.getLeaveTypeID());
                dto.setLeaveTypeName(leaveType != null ? leaveType.getLeaveTypeName() : "Unknown");
            }
        } else if (t.getTicket_Type_ID() == 2) {

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
        return (ticket != null) ? convertToDTO(ticket) : null;
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

    public boolean updateTicket(Ticket ticket) {
        return ticketDAO.update(ticket);
    }
}
