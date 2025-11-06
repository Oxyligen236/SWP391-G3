package hrms.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class OTDetail {
    private int ticketID;
    private LocalDate ot_Date;
    private LocalTime start_Time;
    private LocalTime end_Time;

    public OTDetail() {
    }
    public OTDetail(int ticketID, LocalDate ot_Date, LocalTime start_Time, LocalTime end_Time) {
        this.ticketID = ticketID;
        this.ot_Date = ot_Date;
        this.start_Time = start_Time;
        this.end_Time = end_Time;
    }
    

     
    public double getTotalHours() {
        if (start_Time == null || end_Time == null) {
            return 0.0;
        }
        
        Duration duration = Duration.between(start_Time, end_Time);
        return duration.toMinutes() / 60.0; // Trả về số giờ (double)
    }
    
   
    public String getFormattedStartTime() {
        return start_Time != null ? start_Time.toString() : "";
    }
    
    public String getFormattedEndTime() {
        return end_Time != null ? end_Time.toString() : "";
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public LocalDate getOt_Date() {
        return ot_Date;
    }

    public void setOt_Date(LocalDate ot_Date) {
        this.ot_Date = ot_Date;
    }

    public LocalTime getStart_Time() {
        return start_Time;
    }

    public void setStart_Time(LocalTime start_Time) {
        this.start_Time = start_Time;
    }

    public LocalTime getEnd_Time() {
        return end_Time;
    }

    public void setEnd_Time(LocalTime end_Time) {
        this.end_Time = end_Time;
    }

}    