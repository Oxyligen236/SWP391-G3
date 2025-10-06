package hrms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import hrms.dto.AccountDTO;
import hrms.utils.DBContext;

public class AccountDAO extends DBContext {

    public AccountDTO getAccountByUsername(String username) {
        String sql = "SELECT a.AccountID, a.Username, a.Password, a.Is_active, " +
                     "u.FullName AS UserFullName, r.Name AS RoleName " +
                     "FROM Account a " +
                     "JOIN Users u ON a.UserID = u.UserID " +
                     "JOIN Role r ON a.RoleID = r.RoleID " +
                     "WHERE a.Username = ?";

        try (Connection conn = this.connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                AccountDTO dto = new AccountDTO();
                dto.setAccountID(rs.getInt("AccountID"));
                dto.setUsername(rs.getString("Username"));
                dto.setPassword(rs.getString("Password"));
                dto.setIsActive(rs.getBoolean("Is_active"));
                dto.setRoleName(rs.getString("RoleName"));
                dto.setFullName(rs.getString("UserFullName"));
                return dto;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
