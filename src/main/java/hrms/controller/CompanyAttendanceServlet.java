package hrms.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import hrms.dao.DepartmentDAO;
import hrms.dao.PositionDAO;
import hrms.dao.ShiftDAO;
import hrms.dto.AttendanceDTO;
import hrms.service.AttendanceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/company-attendance")
public class CompanyAttendanceServlet extends HttpServlet {

    private final AttendanceService attendanceService = new AttendanceService();
    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    private final PositionDAO positionDAO = new PositionDAO();
    private final ShiftDAO shiftDAO = new ShiftDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String userName = req.getParameter("userName");
        String department = req.getParameter("department");
        String position = req.getParameter("position");
        String shiftIdParam = req.getParameter("shiftId");
        String fromDateParam = req.getParameter("fromDate");
        String toDateParam = req.getParameter("toDate");
        String resetParam = req.getParameter("reset");

        String hasLateParam = req.getParameter("hasLate");
        String hasEarlyLeaveParam = req.getParameter("hasEarlyLeave");
        String hasOTParam = req.getParameter("hasOT");

        boolean hasLate = "true".equals(hasLateParam);
        boolean hasEarlyLeave = "true".equals(hasEarlyLeaveParam);
        boolean hasOT = "true".equals(hasOTParam);

        boolean isReset = "true".equals(resetParam);
        boolean isFirstLoad = !isReset
                && req.getParameter("userName") == null
                && req.getParameter("department") == null
                && req.getParameter("position") == null
                && req.getParameter("shiftId") == null
                && req.getParameter("fromDate") == null
                && req.getParameter("toDate") == null
                && req.getParameter("hasLate") == null
                && req.getParameter("hasEarlyLeave") == null
                && req.getParameter("hasOT") == null
                && req.getParameter("itemsPerPage") == null
                && req.getParameter("page") == null;

        if (isFirstLoad) {
            YearMonth currentMonth = YearMonth.now();
            fromDateParam = currentMonth.atDay(1).toString();
            toDateParam = currentMonth.atEndOfMonth().toString();
        }

        int itemsPerPage = 10;
        if (req.getParameter("itemsPerPage") != null) {
            try {
                itemsPerPage = Integer.parseInt(req.getParameter("itemsPerPage"));
            } catch (NumberFormatException e) {
                itemsPerPage = 10;
            }
        }

        int currentPage = 1;
        if (req.getParameter("page") != null) {
            try {
                currentPage = Integer.parseInt(req.getParameter("page"));
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        List<AttendanceDTO> attendanceList;

        if (userName != null || department != null || position != null
                || hasLate || hasEarlyLeave || hasOT) {
            attendanceList = attendanceService.searchAttendances(
                    userName, department, position, false, hasLate, hasEarlyLeave, hasOT);
        } else {
            attendanceList = attendanceService.getAllAttendances();
        }

        if (shiftIdParam != null && !shiftIdParam.isEmpty() && !shiftIdParam.equals("All")) {
            try {
                int shiftId = Integer.parseInt(shiftIdParam);
                attendanceList = attendanceList.stream()
                        .filter(a -> a.getShiftID() == shiftId)
                        .collect(Collectors.toList());
            } catch (NumberFormatException e) {
                System.out.println("Invalid shift ID: " + e.getMessage());
            }
        }

        LocalDate fromDate = null;
        LocalDate toDate = null;
        String dateError = null;

        if (fromDateParam != null && !fromDateParam.isEmpty()) {
            try {
                fromDate = LocalDate.parse(fromDateParam);
            } catch (Exception e) {
                dateError = "Invalid 'From Date' format";
            }
        }

        if (toDateParam != null && !toDateParam.isEmpty()) {
            try {
                toDate = LocalDate.parse(toDateParam);
            } catch (Exception e) {
                dateError = "Invalid 'To Date' format";
            }
        }

        if (fromDate != null && toDate != null && fromDate.isAfter(toDate)) {
            dateError = "'From Date' cannot be after 'To Date'";
            fromDate = null;
            toDate = null;
        }

        if (fromDate != null && toDate != null) {
            final LocalDate finalFromDate = fromDate;
            final LocalDate finalToDate = toDate;
            attendanceList = attendanceList.stream()
                    .filter(a -> {
                        LocalDate attDate = a.getDate();
                        return attDate != null
                                && !attDate.isBefore(finalFromDate)
                                && !attDate.isAfter(finalToDate);
                    })
                    .collect(Collectors.toList());
        }

        attendanceList.sort(
                Comparator.comparing(AttendanceDTO::getDepartmentName,
                        Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                        .thenComparing(AttendanceDTO::getUserName,
                                Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER))
                        .thenComparing(AttendanceDTO::getDate,
                                Comparator.nullsLast(Comparator.naturalOrder()))
        );

        int totalItems = attendanceList.size();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);

        if (currentPage > totalPages && totalPages > 0) {
            currentPage = totalPages;
        }

        int startIndex = (currentPage - 1) * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, totalItems);

        List<AttendanceDTO> paginatedAttendances = attendanceList.subList(startIndex, endIndex);

        req.setAttribute("departments", departmentDAO.getAll());
        req.setAttribute("positions", positionDAO.getAll());
        req.setAttribute("shifts", shiftDAO.getAllShifts());
        req.setAttribute("attendances", paginatedAttendances);

        req.setAttribute("searchUserName", userName != null ? userName : "");
        req.setAttribute("searchDepartment", department != null ? department : "");
        req.setAttribute("searchPosition", position != null ? position : "");
        req.setAttribute("selectedShift", shiftIdParam != null ? shiftIdParam : "All");

        req.setAttribute("fromDate", fromDateParam != null ? fromDateParam : "");
        req.setAttribute("toDate", toDateParam != null ? toDateParam : "");

        req.setAttribute("hasLate", hasLate);
        req.setAttribute("hasEarlyLeave", hasEarlyLeave);
        req.setAttribute("hasOT", hasOT);

        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("itemsPerPage", itemsPerPage);
        req.setAttribute("totalItems", totalItems);
        req.setAttribute("dateError", dateError);

        req.getRequestDispatcher("/view/attendance/companyAttendance.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
