package hrms.service;

import java.util.List;

import hrms.dao.PayrollDAO;
import hrms.dto.PayrollDTO;
import hrms.dto.UserDTO;
import hrms.model.Payroll;
import hrms.model.PayrollItem;

public class PayrollService {

    private final PayrollDAO payrollDao = new PayrollDAO();
    private final UserService userService = new UserService();

    public List<Payroll> getPayrollByUserId(int userId) {
        return payrollDao.getPayrollByUserId(userId);
    }

    public List<PayrollItem> getAllPayrollItemsByUserID(int userId) {
        return payrollDao.getAllPayrollItemsByUserID(userId);
    }

    public PayrollDTO getPayrollDetails(int userId) {
        Payroll payroll = payrollDao.getPayrollByUserId(userId).get(0);
        if (payroll == null) {
            return null;
        }
        List<PayrollItem> payrollItems = payrollDao.getAllPayrollItemsByUserID(userId);
        double totalEarnings = payroll.getBaseSalary();
        double totalDeductions = 0;

        PayrollItem payrollItem = new PayrollItem();
        for (PayrollItem item : payrollItems) {
            if (item.isPositive() == false) {
                totalDeductions += item.getAmount();
            } else {
                totalEarnings += item.getAmount();
            }
            payrollItem = item;
        }
        double netSalary = totalEarnings - totalDeductions;
        UserDTO user = userService.getUserById(userId);
        if (user == null) {
            return null;
        }
        return new PayrollDTO(
                payroll.getPayrollID(),
                userId,
                user.getFullname(),
                user.getGender(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getPositionName(),
                payroll.getBaseSalary(),
                payroll.getMonth(),
                payroll.getYear(),
                payrollItems,
                totalDeductions,
                netSalary,
                totalEarnings
        );
    }

}
