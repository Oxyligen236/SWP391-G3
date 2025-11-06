package hrms.model;

import java.time.LocalDate;

public class WorkHistory {

    private int historyID;
    private int userID;
    private String type;
    private LocalDate effectiveDate;
    private String oldValue;
    private String newValue;
    private String description;
    private String evaluate;

    public WorkHistory() {
    }

    public WorkHistory(int historyID, int userID, String type, LocalDate effectiveDate, String oldValue, String newValue, String description, String evaluate) {
        this.historyID = historyID;
        this.userID = userID;
        this.type = type;
        this.effectiveDate = effectiveDate;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.description = description;
        this.evaluate = evaluate;
    }
    
    

    public int getHistoryID() {
        return historyID;
    }

    public void setHistoryID(int historyID) {
        this.historyID = historyID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }
    
    
    
    
    
}
