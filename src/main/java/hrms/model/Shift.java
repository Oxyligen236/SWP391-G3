package hrms.model;

import java.time.LocalTime;

public class Shift {

    private int shiftID;
    private String shiftName;
    private LocalTime checkIn1;
    private LocalTime checkOut1;
    private LocalTime checkIn2;
    private LocalTime checkOut2;
    private LocalTime checkIn3;

    public Shift() {
    }

    public Shift(int shiftID, String shiftName, LocalTime checkIn1, LocalTime checkOut1, LocalTime checkIn2,
            LocalTime checkOut2, LocalTime checkIn3) {
        this.shiftID = shiftID;
        this.shiftName = shiftName;
        this.checkIn1 = checkIn1;
        this.checkOut1 = checkOut1;
        this.checkIn2 = checkIn2;
        this.checkOut2 = checkOut2;
        this.checkIn3 = checkIn3;
    }

    public int getShiftID() {
        return shiftID;
    }

    public void setShiftID(int shiftID) {
        this.shiftID = shiftID;
    }

    public String getShiftName() {
        return shiftName;
    }

    public void setShiftName(String shiftName) {
        this.shiftName = shiftName;
    }

    public LocalTime getCheckIn1() {
        return checkIn1;
    }

    public void setCheckIn1(LocalTime checkIn1) {
        this.checkIn1 = checkIn1;
    }

    public LocalTime getCheckOut1() {
        return checkOut1;
    }

    public void setCheckOut1(LocalTime checkOut1) {
        this.checkOut1 = checkOut1;
    }

    public LocalTime getCheckIn2() {
        return checkIn2;
    }

    public void setCheckIn2(LocalTime checkIn2) {
        this.checkIn2 = checkIn2;
    }

    public LocalTime getCheckOut2() {
        return checkOut2;
    }

    public void setCheckOut2(LocalTime checkOut2) {
        this.checkOut2 = checkOut2;
    }

    public LocalTime getCheckIn3() {
        return checkIn3;
    }

    public void setCheckIn3(LocalTime checkIn3) {
        this.checkIn3 = checkIn3;
    }

}
