package hrms.dto;

import java.time.LocalDate;

public class TicketDTO {

    private int ticketID;
    private int userID;
    private String userFullName;
    private String departmentName;
    private int ticket_Type_ID;
    private String ticketTypeName;
    private String ticket_Content;
    private String status;
    private LocalDate create_Date;
    private Integer approverID;
    private String approverName;
    private LocalDate approve_Date;
    private String comment;

    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;

    private LocalDate overtimeDate;
    private String startTime;
    private String endTime;

    public TicketDTO() {
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    // Thêm getter và setter cho departmentName
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getTicket_Type_ID() {
        return ticket_Type_ID;
    }

    public void setTicket_Type_ID(int ticket_Type_ID) {
        this.ticket_Type_ID = ticket_Type_ID;
    }

    public String getTicketTypeName() {
        return ticketTypeName;
    }

    public void setTicketTypeName(String ticketTypeName) {
        this.ticketTypeName = ticketTypeName;
    }

    public String getTicket_Content() {
        return ticket_Content;
    }

    public void setTicket_Content(String ticket_Content) {
        this.ticket_Content = ticket_Content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getCreate_Date() {
        return create_Date;
    }

    public void setCreate_Date(LocalDate create_Date) {
        this.create_Date = create_Date;
    }

    public Integer getApproverID() {
        return approverID;
    }

    public void setApproverID(Integer approverID) {
        this.approverID = approverID;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public LocalDate getApprove_Date() {
        return approve_Date;
    }

    public void setApprove_Date(LocalDate approve_Date) {
        this.approve_Date = approve_Date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
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

    public LocalDate getOvertimeDate() {
        return overtimeDate;
    }

    public void setOvertimeDate(LocalDate overtimeDate) {
        this.overtimeDate = overtimeDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
