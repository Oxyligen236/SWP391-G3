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

            // ================================
            // ⭐ THÊM VALIDATE MỚI (KHÔNG ĐỤNG CODE CŨ)
            // ================================
            String error = null;

            // Rule 1: Checkin1 không được trước 05:00
            if (checkin1 != null && checkin1.isBefore(LocalTime.of(5, 0))) {
                error = "Checkin 1 không được trước 05:00.";
            }

            // Rule 2: Checkin2 không được trước Checkout1
            if (error == null && checkout1 != null && checkin2 != null && checkin2.isBefore(checkout1)) {
                error = "Checkin 2 không được trước Checkout 1.";
            }

            // Rule 3: Checkin3 phải sau Checkout2
            if (error == null && checkout2 != null && checkin3 != null && checkin3.isBefore(checkout2)) {
                error = "Checkin 3 phải sau Checkout 2.";
            }

            // Rule 4: Checkout3 không được sau 22:00
            if (error == null && checkout3 != null && checkout3.isAfter(LocalTime.of(22, 0))) {
                error = "Checkout 3 không được sau 22:00.";
            }

            // ================================
            // ⭐ GIỮ NGUYÊN VALIDATE CŨ
            // ================================
            if (error == null) {
                if (checkin1 != null && checkout1 != null && !checkin1.isBefore(checkout1)) {
                    error = "Checkin 1 phải nhỏ hơn Checkout 1.";
                } else if (checkin2 != null && checkout2 != null && !checkin2.isBefore(checkout2)) {
                    error = "Checkin 2 phải nhỏ hơn Checkout 2.";
                } else if (checkin3 != null && checkout3 != null && !checkin3.isBefore(checkout3)) {
                    error = "Checkin 3 phải nhỏ hơn Checkout 3.";
                }
            }

            // Nếu có lỗi → trả lại form
            if (error != null) {
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

                req.setAttribute("error", error);
                req.setAttribute("attendance", updated);
                req.getRequestDispatcher("/view/attendance/updateAttendance.jsp").forward(req, resp);
                return;
            }

            // ================================
            // ⭐ GIỮ NGUYÊN LOGIC UPDATE CŨ
            // ================================
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
        if (value == null || value.isEmpty()) {
            return null;
        }
        return LocalTime.parse(value);
    }
}
