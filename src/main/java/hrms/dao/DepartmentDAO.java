package hrms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Department;
import hrms.utils.DBContext;

public class DepartmentDAO extends DBContext {

    public String getNameById(Integer id) {
        if (id == null)
            return null;
        String sql = "SELECT Name FROM Department WHERE DepartmentID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Department> getAll() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT DepartmentID, Name FROM Department";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Department d = new Department();
                d.setDepartmentId(rs.getInt("DepartmentID"));
                d.setName(rs.getString("Name"));
                list.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

 public boolean exists(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Department WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // 🔹 Thêm mới nếu chưa có
    public int insertIfNotExists(String name) throws SQLException {
        if (exists(name)) return 0;
        String sql = "INSERT INTO Department (name) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            return ps.executeUpdate();
        }
    }

     public Department getByName(String name) {
        String sql = "SELECT DepartmentID, Name FROM Departments WHERE Name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Department d = new Department();
                d.setDepartmentId(rs.getInt("DepartmentID"));
                d.setName(rs.getString("Name"));
                return d;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int createReturnId(String name) {
        String sql = "INSERT INTO Departments (Name) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
