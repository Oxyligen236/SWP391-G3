package hrms.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance {
    private int attendanceID;
    private int userID;
    private LocalDate date;
    private String day;
    private LocalTime checkin1;
    private LocalTime checkout1;
    private LocalTime checkin2;
    private LocalTime checkout2;
    private LocalTime checkin3;
    private LocalTime checkout3;
    private int shiftID;
    private LocalTime lateMinutes;
    private LocalTime earlyLeaveMinutes;
    private LocalTime totalWorkHours;
    private LocalTime otHours;

    public Attendance() {
    }

    public Attendance(int attendanceID, int userID, LocalDate date, String day,
                      LocalTime checkin1, LocalTime checkout1,
                      LocalTime checkin2, LocalTime checkout2,
                      LocalTime checkin3, LocalTime checkout3,
                      int shiftID, LocalTime lateMinutes, LocalTime earlyLeaveMinutes,
                      LocalTime totalWorkHours, LocalTime otHours) {
        this.attendanceID = attendanceID;
        this.userID = userID;
        this.date = date;
        this.day = day;
        this.checkin1 = checkin1;
        this.checkout1 = checkout1;
        this.checkin2 = checkin2;
        this.checkout2 = checkout2;
        this.checkin3 = checkin3;
        this.checkout3 = checkout3;
        this.shiftID = shiftID;
        this.lateMinutes = lateMinutes;
        this.earlyLeaveMinutes = earlyLeaveMinutes;
        this.totalWorkHours = totalWorkHours;
        this.otHours = otHours;
    }

    public int getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(int attendanceID) {
        this.attendanceID = attendanceID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public LocalTime getCheckin1() {
        return checkin1;
    }

    public void setCheckin1(LocalTime checkin1) {
        this.checkin1 = checkin1;
    }

    public LocalTime getCheckout1() {
        return checkout1;
    }

    public void setCheckout1(LocalTime checkout1) {
        this.checkout1 = checkout1;
    }

    public LocalTime getCheckin2() {
        return checkin2;
    }

    public void setCheckin2(LocalTime checkin2) {
        this.checkin2 = checkin2;
    }

    public LocalTime getCheckout2() {
        return checkout2;
    }

    public void setCheckout2(LocalTime checkout2) {
        this.checkout2 = checkout2;
    }

    public LocalTime getCheckin3() {
        return checkin3;
    }

    public void setCheckin3(LocalTime checkin3) {
        this.checkin3 = checkin3;
    }

    public LocalTime getCheckout3() {
        return checkout3;
    }

    public void setCheckout3(LocalTime checkout3) {
        this.checkout3 = checkout3;
    }

    public int getShiftID() {
        return shiftID;
    }

    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }

    public LocalTime getLateMinutes() {
        return lateMinutes;
    }

    public void setLateMinutes(LocalTime lateMinutes) {
        this.lateMinutes = lateMinutes;
    }

    public LocalTime getEarlyLeaveMinutes() {
        return earlyLeaveMinutes;
    }

    public void setEarlyLeaveMinutes(LocalTime earlyLeaveMinutes) {
        this.earlyLeaveMinutes = earlyLeaveMinutes;
    }

    public LocalTime getTotalWorkHours() {
        return totalWorkHours;
    }

    public void setTotalWorkHours(LocalTime totalWorkHours) {
        this.totalWorkHours = totalWorkHours;
    }

    public LocalTime getOtHours() {
        return otHours;
    }

    public void setOtHours(LocalTime otHours) {
        this.otHours = otHours;
    }
}
