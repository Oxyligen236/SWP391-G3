package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Payroll;
import hrms.model.PayrollItem;
import hrms.utils.DBContext;

public class PayrollDAO extends DBContext {

    private Payroll extractPayrollFromResultSet(ResultSet rs) throws SQLException {
        return new Payroll(
                rs.getInt(1),
                rs.getInt(2),
                rs.getDouble(3),
                rs.getString(4),
                rs.getString(5),
                rs.getDouble(6),
                rs.getString(7)
        );
    }

    private PayrollItem extractPayrollItemFromResultSet(ResultSet rs) throws SQLException {
        return new PayrollItem(
                rs.getInt(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getString(4),
                rs.getDouble(5),
                rs.getBoolean(6)
        );
    }

    public List<Payroll> getPayrollByUserId(int userId) {
        String sql = "select * from Payroll where UserID = ?";
        List<Payroll> payrolls = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                payrolls.add(extractPayrollFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return payrolls;
    }

    public List<PayrollItem> getAllPayrollItemsByUserID(int userId) {
        String sql = "select * from PayrollItem where PayrollID = (select PayrollID from Payroll where UserID = ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            List<PayrollItem> payrollItems = new ArrayList<>();
            while (rs.next()) {
                payrollItems.add(extractPayrollItemFromResultSet(rs));
            }
            return payrollItems;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

}
