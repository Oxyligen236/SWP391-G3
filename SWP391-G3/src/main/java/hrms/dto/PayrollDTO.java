package hrms.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

public class PayrollDTO {

    private int payrollID;
    private int userID;
    private String userName;
    private String gender;
    private String userPhone;
    private String userEmail;
    private String userDepartment;
    private String userPosition;
    private double baseSalary;
    private int month;
    private int year;
    private List<PayrollItemDetailDTO> payrollItems;
    private Duration totalWorkHours;
    private double totalDeductions;
    private double totalEarnings;
    private double netSalary;
    private LocalDate payDate;
    private String status;

    public PayrollDTO() {
    }

    public PayrollDTO(int payrollID, int userID, String userName, String gender, String userPhone, String userEmail,
            String userDepartment, String userPosition, double baseSalary, int month, int year, List<PayrollItemDetailDTO> payrollItems,
            Duration totalWorkHours, double totalDeductions, double totalEarnings, double netSalary, LocalDate payDate, String status) {
        this.payrollID = payrollID;
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userDepartment = userDepartment;
        this.userPosition = userPosition;
        this.baseSalary = baseSalary;
        this.month = month;
        this.year = year;
        this.payrollItems = payrollItems;
        this.totalWorkHours = totalWorkHours;
        this.totalDeductions = totalDeductions;
        this.totalEarnings = totalEarnings;
        this.netSalary = netSalary;
        this.payDate = payDate;
        this.status = status;
    }

    public PayrollDTO(int payrollID, int userID, String userName, String userDepartment, String userPosition, double baseSalary, int month,
            int year, Duration totalWorkHours, double totalDeductions, double totalEarnings,
            double netSalary, LocalDate payDate, String status) {
        this.payrollID = payrollID;
        this.userID = userID;
        this.userName = userName;
        this.userDepartment = userDepartment;
        this.userPosition = userPosition;
        this.baseSalary = baseSalary;
        this.month = month;
        this.year = year;
        this.totalWorkHours = totalWorkHours;
        this.totalDeductions = totalDeductions;
        this.totalEarnings = totalEarnings;
        this.netSalary = netSalary;
        this.payDate = payDate;
        this.status = status;
    }

    public int getPayrollID() {
        return payrollID;
    }

    public void setPayrollID(int payrollID) {
        this.payrollID = payrollID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPosition() {
        return userPosition;
    }

    public void setUserPosition(String userPosition) {
        this.userPosition = userPosition;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<PayrollItemDetailDTO> getPayrollItems() {
        return payrollItems;
    }

    public void setPayrollItems(List<PayrollItemDetailDTO> payrollItems) {
        this.payrollItems = payrollItems;
    }

    public double getTotalDeductions() {
        return totalDeductions;
    }

    public void setTotalDeductions(double totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Duration getTotalWorkHours() {
        return totalWorkHours;
    }

    public void setTotalWorkHours(Duration totalWorkHours) {
        this.totalWorkHours = totalWorkHours;
    }

    public String getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }
}
