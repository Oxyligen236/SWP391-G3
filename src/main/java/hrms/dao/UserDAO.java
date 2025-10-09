package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.User;
import hrms.utils.DBContext;

public class UserDAO extends DBContext {

    // 🔹 Lấy thông tin User theo ID
    public User getUserById(int id) {
        String sql = "SELECT UserID, FullName, Email, PhoneNumber, BirthDate, Gender, " +
                     "CCCD, Address, Ethnicity, Nation, DegreeID, PositionID, DepartmentID " +
                     "FROM Users WHERE UserID = ?";
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

    // 🔹 Cập nhật thông tin User an toàn (chỉ cập nhật trường có dữ liệu)
    public boolean updateUser(User u) {
        if (u == null || u.getUserId() == 0) {
            System.out.println("❌ User hoặc UserID không hợp lệ.");
            return false;
        }

        StringBuilder sql = new StringBuilder("UPDATE Users SET ");
        List<Object> params = new ArrayList<>();

        // ✅ Các cột có thể cập nhật
        if (u.getFullname() != null) { sql.append("FullName=?, "); params.add(u.getFullname()); }
        if (u.getEmail() != null) { sql.append("Email=?, "); params.add(u.getEmail()); }
        if (u.getPhoneNumber() != null) { sql.append("PhoneNumber=?, "); params.add(u.getPhoneNumber()); }
        if (u.getBirthDate() != null) { sql.append("BirthDate=?, "); params.add(new java.sql.Date(u.getBirthDate().getTime())); }
        if (u.getGender() != null) { sql.append("Gender=?, "); params.add(u.getGender()); }
        if (u.getCccd() != null) { sql.append("CCCD=?, "); params.add(u.getCccd()); }
        if (u.getAddress() != null) { sql.append("Address=?, "); params.add(u.getAddress()); }
        if (u.getNation() != null) { sql.append("Nation=?, "); params.add(u.getNation()); }
        if (u.getEthnicity() != null) { sql.append("Ethnicity=?, "); params.add(u.getEthnicity()); }
        if (u.getDepartmentId() != null) { sql.append("DepartmentID=?, "); params.add(u.getDepartmentId()); }
        if (u.getPositionId() != null) { sql.append("PositionID=?, "); params.add(u.getPositionId()); }
        if (u.getDegreeId() != null) { sql.append("DegreeID=?, "); params.add(u.getDegreeId()); }

        // ⚠️ Nếu không có trường nào cần cập nhật
        if (params.isEmpty()) {
            System.out.println("⚠️ Không có trường nào để cập nhật cho UserID: " + u.getUserId());
            return false;
        }

        // Xóa dấu phẩy cuối cùng, thêm điều kiện WHERE
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE UserID=?");
        params.add(u.getUserId());

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Cập nhật UserID " + u.getUserId() + " thành công.");
                return true;
            } else {
                System.out.println("⚠️ Cập nhật thất bại. Không tìm thấy UserID: " + u.getUserId());
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi SQL khi cập nhật UserID " + u.getUserId());
            e.printStackTrace();
        }

        return false;
    }

    // 🔹 Lấy danh sách tất cả User
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT UserID, FullName, Email, PhoneNumber, BirthDate, Gender, " +
                     "CCCD, Address, Ethnicity, Nation, DegreeID, PositionID, DepartmentID FROM Users";
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

    // 🔹 Hàm helper: ánh xạ ResultSet → User
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

        // ⚙️ Dùng getObject để tránh lỗi khi cột null
        u.setDepartmentId((Integer) rs.getObject("DepartmentID"));
        u.setPositionId((Integer) rs.getObject("PositionID"));
        u.setDegreeId((Integer) rs.getObject("DegreeID"));
        return u;
    }
}
