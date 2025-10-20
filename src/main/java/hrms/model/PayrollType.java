package hrms.model;

public class PayrollType {

    private int payrollTypeID;
    private String typeName;
    private String category;
    private String amountType;
    private boolean isPositive;

    public PayrollType() {
    }

    public PayrollType(int payrollTypeID, String typeName, String category, String amountType, boolean isPositive) {
        this.payrollTypeID = payrollTypeID;
        this.typeName = typeName;
        this.category = category;
        this.amountType = amountType;
        this.isPositive = isPositive;
    }

    public int getPayrollTypeID() {
        return payrollTypeID;
    }

    public void setPayrollTypeID(int payrollTypeID) {
        this.payrollTypeID = payrollTypeID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public void setPositive(boolean isPositive) {
        this.isPositive = isPositive;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
