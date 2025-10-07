package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hrms.model.User;
import hrms.utils.DBContext;

public class UserDAO extends DBContext {

    // Lấy thông tin User theo ID
    public User getUserById(int id) {
        String sql = "SELECT UserID, FullName, Email, PhoneNumber, BirthDate, Gender, " +
                     "Address, Ethnicity, Nation, DegreeID, PositionID, DepartmentID " +
                     "FROM Users WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("UserID"));
                u.setFullname(rs.getString("FullName"));
                u.setEmail(rs.getString("Email"));
                u.setPhoneNumber(rs.getString("PhoneNumber"));
                u.setBirthDate(rs.getDate("BirthDate"));
                u.setGender(rs.getString("Gender"));
                u.setAddress(rs.getString("Address"));
                u.setNation(rs.getString("Nation"));
                u.setEthnicity(rs.getString("Ethnicity"));
                u.setDepartmentId(rs.getInt("DepartmentID"));
                u.setPositionId(rs.getInt("PositionID"));
                u.setDegreeId(rs.getInt("DegreeID"));
                return u;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật thông tin User
    public boolean updateUser(User u) {
        String sql = "UPDATE Users SET FullName=?, Email=?, PhoneNumber=?, BirthDate=?, Gender=?, " +
                     "Address=?, Nation=?, Ethnicity=? WHERE UserID=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, u.getFullname());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPhoneNumber());
            ps.setDate(4, u.getBirthDate() != null ? new java.sql.Date(u.getBirthDate().getTime()) : null);
            ps.setString(5, u.getGender());
            ps.setString(6, u.getAddress());
            ps.setString(7, u.getNation());
            ps.setString(8, u.getEthnicity());
            ps.setInt(9, u.getUserId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
