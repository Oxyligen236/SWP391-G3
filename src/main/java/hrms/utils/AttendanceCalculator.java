package hrms.utils;

import java.time.Duration;
import java.time.LocalTime;

public class AttendanceCalculator {

    public static LocalTime calculateLate(LocalTime actualCheckin, LocalTime shiftStart) {
        if (actualCheckin == null || shiftStart == null) return null;
        if (actualCheckin.isAfter(shiftStart)) {
            return LocalTime.ofSecondOfDay(Duration.between(shiftStart, actualCheckin).getSeconds());
        }
        return LocalTime.of(0, 0);
    }

    public static LocalTime calculateEarlyLeave(LocalTime actualCheckout, LocalTime shiftEnd) {
        if (actualCheckout == null || shiftEnd == null) return null;
        if (actualCheckout.isBefore(shiftEnd)) {
            return LocalTime.ofSecondOfDay(Duration.between(actualCheckout, shiftEnd).getSeconds());
        }
        return LocalTime.of(0, 0);
    }

    // =============================
    //  🔥 HÀM MỚI — Tính tổng Late ca 1 + ca 2
    // =============================
    public static LocalTime calculateLateFull(
            LocalTime checkin1, LocalTime checkin2,
            LocalTime shiftStart1, LocalTime shiftStart2) {

        long sec = 0;

        if (checkin1 != null && shiftStart1 != null && checkin1.isAfter(shiftStart1)) {
            sec += Duration.between(shiftStart1, checkin1).getSeconds();
        }
        if (checkin2 != null && shiftStart2 != null && checkin2.isAfter(shiftStart2)) {
            sec += Duration.between(shiftStart2, checkin2).getSeconds();
        }

        return LocalTime.ofSecondOfDay(sec);
    }

    // =============================
    //  🔥 HÀM MỚI — Tính tổng Early ca 1 + ca 2
    // =============================
    public static LocalTime calculateEarlyFull(
            LocalTime checkout1, LocalTime checkout2,
            LocalTime shiftEnd1, LocalTime shiftEnd2) {

        long sec = 0;

        if (checkout1 != null && shiftEnd1 != null && checkout1.isBefore(shiftEnd1)) {
            sec += Duration.between(checkout1, shiftEnd1).getSeconds();
        }
        if (checkout2 != null && shiftEnd2 != null && checkout2.isBefore(shiftEnd2)) {
            sec += Duration.between(checkout2, shiftEnd2).getSeconds();
        }

        return LocalTime.ofSecondOfDay(sec);
    }

    public static LocalTime calculateWorkHours(LocalTime checkin1, LocalTime checkout1,
                                               LocalTime checkin2, LocalTime checkout2,
                                               LocalTime checkin3, LocalTime checkout3) {
        long seconds = 0;
        if (checkin1 != null && checkout1 != null)
            seconds += Duration.between(checkin1, checkout1).getSeconds();
        if (checkin2 != null && checkout2 != null)
            seconds += Duration.between(checkin2, checkout2).getSeconds();
        if (checkin3 != null && checkout3 != null)
            seconds += Duration.between(checkin3, checkout3).getSeconds();
        return LocalTime.ofSecondOfDay(seconds);
    }

    public static LocalTime calculateOT(LocalTime totalWorkHours, LocalTime standardWorkHours) {
        if (totalWorkHours == null || standardWorkHours == null) return null;
        long diff = Duration.between(standardWorkHours, totalWorkHours).getSeconds();
        return diff > 0 ? LocalTime.ofSecondOfDay(diff) : LocalTime.of(0, 0);
    }
}
