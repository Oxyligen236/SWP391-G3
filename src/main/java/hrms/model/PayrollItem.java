package hrms.model;

public class PayrollItem {

    private int payrollItemID;
    private int payrollID;
    private String typeID;
    private String name;

    public PayrollItem() {
    }

    public PayrollItem(int payrollItemID, int payrollID, String typeID, String name) {
        this.payrollItemID = payrollItemID;
        this.payrollID = payrollID;
        this.typeID = typeID;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
