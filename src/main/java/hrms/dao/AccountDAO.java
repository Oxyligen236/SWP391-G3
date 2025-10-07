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
                rs.getBoolean(6)
        );
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
}
