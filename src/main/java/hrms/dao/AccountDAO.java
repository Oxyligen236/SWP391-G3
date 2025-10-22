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
                rs.getBoolean("Is_active"));
    }

    public List<Account> getAllAccounts() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM Account";
        try (PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery()) {
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

        try (PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery()) {

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

    public boolean changePassword(int accountId, String oldPassword, String newPassword) {
        String verifySql = "SELECT Password FROM Account WHERE AccountID = ?";
        String updateSql = "UPDATE Account SET Password = ? WHERE AccountID = ?";

        PreparedStatement verifyStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;

        try {
            // Bước 1: Kiểm tra mật khẩu cũ
            verifyStmt = connection.prepareStatement(verifySql);
            verifyStmt.setInt(1, accountId);
            rs = verifyStmt.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("Password");

                if (!dbPassword.equals(oldPassword)) {
                    return false;
                }

            } else {
                return false;
            }

            updateStmt = connection.prepareStatement(updateSql);
            updateStmt.setString(1, newPassword);
            updateStmt.setInt(2, accountId);

            int rows = updateStmt.executeUpdate();

            if (rows > 0) {

                return true;
            } else {

                return false;
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi đổi mật khẩu: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (verifyStmt != null)
                    verifyStmt.close();
                if (updateStmt != null)
                    updateStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}