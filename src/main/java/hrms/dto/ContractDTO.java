package hrms.dto;

import java.time.LocalDate;

public class ContractDTO {

    private int contractId;
    private int userId;
    private String employeeName; 
    private String contractTypeName; 
    private String positionName; 
    private String signerName;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate signDate;
    private int duration;
    private double baseSalary;
    private String note;

    public ContractDTO() {
    }

    public ContractDTO(int contractId, int userId, String employeeName, String contractTypeName, String positionName, String signerName, LocalDate startDate, LocalDate endDate, LocalDate signDate, int duration, double baseSalary, String note) {
        this.contractId = contractId;
        this.userId = userId;
        this.employeeName = employeeName;
        this.contractTypeName = contractTypeName;
        this.positionName = positionName;
        this.signerName = signerName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.signDate = signDate;
        this.duration = duration;
        this.baseSalary = baseSalary;
        this.note = note;
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
    public String getEmployeeName() {
        return employeeName;
    }
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    public String getContractTypeName() {
        return contractTypeName;
    }
    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
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
    
}
