package hrms.model;

public class PayrollType {

    private int payrollTypeID;
    private String typeName;
    private String category;

    public PayrollType() {
    }

    public PayrollType(int payrollTypeID, String typeName, String category) {
        this.payrollTypeID = payrollTypeID;
        this.typeName = typeName;
        this.category = category;
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

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
