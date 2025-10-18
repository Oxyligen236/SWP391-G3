package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.dto.UserDTO;
import hrms.utils.DBContext;

public class ChangeDepartmentDAO extends DBContext {
    public boolean updateUserDepartment(int accountID, int departmentID) {
        String sql = "UPDATE Users SET DepartmentID = ? WHERE UserID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, departmentID);
            ps.setInt(2, accountID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<UserDTO> getAllUsersWithDepartment() {
        List<UserDTO> list = new ArrayList<>();
        String sql = "SELECT u.UserID, u.FullName, u.Email, u.PhoneNumber, " +
                     "u.DepartmentID, d.Name AS DepartmentName " +
                     "FROM Users u " +
                     "LEFT JOIN Department d ON u.DepartmentID = d.DepartmentID " +
                     "ORDER BY u.UserID";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserDTO dto = new UserDTO();
                dto.setUserId(rs.getInt("UserID"));
                dto.setFullname(rs.getString("FullName"));
                dto.setEmail(rs.getString("Email"));
                dto.setPhoneNumber(rs.getString("PhoneNumber"));
                dto.setDepartmentId(rs.getInt("DepartmentID"));
                dto.setDepartmentName(rs.getString("DepartmentName"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<UserDTO> searchUsersWithDepartment(String searchValue) {
        List<UserDTO> list = new ArrayList<>();
        String sql = "SELECT u.UserID, u.FullName, u.Email, u.PhoneNumber, " +
                     "u.DepartmentID, d.Name AS DepartmentName " +
                     "FROM Users u " +
                     "LEFT JOIN Department d ON u.DepartmentID = d.DepartmentID " +
                     "WHERE u.UserID LIKE ? OR u.FullName LIKE ? " +
                     "ORDER BY u.UserID";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            String searchPattern = "%" + searchValue + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserDTO dto = new UserDTO();
                dto.setUserId(rs.getInt("UserID"));
                dto.setFullname(rs.getString("FullName"));
                dto.setEmail(rs.getString("Email"));
                dto.setPhoneNumber(rs.getString("PhoneNumber"));
                dto.setDepartmentId(rs.getInt("DepartmentID"));
                dto.setDepartmentName(rs.getString("DepartmentName"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    
}
