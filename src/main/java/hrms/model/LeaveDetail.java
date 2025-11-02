package hrms.model;

import java.time.LocalDate;

public class LeaveDetail {

    private int ticketID;
    private LocalDate start_Date;
    private LocalDate end_Date;
    private int leaveTypeID;

    public LeaveDetail() {
    }

    public LeaveDetail(int ticketID, LocalDate start_Date, LocalDate end_Date, int leaveTypeID) {
        this.ticketID = ticketID;
        this.start_Date = start_Date;
        this.end_Date = end_Date;
        this.leaveTypeID = leaveTypeID;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public LocalDate getStart_Date() {
        return start_Date;
    }

    public void setStart_Date(LocalDate start_Date) {
        this.start_Date = start_Date;
    }

    public LocalDate getEnd_Date() {
        return end_Date;
    }

    public void setEnd_Date(LocalDate end_Date) {
        this.end_Date = end_Date;
    }

    public int getLeaveTypeID() {
        return leaveTypeID;
    }

    public void setLeaveTypeID(int leaveTypeID) {
        this.leaveTypeID = leaveTypeID;
    }

}
