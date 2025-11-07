package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.dto.UserDTO;
import hrms.utils.DBContext;

public class ChangePositionDAO extends DBContext {
    public boolean updateUserPosition(int accountID, int positionID) {
        String sql = "UPDATE Users SET PositionID = ? WHERE UserID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, positionID);
            ps.setInt(2, accountID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<UserDTO> getAllUsersWithPosition() {
        List<UserDTO> list = new ArrayList<>();
        String sql = "SELECT u.UserID, u.FullName, u.Email, u.PhoneNumber, " +
                     "u.PositionID, p.Name AS PositionName " +
                     "FROM Users u " +
                     "LEFT JOIN Positions p ON u.PositionID = p.PositionID " +
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
                dto.setPositionId(rs.getInt("PositionID"));
                dto.setPositionName(rs.getString("PositionName"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<UserDTO> searchUsersWithPosition(String searchValue) {
        List<UserDTO> list = new ArrayList<>();
        String sql = "SELECT u.UserID, u.FullName, u.Email, u.PhoneNumber, " +
                     "u.PositionID, p.Name AS PositionName " +
                     "FROM Users u " +
                     "LEFT JOIN Positions p ON u.PositionID = p.PositionID " +
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
                dto.setPositionId(rs.getInt("PositionID"));
                dto.setPositionName(rs.getString("PositionName"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public UserDTO getUserDetailById(int userID) {
        String sql = "SELECT u.UserID, u.FullName, u.Email, u.PhoneNumber, " +
                     "u.PositionID, p.Name AS PositionName " +
                     "FROM Users u " +
                     "LEFT JOIN Positions p ON u.PositionID = p.PositionID " +
                     "WHERE u.UserID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                UserDTO dto = new UserDTO();
                dto.setUserId(rs.getInt("UserID"));
                dto.setFullname(rs.getString("FullName"));
                dto.setEmail(rs.getString("Email"));
                dto.setPhoneNumber(rs.getString("PhoneNumber"));
                dto.setPositionId(rs.getInt("PositionID"));
                dto.setPositionName(rs.getString("PositionName"));
                return dto;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getCurrentPositionID(int userID) {
        String sql = "SELECT PositionID FROM Users WHERE UserID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("PositionID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
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
    
}
