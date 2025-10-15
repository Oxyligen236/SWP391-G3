/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hrms.model;

import java.sql.Time;
import java.time.LocalDate;


public class Attendance {
    private int attendanceID;
    private int userID;
    private LocalDate date;
    private String day;
    private Time checkin1;
    private Time checkout1;
    private Time checkin2;
    private Time checkout2;
    private Time checkin3;
    private Time checkout3;
    private int shiftID;
    private Time lateMinutes;
    private Time earlyLeaveMinutes;
    private Time totalWorkHours;
    private Time otHours;

    public Attendance() {
    }

    public Attendance(int attendanceID, int userID, LocalDate date, String day, Time checkin1, Time checkout1, Time checkin2, Time checkout2, Time checkin3, Time checkout3, int shiftID, Time lateMinutes, Time earlyLeaveMinutes, Time totalWorkHours, Time otHours) {
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

    public Time getCheckin1() {
        return checkin1;
    }

    public void setCheckin1(Time checkin1) {
        this.checkin1 = checkin1;
    }

    public Time getCheckout1() {
        return checkout1;
    }

    public void setCheckout1(Time checkout1) {
        this.checkout1 = checkout1;
    }

    public Time getCheckin2() {
        return checkin2;
    }

    public void setCheckin2(Time checkin2) {
        this.checkin2 = checkin2;
    }

    public Time getCheckout2() {
        return checkout2;
    }

    public void setCheckout2(Time checkout2) {
        this.checkout2 = checkout2;
    }

    public Time getCheckin3() {
        return checkin3;
    }

    public void setCheckin3(Time checkin3) {
        this.checkin3 = checkin3;
    }

    public Time getCheckout3() {
        return checkout3;
    }

    public void setCheckout3(Time checkout3) {
        this.checkout3 = checkout3;
    }

    public int getShiftID() {
        return shiftID;
    }

    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }

    public Time getLateMinutes() {
        return lateMinutes;
    }

    public void setLateMinutes(Time lateMinutes) {
        this.lateMinutes = lateMinutes;
    }

    public Time getEarlyLeaveMinutes() {
        return earlyLeaveMinutes;
    }

    public void setEarlyLeaveMinutes(Time earlyLeaveMinutes) {
        this.earlyLeaveMinutes = earlyLeaveMinutes;
    }

    public Time getTotalWorkHours() {
        return totalWorkHours;
    }

    public void setTotalWorkHours(Time totalWorkHours) {
        this.totalWorkHours = totalWorkHours;
    }

    public Time getOtHours() {
        return otHours;
    }

    public void setOtHours(Time otHours) {
        this.otHours = otHours;
    }
    
    
    
    
    
    
}
