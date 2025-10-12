package hrms.dto;

import java.time.LocalDate;
import java.util.List;

public class PayrollDTO {

    private int payrollID;
    private int userID;
    private String userName;
    private String gender;
    private String userPhone;
    private String userEmail;
    private String userPosition;
    private double baseSalary;
    private String month;
    private String year;
    private List<PayrollItemDetailDTO> payrollItems;
    private double totalDeductions;
    private double totalEarnings;
    private double netSalary;
    private LocalDate payDate;

    public PayrollDTO() {
    }

    public PayrollDTO(int payrollID, int userID, String userName, String gender, String userPhone, String userEmail,
            String userPosition, double baseSalary, String month, String year, List<PayrollItemDetailDTO> payrollItems,
            double totalDeductions, double totalEarnings, double netSalary, LocalDate payDate) {
        this.payrollID = payrollID;
        this.userID = userID;
        this.userName = userName;
        this.gender = gender;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userPosition = userPosition;
        this.baseSalary = baseSalary;
        this.month = month;
        this.year = year;
        this.payrollItems = payrollItems;
        this.totalDeductions = totalDeductions;
        this.totalEarnings = totalEarnings;
        this.netSalary = netSalary;
        this.payDate = payDate;
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
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

}
