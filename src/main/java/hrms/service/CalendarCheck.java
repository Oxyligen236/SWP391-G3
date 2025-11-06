package hrms.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hrms.dao.HolidayCalendarDAO;
import hrms.dao.HolidayDAO;
import hrms.model.Holiday;
import hrms.model.HolidayCalendar;

public class CalendarCheck {

    public boolean checkHoliday(Date date) {
        if (date == null) {
            return false;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        HolidayDAO holidayDao = new HolidayDAO();
        HolidayCalendarDAO calendarDao = new HolidayCalendarDAO();
        HolidayCalendar calendar = calendarDao.getByYear(year);

        if (calendar == null) {
            return false;
        }

        List<Holiday> holidays = holidayDao.getHolidaysByCalendar(calendar.getCalendarID());

        for (Holiday holiday : holidays) {
            Calendar holidayCal = Calendar.getInstance();
            holidayCal.setTime(holiday.getDateHoliday());
            int holidayDay = holidayCal.get(Calendar.DAY_OF_MONTH);
            int holidayMonth = holidayCal.get(Calendar.MONTH) + 1;

            if (holidayDay == day && holidayMonth == month) {
                return true;
            }
        }

        return false;
    }

    public boolean checkWeekend(Date date) {
        if (date == null) {
            return false;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
    }

    public boolean checkHolidayOverlapWeekend(Date date) {
        if (date == null) {
            return false;
        }
        if (!checkHoliday(date)) {
            return false;
        }
        return checkWeekend(date);
    }

    public boolean checkSubstituteHoliday(Date date) {
        if (date == null) {
            return false;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);

        HolidayDAO holidayDao = new HolidayDAO();
        HolidayCalendarDAO calendarDao = new HolidayCalendarDAO();
        HolidayCalendar calendar = calendarDao.getByYear(year);

        if (calendar == null) {
            return false;
        }

        List<Holiday> holidays = holidayDao.getHolidaysByCalendar(calendar.getCalendarID());

        for (Holiday holiday : holidays) {
            Calendar holidayCal = Calendar.getInstance();
            holidayCal.setTime(holiday.getDateHoliday());
            int holidayDay = holidayCal.get(Calendar.DAY_OF_MONTH);
            int holidayMonth = holidayCal.get(Calendar.MONTH) + 1;

            if (holidayDay == day && holidayMonth == month) {
                return holiday.isSubstitute();
            }
        }

        return false;
    }
}
