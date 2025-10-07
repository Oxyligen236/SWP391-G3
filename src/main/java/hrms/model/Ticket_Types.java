package hrms.model;

public class Ticket_Types {
    private int ticket_Type_ID;
    private String name;

    public Ticket_Types() {
    }
    public Ticket_Types(int ticket_Type_ID, String name) {
        this.ticket_Type_ID = ticket_Type_ID;
        this.name = name;
    }
    public int getTicket_Type_ID() {
        return ticket_Type_ID;
    }
    public void setTicket_Type_ID(int ticket_Type_ID) {
        this.ticket_Type_ID = ticket_Type_ID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    
    
}
