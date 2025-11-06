package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Holiday;
import hrms.utils.DBContext;

public class HolidayDAO extends DBContext {

    public List<Holiday> getHolidaysByCalendar(int calendarID) {
        List<Holiday> list = new ArrayList<>();
        String sql = "SELECT * FROM Holidays WHERE CalendarID = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, calendarID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Holiday holiday = new Holiday(
                        rs.getInt("HolidayID"),
                        rs.getInt("CalendarID"),
                        rs.getDate("Date_Holiday").toLocalDate(), // ✅ Convert Date to LocalDate
                        rs.getString("Name"),
                        rs.getBoolean("Is_Substitute"),
                        rs.getString("Description")
                );
                list.add(holiday);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi khi lấy danh sách holidays: " + ex.getMessage());
            ex.printStackTrace();
        }

        return list;
    }

    public void insert(Holiday h) {
        String sql = "INSERT INTO Holidays (CalendarID, Date_Holiday, Name, Is_Substitute, Description) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, h.getCalendarID());
            ps.setDate(2, java.sql.Date.valueOf(h.getDateHoliday())); // ✅ Convert LocalDate to Date
            ps.setString(3, h.getName());
            ps.setBoolean(4, h.isSubstitute());
            ps.setString(5, h.getDescription());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Lỗi khi thêm holiday: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public List<Holiday> getAllHolidays() {
        List<Holiday> list = new ArrayList<>();
        String sql = "SELECT * FROM Holidays ORDER BY Date_Holiday ASC";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Holiday holiday = new Holiday(
                        rs.getInt("HolidayID"),
                        rs.getInt("CalendarID"),
                        rs.getDate("Date_Holiday").toLocalDate(), // ✅ Convert Date to LocalDate
                        rs.getString("Name"),
                        rs.getBoolean("Is_Substitute"),
                        rs.getString("Description")
                );
                list.add(holiday);
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi khi lấy tất cả holidays: " + ex.getMessage());
            ex.printStackTrace();
        }
        return list;
    }
}
