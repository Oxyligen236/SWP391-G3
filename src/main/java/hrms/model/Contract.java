package hrms.model;

import java.time.LocalDate;

public class Contract {

    private int contractId;
    private int userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate signDate;
    private int duration;
    private double baseSalary;
    private String note;
    private int typeID;
    private int positionId;
    private int signerId;

    private String typeName;
    private String contractName; 
    private String positionName; 
    private String signerName;
    
    public Contract() {
    }

    public Contract(int contractId, int userId, LocalDate startDate, LocalDate endDate, LocalDate signDate, int duration, double baseSalary, String note, int typeID, int positionId, int signerId) {
        this.contractId = contractId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.signDate = signDate;
        this.duration = duration;
        this.baseSalary = baseSalary;
        this.note = note; 
        this.typeID = typeID;
        this.positionId = positionId;
        this.signerId = signerId;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getSignDate() {
        return signDate;
    }

    public void setSignDate(LocalDate signDate) {
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
    
    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public int getPositionId() {
        return positionId;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public int getSignerId() {
        return signerId;
    }

    public void setSignerId(int signerId) {
        this.signerId = signerId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getSignerName() {
        return signerName;
    }

    public void setSignerName(String signerName) {
        this.signerName = signerName;
    }
}
