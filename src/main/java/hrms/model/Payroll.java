package hrms.model;

import java.time.Duration;
import java.time.LocalDate;

public class Payroll {

    private int payrollID;
    private int userID;
    private double baseSalary;
    private int month;
    private int year;
    private Duration workingHours;
    private double netSalary;
    private LocalDate payDate;
    private String status;

    public Payroll() {
    }

    public Payroll(int payrollID, int userID, double baseSalary, int month, int year, Duration workingHours, double netSalary, LocalDate payDate,
            String status) {
        this.payrollID = payrollID;
        this.userID = userID;
        this.baseSalary = baseSalary;
        this.month = month;
        this.year = year;
        this.workingHours = workingHours;
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

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
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

    public Duration getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Duration workingHours) {
        this.workingHours = workingHours;
    }

}
