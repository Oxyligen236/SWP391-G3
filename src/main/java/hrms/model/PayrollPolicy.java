package hrms.model;

public class PayrollPolicy {

    private int policyID;
    private String policyName;
    private double policyValue;
    private String description;

    public PayrollPolicy() {
    }

    public PayrollPolicy(int policyID, String policyName, double policyValue, String description) {
        this.policyID = policyID;
        this.policyName = policyName;
        this.policyValue = policyValue;
        this.description = description;
    }

    // Getters & setters
    public int getPolicyID() {
        return policyID;
    }

    public void setPolicyID(int policyID) {
        this.policyID = policyID;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public double getPolicyValue() {
        return policyValue;
    }

    public void setPolicyValue(double policyValue) {
        this.policyValue = policyValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
