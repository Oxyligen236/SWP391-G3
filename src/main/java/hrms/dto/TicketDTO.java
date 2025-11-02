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

    private int leaveTypeID;
    private String leaveTypeName;
    private LocalDate startDate;
    private LocalDate endDate;

    private LocalDate overtimeDate;
    private String startTime;
    private String endTime;

    public TicketDTO() {
    }

    public TicketDTO(int ticketID, int userID, String userFullName, String departmentName, int ticket_Type_ID,
            String ticketTypeName, String ticket_Content, String status, LocalDate create_Date, Integer approverID,
            String approverName, LocalDate approve_Date, String comment, int leaveTypeID, String leaveTypeName,
            LocalDate startDate, LocalDate endDate, LocalDate overtimeDate, String startTime, String endTime) {
        this.ticketID = ticketID;
        this.userID = userID;
        this.userFullName = userFullName;
        this.departmentName = departmentName;
        this.ticket_Type_ID = ticket_Type_ID;
        this.ticketTypeName = ticketTypeName;
        this.ticket_Content = ticket_Content;
        this.status = status;
        this.create_Date = create_Date;
        this.approverID = approverID;
        this.approverName = approverName;
        this.approve_Date = approve_Date;
        this.comment = comment;
        this.leaveTypeID = leaveTypeID;
        this.leaveTypeName = leaveTypeName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.overtimeDate = overtimeDate;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public int getLeaveTypeID() {
        return leaveTypeID;
    }

    public void setLeaveTypeID(int leaveTypeID) {
        this.leaveTypeID = leaveTypeID;
    }

    public String getLeaveTypeName() {
        return leaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
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
