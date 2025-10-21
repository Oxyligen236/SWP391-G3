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


    public Attendance getById(int attendanceID) {
        String sql = "SELECT * FROM Attendance WHERE AttendanceID = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, attendanceID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractAttendance(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public boolean insertAttendance(Attendance a) {
        String sql = "INSERT INTO Attendance (UserID, Date, Day, Checkin1, Checkout1, Checkin2, Checkout2, Checkin3, Checkout3, ShiftID, LateMinutes, EarlyLeaveMinutes, TotalWorkHours, OT_Hours) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, a.getUserID());
            ps.setDate(2, Date.valueOf(a.getDate()));
            ps.setString(3, a.getDay());
            ps.setTime(4, a.getCheckin1() != null ? Time.valueOf(a.getCheckin1()) : null);
            ps.setTime(5, a.getCheckout1() != null ? Time.valueOf(a.getCheckout1()) : null);
            ps.setTime(6, a.getCheckin2() != null ? Time.valueOf(a.getCheckin2()) : null);
            ps.setTime(7, a.getCheckout2() != null ? Time.valueOf(a.getCheckout2()) : null);
            ps.setTime(8, a.getCheckin3() != null ? Time.valueOf(a.getCheckin3()) : null);
            ps.setTime(9, a.getCheckout3() != null ? Time.valueOf(a.getCheckout3()) : null);
            ps.setInt(10, a.getShiftID());
            ps.setTime(11, a.getLateMinutes() != null ? Time.valueOf(a.getLateMinutes()) : null);
            ps.setTime(12, a.getEarlyLeaveMinutes() != null ? Time.valueOf(a.getEarlyLeaveMinutes()) : null);
            ps.setTime(13, a.getTotalWorkHours() != null ? Time.valueOf(a.getTotalWorkHours()) : null);
            ps.setTime(14, a.getOtHours() != null ? Time.valueOf(a.getOtHours()) : null);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean updateAttendance(Attendance a) {
        String sql = "UPDATE Attendance SET UserID=?, Date=?, Day=?, Checkin1=?, Checkout1=?, Checkin2=?, Checkout2=?, Checkin3=?, Checkout3=?, ShiftID=?, LateMinutes=?, EarlyLeaveMinutes=?, TotalWorkHours=?, OT_Hours=? "
                   + "WHERE AttendanceID=?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, a.getUserID());
            ps.setDate(2, Date.valueOf(a.getDate()));
            ps.setString(3, a.getDay());
            ps.setTime(4, a.getCheckin1() != null ? Time.valueOf(a.getCheckin1()) : null);
            ps.setTime(5, a.getCheckout1() != null ? Time.valueOf(a.getCheckout1()) : null);
            ps.setTime(6, a.getCheckin2() != null ? Time.valueOf(a.getCheckin2()) : null);
            ps.setTime(7, a.getCheckout2() != null ? Time.valueOf(a.getCheckout2()) : null);
            ps.setTime(8, a.getCheckin3() != null ? Time.valueOf(a.getCheckin3()) : null);
            ps.setTime(9, a.getCheckout3() != null ? Time.valueOf(a.getCheckout3()) : null);
            ps.setInt(10, a.getShiftID());
            ps.setTime(11, a.getLateMinutes() != null ? Time.valueOf(a.getLateMinutes()) : null);
            ps.setTime(12, a.getEarlyLeaveMinutes() != null ? Time.valueOf(a.getEarlyLeaveMinutes()) : null);
            ps.setTime(13, a.getTotalWorkHours() != null ? Time.valueOf(a.getTotalWorkHours()) : null);
            ps.setTime(14, a.getOtHours() != null ? Time.valueOf(a.getOtHours()) : null);
            ps.setInt(15, a.getAttendanceID());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean deleteAttendance(int attendanceID) {
        String sql = "DELETE FROM Attendance WHERE AttendanceID = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, attendanceID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}