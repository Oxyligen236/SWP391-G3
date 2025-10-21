package hrms.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceDTO {

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

    private String shiftName;
    private LocalTime shiftCheckin1;
    private LocalTime shiftCheckout1;
    private LocalTime shiftCheckin2;
    private LocalTime shiftCheckout2;

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

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public LocalTime getShiftCheckin1() {
        return shiftCheckin1;
    }

    public void setShiftCheckin1(LocalTime shiftCheckin1) {
        this.shiftCheckin1 = shiftCheckin1;
    }

    public LocalTime getShiftCheckout1() {
        return shiftCheckout1;
    }

    public void setShiftCheckout1(LocalTime shiftCheckout1) {
        this.shiftCheckout1 = shiftCheckout1;
    }

    public LocalTime getShiftCheckin2() {
        return shiftCheckin2;
    }

    public void setShiftCheckin2(LocalTime shiftCheckin2) {
        this.shiftCheckin2 = shiftCheckin2;
    }

    public LocalTime getShiftCheckout2() {
        return shiftCheckout2;
    }

    public void setShiftCheckout2(LocalTime shiftCheckout2) {
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