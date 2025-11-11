package hrms.controller;

import hrms.dao.AttendanceDAO;
import hrms.model.Account;
import hrms.model.Attendance;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

@WebServlet("/myattendance")
public class MyAttendanceServlet extends HttpServlet {

    private static final int DEFAULT_LIMIT = 10;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Account account = (Account) (session != null ? session.getAttribute("account") : null);
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/view/authenticate/login.jsp");
            return;
        }

        int userID = account.getUserID();

        // --- 1. Lấy tham số Paging ---
        int page = 1;
        int limit = DEFAULT_LIMIT;

        String pageParam = request.getParameter("page");
        String limitParam = request.getParameter("limit");
        
        try {
            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            }
            if (limitParam != null && !limitParam.isEmpty()) {
                limit = Integer.parseInt(limitParam);
                if (limit < 1) limit = DEFAULT_LIMIT;
                // Giới hạn max 50 items/page
                if (limit > 50) limit = 50; 
            }
        } catch (NumberFormatException e) {
            page = 1;
            limit = DEFAULT_LIMIT;
        }

        // --- 2. Lấy tham số Filter (Code cũ) ---
        String startStr = request.getParameter("startDate");
        String endStr = request.getParameter("endDate");
        String dayOfWeek = request.getParameter("dayOfWeek");

        LocalDate startDate = null;
        LocalDate endDate = null;

        try {
            if (startStr != null && !startStr.isEmpty()) startDate = LocalDate.parse(startStr);
            if (endStr != null && !endStr.isEmpty()) endDate = LocalDate.parse(endStr);
        } catch (DateTimeParseException e) {
            request.setAttribute("error", "Định dạng ngày không hợp lệ.");
            request.getRequestDispatcher("/view/attendance/myattendance.jsp").forward(request, response);
            return;
        }

        // --- 3. Lấy TOÀN BỘ danh sách đã lọc từ DAO (Giả định DAO không phân trang) ---
        AttendanceDAO dao = new AttendanceDAO();
        List<Attendance> allAttendanceList = dao.getAttendanceFiltered(userID, startDate, endDate, dayOfWeek);
        
        if (allAttendanceList == null) {
            allAttendanceList = Collections.emptyList();
        }

        // --- 4. Thực hiện Sublist Paging ---
        int totalItems = allAttendanceList.size();
        int totalPages = (int) Math.ceil((double) totalItems / limit);

        // Đảm bảo trang hiện tại không vượt quá giới hạn hoặc nhỏ hơn 1
        if (page > totalPages && totalPages > 0) {
            page = totalPages;
        } else if (page < 1 && totalPages > 0) {
            page = 1;
        } else if (totalPages == 0) {
            page = 1;
        }

        int startIndex = (page - 1) * limit;
        int endIndex = Math.min(startIndex + limit, totalItems);

        List<Attendance> paginatedList;
        if (startIndex < endIndex) {
             // Cắt list cho trang hiện tại
            paginatedList = allAttendanceList.subList(startIndex, endIndex);
        } else {
            paginatedList = Collections.emptyList();
        }

        // --- 5. Thiết lập Attributes cho JSP ---
        request.setAttribute("attendanceList", paginatedList);
        request.setAttribute("selectedDay", dayOfWeek);
        request.setAttribute("currentPage", page);
        request.setAttribute("limit", limit);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("/view/attendance/myattendance.jsp").forward(request, response);
    }
}