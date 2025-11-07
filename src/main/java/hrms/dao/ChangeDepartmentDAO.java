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
    
    /**
     * Get detailed user information by user ID
     * @param userID the user ID
     * @return UserDTO object or null if not found
     */
    public UserDTO getUserDetailById(int userID) {
        String sql = "SELECT u.UserID, u.FullName, u.Email, u.PhoneNumber, " +
                     "u.DepartmentID, d.Name AS DepartmentName " +
                     "FROM Users u " +
                     "LEFT JOIN Department d ON u.DepartmentID = d.DepartmentID " +
                     "WHERE u.UserID = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UserDTO dto = new UserDTO();
                dto.setUserId(rs.getInt("UserID"));
                dto.setFullname(rs.getString("FullName"));
                dto.setEmail(rs.getString("Email"));
                dto.setPhoneNumber(rs.getString("PhoneNumber"));
                dto.setDepartmentId(rs.getInt("DepartmentID"));
                dto.setDepartmentName(rs.getString("DepartmentName"));
                return dto;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get current departmentID of a user
     * @param userID the user ID
     * @return departmentID or 0 if not found
     */
    public int getCurrentDepartmentID(int userID) {
        String sql = "SELECT DepartmentID FROM Users WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("DepartmentID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * Get current positionID of a user
     * @param userID the user ID
     * @return positionID or 0 if not found
     */
    public int getCurrentPositionID(int userID) {
        String sql = "SELECT PositionID FROM Users WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("PositionID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * Update user department and reset position to NULL
     * @param userID the user ID
     * @param departmentID the new department ID
     * @return true if successful, false otherwise
     */
    public boolean updateUserDepartmentAndResetPosition(int userID, int departmentID) {
        String sql = "UPDATE Users SET DepartmentID = ?, PositionID = NULL WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, departmentID);
            ps.setInt(2, userID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
