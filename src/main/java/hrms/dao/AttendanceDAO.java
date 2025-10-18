package hrms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Attendance;
import hrms.utils.DBContext;

public class AttendanceDAO extends DBContext {

    private Attendance extractAttendance(ResultSet rs) throws SQLException {
        Attendance a = new Attendance();
        a.setAttendanceID(rs.getInt("AttendanceID"));
        a.setUserID(rs.getInt("UserID"));
        a.setDate(rs.getDate("Date").toLocalDate());
        a.setDay(rs.getString("Day"));
        a.setCheckin1(rs.getTime("Checkin1"));
        a.setCheckout1(rs.getTime("Checkout1"));
        a.setCheckin2(rs.getTime("Checkin2"));
        a.setCheckout2(rs.getTime("Checkout2"));
        a.setCheckin3(rs.getTime("Checkin3"));
        a.setCheckout3(rs.getTime("Checkout3"));
        a.setShiftID(rs.getInt("ShiftID"));
        a.setLateMinutes(rs.getTime("LateMinutes"));
        a.setEarlyLeaveMinutes(rs.getTime("EarlyLeaveMinutes"));
        a.setTotalWorkHours(rs.getTime("TotalWorkHours"));
        a.setOtHours(rs.getTime("OT_Hours"));
        return a;
    }

    public List<Attendance> getAllAttendances() {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM Attendance ORDER BY Date DESC";

        try (Connection conn = new DBContext().getConnection(); Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

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

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
