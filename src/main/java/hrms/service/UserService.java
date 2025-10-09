package hrms.service;

import hrms.dao.DegreeDAO;
import hrms.dao.DepartmentDAO;
import hrms.dao.PositionDAO;
import hrms.dao.UserDAO;
import hrms.dto.UserDTO;
import hrms.model.User;

public class UserService {

    private UserDAO userDAO = new UserDAO();
    private DepartmentDAO departmentDAO = new DepartmentDAO();
    private PositionDAO positionDAO = new PositionDAO();
    private DegreeDAO degreeDAO = new DegreeDAO();

    // Lấy thông tin user theo ID
    public UserDTO getUserById(int userId) {
        User user = userDAO.getUserById(userId);
        if (user == null) return null;

        String departmentName = (user.getDepartmentId() != null)
                ? departmentDAO.getNameById(user.getDepartmentId()) : null;
        String positionName = (user.getPositionId() != null)
                ? positionDAO.getNameById(user.getPositionId()) : null;
        String degreeName = (user.getDegreeId() != null)
                ? degreeDAO.getNameById(user.getDegreeId()) : null;

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
        dto.setCccd(user.getCccd()); // ✅ thêm CCCD
        dto.setDepartmentId(user.getDepartmentId());
        dto.setDepartmentName(departmentName);
        dto.setPositionId(user.getPositionId());
        dto.setPositionName(positionName);
        dto.setDegreeId(user.getDegreeId());
        dto.setDegreeName(degreeName);

        return dto;
    }

    // Cập nhật user
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
        user.setCccd(dto.getCccd()); // ✅ thêm CCCD
        user.setDepartmentId(dto.getDepartmentId());
        user.setPositionId(dto.getPositionId());
        user.setDegreeId(dto.getDegreeId());

        return userDAO.updateUser(user);
    }
}
