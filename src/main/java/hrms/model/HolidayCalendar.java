package hrms.model;

public class HolidayCalendar {

    private int calendarID;
    private int year;
    private String description;

    public HolidayCalendar() {
    }

    public HolidayCalendar(int calendarID, int year, String description) {
        this.calendarID = calendarID;
        this.year = year;
        this.description = description;
    }

    public int getCalendarID() {
        return calendarID;
    }

    public void setCalendarID(int calendarID) {
        this.calendarID = calendarID;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
