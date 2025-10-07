package hrms.model;

import java.time.LocalDate;

public class JobDescription {

    private int jobID;                     
    private int ticketID;                  
    private String jobTitle;               
    private String status; 
    private LocalDate startDate;           
    private LocalDate endDate;           
    private String department;        
    private int vacancies;                 
    private String responsibilities;       
    private String requirements;          
    private String compensation;          
    private String officeAddress;         
    private String workingConditions;    

    public JobDescription() {
    }

    public JobDescription(int jobID, int ticketID, String jobTitle, String status, LocalDate startDate, LocalDate endDate, String department, int vacancies, String responsibilities, String requirements, String compensation, String officeAddress, String workingConditions) {
        this.jobID = jobID;
        this.ticketID = ticketID;
        this.jobTitle = jobTitle;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.department = department;
        this.vacancies = vacancies;
        this.responsibilities = responsibilities;
        this.requirements = requirements;
        this.compensation = compensation;
        this.officeAddress = officeAddress;
        this.workingConditions = workingConditions;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getVacancies() {
        return vacancies;
    }

    public void setVacancies(int vacancies) {
        this.vacancies = vacancies;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getCompensation() {
        return compensation;
    }

    public void setCompensation(String compensation) {
        this.compensation = compensation;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getWorkingConditions() {
        return workingConditions;
    }

    public void setWorkingConditions(String workingConditions) {
        this.workingConditions = workingConditions;
    } 
}
