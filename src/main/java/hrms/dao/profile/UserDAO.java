package hrms.dao.profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.profile.User;
import hrms.utils.DBContext;

public class UserDAO {
    private Connection conn;
    
    public UserDAO() {
        try {
            conn = new DBContext().getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Lấy thông tin user theo ID với đầy đủ thông tin
    public User getUserById(int userId) {
        String sql = "SELECT u.*, d.Name as DepartmentName, p.Name as PositionName, " +
                     "deg.Name as DegreeName, r.Name as RoleName " +
                     "FROM Users u " +
                     "LEFT JOIN Department d ON u.DepartmentID = d.DepartmentID " +
                     "LEFT JOIN Positions p ON u.PositionID = p.PositionID " +
                     "LEFT JOIN Degree deg ON u.DegreeID = deg.DegreeID " +
                     "LEFT JOIN Role r ON u.RoleID = r.RoleID " +
                     "WHERE u.UserID = ?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return extractUserFromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    // Lấy tất cả nhân viên (cho HR Manager)
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.*, d.Name as DepartmentName, p.Name as PositionName, " +
                     "deg.Name as DegreeName, r.Name as RoleName " +
                     "FROM Users u " +
                     "LEFT JOIN Department d ON u.DepartmentID = d.DepartmentID " +
                     "LEFT JOIN Positions p ON u.PositionID = p.PositionID " +
                     "LEFT JOIN Degree deg ON u.DegreeID = deg.DegreeID " +
                     "LEFT JOIN Role r ON u.RoleID = r.RoleID " +
                     "WHERE u.Is_active = 1 " +
                     "ORDER BY u.Fullname";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    // Lấy nhân viên theo phòng ban (cho Department Manager)
    public List<User> getUsersByDepartment(int departmentId) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.*, d.Name as DepartmentName, p.Name as PositionName, " +
                     "deg.Name as DegreeName, r.Name as RoleName " +
                     "FROM Users u " +
                     "LEFT JOIN Department d ON u.DepartmentID = d.DepartmentID " +
                     "LEFT JOIN Positions p ON u.PositionID = p.PositionID " +
                     "LEFT JOIN Degree deg ON u.DegreeID = deg.DegreeID " +
                     "LEFT JOIN Role r ON u.RoleID = r.RoleID " +
                     "WHERE u.DepartmentID = ? AND u.Is_active = 1 " +
                     "ORDER BY u.Fullname";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, departmentId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    // Lấy Department Manager và Employee (cho HR)
    public List<User> getDepartmentManagersAndEmployees() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.*, d.Name as DepartmentName, p.Name as PositionName, " +
                     "deg.Name as DegreeName, r.Name as RoleName " +
                     "FROM Users u " +
                     "LEFT JOIN Department d ON u.DepartmentID = d.DepartmentID " +
                     "LEFT JOIN Positions p ON u.PositionID = p.PositionID " +
                     "LEFT JOIN Degree deg ON u.DegreeID = deg.DegreeID " +
                     "LEFT JOIN Role r ON u.RoleID = r.RoleID " +
                     "WHERE u.RoleID IN (3, 4) AND u.Is_active = 1 " +  // 3=Dept Manager, 4=Employee
                     "ORDER BY u.Fullname";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                users.add(extractUserFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    // Lấy danh sách users theo quyền của người đang login
    public List<User> getUsersByPermission(User currentUser) {
        if (currentUser.isHRManager()) {
            return getAllUsers();
        } else if (currentUser.isDepartmentManager()) {
            return getUsersByDepartment(currentUser.getDepartmentId());
        } else if (currentUser.isHR()) {
            return getDepartmentManagersAndEmployees();
        } else {
            // Employee chỉ thấy chính mình
            List<User> users = new ArrayList<>();
            users.add(currentUser);
            return users;
        }
    }
    
    // Kiểm tra quyền xem profile
    public boolean canViewProfile(User currentUser, int targetUserId) {
        // Xem chính mình
        if (currentUser.getUserId() == targetUserId) {
            return true;
        }
        
        // HR Manager xem tất cả
        if (currentUser.isHRManager()) {
            return true;
        }
        
        // Department Manager xem người trong phòng ban
        if (currentUser.isDepartmentManager()) {
            User targetUser = getUserById(targetUserId);
            return targetUser != null && 
                   targetUser.getDepartmentId() == currentUser.getDepartmentId();
        }
        
        // HR xem Department Manager và Employee
        if (currentUser.isHR()) {
            User targetUser = getUserById(targetUserId);
            return targetUser != null && 
                   (targetUser.isDepartmentManager() || targetUser.isEmployee());
        }
        
        return false;
    }
    
    // Kiểm tra quyền chỉnh sửa profile
    public boolean canEditProfile(User currentUser, int targetUserId) {
        // Chỉnh sửa chính mình (trừ một số trường nhạy cảm)
        if (currentUser.getUserId() == targetUserId) {
            return true;
        }
        
        // HR Manager có thể edit tất cả
        if (currentUser.isHRManager()) {
            return true;
        }
        
        // HR có thể edit Department Manager và Employee
        if (currentUser.isHR()) {
            User targetUser = getUserById(targetUserId);
            return targetUser != null && 
                   (targetUser.isDepartmentManager() || targetUser.isEmployee());
        }
        
        return false;
    }
    
    // Cập nhật profile (full update - dành cho HR/HR Manager)
    public boolean updateUserFull(User user) {
        String sql = "UPDATE Users SET Fullname=?, Email=?, Phone_number=?, " +
                     "BirthDate=?, Gender=?, Address=?, CCCD=?, Ethnicity=?, " +
                     "Nation=?, DepartmentID=?, PositionID=?, DegreeID=? " +
                     "WHERE UserID=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getFullname());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhoneNumber());
            ps.setDate(4, new java.sql.Date(user.getBirthDate().getTime()));

            ps.setString(5, user.getGender());
            ps.setString(6, user.getAddress());
            ps.setString(7, user.getCccd());
            ps.setString(8, user.getEthnicity());
            ps.setString(9, user.getNation());
            ps.setInt(10, user.getDepartmentId());
            ps.setInt(11, user.getPositionId());
            ps.setInt(12, user.getDegreeId());
            ps.setInt(13, user.getUserId());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Cập nhật profile cơ bản (dành cho User tự update)
    public boolean updateUserBasic(User user) {
        String sql = "UPDATE Users SET Email=?, Phone_number=?, Address=? WHERE UserID=?";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPhoneNumber());
            ps.setString(3, user.getAddress());
            ps.setInt(4, user.getUserId());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    // Helper method để extract User từ ResultSet
    private User extractUserFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("UserID"));
        user.setUsername(rs.getString("Username"));
        user.setRoleId(rs.getInt("RoleID"));
        user.setRoleName(rs.getString("RoleName"));
        user.setFullname(rs.getString("Fullname"));
        user.setEmail(rs.getString("Email"));
        user.setPhoneNumber(rs.getString("Phone_number"));
        user.setBirthDate(rs.getDate("BirthDate"));
        user.setGender(rs.getString("Gender"));
        user.setAddress(rs.getString("Address"));
        user.setCccd(rs.getString("CCCD"));
        user.setEthnicity(rs.getString("Ethnicity"));
        user.setNation(rs.getString("Nation"));
        user.setDepartmentId(rs.getInt("DepartmentID"));
        user.setDepartmentName(rs.getString("DepartmentName"));
        user.setPositionId(rs.getInt("PositionID"));
        user.setPositionName(rs.getString("PositionName"));
        user.setDegreeId(rs.getInt("DegreeID"));
        user.setDegreeName(rs.getString("DegreeName"));
        user.setIsActive(rs.getBoolean("Is_active"));
        
        return user;
    }
    
    // Lấy danh sách Department cho dropdown
    public List<Department> getAllDepartments() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT DepartmentID, Name FROM Department ORDER BY Name";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Department dept = new Department();
                dept.setDepartmentId(rs.getInt("DepartmentID"));
                dept.setName(rs.getString("Name"));
                departments.add(dept);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }
    
    // Lấy danh sách Position cho dropdown
    public List<Position> getAllPositions() {
        List<Position> positions = new ArrayList<>();
        String sql = "SELECT PositionID, Name FROM Positions ORDER BY Name";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Position pos = new Position();
                pos.setPositionId(rs.getInt("PositionID"));
                pos.setName(rs.getString("Name"));
                positions.add(pos);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positions;
    }
    
    // Lấy danh sách Degree cho dropdown
    public List<Degree> getAllDegrees() {
        List<Degree> degrees = new ArrayList<>();
        String sql = "SELECT DegreeID, Name FROM Degree ORDER BY Name";
        
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Degree deg = new Degree();
                deg.setDegreeId(rs.getInt("DegreeID"));
                deg.setName(rs.getString("Name"));
                degrees.add(deg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return degrees;
    }
    
    // Inner classes cho Department, Position, Degree
    public static class Department {
        private int departmentId;
        private String name;
        
        public int getDepartmentId() { return departmentId; }
        public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
    
    public static class Position {
        private int positionId;
        private String name;
        
        public int getPositionId() { return positionId; }
        public void setPositionId(int positionId) { this.positionId = positionId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
    
    public static class Degree {
        private int degreeId;
        private String name;
        
        public int getDegreeId() { return degreeId; }
        public void setDegreeId(int degreeId) { this.degreeId = degreeId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }
}