package hrms.service;

import java.util.ArrayList;
import java.util.List;

import hrms.dao.PayrollDAO;
import hrms.dto.PayrollDTO;
import hrms.dto.PayrollItemDetailDTO;
import hrms.dto.UserDTO;
import hrms.model.Payroll;
import hrms.model.PayrollItem;

public class PayrollService {

    private final PayrollDAO payrollDao = new PayrollDAO();
    private final UserService userService = new UserService();

    public List<Payroll> getAllPayrollByUserId(int userId) {
        return payrollDao.getAllPayrollByUserId(userId);
    }

    public List<PayrollItem> getAllPayrollItemsByPayrollId(int payrollId) {
        return payrollDao.getAllPayrollItemsByPayrollId(payrollId);
    }

    public List<PayrollDTO> getPayrollDetails(int userId, int payrollId) {

        UserDTO user = userService.getUserById(userId);

        List<Payroll> payrolls = payrollDao.getAllPayrollByUserId(userId);
        List<PayrollItemDetailDTO> payrollItems = payrollDao.getDetailedPayrollItemsByPayrollId(payrollId);

        List<PayrollDTO> payrollDTOs = new ArrayList<>();

        for (Payroll payroll : payrolls) {
            PayrollDTO dto = new PayrollDTO();
            dto.setPayrollID(payroll.getPayrollID());
            dto.setUserID(user.getUserId());
            dto.setUserName(user.getFullname());
            dto.setGender(user.getGender());
            dto.setUserPhone(user.getPhoneNumber());
            dto.setUserEmail(user.getEmail());
            dto.setUserPosition(user.getPositionName());
            dto.setBaseSalary(payroll.getBaseSalary());
            dto.setMonth(payroll.getMonth());
            dto.setYear(payroll.getYear());
            dto.setPayrollItems(payrollItems);
            dto.setTotalDeductions(payrollDao.getTotalDeductions(payroll.getPayrollID()));
            dto.setTotalEarnings(payrollDao.getTotalEarnings(payroll.getPayrollID()));
            dto.setNetSalary(payroll.getNetSalary());
            dto.setPayDate(payroll.getPayDate());
            payrollDTOs.add(dto);
        }
        return payrollDTOs;
    }

    public PayrollDTO getPayrollByUserIdAndPayrollId(int userId, int payrollId) {
        Payroll payroll = payrollDao.getPayrollByUserIdAndPayrollId(userId, payrollId);

        if (payroll == null) {
            return null;
        }
        UserDTO user = userService.getUserById(userId);
        List<PayrollItemDetailDTO> payrollItems = payrollDao.getDetailedPayrollItemsByPayrollId(payrollId);

        PayrollDTO dto = new PayrollDTO();
        dto.setPayrollID(payroll.getPayrollID());
        dto.setUserID(user.getUserId());
        dto.setUserName(user.getFullname());
        dto.setGender(user.getGender());
        dto.setUserPhone(user.getPhoneNumber());
        dto.setUserEmail(user.getEmail());
        dto.setUserPosition(user.getPositionName());
        dto.setBaseSalary(payroll.getBaseSalary());
        dto.setMonth(payroll.getMonth());
        dto.setYear(payroll.getYear());
        dto.setPayrollItems(payrollItems);
        dto.setTotalDeductions(payrollDao.getTotalDeductions(payroll.getPayrollID()));
        dto.setTotalEarnings(payrollDao.getTotalEarnings(payroll.getPayrollID()));
        dto.setNetSalary(payroll.getNetSalary());
        dto.setPayDate(payroll.getPayDate());
        return dto;
    }

    public List<Payroll> searchPayroll(int userId, String month, String year) {
        return payrollDao.searchPayroll(userId, month, year);
    }

}
