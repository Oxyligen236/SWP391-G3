package hrms.model;

public class PayrollType {

    private int payrollTypeID;
    private String typeName;
    private String category;
    private double amountType;
    private boolean isPositive;

    public PayrollType() {
    }

    public PayrollType(int payrollTypeID, String typeName, String category, double amountType, boolean isPositive) {
        this.payrollTypeID = payrollTypeID;
        this.typeName = typeName;
        this.category = category;
        this.amountType = amountType;
        this.isPositive = isPositive;
    }

    public int getpayrollTypeID() {
        return payrollTypeID;
    }

    public void setpayrollTypeID(int payrollTypeID) {
        this.payrollTypeID = payrollTypeID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmountType() {
        return amountType;
    }

    public void setAmountType(double amountType) {
        this.amountType = amountType;
    }

    public boolean isIsPositive() {
        return isPositive;
    }

    public void setIsPositive(boolean isPositive) {
        this.isPositive = isPositive;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
