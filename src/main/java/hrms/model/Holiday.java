package hrms.model;

import java.sql.Date;

public class Holiday {

    private int holidayID;
    private int calendarID;
    private Date dateHoliday;
    private String name;
    private boolean isSubstitute;
    private String description;

    public Holiday() {
    }

    public Holiday(int holidayID, int calendarID, Date dateHoliday, String name, boolean isSubstitute, String description) {
        this.holidayID = holidayID;
        this.calendarID = calendarID;
        this.dateHoliday = dateHoliday;
        this.name = name;
        this.isSubstitute = isSubstitute;
        this.description = description;
    }

    // Getters & setters
    public int getHolidayID() {
        return holidayID;
    }

    public void setHolidayID(int holidayID) {
        this.holidayID = holidayID;
    }

    public int getCalendarID() {
        return calendarID;
    }

    public void setCalendarID(int calendarID) {
        this.calendarID = calendarID;
    }

    public Date getDateHoliday() {
        return dateHoliday;
    }

    public void setDateHoliday(Date dateHoliday) {
        this.dateHoliday = dateHoliday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSubstitute() {
        return isSubstitute;
    }

    public void setSubstitute(boolean substitute) {
        isSubstitute = substitute;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
