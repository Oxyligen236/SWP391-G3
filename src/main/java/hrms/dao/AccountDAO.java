package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.dto.AccountDTO;
import hrms.model.Account;
import hrms.utils.DBContext;
import hrms.utils.PasswordUtil;

public class AccountDAO extends DBContext {

    private Account extractAccountFromResultSet(ResultSet rs) throws SQLException {
        return new Account(
                rs.getInt(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getString(4),
                rs.getInt(5),
                rs.getBoolean(6),
                rs.getString(7));
    }

    public List<Account> getAllAccounts() {
        List<Account> list = new ArrayList<>();
        String sql = "SELECT * FROM Account";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
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

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

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

    public int migratePasswords() {
        String selectSql = "SELECT UserID, Password FROM Account";
        String updateSql = "UPDATE Account SET Password = ? WHERE UserID = ?";
        int count = 0;
        try (PreparedStatement selectStmt = connection.prepareStatement(selectSql);
                PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("UserID");
                String plainPassword = rs.getString("Password");
                if (!PasswordUtil.isHashed(plainPassword)) {
                    String hashedPassword = PasswordUtil.hashPassword(plainPassword);
                    updateStmt.setString(1, hashedPassword);
                    updateStmt.setInt(2, userId);
                    updateStmt.executeUpdate();
                    count++;
                    System.out.println("Migrated password for UserID: " + userId);
                }
            }
            System.out.println("Migration completed. Total passwords hashed: " + count);
        } catch (SQLException e) {
            System.err.println("Error during password migration: " + e.getMessage());
            System.out.println("Migration halted. Total passwords hashed before error: " + count);
        }
        return count;
    }

    public boolean areAllPasswordsHashed() {
        String sql = "SELECT COUNT(*) as total FROM Account";
        String hashedSql = "SELECT COUNT(*) as hashed FROM Account WHERE Password LIKE '%:%'";
        try (PreparedStatement st1 = connection.prepareStatement(sql);
                PreparedStatement st2 = connection.prepareStatement(hashedSql)) {
            ResultSet rs1 = st1.executeQuery();
            ResultSet rs2 = st2.executeQuery();
            if (rs1.next() && rs2.next()) {
                int total = rs1.getInt("total");
                int hashed = rs2.getInt("hashed");
                return total == hashed;
            }
        } catch (SQLException e) {
            System.err.println("Error checking password status: " + e.getMessage());
        }
        return false;
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
                if (rs != null) {
                    rs.close();
                }
                if (verifyStmt != null) {
                    verifyStmt.close();
                }
                if (updateStmt != null) {
                    updateStmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<AccountDTO> getFilteredAccounts(
            String search, String roleFilter, String statusFilter,
            String sortBy, String sortOrder, int page, int pageSize) throws SQLException {

        List<AccountDTO> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT a.AccountID, a.Username, u.FullName, a.Is_active, r.Name AS RoleName "
                        + "FROM Account a "
                        + "JOIN Users u ON a.UserID = u.UserID "
                        + "JOIN Role r ON a.RoleID = r.RoleID WHERE 1=1 ");

        if (search != null && !search.trim().isEmpty()) {
            sql.append("AND (u.FullName LIKE ? OR a.Username LIKE ?) ");
        }

        if (roleFilter != null && !roleFilter.equalsIgnoreCase("all") && !roleFilter.isEmpty()) {
            sql.append("AND r.RoleID = ? ");
        }

        if (statusFilter != null && !statusFilter.equalsIgnoreCase("all") && !statusFilter.isEmpty()) {
            sql.append("AND a.Is_active = ? ");
        }

        if ("role".equalsIgnoreCase(sortBy)) {
            sql.append("ORDER BY r.Name ");
        } else if ("name".equalsIgnoreCase(sortBy)) {
            sql.append("ORDER BY u.FullName ");
        } else {
            sql.append("ORDER BY a.AccountID ");
        }

        sql.append(" ").append("desc".equalsIgnoreCase(sortOrder) ? "DESC " : "ASC ");
        sql.append("LIMIT ? OFFSET ?");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (search != null && !search.trim().isEmpty()) {
                ps.setString(index++, "%" + search + "%");
                ps.setString(index++, "%" + search + "%");
            }

            if (roleFilter != null && !roleFilter.equalsIgnoreCase("all") && !roleFilter.isEmpty()) {
                ps.setInt(index++, Integer.parseInt(roleFilter));
            }

            if (statusFilter != null && !statusFilter.equalsIgnoreCase("all") && !statusFilter.isEmpty()) {
                ps.setBoolean(index++, "active".equalsIgnoreCase(statusFilter));
            }

            ps.setInt(index++, pageSize);
            ps.setInt(index, (page - 1) * pageSize);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AccountDTO dto = new AccountDTO();
                dto.setAccountID(rs.getInt("AccountID"));
                dto.setUsername(rs.getString("Username"));
                dto.setFullName(rs.getString("FullName"));
                dto.setActive(rs.getBoolean("Is_active"));
                dto.setRoleName(rs.getString("RoleName"));
                list.add(dto);
            }
        }

        return list;
    }

    public int countFilteredAccounts(String search, String roleFilter, String statusFilter) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) FROM Account a "
                        + "JOIN Users u ON a.UserID = u.UserID "
                        + "JOIN Role r ON a.RoleID = r.RoleID WHERE 1=1 ");

        if (search != null && !search.trim().isEmpty()) {
            sql.append("AND (u.FullName LIKE ? OR a.Username LIKE ?) ");
        }
        if (roleFilter != null && !roleFilter.equalsIgnoreCase("all") && !roleFilter.isEmpty()) {
            sql.append("AND r.RoleID = ? ");
        }
        if (statusFilter != null && !statusFilter.equalsIgnoreCase("all") && !statusFilter.isEmpty()) {
            sql.append("AND a.Is_active = ? ");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int index = 1;
            if (search != null && !search.trim().isEmpty()) {
                ps.setString(index++, "%" + search + "%");
                ps.setString(index++, "%" + search + "%");
            }
            if (roleFilter != null && !roleFilter.equalsIgnoreCase("all") && !roleFilter.isEmpty()) {
                ps.setInt(index++, Integer.parseInt(roleFilter));
            }
            if (statusFilter != null && !statusFilter.equalsIgnoreCase("all") && !statusFilter.isEmpty()) {
                ps.setBoolean(index++, "active".equalsIgnoreCase(statusFilter));
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    public Account getAccountByGoogleEmail(String googleEmail) {
        String sql = "SELECT * FROM Account WHERE GoogleEmail = ? AND Is_active = 1";
        try (
                PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, googleEmail);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return extractAccountFromResultSet(rs);
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy account bằng email Google: " + e.getMessage());
        }
        return null;
    }

    public boolean findAccountByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM Account WHERE GoogleEmail = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra email trong Account: " + e.getMessage());
        }
        return false;
    }

   public Account getAccountById(int accountId) {
    String sql = "SELECT AccountID, Username, Password, RoleID, Is_active FROM Account WHERE AccountID = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, accountId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Account account = new Account();
            account.setAccountID(rs.getInt("AccountID"));
            account.setUsername(rs.getString("Username"));
            account.setPassword(rs.getString("Password"));
            account.setRole(rs.getInt("RoleID"));
            account.setIsActive(rs.getBoolean("Is_active"));
            return account;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

    /**
     * Reset mật khẩu của account
     */
    public boolean resetPassword(int accountId, String hashedPassword) {
        String sql = "UPDATE Account SET Password = ? WHERE AccountID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, hashedPassword);
            ps.setInt(2, accountId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

public boolean updateStatus(int accountId, boolean active) throws SQLException {
    String sql = "UPDATE Account SET Is_active = ? WHERE AccountID = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setBoolean(1, active);
        ps.setInt(2, accountId);
        return ps.executeUpdate() > 0;
    }
}


public int countActiveAdmins() throws SQLException {
    String sql = "SELECT COUNT(*) FROM Account WHERE RoleID = 5 AND Is_active = 1";
    try (PreparedStatement ps = connection.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            return rs.getInt(1);
        }
    }
    return 0;
}



}
