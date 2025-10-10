package hrms.model;

public class Payroll {

    private int payrollID;
    private int userID;
    private double baseSalary;
    private String month;
    private String year;
    private double netSalary;
    private String status;

    public Payroll() {
    }

    public Payroll(int payrollID, int userID, double baseSalary, String month, String year, double netSalary,
            String status) {
        this.payrollID = payrollID;
        this.userID = userID;
        this.baseSalary = baseSalary;
        this.month = month;
        this.year = year;
        this.netSalary = netSalary;
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

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
