package hrms.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import hrms.dao.AttendanceDAO;
import hrms.dao.DepartmentDAO;
import hrms.dao.PositionDAO;
import hrms.dao.ShiftDAO;
import hrms.dao.UserDAO;
import hrms.dto.AttendanceDTO;
import hrms.model.Attendance;
import hrms.model.Department;
import hrms.model.Position;
import hrms.model.Shift;
import hrms.model.User;

public class AttendanceService {

    private final AttendanceDAO attendanceDAO = new AttendanceDAO();
    private final ShiftDAO shiftDAO = new ShiftDAO();
    private final UserDAO userDAO = new UserDAO();
    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    private final PositionDAO positionDAO = new PositionDAO();

    // Helper method: Chuyển Model → DTO (KHÔNG CẦN CONVERT LocalTime NỮA)
    private AttendanceDTO convertToDTO(Attendance attendance, List<User> users,
            List<Department> departments, List<Position> positions,
            List<Shift> shifts) {
        AttendanceDTO dto = new AttendanceDTO();

        // Set trực tiếp vì cùng kiểu LocalTime
        dto.setAttendanceID(attendance.getAttendanceID());
        dto.setUserID(attendance.getUserID());
        dto.setDate(attendance.getDate());
        dto.setDay(attendance.getDay());
        dto.setCheckin1(attendance.getCheckin1());
        dto.setCheckout1(attendance.getCheckout1());
        dto.setCheckin2(attendance.getCheckin2());
        dto.setCheckout2(attendance.getCheckout2());
        dto.setCheckin3(attendance.getCheckin3());
        dto.setCheckout3(attendance.getCheckout3());
        dto.setShiftID(attendance.getShiftID());
        dto.setLateMinutes(attendance.getLateMinutes());
        dto.setEarlyLeaveMinutes(attendance.getEarlyLeaveMinutes());
        dto.setTotalWorkHours(attendance.getTotalWorkHours());
        dto.setOtHours(attendance.getOtHours());

        // Lấy thông tin User
        User user = users.stream()
                .filter(u -> u.getUserId() == attendance.getUserID())
                .findFirst()
                .orElse(null);

        if (user != null) {
            dto.setUserName(user.getFullname());

            // Lấy thông tin Department
            Department department = departments.stream()
                    .filter(d -> d.getDepartmentId() == user.getDepartmentId())
                    .findFirst()
                    .orElse(null);
            if (department != null) {
                dto.setDepartmentName(department.getName());
            }

            // Lấy thông tin Position
            Position position = positions.stream()
                    .filter(p -> p.getPositionId() == user.getPositionId())
                    .findFirst()
                    .orElse(null);
            if (position != null) {
                dto.setPositionName(position.getName());
            }
        }

        // Lấy thông tin Shift - Trực tiếp vì cùng kiểu LocalTime
        Shift shift = shifts.stream()
                .filter(s -> s.getShiftID() == attendance.getShiftID())
                .findFirst()
                .orElse(null);

        if (shift != null) {
            dto.setShiftName(shift.getName());
            dto.setShiftCheckin1(shift.getCheckin1());
            dto.setShiftCheckout1(shift.getCheckout1());
            dto.setShiftCheckin2(shift.getCheckin2());
            dto.setShiftCheckout2(shift.getCheckout2());
        }

        return dto;
    }

    public List<AttendanceDTO> getAllAttendances() {
        // Lấy Model từ DAO
        List<Attendance> attendances = attendanceDAO.getAllAttendances();

        // Load dữ liệu cần thiết 1 lần
        List<User> users = userDAO.getAll();
        List<Department> departments = departmentDAO.getAll();
        List<Position> positions = positionDAO.getAll();
        List<Shift> shifts = shiftDAO.getAllShifts();

        // Chuyển đổi sang DTO
        List<AttendanceDTO> attendanceDTOs = new ArrayList<>();
        for (Attendance attendance : attendances) {
            attendanceDTOs.add(convertToDTO(attendance, users, departments, positions, shifts));
        }
        return attendanceDTOs;
    }

    public List<AttendanceDTO> getAttendancesByUser(int userID) {
        List<Attendance> attendances = attendanceDAO.getByUser(userID);

        List<User> users = userDAO.getAll();
        List<Department> departments = departmentDAO.getAll();
        List<Position> positions = positionDAO.getAll();
        List<Shift> shifts = shiftDAO.getAllShifts();

        List<AttendanceDTO> attendanceDTOs = new ArrayList<>();
        for (Attendance attendance : attendances) {
            attendanceDTOs.add(convertToDTO(attendance, users, departments, positions, shifts));
        }
        return attendanceDTOs;
    }

    public List<AttendanceDTO> searchAttendances(String name, String department, String position,
            boolean hasCheckout3, boolean hasLate,
            boolean hasEarlyLeave, boolean hasOT) {
        List<Attendance> attendances = attendanceDAO.getAllAttendances();

        List<User> users = userDAO.getAll();
        List<Department> departments = departmentDAO.getAll();
        List<Position> positions = positionDAO.getAll();
        List<Shift> shifts = shiftDAO.getAllShifts();

        List<AttendanceDTO> attendanceDTOs = new ArrayList<>();
        for (Attendance attendance : attendances) {
            AttendanceDTO dto = convertToDTO(attendance, users, departments, positions, shifts);

            boolean match = true;

            // Filter by name
            if (name != null && !name.trim().isEmpty()) {
                if (dto.getUserName() == null
                        || !dto.getUserName().toLowerCase().contains(name.toLowerCase().trim())) {
                    match = false;
                }
            }

            // Filter by department
            if (department != null && !department.trim().isEmpty()) {
                if (dto.getDepartmentName() == null
                        || !dto.getDepartmentName().toLowerCase().contains(department.toLowerCase().trim())) {
                    match = false;
                }
            }

            // Filter by position
            if (position != null && !position.trim().isEmpty()) {
                if (dto.getPositionName() == null
                        || !dto.getPositionName().toLowerCase().contains(position.toLowerCase().trim())) {
                    match = false;
                }
            }

            // Filter by checkout3
            if (hasCheckout3 && dto.getCheckout3() == null) {
                match = false;
            }

            // Filter by late (00:00:00 = không trễ)
            if (hasLate) {
                if (dto.getLateMinutes() == null
                        || dto.getLateMinutes().equals(LocalTime.MIDNIGHT)
                        || dto.getLateMinutes().equals(LocalTime.of(0, 0, 0))) {
                    match = false;
                }
            }

            // Filter by early leave
            if (hasEarlyLeave) {
                if (dto.getEarlyLeaveMinutes() == null
                        || dto.getEarlyLeaveMinutes().equals(LocalTime.MIDNIGHT)
                        || dto.getEarlyLeaveMinutes().equals(LocalTime.of(0, 0, 0))) {
                    match = false;
                }
            }

            // Filter by OT
            if (hasOT) {
                if (dto.getOtHours() == null
                        || dto.getOtHours().equals(LocalTime.MIDNIGHT)
                        || dto.getOtHours().equals(LocalTime.of(0, 0, 0))) {
                    match = false;
                }
            }

            if (match) {
                attendanceDTOs.add(dto);
            }
        }

        return attendanceDTOs;
    }
}