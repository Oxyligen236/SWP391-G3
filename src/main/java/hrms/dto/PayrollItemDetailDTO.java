package hrms.dto;

public class PayrollItemDetailDTO {

    private int payrollItemID;
    private int payrollID;
    private int typeID;
    private String typeName;
    private double amount;
    private boolean isPositive;

    public PayrollItemDetailDTO() {
    }

    public PayrollItemDetailDTO(int payrollItemID, int payrollID, int typeID, String typeName, double amount, boolean isPositive) {
        this.payrollItemID = payrollItemID;
        this.payrollID = payrollID;
        this.typeID = typeID;
        this.typeName = typeName;
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

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
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
