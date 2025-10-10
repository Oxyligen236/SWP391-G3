package hrms.model;

public class PayrollItem {

    private int payrollItemID;
    private int payrollID;
    private String type;
    private String itemName;
    private double amount;
    private boolean isPositive;

    public PayrollItem() {
    }

    public PayrollItem(int payrollItemID, int payrollID, String type, String itemName, double amount, boolean isPositive) {
        this.payrollItemID = payrollItemID;
        this.payrollID = payrollID;
        this.type = type;
        this.itemName = itemName;
        this.amount = amount;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
