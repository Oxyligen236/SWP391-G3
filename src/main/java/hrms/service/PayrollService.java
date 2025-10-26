package hrms.service;

import java.util.ArrayList;
import java.util.List;

import hrms.dao.PayrollDAO;
import hrms.dto.PayrollDTO;
import hrms.dto.PayrollItemDetailDTO;
import hrms.dto.UserDTO;
import hrms.model.Payroll;
import hrms.model.PayrollItem;
import hrms.model.PayrollType;

public class PayrollService {

    private final PayrollDAO payrollDao = new PayrollDAO();
    private final UserService userService = new UserService();

    private PayrollDTO convertToDTO(Payroll payroll) {
        if (payroll == null) {
            return null;
        }

        UserDTO user = userService.getUserById(payroll.getUserID());

        PayrollDTO dto = new PayrollDTO();
        dto.setPayrollID(payroll.getPayrollID());
        dto.setUserID(payroll.getUserID());
        dto.setBaseSalary(payroll.getBaseSalary());
        dto.setMonth(payroll.getMonth());
        dto.setYear(payroll.getYear());
        dto.setTotalWorkHours(payroll.getWorkingHours());
        dto.setNetSalary(payroll.getNetSalary());
        dto.setPayDate(payroll.getPayDate());
        dto.setStatus(payroll.getStatus());

        if (user != null) {
            dto.setUserName(user.getFullname());
            dto.setGender(user.getGender());
            dto.setUserPhone(user.getPhoneNumber());
            dto.setUserEmail(user.getEmail());
            dto.setUserDepartment(user.getDepartmentName());
            dto.setUserPosition(user.getPositionName());
        }

        double totalEarnings = payrollDao.calculateTotalByType(payroll.getPayrollID(), true, payroll.getBaseSalary());
        double totalDeductions = payrollDao.calculateTotalByType(payroll.getPayrollID(), false, payroll.getBaseSalary());

        dto.setTotalEarnings(totalEarnings);
        dto.setTotalDeductions(totalDeductions);

        return dto;
    }

    private List<PayrollItemDetailDTO> getPayrollItemDetails(int payrollId) {
        List<PayrollItem> items = payrollDao.getPayrollItemsByPayrollId(payrollId);
        List<PayrollItemDetailDTO> details = new ArrayList<>();

        for (PayrollItem item : items) {
            PayrollType type = payrollDao.getPayrollTypeById(item.getTypeID());
            if (type != null) {
                PayrollItemDetailDTO detail = new PayrollItemDetailDTO(
                        item.getPayrollItemID(),
                        item.getPayrollID(),
                        type.getPayrollTypeID(),
                        type.getTypeName(),
                        type.getCategory(),
                        item.getAmount(),
                        item.getAmountType(),
                        item.isPositive()
                );
                details.add(detail);
            }
        }

        return details;
    }

    public List<PayrollDTO> getAllPayrollByUserId(int userId) {
        List<Payroll> payrolls = payrollDao.getAllPayrollByUserId(userId);
        List<PayrollDTO> dtos = new ArrayList<>();

        for (Payroll payroll : payrolls) {
            PayrollDTO dto = convertToDTO(payroll);
            if (dto != null) {
                dtos.add(dto);
            }
        }

        return dtos;
    }

    public List<PayrollDTO> getPayrollDetails(int userId) {
        List<Payroll> payrolls = payrollDao.getAllPayrollByUserId(userId);
        List<PayrollDTO> dtos = new ArrayList<>();

        for (Payroll payroll : payrolls) {
            PayrollDTO dto = convertToDTO(payroll);
            if (dto != null) {
                dto.setPayrollItems(getPayrollItemDetails(payroll.getPayrollID()));
                dtos.add(dto);
            }
        }

        return dtos;
    }

    public PayrollDTO getPayrollByUserIdAndPayrollId(int userId, int payrollId) {
        Payroll payroll = payrollDao.getPayrollByUserIdAndPayrollId(userId, payrollId);

        if (payroll == null) {
            return null;
        }

        PayrollDTO dto = convertToDTO(payroll);
        if (dto != null) {
            dto.setPayrollItems(getPayrollItemDetails(payrollId));
        }

        return dto;
    }

    public List<PayrollDTO> searchPayroll(String userName, String department, String position, int month, int year, String status) {
        List<Payroll> payrolls = payrollDao.searchPayroll(userName, department, position, month, year, status);
        List<PayrollDTO> dtos = new ArrayList<>();

        for (Payroll payroll : payrolls) {
            PayrollDTO dto = convertToDTO(payroll);
            if (dto != null) {
                dtos.add(dto);
            }
        }

        return dtos;
    }

    public List<PayrollDTO> getAllCompanyPayrolls() {
        List<Payroll> payrolls = payrollDao.getAllCompanyPayrolls();
        List<PayrollDTO> dtos = new ArrayList<>();

        for (Payroll payroll : payrolls) {
            PayrollDTO dto = convertToDTO(payroll);
            if (dto != null) {
                dtos.add(dto);
            }
        }

        return dtos;
    }

    public List<PayrollDTO> getAllCompanyPayrollDetails() {
        List<Payroll> payrolls = payrollDao.getAllCompanyPayrolls();
        List<PayrollDTO> dtos = new ArrayList<>();

        for (Payroll payroll : payrolls) {
            PayrollDTO dto = convertToDTO(payroll);
            if (dto != null) {
                dto.setPayrollItems(getPayrollItemDetails(payroll.getPayrollID()));
                dtos.add(dto);
            }
        }

        return dtos;
    }

    public List<PayrollDTO> searchPersonalPayroll(int userId, int month, int year) {
        List<Payroll> payrolls = payrollDao.searchPersonalPayroll(userId, month, year);
        List<PayrollDTO> dtos = new ArrayList<>();

        for (Payroll payroll : payrolls) {
            PayrollDTO dto = convertToDTO(payroll);
            if (dto != null) {
                dtos.add(dto);
            }
        }

        return dtos;
    }

}
