package hrms.dto;

public class PayrollItemDetailDTO {

    private int payrollItemId;
    private int payrollId;
    private int typeId;
    private String typeName;
    private String category;
    private double amount;
    private String amountType;
    private boolean isPositive;

    public PayrollItemDetailDTO() {
    }

    public PayrollItemDetailDTO(int payrollItemId, int payrollId, int typeId, String typeName, String category, double amount, String amountType, boolean isPositive) {
        this.payrollItemId = payrollItemId;
        this.payrollId = payrollId;
        this.typeId = typeId;
        this.typeName = typeName;
        this.category = category;
        this.amount = amount;
        this.amountType = amountType;
        this.isPositive = isPositive;
    }

    public int getPayrollItemId() {
        return payrollItemId;
    }

    public void setPayrollItemId(int payrollItemId) {
        this.payrollItemId = payrollItemId;
    }

    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public void setPositive(boolean isPositive) {
        this.isPositive = isPositive;
    }

}
