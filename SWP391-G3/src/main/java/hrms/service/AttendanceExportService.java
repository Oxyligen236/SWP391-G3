package hrms.service;

import hrms.dto.AttendanceDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class AttendanceExportService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Generate Excel workbook containing attendance data
     * 
     * @param attendances List of attendance records to export
     * @param exportedBy Username of the person performing the export
     * @param appliedFilters Map of filters applied to the data
     * @return Workbook containing formatted attendance data
     */
    public Workbook generateAttendanceExcel(List<AttendanceDTO> attendances, 
                                           String exportedBy, 
                                           Map<String, String> appliedFilters) {
        // Create workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Attendance Report");
        
        // Create header row
        createHeaderRow(sheet, workbook);
        
        // Populate data rows
        populateDataRows(sheet, attendances, workbook);
        
        // Add summary section
        int lastRowNum = sheet.getLastRowNum();
        addSummarySection(sheet, lastRowNum, attendances.size(), exportedBy, appliedFilters, workbook);
        
        // Apply final formatting
        applyFormatting(sheet);
        
        return workbook;
    }

    /**
     * Create header cell style with blue background, white text, and bold font
     */
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        
        // Set blue background color (#4472C4)
        style.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        // Set white text color and bold font
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);
        style.setFont(font);
        
        // Add borders
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        
        // Center alignment
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        
        return style;
    }

    /**
     * Create date cell style with yyyy-MM-dd format and center alignment
     */
    private CellStyle createDateStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        
        // Set date format
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("yyyy-mm-dd"));
        
        // Center alignment
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        
        // Add borders
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        
        return style;
    }

    /**
     * Create time cell style with HH:mm:ss format and center alignment
     */
    private CellStyle createTimeStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        
        // Center alignment
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        
        // Add borders
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        
        return style;
    }

    /**
     * Create summary section style with gray background
     */
    private CellStyle createSummaryStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        
        // Set gray background color (#E7E6E6)
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        // Add borders
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        
        // Left alignment
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        
        return style;
    }

    /**
     * Create general text cell style with left alignment and borders
     */
    private CellStyle createTextStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        
        // Left alignment
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        
        // Add borders
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        
        return style;
    }

    /**
     * Create header row with 17 columns and professional formatting
     */
    private void createHeaderRow(Sheet sheet, Workbook workbook) {
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = createHeaderStyle(workbook);
        
        String[] headers = {
            "Employee ID", "Employee Name", "Department", "Position", 
            "Date", "Day", "Shift", 
            "Check-in 1", "Check-out 1", 
            "Check-in 2", "Check-out 2", 
            "Check-in 3", "Check-out 3", 
            "Late Minutes", "Early Leave Minutes", 
            "Total Work Hours", "OT Hours"
        };
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    /**
     * Populate data rows from AttendanceDTO list with proper formatting
     */
    private void populateDataRows(Sheet sheet, List<AttendanceDTO> attendances, Workbook workbook) {
        CellStyle textStyle = createTextStyle(workbook);
        CellStyle dateStyle = createDateStyle(workbook);
        CellStyle timeStyle = createTimeStyle(workbook);
        
        int rowNum = 1; // Start after header row
        
        for (AttendanceDTO attendance : attendances) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            
            // Employee ID
            Cell cell = row.createCell(colNum++);
            cell.setCellValue(attendance.getUserID());
            cell.setCellStyle(textStyle);
            
            // Employee Name
            cell = row.createCell(colNum++);
            cell.setCellValue(attendance.getUserName() != null ? attendance.getUserName() : "");
            cell.setCellStyle(textStyle);
            
            // Department
            cell = row.createCell(colNum++);
            cell.setCellValue(attendance.getDepartmentName() != null ? attendance.getDepartmentName() : "N/A");
            cell.setCellStyle(textStyle);
            
            // Position
            cell = row.createCell(colNum++);
            cell.setCellValue(attendance.getPositionName() != null ? attendance.getPositionName() : "N/A");
            cell.setCellStyle(textStyle);
            
            // Date
            cell = row.createCell(colNum++);
            cell.setCellValue(formatDateValue(attendance.getDate()));
            cell.setCellStyle(dateStyle);
            
            // Day
            cell = row.createCell(colNum++);
            cell.setCellValue(attendance.getDay() != null ? attendance.getDay() : "");
            cell.setCellStyle(textStyle);
            
            // Shift
            cell = row.createCell(colNum++);
            cell.setCellValue(attendance.getShiftName() != null ? attendance.getShiftName() : "");
            cell.setCellStyle(textStyle);
            
            // Check-in 1
            cell = row.createCell(colNum++);
            cell.setCellValue(formatTimeValue(attendance.getCheckin1()));
            cell.setCellStyle(timeStyle);
            
            // Check-out 1
            cell = row.createCell(colNum++);
            cell.setCellValue(formatTimeValue(attendance.getCheckout1()));
            cell.setCellStyle(timeStyle);
            
            // Check-in 2
            cell = row.createCell(colNum++);
            cell.setCellValue(formatTimeValue(attendance.getCheckin2()));
            cell.setCellStyle(timeStyle);
            
            // Check-out 2
            cell = row.createCell(colNum++);
            cell.setCellValue(formatTimeValue(attendance.getCheckout2()));
            cell.setCellStyle(timeStyle);
            
            // Check-in 3
            cell = row.createCell(colNum++);
            cell.setCellValue(formatTimeValue(attendance.getCheckin3()));
            cell.setCellStyle(timeStyle);
            
            // Check-out 3
            cell = row.createCell(colNum++);
            cell.setCellValue(formatTimeValue(attendance.getCheckout3()));
            cell.setCellStyle(timeStyle);
            
            // Late Minutes
            cell = row.createCell(colNum++);
            cell.setCellValue(formatTimeValue(attendance.getLateMinutes()));
            cell.setCellStyle(timeStyle);
            
            // Early Leave Minutes
            cell = row.createCell(colNum++);
            cell.setCellValue(formatTimeValue(attendance.getEarlyLeaveMinutes()));
            cell.setCellStyle(timeStyle);
            
            // Total Work Hours
            cell = row.createCell(colNum++);
            cell.setCellValue(formatTimeValue(attendance.getTotalWorkHours()));
            cell.setCellStyle(timeStyle);
            
            // OT Hours
            cell = row.createCell(colNum++);
            cell.setCellValue(formatTimeValue(attendance.getOtHours()));
            cell.setCellStyle(timeStyle);
        }
    }

    /**
     * Add summary section at the bottom of the Excel file
     */
    private void addSummarySection(Sheet sheet, int lastRowNum, int totalRecords, 
                                   String exportedBy, Map<String, String> appliedFilters, 
                                   Workbook workbook) {
        CellStyle summaryStyle = createSummaryStyle(workbook);
        
        // Add empty row separator
        int summaryStartRow = lastRowNum + 2;
        
        // Total Records row
        Row row = sheet.createRow(summaryStartRow);
        Cell cell = row.createCell(0);
        cell.setCellValue("Total Records:");
        cell.setCellStyle(summaryStyle);
        cell = row.createCell(1);
        cell.setCellValue(totalRecords);
        cell.setCellStyle(summaryStyle);
        
        // Export Date/Time row
        row = sheet.createRow(summaryStartRow + 1);
        cell = row.createCell(0);
        cell.setCellValue("Export Date/Time:");
        cell.setCellStyle(summaryStyle);
        cell = row.createCell(1);
        cell.setCellValue(LocalDateTime.now().format(DATETIME_FORMATTER));
        cell.setCellStyle(summaryStyle);
        
        // Exported By row
        row = sheet.createRow(summaryStartRow + 2);
        cell = row.createCell(0);
        cell.setCellValue("Exported By:");
        cell.setCellStyle(summaryStyle);
        cell = row.createCell(1);
        cell.setCellValue(exportedBy != null ? exportedBy : "Unknown");
        cell.setCellStyle(summaryStyle);
        
        // Applied Filters row
        row = sheet.createRow(summaryStartRow + 3);
        cell = row.createCell(0);
        cell.setCellValue("Applied Filters:");
        cell.setCellStyle(summaryStyle);
        cell = row.createCell(1);
        
        // Format filters as readable string
        String filtersString = formatFilters(appliedFilters);
        cell.setCellValue(filtersString);
        cell.setCellStyle(summaryStyle);
    }

    /**
     * Format filters map as readable string
     */
    private String formatFilters(Map<String, String> filters) {
        if (filters == null || filters.isEmpty()) {
            return "None";
        }
        
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(entry.getKey()).append(": ").append(entry.getValue());
            }
        }
        
        return sb.length() > 0 ? sb.toString() : "None";
    }

    /**
     * Apply final formatting to the sheet including auto-sizing columns
     */
    private void applyFormatting(Sheet sheet) {
        // Auto-size all 17 columns to fit content
        for (int i = 0; i < 17; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    /**
     * Format LocalTime to HH:mm:ss string
     * Returns empty string for null values
     * Returns "00:00:00" for zero time values (not empty)
     */
    private String formatTimeValue(LocalTime time) {
        if (time == null) {
            return "";
        }
        return time.format(TIME_FORMATTER);
    }

    /**
     * Format LocalDate to yyyy-MM-dd string
     */
    private String formatDateValue(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DATE_FORMATTER);
    }
}
