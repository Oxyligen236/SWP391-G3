package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Payroll;
import hrms.model.PayrollItem;
import hrms.model.PayrollType;
import hrms.utils.DBContext;

public class PayrollDAO extends DBContext {

    public Duration getTotalWorkHour(String totalWorkStr) {
        Duration workingHours = Duration.ZERO;
        if (totalWorkStr != null && !totalWorkStr.isEmpty()) {
            String[] parts = totalWorkStr.split(":");
            long hours = Long.parseLong(parts[0]);
            long minutes = Long.parseLong(parts[1]);
            workingHours = Duration.ofHours(hours).plusMinutes(minutes);
        }
        return workingHours;
    }

    private Payroll extractPayrollFromResultSet(ResultSet rs) throws SQLException {
        String totalWorkStr = rs.getString("TotalWorkHours");
        Duration workingHours = getTotalWorkHour(totalWorkStr);

        return new Payroll(
                rs.getInt(1),
                rs.getInt(2),
                rs.getDouble(3),
                rs.getString(4),
                rs.getString(5),
                workingHours,
                rs.getDouble(7),
                rs.getDate(8).toLocalDate(),
                rs.getString(9)
        );
    }

    private PayrollItem extractPayrollItemFromResultSet(ResultSet rs) throws SQLException {
        return new PayrollItem(
                rs.getInt(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getDouble(4)
        );
    }

    private PayrollType extractPayrollTypeFromResultSet(ResultSet rs) throws SQLException {
        return new PayrollType(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getBoolean(5)
        );
    }

    public List<Payroll> getAllCompanyPayrolls() {
        String sql = "select * from Payroll";
        List<Payroll> payrolls = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                payrolls.add(extractPayrollFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return payrolls;
    }

    public List<Payroll> getAllPayrollByUserId(int userId) {
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

    public Payroll getPayrollByUserIdAndPayrollId(int userId, int payrollId) {
        String sql = "select * from Payroll where UserID = ? and Payroll_ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, payrollId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return extractPayrollFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<PayrollItem> getPayrollItemsByPayrollId(int payrollId) {
        String sql = "select * from Payroll_Item where Payroll_ID = ?";
        List<PayrollItem> items = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, payrollId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                items.add(extractPayrollItemFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return items;
    }

    public PayrollType getPayrollTypeById(String typeId) {
        String sql = "select * from Payroll_Type where Type_ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, typeId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return extractPayrollTypeFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<PayrollType> getAllPayrollTypes() {
        String sql = "select * from Payroll_Type";
        List<PayrollType> types = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                types.add(extractPayrollTypeFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return types;
    }

    public List<Payroll> searchPayroll(String userName, String department, String position, int month, int year, String status) {
        StringBuilder sql = new StringBuilder("""
            select p.* from Payroll p
            join Users u on p.UserID = u.UserID
            left join Department d on u.DepartmentID = d.DepartmentID
            left join Positions pos on u.PositionID = pos.PositionID
            where 1=1
        """);

        List<Object> params = new ArrayList<>();

        if (userName != null && !userName.trim().isEmpty()) {
            sql.append(" and u.FullName like ?");
            params.add("%" + userName + "%");
        }
        if (department != null && !department.trim().isEmpty()) {
            sql.append(" and d.name = ?");
            params.add(department);
        }
        if (position != null && !position.trim().isEmpty()) {
            sql.append(" and pos.name = ?");
            params.add(position);
        }
        if (month > 0) {
            sql.append(" and p.Month = ?");
            params.add(month);
        }
        if (year > 0) {
            sql.append(" and p.Year = ?");
            params.add(year);
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" and p.Status = ?");
            params.add(status);
        }

        List<Payroll> payrolls = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql.toString());

            // Set parameters
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                payrolls.add(extractPayrollFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return payrolls;
    }

    public List<Payroll> searchPersonalPayroll(int userId, int month, int year) {
        StringBuilder sql = new StringBuilder("select * from Payroll where UserID = ?");
        List<Object> params = new ArrayList<>();
        params.add(userId);

        if (month > 0) {
            sql.append(" and Month = ?");
            params.add(String.valueOf(month));
        }

        if (year > 0) {
            sql.append(" and Year = ?");
            params.add(String.valueOf(year));
        }

        List<Payroll> payrolls = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql.toString());

            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                payrolls.add(extractPayrollFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return payrolls;
    }

    public double calculateTotalByType(int payrollId, boolean isPositive, double baseSalary) {
        String sql = """
            select
                ROUND(SUM(
                    CASE 
                        WHEN pt.AmountType = 'fixed' THEN pi.Amount
                        WHEN pt.AmountType = 'percent' THEN (? * pi.Amount / 100)
                    END
                ), 3) AS Total
            FROM Payroll_Item pi
            JOIN Payroll_Type pt ON pi.Type_ID = pt.Type_ID
            WHERE pi.Payroll_ID = ? AND pt.Is_Positive = ?
        """;

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setDouble(1, baseSalary);
            st.setInt(2, payrollId);
            st.setBoolean(3, isPositive);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getDouble("Total");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }
}
