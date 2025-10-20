package hrms.dao;

import hrms.model.Attendance;
import hrms.utils.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO extends DBContext {

    private Attendance extractAttendance(ResultSet rs) throws SQLException {
        Attendance a = new Attendance();
        a.setAttendanceID(rs.getInt("AttendanceID"));
        a.setUserID(rs.getInt("UserID"));
        a.setDate(rs.getDate("Date").toLocalDate());
        a.setDay(rs.getString("Day"));

        // Chuyển từ java.sql.Time → java.time.LocalTime (có thể null)
        a.setCheckin1(rs.getTime("Checkin1") != null ? rs.getTime("Checkin1").toLocalTime() : null);
        a.setCheckout1(rs.getTime("Checkout1") != null ? rs.getTime("Checkout1").toLocalTime() : null);
        a.setCheckin2(rs.getTime("Checkin2") != null ? rs.getTime("Checkin2").toLocalTime() : null);
        a.setCheckout2(rs.getTime("Checkout2") != null ? rs.getTime("Checkout2").toLocalTime() : null);
        a.setCheckin3(rs.getTime("Checkin3") != null ? rs.getTime("Checkin3").toLocalTime() : null);
        a.setCheckout3(rs.getTime("Checkout3") != null ? rs.getTime("Checkout3").toLocalTime() : null);

        a.setShiftID(rs.getInt("ShiftID"));

        a.setLateMinutes(rs.getTime("LateMinutes") != null ? rs.getTime("LateMinutes").toLocalTime() : null);
        a.setEarlyLeaveMinutes(rs.getTime("EarlyLeaveMinutes") != null ? rs.getTime("EarlyLeaveMinutes").toLocalTime() : null);
        a.setTotalWorkHours(rs.getTime("TotalWorkHours") != null ? rs.getTime("TotalWorkHours").toLocalTime() : null);
        a.setOtHours(rs.getTime("OT_Hours") != null ? rs.getTime("OT_Hours").toLocalTime() : null);

        return a;
    }

    public List<Attendance> getAllAttendances() {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM Attendance ORDER BY Date DESC";

        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(extractAttendance(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Attendance> getByUser(int userID) {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM Attendance WHERE UserID = ? ORDER BY Date DESC";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extractAttendance(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
