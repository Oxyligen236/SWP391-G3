package hrms.model;

public class PayrollItem {

    private int payrollItemID;
    private int payrollID;
    private String typeID;
    private double amount;

    public PayrollItem() {
    }

    public PayrollItem(int payrollItemID, int payrollID, String typeID, double amount) {
        this.payrollItemID = payrollItemID;
        this.payrollID = payrollID;
        this.typeID = typeID;
        this.amount = amount;
    }

    public int getPayrollItemID() {
        return payrollItemID;
    }

    public void setPayrollItemID(int payrollItemID) {
        this.payrollItemID = payrollItemID;
    }

    public int getPayrollID() {
        return payrollID;
    }

    public void setPayrollID(int payrollID) {
        this.payrollID = payrollID;
    }

    public String getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID = typeID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
