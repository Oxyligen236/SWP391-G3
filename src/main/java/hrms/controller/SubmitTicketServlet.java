package hrms.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import hrms.dao.LeaveDetailDAO;
import hrms.dao.OTDetailDAO;
import hrms.dao.TicketDAO;
import hrms.model.Account;
import hrms.model.LeaveDetail;
import hrms.model.OTDetail;
import hrms.model.Ticket;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/submit-ticket")
public class SubmitTicketServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO: handle GET request
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        
        
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/view/login/login.jsp");
            return;
        }

     
        int userId = account.getUserID();
        String selectedTypeIdRaw = request.getParameter("selectedTypeId");

        String leaveType = null;
        String startDate = null;        
        String endDate = null;
        String reason = null;
        String overtimeDate = null;
        String startTime = null;
        String endTime = null;
     
        try {
            int selectedTypeId = Integer.parseInt(selectedTypeIdRaw);

            TicketDAO ticketDAO = new TicketDAO();
        
            switch (selectedTypeId) {
                case 1: 
                     leaveType = request.getParameter("leaveType");
                     startDate = request.getParameter("startDate");
                     endDate = request.getParameter("endDate");
                     reason = request.getParameter("reason");
                     LocalDate leaveStart = LocalDate.parse(startDate);
                     LocalDate leaveEnd = LocalDate.parse(endDate);
                    
                    if (leaveEnd.isBefore(leaveStart)) {
                        session.setAttribute("errorMessage", "End date cannot be before start date!");
                        session.setAttribute("selectedTypeId", selectedTypeId);
                        response.sendRedirect(request.getContextPath() + "/create-ticket");
                        return;
                    }

                    break;

                case 2: 
                     overtimeDate = request.getParameter("overtimeDate");
                     startTime = request.getParameter("startTime");
                     endTime = request.getParameter("endTime");
                     reason = request.getParameter("reason");

                    LocalTime otStart = LocalTime.parse(startTime);
                    LocalTime otEnd = LocalTime.parse(endTime);
                    
                    if (otStart.equals(otEnd)) {
                        session.setAttribute("errorMessage", "Start time and end time cannot be the same!");
                        session.setAttribute("selectedTypeId", selectedTypeId);
                        response.sendRedirect(request.getContextPath() + "/create-ticket");
                        return;
                    }
                    
                    if (otEnd.isBefore(otStart)) {
                        session.setAttribute("errorMessage", "End time cannot be before start time!");
                        session.setAttribute("selectedTypeId", selectedTypeId);
                        response.sendRedirect(request.getContextPath() + "/create-ticket");
                        return;
                    }
              
                    break;

                case 3: 
                    reason = request.getParameter("ticketContent");
                
                    break;
                default:
                    reason = request.getParameter("ticketContent");
                    break;

            }
            Ticket ticket = new Ticket();
            ticket.setUserID(userId);
            ticket.setTicket_Type_ID(selectedTypeId);
            ticket.setStatus("Pending");
            ticket.setTicket_Content(reason);
            ticket.setCreate_Date(LocalDate.now());
            int ticketId = ticketDAO.add(ticket);

            if (ticketId <= 0) {
                session.setAttribute("errorMessage", "Failed to create ticket. Please try again.");
                session.setAttribute("selectedTypeId", selectedTypeId);
                response.sendRedirect(request.getContextPath() + "/create-ticket");
                return;
            }

            if (selectedTypeId == 1) {
                LeaveDetailDAO leaveDetailDAO = new LeaveDetailDAO();
        
                LeaveDetail leaveDetail = new LeaveDetail();
                leaveDetail.setLeaveType(leaveType);
                leaveDetail.setStart_Date(LocalDate.parse(startDate));
                leaveDetail.setEnd_Date(LocalDate.parse(endDate));
                leaveDetail.setTicketID(ticketId);
                leaveDetailDAO.add(leaveDetail);

            } else if (selectedTypeId == 2) {
               OTDetailDAO otDetailDAO = new OTDetailDAO();
               OTDetail otDetail = new OTDetail();
               otDetail.setTicketID(ticketId);
               otDetail.setOt_Date(LocalDate.parse(overtimeDate));
               otDetail.setStart_Time(LocalTime.parse(startTime));
               otDetail.setEnd_Time(LocalTime.parse(endTime));
               otDetailDAO.add(otDetail);
            }            
          
            response.sendRedirect(request.getContextPath() + "/view/ticket/ticketSuccess.jsp");
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/create-ticket?error=failed");
        }
    }
}