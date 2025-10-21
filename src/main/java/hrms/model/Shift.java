package hrms.model;

import java.time.LocalTime;

public class Shift {
    private int shiftID;
    private String name;
    private LocalTime checkin1;
    private LocalTime checkout1;
    private LocalTime checkin2;
    private LocalTime checkout2;

    public Shift() {
    }

    public Shift(int shiftID, String name, LocalTime checkin1, LocalTime checkout1, LocalTime checkin2, LocalTime checkout2) {
        this.shiftID = shiftID;
        this.name = name;
        this.checkin1 = checkin1;
        this.checkout1 = checkout1;
        this.checkin2 = checkin2;
        this.checkout2 = checkout2;
    }

    public int getShiftID() {
        return shiftID;
    }

    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
