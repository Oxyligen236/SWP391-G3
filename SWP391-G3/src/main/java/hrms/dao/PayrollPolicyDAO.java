package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.PayrollPolicy;
import hrms.utils.DBContext;

public class PayrollPolicyDAO extends DBContext {

    public List<PayrollPolicy> getAllPolicies() {
        List<PayrollPolicy> list = new ArrayList<>();
        String sql = "SELECT * FROM payroll_policy";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new PayrollPolicy(
                        rs.getInt("PolicyID"),
                        rs.getString("PolicyName"),
                        rs.getDouble("PolicyValue"),
                        rs.getString("Description")
                ));
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi khi lấy danh sách payroll_policy: " + ex.getMessage());
        }
        return list;
    }

    public PayrollPolicy getByName(String name) {
        String sql = "SELECT * FROM payroll_policy WHERE PolicyName = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new PayrollPolicy(
                        rs.getInt("PolicyID"),
                        rs.getString("PolicyName"),
                        rs.getDouble("PolicyValue"),
                        rs.getString("Description")
                );
            }
        } catch (SQLException ex) {
            System.err.println("Lỗi khi lấy payroll_policy theo tên: " + ex.getMessage());
        }
        return null;
    }

    public void updatePolicyValue(String name, double newValue) {
        String sql = "UPDATE payroll_policy SET PolicyValue = ? WHERE PolicyName = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, newValue);
            ps.setString(2, name);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Lỗi khi cập nhật payroll_policy: " + ex.getMessage());
        }
    }
}
