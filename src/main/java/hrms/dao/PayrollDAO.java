package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
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
        LocalDate payDate = null;
        if (rs.getDate(8) != null) {
            payDate = rs.getDate(8).toLocalDate();
        }
        return new Payroll(
                rs.getInt(1),
                rs.getInt(2),
                rs.getDouble(3),
                rs.getInt(4),
                rs.getInt(5),
                workingHours,
                rs.getDouble(7),
                payDate,
                rs.getString(9)
        );
    }

    private PayrollItem extractPayrollItemFromResultSet(ResultSet rs) throws SQLException {
        return new PayrollItem(
                rs.getInt(1),
                rs.getInt(2),
                rs.getInt(3),
                rs.getDouble(4),
                rs.getString(5),
                rs.getBoolean(6)
        );
    }

    private PayrollType extractPayrollTypeFromResultSet(ResultSet rs) throws SQLException {
        return new PayrollType(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3)
        );
    }

    public List<Payroll> getAllCompanyPayrolls() {
        String sql = "select * from payroll";
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
        String sql = "select * from payroll where userid = ?";
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
        String sql = "select * from payroll where userid = ? and payroll_id = ?";
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
        String sql = "select * from payroll_item where payroll_id = ?";
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

    public PayrollType getPayrollTypeById(int typeId) {
        String sql = "select * from payroll_type where type_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, typeId);
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
        String sql = "select * from payroll_type";
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

    public List<Payroll> searchPayroll(int userID, int month, int year, String status) {
        StringBuilder sql = new StringBuilder("""
            select p.* from payroll p
            join users u on p.userid = u.userid
            left join department d on u.departmentid = d.departmentid
            left join positions pos on u.positionid = pos.positionid
            where 1=1
        """);

        List<Object> params = new ArrayList<>();
        if (userID > 0) {
            sql.append(" and p.userid = ?");
            params.add(userID);
        }

        if (month > 0) {
            sql.append(" and p.month = ?");
            params.add(month);
        }
        if (year > 0) {
            sql.append(" and p.year = ?");
            params.add(year);
        }
        if (status != null && !status.trim().isEmpty()) {
            sql.append(" and p.status = ?");
            params.add(status);
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

    public List<Payroll> searchPersonalPayroll(int userId, int month, int year) {
        StringBuilder sql = new StringBuilder("select * from payroll where userid = ?");
        List<Object> params = new ArrayList<>();
        params.add(userId);

        if (month > 0) {
            sql.append(" and month = ?");
            params.add(month);
        }

        if (year > 0) {
            sql.append(" and year = ?");
            params.add(year);
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
                round(sum(
                    case 
                        when pi.amounttype = 'fixed' then pi.amount
                        when pi.amounttype = 'percent' then (? * pi.amount / 100)
                    end
                ), 2) as total
            from payroll_item pi
            where pi.payroll_id = ? and pi.is_positive = ?
        """;

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setDouble(1, baseSalary);
            st.setInt(2, payrollId);
            st.setBoolean(3, isPositive);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                double total = rs.getDouble("total");
                return rs.wasNull() ? 0.0 : total;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public boolean addPayrollItem(int payrollId, int typeId, double amount, String amountType, boolean isPositive) {
        String sql = "insert into payroll_item (payroll_id, type_id, amount, amounttype, is_positive) values (?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, payrollId);
            st.setInt(2, typeId);
            st.setDouble(3, amount);
            st.setString(4, amountType);
            st.setBoolean(5, isPositive);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean updatePayrollItem(int itemId, double amount, String amountType) {
        String sql = "update payroll_item set amount = ?, amounttype = ? where payroll_item_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setDouble(1, amount);
            st.setString(2, amountType);
            st.setInt(3, itemId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean deletePayrollItem(int itemId) {
        String sql = "delete from payroll_item where payroll_item_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, itemId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public PayrollItem getPayrollItemByPayrollIdAndTypeId(int payrollId, int typeId) {
        String sql = "select * from payroll_item where payroll_id = ? and type_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, payrollId);
            st.setInt(2, typeId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return extractPayrollItemFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean recalculateNetSalary(int payrollId) {
        String sql = """
            update payroll p
            set netsalary = (
                select 
                    p.basesalary +
                    coalesce(sum(
                        case 
                            when pi.is_positive = 1 then
                                case 
                                    when pi.amounttype = 'fixed' then pi.amount
                                    when pi.amounttype = 'percent' then (p.basesalary * pi.amount / 100)
                                end
                            else 0
                        end
                    ), 0) -
                    coalesce(sum(
                        case 
                            when pi.is_positive = 0 then
                                case 
                                    when pi.amounttype = 'fixed' then pi.amount
                                    when pi.amounttype = 'percent' then (p.basesalary * pi.amount / 100)
                                end
                            else 0
                        end
                    ), 0)
                from payroll_item pi
                where pi.payroll_id = p.payroll_id
            )
            where p.payroll_id = ?
        """;

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, payrollId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public Payroll getPayrollById(int payrollId) {
        String sql = "select * from payroll where payroll_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, payrollId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return extractPayrollFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean addNewPayroll(int userId, double baseSalary, int month, int year) {
        String sql = "insert into payroll (userid, basesalary, month, year, totalworkhours, paydate, status) values (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.setDouble(2, baseSalary);
            st.setInt(3, month);
            st.setInt(4, year);
            st.setString(5, "00:00");
            st.setObject(6, null);
            st.setString(7, "Pending");
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean updatePayrollSalary(int payrollId, double netSalary, String workHoursStr, String paymentDate) {
        String sql = "update payroll set netsalary = ?, totalworkhours = ?, paymentdate = ?, status = 'Paid' where payroll_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setDouble(1, netSalary);
            st.setString(2, workHoursStr);
            st.setString(3, paymentDate);
            st.setInt(4, payrollId);
            int result = st.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error updating payroll: " + e.getMessage());
            return false;
        }
    }

    public boolean insertPayroll(Payroll payroll, String workHoursStr) {
        String sql = "insert into payroll (userid, basesalary, month, year, totalworkhours, netsalary, paymentdate, status) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, payroll.getUserID());
            st.setDouble(2, payroll.getBaseSalary());
            st.setInt(3, payroll.getMonth());
            st.setInt(4, payroll.getYear());
            st.setString(5, workHoursStr);
            st.setDouble(6, payroll.getNetSalary());
            st.setObject(7, null);
            st.setString(8, payroll.getStatus());
            int result = st.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting payroll: " + e.getMessage());
            return false;
        }
    }

    public boolean isPayrollExistForMonth(int month, int year) {
        String sql = "select count(*) from payroll where month = ? and year = ? and paymentdate is not null";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, month);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

}
