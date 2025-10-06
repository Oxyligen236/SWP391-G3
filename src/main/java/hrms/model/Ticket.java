package hrms.model;

import java.util.Date;

public class Ticket {

    private int ticketID;
    private int userID;
    private int ticket_Type_ID;
    private Date create_Date;
    private String ticket_Content;
    private int approveID;
    private Date approve_Date;
    private String comment;
    private String status;

    //constructor
    public Ticket() {
    }

    public Ticket(int ticketID, int userID, int ticket_Type_ID, Date create_Date, String ticket_Content, int approveID,
            Date approve_Date, String comment, String status) {
        this.ticketID = ticketID;
        this.userID = userID;
        this.ticket_Type_ID = ticket_Type_ID;
        this.create_Date = create_Date;
        this.ticket_Content = ticket_Content;
        this.approveID = approveID;
        this.approve_Date = approve_Date;
        this.comment = comment;
        this.status = status;
    }
    //getter and setter

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

    public Date getCreate_Date() {
        return create_Date;
    }

    public void setCreate_Date(Date create_Date) {
        this.create_Date = create_Date;
    }

    public String getTicket_Content() {
        return ticket_Content;
    }

    public void setTicket_Content(String ticket_Content) {
        this.ticket_Content = ticket_Content;
    }

    public int getApproveID() {
        return approveID;
    }

    public void setApproveID(int approveID) {
        this.approveID = approveID;
    }

    public Date getApprove_Date() {
        return approve_Date;
    }

    public void setApprove_Date(Date approve_Date) {
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
