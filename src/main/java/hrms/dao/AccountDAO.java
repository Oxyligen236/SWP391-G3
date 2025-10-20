package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.dto.AccountDTO;
import hrms.model.Account;
import hrms.utils.DBContext;

public class AccountDAO extends DBContext {

    private Account extractAccountFromResultSet(ResultSet rs) throws SQLException {
        return new Account(
                rs.getInt("AccountID"),
                rs.getInt("UserID"),
                rs.getString("Username"),
                rs.getString("Password"),
                rs.getInt("RoleID"),
                rs.getBoolean("Is_active")
        );
    }

    public List<Account> getAllAccounts() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM Account";
        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                list.add(extractAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách account: " + e.getMessage());
        }
        return list;
    }

    public Account getAccountByUsername(String username) {
        String sql = "SELECT * FROM Account WHERE Username = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return extractAccountFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi getAccountByUsername: " + e.getMessage());
        }
        return null;
    }

    public Account getAccountByUserID(int userID) {
        String sql = "SELECT * FROM Account WHERE UserID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, userID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return extractAccountFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi getAccountByUserID: " + e.getMessage());
        }
        return null;
    }

    public boolean createAccount(Account account) {
        String sql = "INSERT INTO Account (UserID, Username, Password, RoleID, Is_active) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, account.getUserID());
            st.setString(2, account.getUsername());
            st.setString(3, account.getPassword());
            st.setInt(4, account.getRole());
            st.setBoolean(5, account.isIsActive());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi tạo account: " + e.getMessage());
        }
        return false;
    }

    public List<AccountDTO> getAllAccountsDTO() {
        List<AccountDTO> list = new ArrayList<>();
        String sql = """
            SELECT a.AccountID, a.Username, a.Is_active,
                   u.Fullname AS fullName,
                   r.Name AS roleName
            FROM Account a
            LEFT JOIN Users u ON a.UserID = u.UserID
            LEFT JOIN Role r ON a.RoleID = r.RoleID
        """;

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                AccountDTO dto = new AccountDTO();
                dto.setAccountID(rs.getInt("AccountID"));
                dto.setUsername(rs.getString("Username"));
                dto.setActive(rs.getBoolean("Is_active"));
                dto.setFullName(rs.getString("fullName"));
                dto.setRoleName(rs.getString("roleName"));
                list.add(dto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean toggleAccountStatus(int accountID) {
        String sql = "UPDATE Account SET Is_active = CASE WHEN Is_active = 1 THEN 0 ELSE 1 END WHERE AccountID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, accountID);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public AccountDTO getAccountDTOByID(int accountID) {
        String sql = """
        SELECT a.AccountID, a.Username, a.Is_active,
               u.Fullname AS fullName,
               r.Name AS roleName
        FROM Account a
        LEFT JOIN Users u ON a.UserID = u.UserID
        LEFT JOIN Role r ON a.RoleID = r.RoleID
        WHERE a.AccountID = ?
    """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, accountID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                AccountDTO dto = new AccountDTO();
                dto.setAccountID(rs.getInt("AccountID"));
                dto.setUsername(rs.getString("Username"));
                dto.setActive(rs.getBoolean("Is_active"));
                dto.setFullName(rs.getString("fullName"));
                dto.setRoleName(rs.getString("roleName"));
                return dto;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePassword(int userId, String newPassword) {
        String sql = "update Account set Password = ? where UserID = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, newPassword);
            st.setInt(2, userId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
            return false;
        }
    }

    // public int migratePasswords() {
    //     String selectSql = "SELECT UserID, Password FROM Account";
    //     String updateSql = "UPDATE Account SET Password = ? WHERE UserID = ?";
    //     int count = 0;
    //     try (PreparedStatement selectStmt = connection.prepareStatement(selectSql); PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
    //         ResultSet rs = selectStmt.executeQuery();
    //         while (rs.next()) {
    //             int userId = rs.getInt("UserID");
    //             String plainPassword = rs.getString("Password");
    //             if (!PasswordUtil.isHashed(plainPassword)) {
    //                 String hashedPassword = PasswordUtil.hashPassword(plainPassword);
    //                 updateStmt.setString(1, hashedPassword);
    //                 updateStmt.setInt(2, userId);
    //                 updateStmt.executeUpdate();
    //                 count++;
    //                 System.out.println("Migrated password for UserID: " + userId);
    //             }
    //         }
    //         System.out.println("Migration completed. Total passwords hashed: " + count);
    //     } catch (SQLException e) {
    //         System.err.println("Error during password migration: " + e.getMessage());
    //         e.printStackTrace();
    //     }
    //     return count;
    // }
    // public boolean areAllPasswordsHashed() {
    //     String sql = "SELECT COUNT(*) as total FROM Account";
    //     String hashedSql = "SELECT COUNT(*) as hashed FROM Account WHERE Password LIKE '%:%'";
    //     try (PreparedStatement st1 = connection.prepareStatement(sql); PreparedStatement st2 = connection.prepareStatement(hashedSql)) {
    //         ResultSet rs1 = st1.executeQuery();
    //         ResultSet rs2 = st2.executeQuery();
    //         if (rs1.next() && rs2.next()) {
    //             int total = rs1.getInt("total");
    //             int hashed = rs2.getInt("hashed");
    //             return total == hashed;
    //         }
    //     } catch (SQLException e) {
    //         System.err.println("Error checking password status: " + e.getMessage());
    //     }
    //     return false;
    // }
}
