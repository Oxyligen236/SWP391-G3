package hrms.service;

import java.util.ArrayList;
import java.util.List;

import hrms.dao.UserDAO;
import hrms.dao.DepartmentDAO;
import hrms.dao.PositionDAO;
import hrms.dao.DegreeDAO;
import hrms.dto.UserDTO;
import hrms.model.User;

public class UserService {

    private UserDAO userDAO = new UserDAO();
    private DepartmentDAO departmentDAO = new DepartmentDAO();
    private PositionDAO positionDAO = new PositionDAO();
    private DegreeDAO degreeDAO = new DegreeDAO();

    // Lấy thông tin 1 user theo ID (dùng cho view profile)
    public UserDTO getUserById(int userId) {
        User user = userDAO.getUserById(userId);
        if (user == null) return null;

        String departmentName = (user.getDepartmentId() != null) ?
                                departmentDAO.getNameById(user.getDepartmentId()) : null;

        String positionName = (user.getPositionId() != null) ?
                                positionDAO.getNameById(user.getPositionId()) : null;

        String degreeName = (user.getDegreeId() != null) ?
                                degreeDAO.getNameById(user.getDegreeId()) : null;

        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setFullname(user.getFullname());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setBirthDate(user.getBirthDate());
        dto.setGender(user.getGender());
        dto.setAddress(user.getAddress());
        dto.setNation(user.getNation());
        dto.setEthnicity(user.getEthnicity());
        dto.setDepartmentId(user.getDepartmentId());
        dto.setDepartmentName(departmentName);
        dto.setPositionId(user.getPositionId());
        dto.setPositionName(positionName);
        dto.setDegreeId(user.getDegreeId());
        dto.setDegreeName(degreeName);

        return dto;
    }

    // Cập nhật thông tin user
    public boolean updateUser(UserDTO dto) {
        if (dto == null) return false;

        User user = new User();
        user.setUserId(dto.getUserId());
        user.setFullname(dto.getFullname());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setBirthDate(dto.getBirthDate());
        user.setGender(dto.getGender());
        user.setAddress(dto.getAddress());
        user.setNation(dto.getNation());
        user.setEthnicity(dto.getEthnicity());
        user.setDepartmentId(dto.getDepartmentId());
        user.setPositionId(dto.getPositionId());
        user.setDegreeId(dto.getDegreeId());

        return userDAO.updateUser(user);
    }

    // Lấy tất cả user (dùng nếu cần hiển thị danh sách)
    public List<UserDTO> getAllUsers() {
        List<UserDTO> list = new ArrayList<>();
        List<User> users = userDAO.getAll(); // bạn có thể thêm method getAll() trong UserDAO

        for (User u : users) {
            String departmentName = (u.getDepartmentId() != null) ?
                                    departmentDAO.getNameById(u.getDepartmentId()) : null;
            String positionName = (u.getPositionId() != null) ?
                                    positionDAO.getNameById(u.getPositionId()) : null;
            String degreeName = (u.getDegreeId() != null) ?
                                    degreeDAO.getNameById(u.getDegreeId()) : null;

            UserDTO dto = new UserDTO();
            dto.setUserId(u.getUserId());
            dto.setFullname(u.getFullname());
            dto.setEmail(u.getEmail());
            dto.setPhoneNumber(u.getPhoneNumber());
            dto.setBirthDate(u.getBirthDate());
            dto.setGender(u.getGender());
            dto.setAddress(u.getAddress());
            dto.setNation(u.getNation());
            dto.setEthnicity(u.getEthnicity());
            dto.setDepartmentId(u.getDepartmentId());
            dto.setDepartmentName(departmentName);
            dto.setPositionId(u.getPositionId());
            dto.setPositionName(positionName);
            dto.setDegreeId(u.getDegreeId());
            dto.setDegreeName(degreeName);

            list.add(dto);
        }

        return list;
    }
}
