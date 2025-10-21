package hrms.dao;

import hrms.model.Shift;
import hrms.utils.DBContext;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ShiftDAO extends DBContext {

    private Shift extractShiftFromResultSet(ResultSet rs) throws SQLException {
        Shift s = new Shift();
        s.setShiftID(rs.getInt("ShiftID"));
        s.setName(rs.getString("Name"));
        s.setCheckin1(rs.getObject("Checkin1", LocalTime.class));
        s.setCheckout1(rs.getObject("Checkout1", LocalTime.class));
        s.setCheckin2(rs.getObject("Checkin2", LocalTime.class));
        s.setCheckout2(rs.getObject("Checkout2", LocalTime.class));
        return s;
    }

    public List<Shift> getAllShifts() {
        List<Shift> list = new ArrayList<>();
        String sql = "SELECT * FROM Shift";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractShiftFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Shift getShiftById(int id) {
        String sql = "SELECT * FROM Shift WHERE ShiftID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractShiftFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addShift(Shift s) {
        String sql = "INSERT INTO Shift (Name, Checkin1, Checkout1, Checkin2, Checkout2) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setObject(2, s.getCheckin1());
            ps.setObject(3, s.getCheckout1());
            ps.setObject(4, s.getCheckin2());
            ps.setObject(5, s.getCheckout2());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateShift(Shift s) {
        String sql = "UPDATE Shift SET Name=?, Checkin1=?, Checkout1=?, Checkin2=?, Checkout2=? WHERE ShiftID=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setObject(2, s.getCheckin1());
            ps.setObject(3, s.getCheckout1());
            ps.setObject(4, s.getCheckin2());
            ps.setObject(5, s.getCheckout2());
            ps.setInt(6, s.getShiftID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteShift(int id) {
        String sql = "DELETE FROM Shift WHERE ShiftID=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
