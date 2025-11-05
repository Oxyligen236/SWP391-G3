package hrms.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import hrms.dto.AttendanceDTO;
import hrms.service.AttendanceService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet to handle attendance export preview
 * Displays filtered attendance data before Excel export
 */
@WebServlet("/preview-attendance-export")
public class PreviewAttendanceExportServlet extends HttpServlet {

    private final AttendanceService attendanceService;
    
    public PreviewAttendanceExportServlet() {
        this.attendanceService = new AttendanceService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            // Validate user session and get username
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
            
            // Call AttendanceService.searchAttendances() or getAllAttendances() based on filter presence
            List<AttendanceDTO> attendanceList;
            
            // Check if any text filter is provided (not null and not empty)
            boolean hasTextFilter = (userName != null && !userName.trim().isEmpty()) 
                                 || (department != null && !department.trim().isEmpty() && !"All".equals(department))
                                 || (position != null && !position.trim().isEmpty() && !"All".equals(position));
            
            if (hasTextFilter || hasCheckout3 || hasLate || hasEarlyLeave || hasOT) {
                attendanceList = attendanceService.searchAttendances(
                        userName, department, position, hasCheckout3, hasLate, hasEarlyLeave, hasOT);
            } else {
                attendanceList = attendanceService.getAllAttendances();
            }
            
            // Check if attendanceList is null and initialize empty list if needed
            if (attendanceList == null) {
                attendanceList = new java.util.ArrayList<>();
            }
            
            // Apply shift filter by filtering stream where shiftID matches
            if (shiftIdParam != null && !shiftIdParam.isEmpty() && !shiftIdParam.equals("All")) {
                try {
                    int shiftId = Integer.parseInt(shiftIdParam);
                    attendanceList = attendanceList.stream()
                            .filter(a -> a.getShiftID() == shiftId)
                            .collect(Collectors.toList());
                } catch (NumberFormatException e) {
                    // Invalid shift ID, skip filter
                }
            }
            
            // Apply date range filter by parsing fromDate and toDate, validating range, and filtering stream
            LocalDate fromDate = null;
            LocalDate toDate = null;
            
            if (fromDateParam != null && !fromDateParam.isEmpty()) {
                try {
                    fromDate = LocalDate.parse(fromDateParam);
                } catch (Exception e) {
                    // Invalid date format, skip filter
                }
            }
            
            if (toDateParam != null && !toDateParam.isEmpty()) {
                try {
                    toDate = LocalDate.parse(toDateParam);
                } catch (Exception e) {
                    // Invalid date format, skip filter
                }
            }
            
            // Validate and apply date range
            if (fromDate != null && toDate != null) {
                if (!fromDate.isAfter(toDate)) {
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
            
            // Sort attendance list by date descending using Comparator
            attendanceList.sort(Comparator.comparing(AttendanceDTO::getDate,
                    Comparator.nullsLast(Comparator.reverseOrder())));
            
            // Handle empty result case by setting error message in session and redirecting to company-attendance
            if (attendanceList.isEmpty()) {
                session.setAttribute("errorMessage", "No data to export");
                response.sendRedirect("company-attendance");
                return;
            }
            
            // Create appliedFilters Map by extracting non-empty filter parameters
            Map<String, String> appliedFilters = extractFilters(request);
            
            // Set request attributes: attendances, totalRecords, appliedFilters, and all filter values for preservation
            request.setAttribute("attendances", attendanceList);
            request.setAttribute("totalRecords", attendanceList.size());
            request.setAttribute("appliedFilters", appliedFilters);
            
            // Preserve all filter values for form population
            request.setAttribute("searchUserName", userName);
            request.setAttribute("searchDepartment", department);
            request.setAttribute("searchPosition", position);
            request.setAttribute("selectedShift", shiftIdParam);
            request.setAttribute("fromDate", fromDateParam);
            request.setAttribute("toDate", toDateParam);
            request.setAttribute("hasCheckout3", hasCheckout3);
            request.setAttribute("hasLate", hasLate);
            request.setAttribute("hasEarlyLeave", hasEarlyLeave);
            request.setAttribute("hasOT", hasOT);
            
            // Forward to /view/attendance/attendanceExportPreview.jsp
            request.getRequestDispatcher("/view/attendance/attendanceExportPreview.jsp").forward(request, response);
            
        } catch (Exception e) {
            // Set error message in session for user display
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Failed to preview data: " + e.getMessage());
            
            // Redirect back to company attendance instead of showing error page
            response.sendRedirect(request.getContextPath() + "/company-attendance");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Implement doPost method to delegate to doGet
        doGet(request, response);
    }
    
    /**
     * Extract all filter parameters into Map for display in summary section
     * 
     * @param request HTTP request containing filter parameters
     * @return Map of filter names to values
     */
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
}
