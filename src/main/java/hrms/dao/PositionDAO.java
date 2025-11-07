package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.dto.PositionDTO;
import hrms.model.Position;
import hrms.utils.DBContext;

public class PositionDAO extends DBContext {
    

    /**
     * Lấy tất cả Position kèm tên Department
     */
    public List<PositionDTO> getAllWithDepartmentName() {
        List<PositionDTO> list = new ArrayList<>();
        String sql = "SELECT p.PositionID, p.Name AS PositionName, p.DepartmentID, d.Name AS DepartmentName " +
                     "FROM Positions p LEFT JOIN Department d ON p.DepartmentID = d.DepartmentID " +
                     "ORDER BY p.Name ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PositionDTO dto = new PositionDTO();
                dto.setPositionId(rs.getInt("PositionID"));
                dto.setName(rs.getString("PositionName"));
                dto.setDepartmentId(rs.getInt("DepartmentID"));
                dto.setDepartmentName(rs.getString("DepartmentName"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Kiểm tra Position tồn tại trong Department
     */
    public boolean exists(String name, int departmentId) throws SQLException {
        if (name == null || name.trim().isEmpty()) return false;

        String sql = "SELECT COUNT(*) FROM Positions WHERE Name = ? AND DepartmentID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            ps.setInt(2, departmentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    /**
     * Thêm Position mới nếu chưa tồn tại trong Department
     */
    public int insertIfNotExists(String name, int departmentId) throws SQLException {
        if (name == null || name.trim().isEmpty()) return 0;
        if (exists(name, departmentId)) return 0;

        String sql = "INSERT INTO Positions (Name, DepartmentID) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            ps.setInt(2, departmentId);
            return ps.executeUpdate();
        }
    }

    /**
     * Tạo Position mới và trả về ID
     */
    public int createReturnId(String name, int departmentId) {
        if (name == null || name.trim().isEmpty()) return 0;

        String sql = "INSERT INTO Positions (Name, DepartmentID) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name.trim());
            ps.setInt(2, departmentId);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    // Trả về List<Position> như trước
public List<Position> getAll() {
    List<Position> list = new ArrayList<>();
    String sql = "SELECT PositionID, Name, DepartmentID FROM Positions ORDER BY Name ASC";
    try (PreparedStatement ps = connection.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            Position p = new Position();
            p.setPositionId(rs.getInt("PositionID"));
            p.setName(rs.getString("Name"));
            p.setDepartmentId(rs.getInt("DepartmentID"));
            list.add(p);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}

// Lấy tên Position theo id
public String getNameById(Integer id) {
    if (id == null) return null;
    String sql = "SELECT Name FROM Positions WHERE PositionID = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, id);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getString("Name");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
public List<Position> getByDepartmentId(int departmentId) {
    List<Position> list = new ArrayList<>();
    String sql = "SELECT * FROM Position WHERE departmentId = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, departmentId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Position p = new Position();
            p.setPositionId(rs.getInt("positionId"));
            p.setName(rs.getString("name"));
            p.setDepartmentId(rs.getInt("departmentId"));
            list.add(p);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

}
