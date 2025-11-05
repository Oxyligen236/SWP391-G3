package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Department;
import hrms.utils.DBContext;

public class DepartmentDAO extends DBContext {

    /**
     * Lấy tên Department theo ID
     * @param id - DepartmentID
     * @return Department name hoặc null
     */
    public String getNameById(Integer id) {
        if (id == null) return null;
        
        String sql = "SELECT Name FROM Department WHERE DepartmentID = ?";
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
     * ✅ Lấy tất cả Department (FIX: Use this.connection instead of getConnection())
     * @return List<Department>
     */
    public List<Department> getAll() {
        List<Department> list = new ArrayList<>();
        String sql = "SELECT DepartmentID, Name FROM Department ORDER BY Name ASC";
        
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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

    /**
     * ✅ Kiểm tra Department tồn tại (FIX: Use this.connection instead of getConnection())
     * @param name - Department name
     * @return true nếu tồn tại
     */
    public boolean exists(String name) {
        if (name == null || name.trim().isEmpty()) return false;
        
        String sql = "SELECT COUNT(*) FROM Department WHERE Name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ✅ Thêm mới Department nếu chưa tồn tại (FIX: Use this.connection)
     * @param name - Department name
     * @return số dòng được thêm (1 = thành công, 0 = tồn tại)
     */
    public int insertIfNotExists(String name) throws SQLException {
        if (name == null || name.trim().isEmpty()) return 0;
        if (exists(name)) return 0;
        
        String sql = "INSERT INTO Department (Name) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            return ps.executeUpdate();
        }
    }

    /**
     * ✅ Lấy Department theo Name
     * @param name - Department name
     * @return Department object hoặc null
     */
    public Department getByName(String name) {
        if (name == null || name.trim().isEmpty()) return null;
        
        String sql = "SELECT DepartmentID, Name FROM Department WHERE Name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Department d = new Department();
                    d.setDepartmentId(rs.getInt("DepartmentID"));
                    d.setName(rs.getString("Name"));
                    return d;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ✅ Tạo Department mới và trả về ID
     * @param name - Department name
     * @return ID của Department mới, hoặc 0 nếu thất bại
     */
    public int createReturnId(String name) {
        if (name == null || name.trim().isEmpty()) return 0;
        
        String sql = "INSERT INTO Department (Name) VALUES (?)";
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