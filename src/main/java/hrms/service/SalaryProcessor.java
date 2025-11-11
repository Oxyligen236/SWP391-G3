package hrms.service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hrms.dao.AttendanceDAO;
import hrms.dao.LeaveDetailDAO;
import hrms.dao.OTDetailDAO;
import hrms.dao.PayrollPolicyDAO;
import hrms.dao.TicketDAO;
import hrms.dto.PayrollItemDetailDTO;
import hrms.model.Attendance;
import hrms.model.LeaveDetail;
import hrms.model.OTDetail;
import hrms.model.Payroll;
import hrms.model.PayrollPolicy;
import hrms.model.Ticket;

public class SalaryProcessor {

    private final AttendanceDAO attendanceDAO;
    private final OTDetailDAO otDetailDAO;
    private final LeaveDetailDAO leaveDetailDAO;
    private final TicketDAO ticketDAO;
    private final PayrollPolicyDAO policyDAO;
    private final PayrollService payrollService;
    private final CalendarCheck calendarCheck;

    public SalaryProcessor() {
        this.attendanceDAO = new AttendanceDAO();
        this.otDetailDAO = new OTDetailDAO();
        this.leaveDetailDAO = new LeaveDetailDAO();
        this.ticketDAO = new TicketDAO();
        this.policyDAO = new PayrollPolicyDAO();
        this.payrollService = new PayrollService();
        this.calendarCheck = new CalendarCheck();
    }

    public LocalDate getPayrollStartDate(int month, int year) {
        if (month == 1) {
            return LocalDate.of(year - 1, 12, 26);
        }
        return LocalDate.of(year, month - 1, 26);
    }

    public LocalDate getPayrollEndDate(int month, int year) {
        return LocalDate.of(year, month, 25);
    }

    public int calculateStandardWorkDays(int month, int year) {
        LocalDate startDate = getPayrollStartDate(month, year);
        LocalDate endDate = getPayrollEndDate(month, year);

        int workDays = 0;
        LocalDate current = startDate;

        while (!current.isAfter(endDate)) {
            if (!calendarCheck.checkWeekend(current) && !calendarCheck.checkHoliday(current)) {
                workDays++;
            }
            current = current.plusDays(1);
        }

        return workDays;
    }

    public double calculateStandardWorkHours(int month, int year) {
        PayrollPolicy hoursPerDay = policyDAO.getByName("max_hours_per_day");
        double standardHoursPerDay = hoursPerDay != null ? hoursPerDay.getPolicyValue() : 10.0;

        int workDays = calculateStandardWorkDays(month, year);
        return workDays * standardHoursPerDay;
    }

    public double calculateHourlyRate(double baseSalary, int month, int year) {
        double standardWorkHours = calculateStandardWorkHours(month, year);
        return baseSalary / standardWorkHours;
    }

    public double convertTimeToHours(LocalTime time) {
        if (time == null) {
            return 0.0;
        }
        return time.getHour() + (time.getMinute() / 60.0);
    }

    public double calculateLateEarlyPenalty(int userId, int month, int year) {
        LocalDate startDate = getPayrollStartDate(month, year);
        LocalDate endDate = getPayrollEndDate(month, year);

        List<Attendance> attendances = attendanceDAO.getByUser(userId);

        PayrollPolicy gracePeriodPolicy = policyDAO.getByName("grace_period_minutes");
        double gracePeriodMinutes = gracePeriodPolicy != null ? gracePeriodPolicy.getPolicyValue() : 5.0;

        double totalPenaltyHours = 0.0;

        for (Attendance att : attendances) {
            if (att.getDate() != null
                    && !att.getDate().isBefore(startDate)
                    && !att.getDate().isAfter(endDate)) {

                LocalTime lateTime = att.getLateMinutes();
                LocalTime earlyTime = att.getEarlyLeaveMinutes();

                double lateMinutes = lateTime != null
                        ? (lateTime.getHour() * 60 + lateTime.getMinute()) : 0.0;
                double earlyMinutes = earlyTime != null
                        ? (earlyTime.getHour() * 60 + earlyTime.getMinute()) : 0.0;

                if (lateMinutes > gracePeriodMinutes) {
                    totalPenaltyHours += (lateMinutes - gracePeriodMinutes) / 60.0;
                }

                if (earlyMinutes > gracePeriodMinutes) {
                    totalPenaltyHours += (earlyMinutes - gracePeriodMinutes) / 60.0;
                }
            }
        }

        return totalPenaltyHours;
    }

    public double calculateActualWorkHours(int userId, int month, int year) {
        LocalDate startDate = getPayrollStartDate(month, year);
        LocalDate endDate = getPayrollEndDate(month, year);

        List<Attendance> attendances = attendanceDAO.getByUser(userId);
        double totalHours = 0.0;

        for (Attendance att : attendances) {
            if (att.getDate() != null
                    && !att.getDate().isBefore(startDate)
                    && !att.getDate().isAfter(endDate)) {

                if (att.getTotalWorkHours() != null) {
                    totalHours += convertTimeToHours(att.getTotalWorkHours());
                }
            }
        }

        return totalHours;
    }

    public Map<String, Double> calculateOTHours(int userId, int month, int year) {
        LocalDate startDate = getPayrollStartDate(month, year);
        LocalDate endDate = getPayrollEndDate(month, year);

        List<OTDetail> otDetails = otDetailDAO.getByUserId(userId);
        List<Ticket> tickets = ticketDAO.getTicketsByUserId(userId);

        Map<Integer, String> ticketStatusMap = new HashMap<>();
        for (Ticket ticket : tickets) {
            ticketStatusMap.put(ticket.getTicketID(), ticket.getStatus());
        }

        double weekdayOT = 0.0;
        double weekendOT = 0.0;
        double holidayOT = 0.0;

        for (OTDetail ot : otDetails) {
            String status = ticketStatusMap.get(ot.getTicketID());
            if (!"Approved".equals(status)) {
                continue;
            }

            LocalDate otDate = ot.getOt_Date();
            if (otDate != null && !otDate.isBefore(startDate) && !otDate.isAfter(endDate)) {
                LocalTime startTime = ot.getStart_Time();
                LocalTime endTime = ot.getEnd_Time();

                double hours = Duration.between(startTime, endTime).toMinutes() / 60.0;

                if (calendarCheck.checkHoliday(otDate)) {
                    holidayOT += hours;
                } else if (calendarCheck.checkWeekend(otDate)) {
                    weekendOT += hours;
                } else {
                    weekdayOT += hours;
                }
            }
        }

        PayrollPolicy maxOTPolicy = policyDAO.getByName("max_overtime_hours_per_month");
        double maxOT = maxOTPolicy != null ? maxOTPolicy.getPolicyValue() : 40.0;
        double totalOT = weekdayOT + weekendOT + holidayOT;

        if (totalOT > maxOT) {
            double ratio = maxOT / totalOT;
            weekdayOT *= ratio;
            weekendOT *= ratio;
            holidayOT *= ratio;
        }

        Map<String, Double> result = new HashMap<>();
        result.put("weekday", weekdayOT);
        result.put("weekend", weekendOT);
        result.put("holiday", holidayOT);
        result.put("total", weekdayOT + weekendOT + holidayOT);

        return result;
    }

    public double calculateOTSalary(double baseSalary, Map<String, Double> otHours, int month, int year) {
        double hourlyRate = calculateHourlyRate(baseSalary, month, year);

        PayrollPolicy weekdayPolicy = policyDAO.getByName("ot_multiplier_weekday");
        PayrollPolicy weekendPolicy = policyDAO.getByName("ot_multiplier_weekend");
        PayrollPolicy holidayPolicy = policyDAO.getByName("ot_multiplier_holiday");

        double weekdayMultiplier = weekdayPolicy != null ? weekdayPolicy.getPolicyValue() / 100.0 : 1.5;
        double weekendMultiplier = weekendPolicy != null ? weekendPolicy.getPolicyValue() / 100.0 : 2.0;
        double holidayMultiplier = holidayPolicy != null ? holidayPolicy.getPolicyValue() / 100.0 : 3.0;

        double otSalary = 0.0;
        otSalary += otHours.get("weekday") * hourlyRate * weekdayMultiplier;
        otSalary += otHours.get("weekend") * hourlyRate * weekendMultiplier;
        otSalary += otHours.get("holiday") * hourlyRate * holidayMultiplier;

        return otSalary;
    }

    public int calculateUnpaidLeaveDays(int userId, int month, int year) {
        LocalDate startDate = getPayrollStartDate(month, year);
        LocalDate endDate = getPayrollEndDate(month, year);

        List<LeaveDetail> leaveDetails = leaveDetailDAO.getByUserId(userId);
        List<Ticket> tickets = ticketDAO.getTicketsByUserId(userId);

        Map<Integer, Ticket> ticketMap = new HashMap<>();
        for (Ticket ticket : tickets) {
            ticketMap.put(ticket.getTicketID(), ticket);
        }

        int unpaidDays = 0;

        for (LeaveDetail leave : leaveDetails) {
            Ticket ticket = ticketMap.get(leave.getTicketID());
            if (ticket == null || !"Approved".equals(ticket.getStatus())) {
                continue;
            }

            if (leave.getLeaveTypeID() != 2) {
                continue;
            }

            LocalDate leaveStart = leave.getStart_Date();
            LocalDate leaveEnd = leave.getEnd_Date();

            LocalDate overlapStart = leaveStart.isBefore(startDate) ? startDate : leaveStart;
            LocalDate overlapEnd = leaveEnd.isAfter(endDate) ? endDate : leaveEnd;

            if (!overlapStart.isAfter(overlapEnd)) {
                LocalDate current = overlapStart;
                while (!current.isAfter(overlapEnd)) {
                    if (!calendarCheck.checkWeekend(current) && !calendarCheck.checkHoliday(current)) {
                        unpaidDays++;
                    }
                    current = current.plusDays(1);
                }
            }
        }

        return unpaidDays;
    }

    public double calculateUnpaidLeaveDeduction(double baseSalary, int unpaidDays, int month, int year) {
        PayrollPolicy hoursPerDay = policyDAO.getByName("max_hours_per_day");
        double standardHoursPerDay = hoursPerDay != null ? hoursPerDay.getPolicyValue() : 8.0;

        double hourlyRate = calculateHourlyRate(baseSalary, month, year);
        return unpaidDays * standardHoursPerDay * hourlyRate;
    }

    public Map<String, Object> calculatePayrollItems(int payrollId, double baseSalary, int month, int year) {
        List<PayrollItemDetailDTO> items = payrollService.getPayrollItemDetails(payrollId);

        double totalAllowances = 0.0;
        double totalBonuses = 0.0;
        double totalDeductions = 0.0;
        double totalInsurances = 0.0;

        for (PayrollItemDetailDTO item : items) {
            double amount = item.getAmount();

            if ("Percentage".equals(item.getAmountType())) {
                amount = baseSalary * (amount / 100.0);
            }

            String category = item.getCategory();
            boolean isPositive = item.isPositive();

            if (isPositive) {
                if ("Allowance".equals(category)) {
                    totalAllowances += amount;
                } else if ("Bonus".equals(category)) {
                    totalBonuses += amount;
                }
            } else {
                if ("Insurance".equals(category)) {
                    totalInsurances += amount;
                } else if ("Deduction".equals(category)) {
                    totalDeductions += amount;
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("allowances", totalAllowances);
        result.put("bonuses", totalBonuses);
        result.put("deductions", totalDeductions);
        result.put("insurances", totalInsurances);
        result.put("totalPositive", totalAllowances + totalBonuses);
        result.put("totalNegative", totalDeductions + totalInsurances);
        result.put("netAdjustment", (totalAllowances + totalBonuses) - (totalDeductions + totalInsurances));

        return result;
    }

    public Map<String, Object> calculateSalaryDetails(int userId, int payrollId, double baseSalary, int month, int year) {
        Map<String, Object> result = new HashMap<>();

        double standardWorkHours = calculateStandardWorkHours(month, year);
        double actualWorkHours = calculateActualWorkHours(userId, month, year);
        double hourlyRate = calculateHourlyRate(baseSalary, month, year);

        Map<String, Double> otHours = calculateOTHours(userId, month, year);
        double otSalary = calculateOTSalary(baseSalary, otHours, month, year);

        int unpaidDays = calculateUnpaidLeaveDays(userId, month, year);
        double unpaidDeduction = calculateUnpaidLeaveDeduction(baseSalary, unpaidDays, month, year);

        double lateEarlyPenaltyHours = calculateLateEarlyPenalty(userId, month, year);
        double lateEarlyDeduction = lateEarlyPenaltyHours * hourlyRate;

        double actualSalary = actualWorkHours * hourlyRate;

        Map<String, Object> payrollItems = calculatePayrollItems(payrollId, baseSalary, month, year);
        double payrollItemsAdjustment = (double) payrollItems.get("netAdjustment");

        double totalSalary = actualSalary + otSalary - unpaidDeduction - lateEarlyDeduction + payrollItemsAdjustment;

        result.put("standardWorkHours", standardWorkHours);
        result.put("actualWorkHours", actualWorkHours);
        result.put("hourlyRate", hourlyRate);
        result.put("baseSalary", baseSalary);
        result.put("actualSalary", actualSalary);
        result.put("otHours", otHours);
        result.put("otSalary", otSalary);
        result.put("unpaidDays", unpaidDays);
        result.put("unpaidDeduction", unpaidDeduction);
        result.put("lateEarlyPenaltyHours", lateEarlyPenaltyHours);
        result.put("lateEarlyDeduction", lateEarlyDeduction);
        result.put("payrollItems", payrollItems);
        result.put("totalSalary", totalSalary);

        return result;
    }

    public Payroll calculatePayroll(int userId, int payrollId, double baseSalary, int month, int year) {
        Map<String, Object> details = calculateSalaryDetails(userId, payrollId, baseSalary, month, year);

        double actualWorkHours = (double) details.get("actualWorkHours");
        double totalSalary = (double) details.get("totalSalary");

        long hours = (long) actualWorkHours;
        long minutes = (long) ((actualWorkHours - hours) * 60);
        Duration workingHours = Duration.ofHours(hours).plusMinutes(minutes);

        Payroll payroll = new Payroll();
        payroll.setPayrollID(payrollId);
        payroll.setUserID(userId);
        payroll.setBaseSalary(baseSalary);
        payroll.setMonth(month);
        payroll.setYear(year);
        payroll.setWorkingHours(workingHours);
        payroll.setNetSalary(totalSalary);
        payroll.setPayDate(LocalDate.now());
        payroll.setStatus("Pending");

        return payroll;
    }

    public Payroll createPayroll(int userId, double baseSalary, int month, int year) {
        Payroll payroll = new Payroll();
        payroll.setUserID(userId);
        payroll.setBaseSalary(baseSalary);
        payroll.setMonth(month);
        payroll.setYear(year);

        payroll.setWorkingHours(Duration.ZERO);
        payroll.setNetSalary(0.0);
        payroll.setPayDate(null);
        payroll.setStatus("Pending");

        return payroll;
    }
}
