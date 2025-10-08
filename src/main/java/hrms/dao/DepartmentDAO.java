package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Department;
import hrms.utils.DBContext;

public class DepartmentDAO extends DBContext {

    // Lấy tên Department theo ID
    public String getNameById(Integer id) {
        if (id == null) return null;
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

    // Lấy tất cả phòng ban (nếu cần)
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
}
