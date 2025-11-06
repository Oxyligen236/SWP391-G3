package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Role;
import hrms.utils.DBContext;

public class RoleDAO extends DBContext {

    private Role extractRoleFromResultSet(ResultSet rs) throws SQLException {
        return new Role(rs.getInt("RoleID"), rs.getString("Name"));
    }

    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM Role";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while(rs.next()) {
                roles.add(extractRoleFromResultSet(rs));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    public Role getRoleById(int roleID) {
        String sql = "SELECT * FROM Role WHERE RoleID=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, roleID);
            ResultSet rs = st.executeQuery();
            if(rs.next()) {
                return extractRoleFromResultSet(rs);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}