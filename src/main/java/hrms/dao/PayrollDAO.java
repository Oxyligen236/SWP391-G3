package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.dto.PayrollItemDetailDTO;
import hrms.model.Payroll;
import hrms.model.PayrollItem;
import hrms.model.PayrollType;
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
                rs.getDate(7).toLocalDate(),
                rs.getString(8)
        );
    }

    private PayrollItem extractPayrollItemFromResultSet(ResultSet rs) throws SQLException {
        return new PayrollItem(
                rs.getInt(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getString(4)
        );
    }

    private PayrollType extractPayrollTypeFromResultSet(ResultSet rs) throws SQLException {
        return new PayrollType(
                rs.getInt(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getDouble(4),
                rs.getBoolean(5)
        );
    }

    // Cập nhật netSalary trong bảng Payroll dựa trên các mục lương liên quan
    public void updateNetSalaryByPayrollId(int payrollID) {
        String sql = """
                update payroll p
                join (
                    select 
                    pi.payroll_id,
                    sum(
                        case 
                        when pt.is_positive = 1 then pt.amount
                        else -pt.amount
                end
                ) as adjustment
                from payroll_item pi
                join payroll_type pt on pi.payroll_item_id = pt.payroll_item_id
                group by pi.payroll_id
            ) as calc on p.payroll_id = calc.payroll_id
            set p.netsalary = p.basesalary + calc.adjustment
            where p.payroll_id = ?;
        """;

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, payrollID);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Thêm một mục lương mới vào bảng PayrollItem
    // public void addPayrollItem(int payrollID, String typeID) {
    //     String sql = "insert into PayrollItem (PayrollID, TypeID) values (?, ?)";
    //     try {
    //         PreparedStatement st = connection.prepareStatement(sql);
    //         st.setInt(1, payrollID);
    //         st.setString(2, typeID);
    //         st.executeUpdate();
    //         updateNetSalaryByPayrollId(payrollID);
    //     } catch (SQLException e) {
    //         System.out.println(e);
    //     }
    // }
    // Lấy tổng các khoản khấu trừ hoặc thu nhập từ bảng PayrollItem dựa trên loại
    public double getTotalDeductions(int payrollID) {
        String sql = """
           select
                sum(pt.amount) as total_decrease
                from payroll_item pi
                join payroll_type pt on pi.payroll_item_id = pt.payroll_item_id
                where pi.payroll_id = ? and pt.is_positive = 0;
        """;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, payrollID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public double getTotalEarnings(int payrollID) {
        String sql = """
     select
            sum(pt.amount) as total_increase
        from payroll_item pi
        join payroll_type pt on pi.payroll_item_id = pt.payroll_item_id
        where pi.payroll_id = ? and pt.is_positive = 1;
        """;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, payrollID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    // Lấy tất cả các bản ghi Payroll của nhân viên
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

    // Lấy tất cả các mục lương liên quan đến một bảng lương cụ thể
    public List<PayrollItem> getAllPayrollItemsByPayrollId(int payrollId) {
        String sql = "select * from Payroll_Item where Payroll_ID = ?";
        List<PayrollItem> payrollItems = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, payrollId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                payrollItems.add(extractPayrollItemFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return payrollItems;
    }

    // Lấy tất cả các loại mục lương từ bảng PayrollType
    public List<PayrollType> getAllPayrollTypes() {
        String sql = "select * from Payroll_Type";
        List<PayrollType> payrollTypes = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                payrollTypes.add(extractPayrollTypeFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return payrollTypes;
    }

    // Lấy bảng lương theo UserID và PayrollID
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

    // Lấy chi tiết các mục lương liên quan đến một bảng lương cụ thể (PayrollDetailDTO)
    public List<PayrollItemDetailDTO> getDetailedPayrollItemsByPayrollId(int payrollId) {
        String sql = """
           select 
                pi.payroll_item_id,
                pi.payroll_id,
                pi.type_id,
                pt.typename,
                pt.amount,
                pt.is_positive
            from payroll_item pi
            join payroll_type pt on pi.payroll_item_id = pt.payroll_item_id
            where pi.payroll_id = ?;
        """;
        List<PayrollItemDetailDTO> payrollItemDetails = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, payrollId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PayrollItemDetailDTO detail = new PayrollItemDetailDTO(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getDouble(5),
                        rs.getBoolean(6)
                );
                payrollItemDetails.add(detail);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return payrollItemDetails;
    }

    // Cập nhật netSalary và trạng thái của tất cả các bảng lương trong một tháng và năm cụ thể
    public boolean updatePayrollNetSalaryAndStatus(int month, int year) {
        String sql = """
            update payroll p
                join (
                    select payroll_id 
                    from payroll 
                    where month = ? and year = ?
                ) as target on p.payroll_id = target.payroll_id
                set 
                    p.netsalary = p.basesalary
                        + ifnull((
                            select sum(pt.amount)
                            from payroll_item pi
                            join payroll_type pt on pi.payroll_item_id = pt.payroll_item_id
                            where pi.payroll_id = p.payroll_id and pt.is_positive = 1
                        ), 0)
                        - ifnull((
                            select sum(pt.amount)
                            from payroll_item pi
                            join payroll_type pt on pi.payroll_item_id = pt.payroll_item_id
                            where pi.payroll_id = p.payroll_id and pt.is_positive = 0
                        ), 0),
                    p.status = 'Paid';
    """;

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, month);
            st.setInt(2, year);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public List<Payroll> searchPayroll(int userId, String month, String year) {
        List<Payroll> payrolls = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder("select * from Payroll where UserID = ?");

            if (month != null && !month.isEmpty()) {
                sql.append(" and Month = ?");
            }
            if (year != null && !year.isEmpty()) {
                sql.append(" and Year = ?");
            }

            PreparedStatement st = connection.prepareStatement(sql.toString());
            st.setInt(1, userId);

            int index = 2;
            if (month != null && !month.isEmpty()) {
                st.setString(index++, month);
            }
            if (year != null && !year.isEmpty()) {
                st.setString(index++, year);
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

}
