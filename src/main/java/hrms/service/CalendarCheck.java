package hrms.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import hrms.dao.HolidayCalendarDAO;
import hrms.dao.HolidayDAO;
import hrms.model.Holiday;
import hrms.model.HolidayCalendar;

public class CalendarCheck {

    public boolean checkHoliday(LocalDate date) {
        if (date == null) {
            return false;
        }

        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();

        HolidayDAO holidayDao = new HolidayDAO();
        HolidayCalendarDAO calendarDao = new HolidayCalendarDAO();
        HolidayCalendar calendar = calendarDao.getByYear(year);

        if (calendar == null) {
            return false;
        }

        List<Holiday> holidays = holidayDao.getHolidaysByCalendar(calendar.getCalendarID());

        for (Holiday holiday : holidays) {
            LocalDate holidayDate = holiday.getDateHoliday().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            if (holidayDate.getDayOfMonth() == day && holidayDate.getMonthValue() == month) {
                return true;
            }
        }

        return false;
    }

    public boolean checkWeekend(LocalDate date) {
        if (date == null) {
            return false;
        }

        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
    }

    public boolean checkHolidayOverlapWeekend(LocalDate date) {
        if (date == null) {
            return false;
        }
        if (!checkHoliday(date)) {
            return false;
        }
        return checkWeekend(date);
    }

    public boolean checkSubstituteHoliday(LocalDate date) {
        if (date == null) {
            return false;
        }

        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();

        HolidayDAO holidayDao = new HolidayDAO();
        HolidayCalendarDAO calendarDao = new HolidayCalendarDAO();
        HolidayCalendar calendar = calendarDao.getByYear(year);

        if (calendar == null) {
            return false;
        }

        List<Holiday> holidays = holidayDao.getHolidaysByCalendar(calendar.getCalendarID());

        for (Holiday holiday : holidays) {
            LocalDate holidayDate = holiday.getDateHoliday().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            if (holidayDate.getDayOfMonth() == day && holidayDate.getMonthValue() == month) {
                return holiday.isSubstitute();
            }
        }

        return false;
    }
}
