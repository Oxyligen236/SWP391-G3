package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Attendance;
import hrms.utils.DBContext;
import java.sql.Date;
import java.time.LocalDate;

public class AttendanceDAO extends DBContext {

    private Attendance extractAttendance(ResultSet rs) throws SQLException {
        Attendance a = new Attendance();
        a.setAttendanceID(rs.getInt("AttendanceID"));
        a.setUserID(rs.getInt("UserID"));
        a.setDate(rs.getDate("Date").toLocalDate());
        a.setDay(rs.getString("Day"));

        a.setCheckin1(rs.getObject("Checkin1", LocalTime.class));
        a.setCheckout1(rs.getObject("Checkout1", LocalTime.class));
        a.setCheckin2(rs.getObject("Checkin2", LocalTime.class));
        a.setCheckout2(rs.getObject("Checkout2", LocalTime.class));
        a.setCheckin3(rs.getObject("Checkin3", LocalTime.class));
        a.setCheckout3(rs.getObject("Checkout3", LocalTime.class));

        a.setShiftID(rs.getInt("ShiftID"));

        a.setLateMinutes(rs.getObject("LateMinutes", LocalTime.class));
        a.setEarlyLeaveMinutes(rs.getObject("EarlyLeaveMinutes", LocalTime.class));
        a.setTotalWorkHours(rs.getObject("TotalWorkHours", LocalTime.class));
        a.setOtHours(rs.getObject("OT_Hours", LocalTime.class));

        return a;
    }

    public List<Attendance> getAllAttendances() {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM Attendance ORDER BY Date DESC";

        try (Statement st = connection.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(extractAttendance(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error in getAllAttendances: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<Attendance> getByUser(int userID) {
        List<Attendance> list = new ArrayList<>();
        String sql = "SELECT * FROM Attendance WHERE UserID = ? ORDER BY Date DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(extractAttendance(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error in getByUser: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public Attendance getByID(int attendanceID) {
        String sql = "SELECT * FROM Attendance WHERE AttendanceID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, attendanceID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractAttendance(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error in getByID: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertAttendance(Attendance attendance) {
        String sql = """
            INSERT INTO Attendance 
            (UserID, Date, Day, Checkin1, Checkout1, Checkin2, Checkout2, 
             Checkin3, Checkout3, ShiftID, LateMinutes, EarlyLeaveMinutes, 
             TotalWorkHours, OT_Hours) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, attendance.getUserID());
            ps.setDate(2, java.sql.Date.valueOf(attendance.getDate()));
            ps.setString(3, attendance.getDay());
            ps.setObject(4, attendance.getCheckin1());
            ps.setObject(5, attendance.getCheckout1());
            ps.setObject(6, attendance.getCheckin2());
            ps.setObject(7, attendance.getCheckout2());
            ps.setObject(8, attendance.getCheckin3());
            ps.setObject(9, attendance.getCheckout3());
            ps.setInt(10, attendance.getShiftID());
            ps.setObject(11, attendance.getLateMinutes());
            ps.setObject(12, attendance.getEarlyLeaveMinutes());
            ps.setObject(13, attendance.getTotalWorkHours());
            ps.setObject(14, attendance.getOtHours());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error in insertAttendance: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateAttendance(Attendance attendance) {
        String sql = """
            UPDATE Attendance 
            SET UserID = ?, Date = ?, Day = ?, 
                Checkin1 = ?, Checkout1 = ?, 
                Checkin2 = ?, Checkout2 = ?, 
                Checkin3 = ?, Checkout3 = ?, 
                ShiftID = ?, LateMinutes = ?, 
                EarlyLeaveMinutes = ?, TotalWorkHours = ?, OT_Hours = ?
            WHERE AttendanceID = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, attendance.getUserID());
            ps.setDate(2, java.sql.Date.valueOf(attendance.getDate()));
            ps.setString(3, attendance.getDay());
            ps.setObject(4, attendance.getCheckin1());
            ps.setObject(5, attendance.getCheckout1());
            ps.setObject(6, attendance.getCheckin2());
            ps.setObject(7, attendance.getCheckout2());
            ps.setObject(8, attendance.getCheckin3());
            ps.setObject(9, attendance.getCheckout3());
            ps.setInt(10, attendance.getShiftID());
            ps.setObject(11, attendance.getLateMinutes());
            ps.setObject(12, attendance.getEarlyLeaveMinutes());
            ps.setObject(13, attendance.getTotalWorkHours());
            ps.setObject(14, attendance.getOtHours());
            ps.setInt(15, attendance.getAttendanceID());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error in updateAttendance: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteAttendance(int attendanceID) {
        String sql = "DELETE FROM Attendance WHERE AttendanceID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, attendanceID);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Error in deleteAttendance: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }
    
    public List<Attendance> getAttendanceByDateRange(LocalDate startDate, LocalDate endDate) {
    List<Attendance> list = new ArrayList<>();
    String sql = """
        SELECT * 
        FROM Attendance 
        WHERE Date >= ? AND Date < ? 
        ORDER BY UserID, Date
    """;

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setDate(1, Date.valueOf(startDate));
        ps.setDate(2, Date.valueOf(endDate));
        
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(extractAttendance(rs));
        }

    } catch (SQLException e) {
        System.err.println("Error in getAttendanceByDateRange: " + e.getMessage());
        e.printStackTrace();
    }

    return list;
}
}