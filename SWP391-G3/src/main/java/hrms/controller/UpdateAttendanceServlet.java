package hrms.controller;

import hrms.dao.AttendanceDAO;
import hrms.model.Attendance;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/updateattendance")
public class UpdateAttendanceServlet extends HttpServlet {

    private final AttendanceDAO dao = new AttendanceDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/company-attendance");
            return;
        }

        int attendanceID = Integer.parseInt(idParam);
        Attendance attendance = dao.getByID(attendanceID);

        if (attendance == null) {
            req.setAttribute("error", "Không tìm thấy bản ghi Attendance.");
            req.getRequestDispatcher("/company-attendance").forward(req, resp);
            return;
        }

        req.setAttribute("attendance", attendance);
        req.getRequestDispatcher("/view/attendance/updateAttendance.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int attendanceID = Integer.parseInt(req.getParameter("attendanceID"));
            int userID = Integer.parseInt(req.getParameter("userID"));
            LocalDate date = LocalDate.parse(req.getParameter("date"));
            String day = req.getParameter("day");

            LocalTime checkin1 = parseTime(req.getParameter("checkin1"));
            LocalTime checkout1 = parseTime(req.getParameter("checkout1"));
            LocalTime checkin2 = parseTime(req.getParameter("checkin2"));
            LocalTime checkout2 = parseTime(req.getParameter("checkout2"));
            LocalTime checkin3 = parseTime(req.getParameter("checkin3"));
            LocalTime checkout3 = parseTime(req.getParameter("checkout3"));

            int shiftID = Integer.parseInt(req.getParameter("shiftID"));
            LocalTime lateMinutes = parseTime(req.getParameter("lateMinutes"));
            LocalTime earlyLeaveMinutes = parseTime(req.getParameter("earlyLeaveMinutes"));
            LocalTime totalWorkHours = parseTime(req.getParameter("totalWorkHours"));
            LocalTime otHours = parseTime(req.getParameter("otHours"));

            Attendance updated = new Attendance();
            updated.setAttendanceID(attendanceID);
            updated.setUserID(userID);
            updated.setDate(date);
            updated.setDay(day);
            updated.setCheckin1(checkin1);
            updated.setCheckout1(checkout1);
            updated.setCheckin2(checkin2);
            updated.setCheckout2(checkout2);
            updated.setCheckin3(checkin3);
            updated.setCheckout3(checkout3);
            updated.setShiftID(shiftID);
            updated.setLateMinutes(lateMinutes);
            updated.setEarlyLeaveMinutes(earlyLeaveMinutes);
            updated.setTotalWorkHours(totalWorkHours);
            updated.setOtHours(otHours);

            boolean success = dao.updateAttendance(updated);

            if (success) {
                resp.sendRedirect(req.getContextPath() + "/company-attendance");
            } else {
                req.setAttribute("error", "Cập nhật không thành công.");
                req.setAttribute("attendance", updated);
                req.getRequestDispatcher("/view/attendance/updateAttendance.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi xử lý dữ liệu: " + e.getMessage());
            req.getRequestDispatcher("/view/attendance/updateAttendance.jsp").forward(req, resp);
        }
    }

    private LocalTime parseTime(String value) {
        if (value == null || value.isEmpty()) return null;
        return LocalTime.parse(value);
    }
}
