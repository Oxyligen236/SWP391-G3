package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Position;
import hrms.utils.DBContext;

public class PositionDAO extends DBContext {

    /**
     * Lấy tên Position theo ID
     * @param id - PositionID
     * @return Position name hoặc null
     */
    public String getNameById(Integer id) {
        if (id == null) return null;
        
        String sql = "SELECT Name FROM Positions WHERE PositionID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ✅ Lấy tất cả Position
     * @return List<Position>
     */
    public List<Position> getAll() {
        List<Position> list = new ArrayList<>();
        String sql = "SELECT PositionID, Name FROM Positions ORDER BY Name ASC";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Position p = new Position();
                p.setPositionId(rs.getInt("PositionID"));
                p.setName(rs.getString("Name"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * ✅ Kiểm tra Position tồn tại
     * @param name - Position name
     * @return true nếu tồn tại
     */
    public boolean exists(String name) throws SQLException {
        if (name == null || name.trim().isEmpty()) return false;
        
        String sql = "SELECT COUNT(*) FROM Positions WHERE Name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    /**
     * ✅ Thêm mới Position nếu chưa tồn tại
     * @param name - Position name
     * @return số dòng được thêm (1 = thành công, 0 = tồn tại)
     */
    public int insertIfNotExists(String name) throws SQLException {
        if (name == null || name.trim().isEmpty()) return 0;
        if (exists(name)) return 0;
        
        String sql = "INSERT INTO Positions (Name) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            return ps.executeUpdate();
        }
    }

    /**
     * ✅ Lấy Position theo Name
     * @param name - Position name
     * @return Position object hoặc null
     */
    public Position getByName(String name) {
        if (name == null || name.trim().isEmpty()) return null;
        
        String sql = "SELECT PositionID, Name FROM Positions WHERE Name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Position p = new Position();
                    p.setPositionId(rs.getInt("PositionID"));
                    p.setName(rs.getString("Name"));
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ✅ Tạo Position mới và trả về ID
     * @param name - Position name
     * @return ID của Position mới, hoặc 0 nếu thất bại
     */
    public int createReturnId(String name) {
        if (name == null || name.trim().isEmpty()) return 0;
        
        String sql = "INSERT INTO Positions (Name) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name.trim());
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}