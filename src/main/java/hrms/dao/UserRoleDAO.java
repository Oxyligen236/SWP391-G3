package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.dto.AccountDTO;
import hrms.model.Role;
import hrms.utils.DBContext;

public class UserRoleDAO extends DBContext {

    public boolean updateUserRole(int accountID, int newRoleID) {
        String sql = "UPDATE Account SET RoleID = ? WHERE AccountID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, newRoleID);
            st.setInt(2, accountID);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<AccountDTO> getAllAccountWithRoles() {
        List<AccountDTO> list = new ArrayList<>();
        String sql = """
        SELECT a.AccountID, a.Username, u.Fullname AS FullName, r.Name AS RoleName
        FROM Account a
        LEFT JOIN Users u ON a.UserID = u.UserID
        LEFT JOIN Role r ON a.RoleID = r.RoleID
    """;

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                AccountDTO dto = new AccountDTO();
                dto.setAccountID(rs.getInt("AccountID"));
                dto.setUsername(rs.getString("Username"));
                dto.setFullName(rs.getString("FullName"));
                dto.setRoleName(rs.getString("RoleName"));
                list.add(dto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Role> getAllRoles() {
        List<Role> list = new ArrayList<>();
        String sql = "SELECT RoleID, Name FROM Role";

        try (PreparedStatement st = connection.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Role r = new Role();
                r.setRoleID(rs.getInt("RoleID"));
                r.setName(rs.getString("Name"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<AccountDTO> searchAccountByNameOrID(String keyword) {
    List<AccountDTO> list = new ArrayList<>();
    String sql = "SELECT a.AccountID, u.FullName, r.Name AS RoleName "
               + "FROM Account a "
               + "LEFT JOIN Users u ON a.UserID = u.UserID "
               + "LEFT JOIN Role r ON a.RoleID = r.RoleID "
               + "WHERE a.AccountID LIKE ? OR u.FullName LIKE ?";

    try (
         PreparedStatement ps = connection.prepareStatement(sql)) {
        String search = "%" + keyword + "%";
        ps.setString(1, search);
        ps.setString(2, search);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            AccountDTO acc = new AccountDTO();
            acc.setAccountID(rs.getInt("AccountID"));
            acc.setFullName(rs.getString("FullName"));
            acc.setRoleName(rs.getString("RoleName"));
            list.add(acc);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

    public AccountDTO getAccountDetailById(int accountID) {
        String sql = "SELECT a.AccountID, a.Username, u.FullName, r.Name AS RoleName "
                   + "FROM Account a "
                   + "LEFT JOIN Users u ON a.UserID = u.UserID "
                   + "LEFT JOIN Role r ON a.RoleID = r.RoleID "
                   + "WHERE a.AccountID = ?";
        
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, accountID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                AccountDTO acc = new AccountDTO();
                acc.setAccountID(rs.getInt("AccountID"));
                acc.setUsername(rs.getString("Username"));
                acc.setFullName(rs.getString("FullName"));
                acc.setRoleName(rs.getString("RoleName"));
                return acc;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public int getCurrentRoleID(int accountID) {
        String sql = "SELECT RoleID FROM Account WHERE AccountID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, accountID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("RoleID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    

    public String getRoleNameById(int roleID) {
        String sql = "SELECT Name FROM Role WHERE RoleID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roleID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
