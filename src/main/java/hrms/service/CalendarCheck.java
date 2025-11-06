// package hrms.service;

// import java.time.DayOfWeek;
// import java.time.LocalDate;
// import java.util.List;

// import hrms.dao.HolidayCalendarDAO;
// import hrms.dao.HolidayDAO;
// import hrms.dao.PayrollPolicyDAO;
// import hrms.model.Holiday;
// import hrms.model.HolidayCalendar;
// import hrms.model.PayrollPolicy;

// public class CalendarCheck {

//     private HolidayDAO holidayDao;
//     private HolidayCalendarDAO calendarDao;
//     private PayrollPolicyDAO policyDAO;

//     public CalendarCheck() {
//         this.holidayDao = new HolidayDAO();
//         this.calendarDao = new HolidayCalendarDAO();
//         this.policyDAO = new PayrollPolicyDAO();
//     }

//     public boolean checkHoliday(LocalDate date) {
//         if (date == null) {
//             return false;
//         }

//         int day = date.getDayOfMonth();
//         int month = date.getMonthValue();
//         int year = date.getYear();

//         HolidayCalendar calendar = calendarDao.getByYear(year);

//         if (calendar == null) {
//             return false;
//         }

//         List<Holiday> holidays = holidayDao.getHolidaysByCalendar(calendar.getCalendarID());

//         for (Holiday holiday : holidays) {
//             LocalDate holidayDate = holiday.getDateHoliday();
//             int holidayDay = holidayDate.getDayOfMonth();
//             int holidayMonth = holidayDate.getMonthValue();

//             if (holidayDay == day && holidayMonth == month) {
//                 return true;
//             }
//         }

//         return false;
//     }

//     public boolean checkWeekend(LocalDate date) {
//         if (date == null) {
//             return false;
//         }

//         DayOfWeek dayOfWeek = date.getDayOfWeek();

//         return (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
//     }

//     public boolean checkHolidayOverlapWeekend(LocalDate date) {
//         if (date == null) {
//             return false;
//         }
//         if (!checkHoliday(date)) {
//             return false;
//         }
//         return checkWeekend(date);
//     }

//     public boolean checkSubstituteHoliday(LocalDate date) {
//         if (date == null) {
//             return false;
//         }

//         int day = date.getDayOfMonth();
//         int month = date.getMonthValue();
//         int year = date.getYear();

//         HolidayCalendar calendar = calendarDao.getByYear(year);

//         if (calendar == null) {
//             return false;
//         }

//         List<Holiday> holidays = holidayDao.getHolidaysByCalendar(calendar.getCalendarID());

//         for (Holiday holiday : holidays) {
//             LocalDate holidayDate = holiday.getDateHoliday();
//             int holidayDay = holidayDate.getDayOfMonth();
//             int holidayMonth = holidayDate.getMonthValue();

//             if (holidayDay == day && holidayMonth == month) {
//                 return holiday.isSubstitute();
//             }
//         }

//         return false;
//     }

//     public String getDayType(LocalDate date) {
//         if (date == null) {
//             return "Weekday";
//         }

//         if (checkHoliday(date) || checkSubstituteHoliday(date)) {
//             return "Holiday";
//         }

//         if (checkWeekend(date)) {
//             return "Weekend";
//         }

//         return "Weekday";
//     }

//     public double getOTSalaryPercentage(String dayType) {
//         String policyName = "";

//         if ("Holiday".equals(dayType)) {
//             policyName = "ot_multiplier_holiday";
//         } else if ("Weekend".equals(dayType)) {
//             policyName = "ot_multiplier_weekend";
//         } else {
//             policyName = "ot_multiplier_weekday";
//         }

//         PayrollPolicy policy = policyDAO.getByName(policyName);
//         if (policy != null) {
//             return policy.getPolicyValue();
//         }

//         return 150.0;
//     }

// }
