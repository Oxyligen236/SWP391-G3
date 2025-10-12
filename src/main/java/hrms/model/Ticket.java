package hrms.model;

import java.time.LocalDate;

public class Ticket {

    private int ticketID;
    private int userID;
    private int ticket_Type_ID;
    private LocalDate create_Date;
    private String ticket_Content;
    private int approverID;
    private LocalDate approve_Date;
    private String comment;
    private String status;

    public Ticket() {
    }
    public Ticket(int ticketID, int userID, int ticket_Type_ID, LocalDate create_Date, String ticket_Content,
            int approverID, LocalDate approve_Date, String comment, String status) {
        this.ticketID = ticketID;
        this.userID = userID;
        this.ticket_Type_ID = ticket_Type_ID;
        this.create_Date = create_Date;
        this.ticket_Content = ticket_Content;
        this.approverID = approverID;
        this.approve_Date = approve_Date;
        this.comment = comment;
        this.status = status;
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
    public int getTicket_Type_ID() {
        return ticket_Type_ID;
    }
    public void setTicket_Type_ID(int ticket_Type_ID) {
        this.ticket_Type_ID = ticket_Type_ID;
    }
    public LocalDate getCreate_Date() {
        return create_Date;
    }
    public void setCreate_Date(LocalDate create_Date) {
        this.create_Date = create_Date;
    }
    public String getTicket_Content() {
        return ticket_Content;
    }
    public void setTicket_Content(String ticket_Content) {
        this.ticket_Content = ticket_Content;
    }
    public int getApproverID() {
        return approverID;
    }
    public void setApproverID(int approverID) {
        this.approverID = approverID;
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
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

   


    

}
