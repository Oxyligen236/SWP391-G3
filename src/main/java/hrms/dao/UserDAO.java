package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.dto.UserDTO;
import hrms.model.User;
import hrms.utils.DBContext;

public class UserDAO extends DBContext {

    public User getUserById(int id) {
        String sql = "SELECT UserID, FullName, Email, PhoneNumber, BirthDate, Gender, "
                + "CCCD, Address, Ethnicity, Nation, DegreeID, PositionID, DepartmentID "
                + "FROM Users WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUser(User u) {
        if (u == null || u.getUserId() == 0) {
            System.out.println("❌ User hoặc UserID không hợp lệ.");
            return false;
        }

        String sql = "UPDATE Users SET "
                + "FullName = ?, "
                + "Email = ?, "
                + "PhoneNumber = ?, "
                + "BirthDate = ?, "
                + "Gender = ?, "
                + "CCCD = ?, "
                + "Address = ?, "
                + "Nation = ?, "
                + "Ethnicity = ?, "
                + "DepartmentID = ?, "
                + "PositionID = ?, "
                + "DegreeID = ? "
                + "WHERE UserID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, u.getFullname());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPhoneNumber());

            if (u.getBirthDate() != null) {
                ps.setDate(4, new java.sql.Date(u.getBirthDate().getTime()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }

            ps.setString(5, u.getGender());
            ps.setString(6, u.getCccd());
            ps.setString(7, u.getAddress());
            ps.setString(8, u.getNation());
            ps.setString(9, u.getEthnicity());

            if (u.getDepartmentId() != null) {
                ps.setInt(10, u.getDepartmentId());
            } else {
                ps.setNull(10, java.sql.Types.INTEGER);
            }

            if (u.getPositionId() != null) {
                ps.setInt(11, u.getPositionId());
            } else {
                ps.setNull(11, java.sql.Types.INTEGER);
            }

            if (u.getDegreeId() != null) {
                ps.setInt(12, u.getDegreeId());
            } else {
                ps.setNull(12, java.sql.Types.INTEGER);
            }

            ps.setInt(13, u.getUserId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Cập nhật thành công UserID: " + u.getUserId());
                return true;
            } else {
                System.out.println("⚠️ Không có bản ghi nào được cập nhật.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi SQL khi cập nhật user:");
            e.printStackTrace();
        }

        return false;
    }

    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT UserID, FullName, Email, PhoneNumber, BirthDate, Gender, "
                + "CCCD, Address, Ethnicity, Nation, DegreeID, PositionID, DepartmentID FROM Users";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt("UserID"));
        u.setFullname(rs.getString("FullName"));
        u.setEmail(rs.getString("Email"));
        u.setPhoneNumber(rs.getString("PhoneNumber"));
        u.setBirthDate(rs.getDate("BirthDate"));
        u.setGender(rs.getString("Gender"));
        u.setCccd(rs.getString("CCCD"));
        u.setAddress(rs.getString("Address"));
        u.setNation(rs.getString("Nation"));
        u.setEthnicity(rs.getString("Ethnicity"));

        u.setDepartmentId((Integer) rs.getObject("DepartmentID"));
        u.setPositionId((Integer) rs.getObject("PositionID"));
        u.setDegreeId((Integer) rs.getObject("DegreeID"));
        return u;
    }

    public boolean createUser(UserDTO user) {
        String sql = "INSERT INTO Users (Fullname, Email, PhoneNumber, BirthDate, Gender, Address, Nation, Ethnicity, CCCD, DepartmentID, PositionID, DegreeID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, user.getFullname());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhoneNumber());

            if (user.getBirthDate() != null) {
                ps.setDate(4, user.getBirthDate());
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }

            ps.setString(5, user.getGender());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getNation());
            ps.setString(8, user.getEthnicity());
            ps.setString(9, user.getCccd());

            if (user.getDepartmentId() != null) {
                ps.setInt(10, user.getDepartmentId());
            } else {
                ps.setNull(10, java.sql.Types.INTEGER);
            }

            if (user.getPositionId() != null) {
                ps.setInt(11, user.getPositionId());
            } else {
                ps.setNull(11, java.sql.Types.INTEGER);
            }

            if (user.getDegreeId() != null) {
                ps.setInt(12, user.getDegreeId());
            } else {
                ps.setNull(12, java.sql.Types.INTEGER);
            }

            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean existsCccd(String cccd) {
        String sql = "SELECT COUNT(*) FROM Users WHERE CCCD = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cccd);
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

    public boolean existsEmail(String email) {
        String sql = "SELECT COUNT(*) FROM Users WHERE Email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
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

    public boolean existsPhoneNumber(String phoneNumber) {
        String sql = "SELECT COUNT(*) FROM Users WHERE PhoneNumber = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phoneNumber);
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

    public Integer createUserReturnId(UserDTO user) {
        String sql = "INSERT INTO Users (Fullname, Email, PhoneNumber, BirthDate, Gender, Address, Nation, Ethnicity, CCCD, DepartmentID, PositionID, DegreeID) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getFullname());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhoneNumber());
            ps.setDate(4, user.getBirthDate());
            ps.setString(5, user.getGender());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getNation());
            ps.setString(8, user.getEthnicity());
            ps.setString(9, user.getCccd());

            if (user.getDepartmentId() != null) {
                ps.setInt(10, user.getDepartmentId());
            } else {
                ps.setNull(10, java.sql.Types.INTEGER);
            }

            if (user.getPositionId() != null) {
                ps.setInt(11, user.getPositionId());
            } else {
                ps.setNull(11, java.sql.Types.INTEGER);
            }

            if (user.getDegreeId() != null) {
                ps.setInt(12, user.getDegreeId());
            } else {
                ps.setNull(12, java.sql.Types.INTEGER);
            }

            int affected = ps.executeUpdate();

            if (affected == 0) {
                return null;
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getRoleIdByUserId(int userId) {
        int roleId = -1; // mặc định nếu không tìm thấy
        String sql = "SELECT RoleID FROM Users WHERE UserID = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    roleId = rs.getInt("RoleID");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return roleId;
    }

    public String getRoleNameByUserId(int userId) {
        String sql = "SELECT r.Name AS RoleName "
                + "FROM Users u "
                + "JOIN Role r ON u.RoleID = r.RoleID "
                + "WHERE u.UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("RoleName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

// public List<User> getAllWithJoin() {
//     List<User> list = new ArrayList<>();
//     String sql = """
//         SELECT 
//             u.UserID, u.FullName, u.Email, u.PhoneNumber, 
//             u.BirthDate, u.Gender, u.CCCD, u.Address, 
//             u.Ethnicity, u.Nation, 
//             u.DegreeID, d.Name AS DegreeName, 
//             u.PositionID, p.Name AS PositionName, 
//             u.DepartmentID, dep.Name AS DepartmentName
//         FROM Users u
//         LEFT JOIN Degree d ON u.DegreeID = d.DegreeID
//         LEFT JOIN Positions p ON u.PositionID = p.PositionID
//         LEFT JOIN Department dep ON u.DepartmentID = dep.DepartmentID
//         ORDER BY u.UserID;
//         """;
//     try (PreparedStatement ps = connection.prepareStatement(sql)) {
//         ResultSet rs = ps.executeQuery();
//         while (rs.next()) {
//             User u = mapResultSetToUser(rs);
//             u.setDepartmentName(rs.getString("DepartmentName"));
//             u.setDegreeName(rs.getString("DegreeName"));
//             u.setPositionName(rs.getString("PositionName"));
//             list.add(u);
//         }
//     } catch (SQLException e) {
//         e.printStackTrace();
//     }
//     return list;
// }
}
