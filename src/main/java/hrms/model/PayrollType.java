package hrms.model;

public class PayrollType {

    private int payrollTypeID;
    private int payrollItemID;
    private String typeName;
    private double amount;
    private boolean isPositive;

    public PayrollType() {
    }

    public PayrollType(int payrollTypeID, int payrollItemID, String typeName, double amount, boolean isPositive) {
        this.payrollTypeID = payrollTypeID;
        this.payrollItemID = payrollItemID;
        this.typeName = typeName;
        this.amount = amount;
        this.isPositive = isPositive;
    }

    public int getpayrollTypeID() {
        return payrollTypeID;
    }

    public void setpayrollTypeID(int payrollTypeID) {
        this.payrollTypeID = payrollTypeID;
    }

    public int getItemID() {
        return payrollItemID;
    }

    public void setItemID(int payrollItemID) {
        this.payrollItemID = payrollItemID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void setPositive(boolean isPositive) {
        this.isPositive = isPositive;
    }

}
