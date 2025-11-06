package hrms.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import hrms.dao.Leave_TypesDAO;
import hrms.dao.Ticket_TypesDAO;
import hrms.dao.UserDAO;
import hrms.model.Account;
import hrms.model.Leave_Types;
import hrms.model.Ticket_Types;
import hrms.service.CalendarCheck;
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Kiểm tra nếu user chọn Overtime Date
        String overtimeDateStr = request.getParameter("overtimeDate");

        if (overtimeDateStr != null && !overtimeDateStr.isEmpty()) {
            try {
                LocalDate overtimeDate = LocalDate.parse(overtimeDateStr);

                // Gọi CalendarCheck để xác định loại ngày
                CalendarCheck calendarCheck = new CalendarCheck();
                String dayType = calendarCheck.getDayType(overtimeDate);
                double otSalaryPercent = calendarCheck.getOTSalaryPercentage(dayType);

                // Gửi thông tin về JSP
                request.setAttribute("selectedOvertimeDate", overtimeDateStr);
                request.setAttribute("dayType", dayType);
                request.setAttribute("otSalaryPer", otSalaryPercent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/view/login/login.jsp");
            return;
        }

        UserDAO userDAO = new UserDAO();
        int userId = account.getUserID();
        String fullName = userDAO.getUserById(userId).getFullname();

        Ticket_TypesDAO ticketTypesDAO = new Ticket_TypesDAO();
        List<Ticket_Types> ticketTypes = ticketTypesDAO.getAllTicketTypes();

        Leave_TypesDAO leaveTypesDAO = new Leave_TypesDAO();
        List<Leave_Types> leaveTypes = leaveTypesDAO.getAllLeaveTypes();

        String currentDate = LocalDate.now().toString();
        String selectedTypeId = request.getParameter("selectedTypeId");

        request.setAttribute("userId", userId);
        request.setAttribute("fullname", fullName);
        request.setAttribute("ticketTypes", ticketTypes);
        request.setAttribute("leaveTypes", leaveTypes);
        request.setAttribute("currentDate", currentDate);
        request.setAttribute("selectedTypeId", selectedTypeId);

        request.getRequestDispatcher("/view/ticket/createTicket.jsp").forward(request, response);
    }
}
