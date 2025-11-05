package hrms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Degree;
import hrms.utils.DBContext;

public class DegreeDAO extends DBContext {

    public String getNameById(Integer id) {
        if (id == null) return null;
        String sql = "SELECT Name FROM Degree WHERE DegreeID = ?";
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

    public List<Degree> getAll() {
        List<Degree> list = new ArrayList<>();
        String sql = "SELECT DegreeID, Name FROM Degree";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Degree d = new Degree();
                d.setDegreeId(rs.getInt("DegreeID"));
                d.setName(rs.getString("Name"));
                list.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
// 🔹 Kiểm tra xem tên học vị đã tồn tại chưa
    public boolean exists(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Degree WHERE name = ?";
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
        String sql = "INSERT INTO Degree (name) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            return ps.executeUpdate();
        }
    }
}
