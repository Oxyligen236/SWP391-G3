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
import hrms.service.CalendarCheck;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

        try {
            int selectedTypeId = Integer.parseInt(selectedTypeIdRaw);

            TicketDAO ticketDAO = new TicketDAO();

            String startDate = null;
            String endDate = null;
            String reason = null;
            String overtimeDate = null;
            String startTime = null;
            String endTime = null;
            String leaveTypeIDRaw = null;

            switch (selectedTypeId) {
                case 1: // ===== LEAVE TICKET =====
                    leaveTypeIDRaw = request.getParameter("leaveTypeID");
                    startDate = request.getParameter("startDate");
                    endDate = request.getParameter("endDate");
                    reason = request.getParameter("reason");

                    //Kiểm tra null/empty
                    if (leaveTypeIDRaw == null || leaveTypeIDRaw.isEmpty()
                            || startDate == null || startDate.isEmpty()
                            || endDate == null || endDate.isEmpty()
                            || reason == null || reason.trim().isEmpty()) {

                        session.setAttribute("errorMessage", "All fields are required!");
                        session.setAttribute("selectedTypeId", selectedTypeId);
                        response.sendRedirect(request.getContextPath() + "/create-ticket");
                        return;
                    }

                    LocalDate leaveStart = LocalDate.parse(startDate);
                    LocalDate leaveEnd = LocalDate.parse(endDate);

                    // End date không được trước start date
                    if (leaveEnd.isBefore(leaveStart)) {
                        session.setAttribute("errorMessage", "End date cannot be before start date!");
                        session.setAttribute("selectedTypeId", selectedTypeId);
                        response.sendRedirect(request.getContextPath() + "/create-ticket");
                        return;
                    }

                    // Không được đăng ký nghỉ quá khứ
                    if (leaveStart.isBefore(LocalDate.now())) {
                        session.setAttribute("errorMessage", "Start date cannot be in the past!");
                        session.setAttribute("selectedTypeId", selectedTypeId);
                        response.sendRedirect(request.getContextPath() + "/create-ticket");
                        return;
                    }

                    // Reason không được quá ngắn
                    if (reason.trim().length() < 2) {
                        session.setAttribute("errorMessage", "Reason must be at least 1 character!");
                        session.setAttribute("selectedTypeId", selectedTypeId);
                        response.sendRedirect(request.getContextPath() + "/create-ticket");
                        return;
                    }

                    break;

                case 2: //OVERTIME TICKET
                    overtimeDate = request.getParameter("overtimeDate");
                    startTime = request.getParameter("startTime");
                    endTime = request.getParameter("endTime");
                    reason = request.getParameter("reason");

                    // Kiểm tra null/empty
                    if (overtimeDate == null || overtimeDate.isEmpty()
                            || startTime == null || startTime.isEmpty()
                            || endTime == null || endTime.isEmpty()
                            || reason == null || reason.trim().isEmpty()) {

                        session.setAttribute("errorMessage", "All fields are required!");
                        session.setAttribute("selectedTypeId", selectedTypeId);
                        response.sendRedirect(request.getContextPath() + "/create-ticket");
                        return;
                    }

                    LocalDate otDate = LocalDate.parse(overtimeDate);
                    LocalTime otStart = LocalTime.parse(startTime);
                    LocalTime otEnd = LocalTime.parse(endTime);

                    // Không được chọn ngày quá khứ
                    if (otDate.isBefore(LocalDate.now())) {
                        session.setAttribute("errorMessage", "Overtime date cannot be in the past!");
                        session.setAttribute("selectedTypeId", selectedTypeId);
                        response.sendRedirect(request.getContextPath() + "/create-ticket");
                        return;
                    }

                    //  End time phải sau start time
                    if (otEnd.isBefore(otStart) || otEnd.equals(otStart)) {
                        session.setAttribute("errorMessage", "End time must be after start time!");
                        session.setAttribute("selectedTypeId", selectedTypeId);
                        response.sendRedirect(request.getContextPath() + "/create-ticket");
                        return;
                    }

                    // Kiểm tra số giờ OT tối thiểu 
                    long minutes = java.time.temporal.ChronoUnit.MINUTES.between(otStart, otEnd);
                    if (minutes < 30) {
                        session.setAttribute("errorMessage", "Overtime must be at least 30 minutes!");
                        session.setAttribute("selectedTypeId", selectedTypeId);
                        response.sendRedirect(request.getContextPath() + "/create-ticket");
                        return;
                    }

                    // Kiểm tra giới hạn giờ OT theo chính sách
                    CalendarCheck calendarCheck = new CalendarCheck();
                    String dayType = calendarCheck.getDayType(otDate);
                    double maxHours = getMaxOvertimeHours(dayType);
                    double otHours = minutes / 60.0;

                    if (otHours > maxHours) {
                        session.setAttribute("errorMessage",
                                String.format("Overtime hours (%.1f) exceed maximum allowed for %s (%.1f hours)!",
                                        otHours, dayType, maxHours));
                        session.setAttribute("selectedTypeId", selectedTypeId);
                        response.sendRedirect(request.getContextPath() + "/create-ticket");
                        return;
                    }

                    // Reason không được quá ngắn
                    if (reason.trim().length() < 10) {
                        session.setAttribute("errorMessage", "Reason must be at least 10 characters!");
                        session.setAttribute("selectedTypeId", selectedTypeId);
                        response.sendRedirect(request.getContextPath() + "/create-ticket");
                        return;
                    }

                    break;

                case 3: // ===== RECRUITMENT TICKET =====
                    reason = request.getParameter("ticketContent");

                    // Kiểm tra null/empty
                    if (reason == null || reason.trim().isEmpty()) {
                        session.setAttribute("errorMessage", "Recruitment request content is required!");
                        session.setAttribute("selectedTypeId", selectedTypeId);
                        response.sendRedirect(request.getContextPath() + "/create-ticket");
                        return;
                    }

                    // Content không được quá ngắn
                    if (reason.trim().length() < 1) {
                        session.setAttribute("errorMessage", "Recruitment request must be at least 1 character!");
                        session.setAttribute("selectedTypeId", selectedTypeId);
                        response.sendRedirect(request.getContextPath() + "/create-ticket");
                        return;
                    }

                    break;

                default: // ===== OTHER TICKET =====
                    reason = request.getParameter("ticketContent");

                    // Kiểm tra null/empty
                    if (reason == null || reason.trim().isEmpty()) {
                        session.setAttribute("errorMessage", "Ticket content is required!");
                        session.setAttribute("selectedTypeId", selectedTypeId);
                        response.sendRedirect(request.getContextPath() + "/create-ticket");
                        return;
                    }

                    // Content không được quá ngắn
                    if (reason.trim().length() < 1) {
                        session.setAttribute("errorMessage", "Ticket content must be at least 1 character!");
                        session.setAttribute("selectedTypeId", selectedTypeId);
                        response.sendRedirect(request.getContextPath() + "/create-ticket");
                        return;
                    }

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

            //THÊM DETAIL
            if (selectedTypeId == 1) {
                LeaveDetailDAO leaveDetailDAO = new LeaveDetailDAO();
                LeaveDetail leaveDetail = new LeaveDetail();
                leaveDetail.setLeaveTypeID(Integer.parseInt(leaveTypeIDRaw));
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

            request.getRequestDispatcher("/view/ticket/ticketSuccess.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            session.setAttribute("errorMessage", "Invalid input format!");
            response.sendRedirect(request.getContextPath() + "/create-ticket");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMessage", "An error occurred: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/create-ticket");
        }
    }

    // ===== HELPER METHOD =====
    private double getMaxOvertimeHours(String dayType) {
        String policyName = "Weekday".equals(dayType)
                ? "max_overtime_hours_per_day_weekday"
                : "max_overtime_hours_per_day_off";

        hrms.dao.PayrollPolicyDAO policyDAO = new hrms.dao.PayrollPolicyDAO();
        hrms.model.PayrollPolicy policy = policyDAO.getByName(policyName);

        return policy != null ? policy.getPolicyValue() : 4.0;
    }
}
