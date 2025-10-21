package hrms.dto;

import java.sql.Time;
import java.time.LocalDate;

public class AttendanceDTO {

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

    private String shiftName;
    private Time shiftCheckin1;
    private Time shiftCheckout1;
    private Time shiftCheckin2;
    private Time shiftCheckout2;

    private String userName;

    private String departmentName;

    private String positionName;

    public AttendanceDTO() {
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

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public Time getShiftCheckin1() {
        return shiftCheckin1;
    }

    public void setShiftCheckin1(Time shiftCheckin1) {
        this.shiftCheckin1 = shiftCheckin1;
    }

    public Time getShiftCheckout1() {
        return shiftCheckout1;
    }

    public void setShiftCheckout1(Time shiftCheckout1) {
        this.shiftCheckout1 = shiftCheckout1;
    }

    public Time getShiftCheckin2() {
        return shiftCheckin2;
    }

    public void setShiftCheckin2(Time shiftCheckin2) {
        this.shiftCheckin2 = shiftCheckin2;
    }

    public Time getShiftCheckout2() {
        return shiftCheckout2;
    }

    public void setShiftCheckout2(Time shiftCheckout2) {
        this.shiftCheckout2 = shiftCheckout2;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
