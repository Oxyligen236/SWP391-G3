package hrms.model;

public class PayrollItem {

    private int payrollItemID;
    private int payrollID;
    private int typeID;
    private double amount;
    private String amountType;
    private boolean isPositive;

    public PayrollItem() {
    }

    public PayrollItem(int payrollItemID, int payrollID, int typeID, double amount, String amountType, boolean isPositive) {
        this.payrollItemID = payrollItemID;
        this.payrollID = payrollID;
        this.typeID = typeID;
        this.amount = amount;
        this.amountType = amountType;
        this.isPositive = isPositive;
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

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public void setPositive(boolean positive) {
        isPositive = positive;
    }

}
