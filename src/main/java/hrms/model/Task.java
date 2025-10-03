package hrms.model;

import java.sql.Date;
import java.time.LocalDate;
public class Task {
    private int taskId;
    private int taskTypeId;
    private String title;
    private String description;
    private String status;
    private String assignedBy;
    private Date startDate;
    private Date endDate;

    public Task() {
    }

    public Task(int taskId, int taskTypeId, String title, String description, String status, String assignedBy, Date startDate, Date endDate) {
        this.taskId = taskId;
        this.taskTypeId = taskTypeId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.assignedBy = assignedBy;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskTypeId() {
        return taskTypeId;
    }

    public void setTaskTypeId(int taskTypeId) {
        this.taskTypeId = taskTypeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
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
    

}
