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
