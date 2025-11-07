package hrms.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Workbook;

import hrms.dto.AttendanceDTO;
import hrms.service.AttendanceExportService;
import hrms.service.AttendanceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet to handle attendance data export to Excel
 * Supports filtering by various criteria and generates formatted Excel files
 */
@WebServlet("/export-attendance")
public class ExportAttendanceServlet extends HttpServlet {

    private static final DateTimeFormatter FILENAME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    
    private final AttendanceService attendanceService;
    private final AttendanceExportService exportService;
    
    public ExportAttendanceServlet() {
        this.attendanceService = new AttendanceService();
        this.exportService = new AttendanceExportService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Workbook workbook = null;
        
        try {
            // Get current user's username from session for logging
            HttpSession session = request.getSession();
            String username = (String) session.getAttribute("username");
            if (username == null) {
                username = "Unknown";
            }
            
            // Extract all filter parameters from request
            String userName = request.getParameter("userName");
            String department = request.getParameter("department");
            String position = request.getParameter("position");
            String shiftIdParam = request.getParameter("shiftId");
            String fromDateParam = request.getParameter("fromDate");
            String toDateParam = request.getParameter("toDate");
            
            String hasCheckout3Param = request.getParameter("hasCheckout3");
            String hasLateParam = request.getParameter("hasLate");
            String hasEarlyLeaveParam = request.getParameter("hasEarlyLeave");
            String hasOTParam = request.getParameter("hasOT");
            
            boolean hasCheckout3 = "true".equals(hasCheckout3Param);
            boolean hasLate = "true".equals(hasLateParam);
            boolean hasEarlyLeave = "true".equals(hasEarlyLeaveParam);
            boolean hasOT = "true".equals(hasOTParam);
            
            // Get filtered attendance data
            List<AttendanceDTO> attendanceList;
            
            if (userName != null || department != null || position != null
                    || hasCheckout3 || hasLate || hasEarlyLeave || hasOT) {
                attendanceList = attendanceService.searchAttendances(
                        userName, department, position, hasCheckout3, hasLate, hasEarlyLeave, hasOT);
            } else {
                attendanceList = attendanceService.getAllAttendances();
            }
            
            // Apply shift filter if specified
            if (shiftIdParam != null && !shiftIdParam.isEmpty() && !shiftIdParam.equals("All")) {
                try {
                    int shiftId = Integer.parseInt(shiftIdParam);
                    attendanceList = attendanceList.stream()
                            .filter(a -> a.getShiftID() == shiftId)
                            .collect(Collectors.toList());
                } catch (NumberFormatException e) {
                    System.out.println("[WARN] Invalid shift ID parameter: " + shiftIdParam);
                }
            }
            
            // Apply date range filter if specified
            LocalDate fromDate = null;
            LocalDate toDate = null;
            
            if (fromDateParam != null && !fromDateParam.isEmpty()) {
                try {
                    fromDate = LocalDate.parse(fromDateParam);
                } catch (Exception e) {
                    System.out.println("[WARN] Invalid 'From Date' format: " + fromDateParam);
                }
            }
            
            if (toDateParam != null && !toDateParam.isEmpty()) {
                try {
                    toDate = LocalDate.parse(toDateParam);
                } catch (Exception e) {
                    System.out.println("[WARN] Invalid 'To Date' format: " + toDateParam);
                }
            }
            
            // Validate and apply date range
            if (fromDate != null && toDate != null) {
                if (fromDate.isAfter(toDate)) {
                    System.out.println("[WARN] 'From Date' is after 'To Date', ignoring date filter");
                } else {
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
            }
            
            // Sort by date descending (same as web page display)
            attendanceList.sort(Comparator.comparing(AttendanceDTO::getDate,
                    Comparator.nullsLast(Comparator.reverseOrder())));
            
            // Check if result is empty and handle accordingly
            if (attendanceList.isEmpty()) {
                session.setAttribute("errorMessage", "No data to export");
                response.sendRedirect("company-attendance");
                System.out.println("[INFO] Export attempted by user " + username + " but no data matched filters");
                return;
            }
            
            // Create Map of applied filters for summary section
            Map<String, String> appliedFilters = extractFilters(request);
            
            // Generate Excel file
            workbook = exportService.generateAttendanceExcel(attendanceList, username, appliedFilters);
            
            // Generate filename with pattern "Attendance_Report_YYYYMMDD_HHmmss.xlsx"
            String timestamp = LocalDateTime.now().format(FILENAME_FORMATTER);
            String filename = "Attendance_Report_" + timestamp + ".xlsx";
            
            // Set response content type for Excel file
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            
            // Write workbook to response output stream
            workbook.write(response.getOutputStream());
            
            // Log successful export
            System.out.println("[INFO] Attendance export successful: " + attendanceList.size() + 
                    " records exported by user " + username + " with filters: " + formatFiltersForLog(appliedFilters));
            
        } catch (Exception e) {
            // Log failed export with ERROR level
            System.err.println("[ERROR] Failed to export attendance data");
            e.printStackTrace();
            
            // Send HTTP 500 error response with user-friendly message
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Failed to generate Excel file. Please try again or contact support.");
            
        } finally {
            // Ensure workbook is closed in finally block to free resources
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    System.err.println("[ERROR] Failed to close workbook");
                    e.printStackTrace();
                }
            }
        }
    }

    private Map<String, String> extractFilters(HttpServletRequest request) {
        Map<String, String> filters = new HashMap<>();
        
        String userName = request.getParameter("userName");
        if (userName != null && !userName.trim().isEmpty()) {
            filters.put("Employee Name", userName);
        }
        
        String department = request.getParameter("department");
        if (department != null && !department.trim().isEmpty()) {
            filters.put("Department", department);
        }
        
        String position = request.getParameter("position");
        if (position != null && !position.trim().isEmpty()) {
            filters.put("Position", position);
        }
        
        String shiftId = request.getParameter("shiftId");
        if (shiftId != null && !shiftId.trim().isEmpty() && !shiftId.equals("All")) {
            filters.put("Shift ID", shiftId);
        }
        
        String fromDate = request.getParameter("fromDate");
        if (fromDate != null && !fromDate.trim().isEmpty()) {
            filters.put("From Date", fromDate);
        }
        
        String toDate = request.getParameter("toDate");
        if (toDate != null && !toDate.trim().isEmpty()) {
            filters.put("To Date", toDate);
        }
        
        if ("true".equals(request.getParameter("hasCheckout3"))) {
            filters.put("Has Checkout 3", "Yes");
        }
        
        if ("true".equals(request.getParameter("hasLate"))) {
            filters.put("Has Late", "Yes");
        }
        
        if ("true".equals(request.getParameter("hasEarlyLeave"))) {
            filters.put("Has Early Leave", "Yes");
        }
        
        if ("true".equals(request.getParameter("hasOT"))) {
            filters.put("Has OT", "Yes");
        }
        
        return filters;
    }
    
   
    private String formatFiltersForLog(Map<String, String> filters) {
        if (filters == null || filters.isEmpty()) {
            return "None";
        }
        
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        
        return sb.toString();
    }
}
