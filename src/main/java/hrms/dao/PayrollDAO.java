package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import hrms.dto.PayrollDTO;
import hrms.dto.PayrollItemDetailDTO;
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
                rs.getDouble(4),
                rs.getBoolean(5)
        );
    }

    // Lấy tất cả bảng lương của công ty
    public List<PayrollDTO> getAllCompanyPayrolls() {
        String sql = """
                    select 
                        p.Payroll_ID,
                        p.UserID,
                        u.FullName,
                        d.name,
                        pos.name,
                        p.BaseSalary,
                        p.Month,
                        p.Year,
                        p.TotalWorkHours,
                        p.NetSalary,
                        p.PaymentDate,
                        p.Status
                    from Payroll p
                    join Users u on p.UserID = u.UserID
                    left join Department d on u.DepartmentID = d.DepartmentID
                    left join Positions pos on u.PositionID = pos.PositionID
                """;;
        List<PayrollDTO> payrollDTOs = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PayrollDTO dto = new PayrollDTO(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDouble(6),
                        rs.getString(7),
                        rs.getString(8),
                        getTotalWorkHour(rs.getString(9)),
                        getTotalDeductions(rs.getInt(1)),
                        getTotalEarnings(rs.getInt(1)),
                        rs.getDouble(10),
                        rs.getDate(11).toLocalDate(),
                        rs.getString(12)
                );
                payrollDTOs.add(dto);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return payrollDTOs;
    }

    // Cập nhật netSalary trong bảng Payroll dựa trên các mục lương liên quan
    // public void updateNetSalaryByPayrollId(int payrollID) {
    //     String sql = """
    //             update payroll p
    //             join (
    //                 select 
    //                 pi.payroll_id,
    //                 sum(
    //                     case 
    //                     when pt.is_positive = 1 then pt.amount
    //                     else -pt.amount
    //             end
    //             ) as adjustment
    //             from payroll_item pi
    //             join payroll_type pt on pi.payroll_item_id = pt.payroll_item_id
    //             group by pi.payroll_id
    //         ) as calc on p.payroll_id = calc.payroll_id
    //         set p.netsalary = p.basesalary + calc.adjustment
    //         where p.payroll_id = ?;
    //     """;
    //     try {
    //         PreparedStatement st = connection.prepareStatement(sql);
    //         st.setInt(1, payrollID);
    //         st.executeUpdate();
    //     } catch (SQLException e) {
    //         System.out.println(e);
    //     }
    // }
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
                SELECT 
                pi.Payroll_ID,
                ROUND(SUM(
                    CASE 
                        WHEN pt.AmountType = 'fixed' THEN pi.Amount
                        WHEN pt.AmountType = 'percent' THEN (p.BaseSalary * pi.Amount / 100)
                    END
                ), 3) AS TotalAdditions
            FROM Payroll_Item pi
            JOIN Payroll_Type pt ON pi.Type_ID = pt.Type_ID
            JOIN Payroll p ON pi.Payroll_ID = p.Payroll_ID
            WHERE pt.Is_Positive = 0 and p.payroll_id = ?
            GROUP BY pi.Payroll_ID;

        """;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, payrollID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getDouble(2);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    public double getTotalEarnings(int payrollID) {
        String sql = """
            SELECT 
                pi.Payroll_ID,
                ROUND(SUM(
                    CASE 
                        WHEN pt.AmountType = 'fixed' THEN pi.Amount
                        WHEN pt.AmountType = 'percent' THEN (p.BaseSalary * pi.Amount / 100)
                    END
                ), 3) AS TotalDeductions
            FROM Payroll_Item pi
            JOIN Payroll_Type pt ON pi.Type_ID = pt.Type_ID
            JOIN Payroll p ON pi.Payroll_ID = p.Payroll_ID
            WHERE pt.Is_Positive = 1 and p.payroll_id = ?
            GROUP BY pi.Payroll_ID;

        """;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, payrollID);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getDouble(2);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    // Lấy tất cả các bản ghi Payroll của nhân viên
    public List<PayrollDTO> getAllPayrollByUserId(int userId) {
        String sql = """
                select 
                p.Payroll_ID,
                p.UserID,
                u.FullName,
                d.name,
                pos.name,
                p.BaseSalary,
                p.Month,
                p.Year,
                p.TotalWorkHours,
                p.NetSalary,
                p.PaymentDate,
                p.Status
            from Payroll p
            join Users u on p.UserID = u.UserID
            left join Positions pos on u.PositionID = pos.PositionID
            left join Department d on u.DepartmentID = d.DepartmentID
            where u.userid = ?;
                """;
        List<PayrollDTO> payrolls = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                PayrollDTO dto = new PayrollDTO(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDouble(6),
                        rs.getString(7),
                        rs.getString(8),
                        getTotalWorkHour(rs.getString(9)),
                        getTotalDeductions(rs.getInt(1)),
                        getTotalEarnings(rs.getInt(1)),
                        rs.getDouble(10),
                        rs.getDate(11).toLocalDate(),
                        rs.getString(12)
                );
                payrolls.add(dto);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return payrolls;
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
                pi.Payroll_Item_ID,
                pi.Payroll_ID,
                pi.Type_ID,
                pt.TypeName,
                pt.Category,
                pi.Amount,
                pt.AmountType,
                pt.Is_Positive
            from Payroll_Item pi
            join Payroll_Type pt on pi.Type_ID = pt.Type_ID
            where pi.Payroll_ID = ?;
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
                        rs.getString(5),
                        rs.getDouble(6),
                        rs.getString(7),
                        rs.getBoolean(8)
                );
                payrollItemDetails.add(detail);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return payrollItemDetails;
    }

    // Cập nhật netSalary và trạng thái của tất cả các bảng lương trong một tháng và năm cụ thể
    // public boolean updatePayrollNetSalaryAndStatus(int month, int year) {
    //     String sql = """
    //         update payroll p
    //             join (
    //                 select payroll_id 
    //                 from payroll 
    //                 where month = ? and year = ?
    //             ) as target on p.payroll_id = target.payroll_id
    //             set 
    //                 p.netsalary = p.basesalary
    //                     + ifnull((
    //                         select sum(pt.amount)
    //                         from payroll_item pi
    //                         join payroll_type pt on pi.payroll_item_id = pt.payroll_item_id
    //                         where pi.payroll_id = p.payroll_id and pt.is_positive = 1
    //                     ), 0)
    //                     - ifnull((
    //                         select sum(pt.amount)
    //                         from payroll_item pi
    //                         join payroll_type pt on pi.payroll_item_id = pt.payroll_item_id
    //                         where pi.payroll_id = p.payroll_id and pt.is_positive = 0
    //                     ), 0),
    //                 p.status = 'Paid';
    // """;
    //     try {
    //         PreparedStatement st = connection.prepareStatement(sql);
    //         st.setInt(1, month);
    //         st.setInt(2, year);
    //         st.executeUpdate();
    //         return true;
    //     } catch (SQLException e) {
    //         System.out.println(e);
    //     }
    //     return false;
    // }
    // public boolean countTotalWorkHoursForAll(String startDate) {
    //     String sql = """
    //     SET SQL_SAFE_UPDATES = 0;
    //     UPDATE Payroll p
    //     JOIN (
    //         SELECT UserID, MONTH(Date) AS Month, YEAR(Date) AS Year,
    //                SEC_TO_TIME(SUM(TIME_TO_SEC(TotalWorkHours))) AS TotalWork
    //         FROM Attendance
    //         WHERE Date >= ? AND Date < DATE_ADD(?, INTERVAL 1 MONTH)
    //         GROUP BY UserID, MONTH(Date), YEAR(Date)
    //     ) a ON p.UserID = a.UserID AND p.Month = a.Month AND p.Year = a.Year
    //     SET p.TotalWorkHours = a.TotalWork
    //     SET SQL_SAFE_UPDATES = 1;
    //     """;
    //     try (PreparedStatement st = connection.prepareStatement(sql)) {
    //         st.setString(1, startDate);
    //         st.setString(2, startDate);
    //         st.executeUpdate();
    //         return true;
    //     } catch (SQLException e) {
    //         System.out.println(e);
    //     }
    //     return false;
    // }
    public List<PayrollDTO> searchPayroll(String userName, String department, String position, int month, int year, String status) {
        List<PayrollDTO> payrollDTOs = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
            select 
                p.Payroll_ID,
                p.UserID,
                u.FullName,
                d.name,
                pos.name,
                p.BaseSalary,
                p.Month,
                p.Year,
                p.TotalWorkHours,
                p.NetSalary,
                p.PaymentDate,
                p.Status
            from Payroll p
            join Users u on p.UserID = u.UserID
            left join Department d on u.DepartmentID = d.DepartmentID
            left join Positions pos on u.PositionID = pos.PositionID
            where 1=1
        """);

        if (userName != null && !userName.isEmpty()) {
            sql.append(" and u.FullName like '%").append(userName).append("%'");
        }
        if (department != null && !department.isEmpty()) {
            sql.append(" and d.name = '").append(department).append("'");
        }
        if (position != null && !position.isEmpty()) {
            sql.append(" and pos.name = '").append(position).append("'");
        }
        if (month > 0) {
            sql.append(" and p.Month = ").append(month);
        }
        if (year > 0) {
            sql.append(" and p.Year = ").append(year);
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" and p.Status = '").append(status).append("'");
        }

        try {
            PreparedStatement st = connection.prepareStatement(sql.toString());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                PayrollDTO dto = new PayrollDTO(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDouble(6),
                        rs.getString(7),
                        rs.getString(8),
                        getTotalWorkHour(rs.getString(9)),
                        getTotalDeductions(rs.getInt(1)),
                        getTotalEarnings(rs.getInt(1)),
                        rs.getDouble(10),
                        rs.getDate(11).toLocalDate(),
                        rs.getString(12)
                );
                payrollDTOs.add(dto);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return payrollDTOs;
    }

}
