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
import hrms.utils.AttendanceCalculator;

public class AttendanceService {

    private final AttendanceDAO attendanceDAO = new AttendanceDAO();
    private final ShiftDAO shiftDAO = new ShiftDAO();
    private final UserDAO userDAO = new UserDAO();
    private final DepartmentDAO departmentDAO = new DepartmentDAO();
    private final PositionDAO positionDAO = new PositionDAO();

    private AttendanceDTO convertToDTO(Attendance attendance, List<User> users,
            List<Department> departments, List<Position> positions,
            List<Shift> shifts) {

        Shift shift = shifts.stream()
                .filter(s -> s.getShiftID() == attendance.getShiftID())
                .findFirst()
                .orElse(null);

        if (shift != null) {

            // ================================
            // 🔥 Tính Late và EarlyLeave
            // ================================
            attendance.setLateMinutes(
                AttendanceCalculator.calculateLateFull(
                    attendance.getCheckin1(),
                    attendance.getCheckin2(),
                    shift.getCheckin1(),
                    shift.getCheckin2()
                )
            );

            attendance.setEarlyLeaveMinutes(
                AttendanceCalculator.calculateEarlyFull(
                    attendance.getCheckout1(),
                    attendance.getCheckout2(),
                    shift.getCheckout1(),
                    shift.getCheckout2()
                )
            );

            // ================================
            // Tính WorkHours và OT
            // ================================
            LocalTime totalWorkHours = AttendanceCalculator.calculateWorkHours(
                    attendance.getCheckin1(), attendance.getCheckout1(),
                    attendance.getCheckin2(), attendance.getCheckout2(),
                    attendance.getCheckin3(), attendance.getCheckout3()
            );
            attendance.setTotalWorkHours(totalWorkHours);

            // Nếu OT null hoặc chưa có dữ liệu, vẫn tính dựa trên ca chuẩn
            LocalTime standardHours = AttendanceCalculator.calculateWorkHours(
                    shift.getCheckin1(), shift.getCheckout1(),
                    shift.getCheckin2(), shift.getCheckout2(),
                    null, null
            );

            attendance.setOtHours(AttendanceCalculator.calculateOT(totalWorkHours, standardHours));

            // Cập nhật database
            attendanceDAO.updateAttendanceTimes(attendance);
        }

        AttendanceDTO dto = new AttendanceDTO();

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

        User user = users.stream()
                .filter(u -> u.getUserId() == attendance.getUserID())
                .findFirst()
                .orElse(null);
        if (user != null) {
            dto.setUserName(user.getFullname());

            Department department = departments.stream()
                    .filter(d -> d.getDepartmentId() == user.getDepartmentId())
                    .findFirst()
                    .orElse(null);
            if (department != null) {
                dto.setDepartmentName(department.getName());
            }

            Position position = positions.stream()
                    .filter(p -> p.getPositionId() == user.getPositionId())
                    .findFirst()
                    .orElse(null);
            if (position != null) {
                dto.setPositionName(position.getName());
            }
        }

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
        List<Attendance> attendances = attendanceDAO.getAllAttendances();

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

            if (name != null && !name.trim().isEmpty()) {
                if (dto.getUserName() == null
                        || !dto.getUserName().toLowerCase().contains(name.toLowerCase().trim())) {
                    match = false;
                }
            }

            if (department != null && !department.trim().isEmpty()) {
                if (dto.getDepartmentName() == null
                        || !dto.getDepartmentName().toLowerCase().contains(department.toLowerCase().trim())) {
                    match = false;
                }
            }

            if (position != null && !position.trim().isEmpty()) {
                if (dto.getPositionName() == null
                        || !dto.getPositionName().toLowerCase().contains(position.toLowerCase().trim())) {
                    match = false;
                }
            }

            if (hasCheckout3 && dto.getCheckout3() == null) {
                match = false;
            }

            if (hasLate && (dto.getLateMinutes() == null || dto.getLateMinutes().equals(LocalTime.MIDNIGHT))) {
                match = false;
            }

            if (hasEarlyLeave && (dto.getEarlyLeaveMinutes() == null || dto.getEarlyLeaveMinutes().equals(LocalTime.MIDNIGHT))) {
                match = false;
            }

            if (hasOT && (dto.getOtHours() == null || dto.getOtHours().equals(LocalTime.MIDNIGHT))) {
                match = false;
            }

            if (match) {
                attendanceDTOs.add(dto);
            }
        }

        return attendanceDTOs;
    }
}
