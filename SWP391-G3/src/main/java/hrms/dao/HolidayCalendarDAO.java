package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hrms.model.HolidayCalendar;
import hrms.utils.DBContext;

public class HolidayCalendarDAO extends DBContext {

    public List<HolidayCalendar> getAllCalendars() {
        List<HolidayCalendar> list = new ArrayList<>();
        String sql = "SELECT * FROM holiday_calendar";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new HolidayCalendar(
                        rs.getInt("CalendarID"),
                        rs.getInt("Year"),
                        rs.getString("Description")
                ));
            }

        } catch (SQLException ex) {
            System.err.println("Lỗi khi lấy danh sách holiday_calendar: " + ex.getMessage());
        }

        return list;
    }

    public int insert(HolidayCalendar c) {
        String sql = "INSERT INTO holiday_calendar (Year, Description) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getYear());
            ps.setString(2, c.getDescription());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // trả về CalendarID vừa tạo
            }

        } catch (SQLException ex) {
            System.err.println("Lỗi khi thêm holiday_calendar: " + ex.getMessage());
        }
        return -1;
    }

    public HolidayCalendar getByYear(int year) {
        String sql = "SELECT * FROM holiday_calendar WHERE Year = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, year);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new HolidayCalendar(
                        rs.getInt("CalendarID"),
                        rs.getInt("Year"),
                        rs.getString("Description")
                );
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi khi lấy holiday_calendar theo năm: " + ex.getMessage());
        }
        return null;
    }
}
