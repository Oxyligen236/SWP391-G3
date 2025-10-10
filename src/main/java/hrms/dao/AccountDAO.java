package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Account;
import hrms.utils.DBContext;

public class AccountDAO extends DBContext {

    private Account extractAccountFromResultSet(ResultSet rs) throws SQLException {
        return new Account(
                rs.getInt(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getString(4),
                rs.getInt(5),
                rs.getBoolean(6));
    }

    public List<Account> getAll() {
        String sql = "select * from Account";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            List<Account> list = new ArrayList<>();
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Account a = extractAccountFromResultSet(rs);
                list.add(a);
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Account getAccountByUsername(String username) {
        String sql = "select * from Account where username = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return extractAccountFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Account getAccountByUserID(int userID) {
        String sql = "SELECT * FROM Account WHERE UserID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return extractAccountFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean createAccount(Account account) {
        String sql = "INSERT INTO Account (UserID, Username, Password, RoleID, Is_active) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, account.getUserID());
            st.setString(2, account.getUsername());
            st.setString(3, account.getPassword()); // TODO: hash password nếu muốn
            st.setInt(4, account.getRole());
            st.setBoolean(5, account.isIsActive());

            int rowsInserted = st.executeUpdate();
            return rowsInserted > 0; // true nếu có ít nhất 1 row được thêm
        } catch (SQLException e) {
            System.out.println("Lỗi tạo account: " + e.getMessage());
        }
        return false;
    }
}
