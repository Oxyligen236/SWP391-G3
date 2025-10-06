package hrms.model;

import java.util.Date;

public class Contract {

    private int contractId;
    private int userId;
    private Date startDate;
    private Date endDate;
    private Date signDate;
    private int duration;
    private double baseSalary;
    private String note;
    private int typeID;

    private String typeName;

    public Contract() {
    }

    public Contract(int contractId, int userId, Date startDate, Date endDate, Date signDate, int duration, double baseSalary, String note, int typeID) {
        this.contractId = contractId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.signDate = signDate;
        this.duration = duration;
        this.baseSalary = baseSalary;
        this.note = note;
        this.typeID = typeID;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractID(int contractId) {
        this.contractId = contractId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
}
