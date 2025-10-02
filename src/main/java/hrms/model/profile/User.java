package hrms.model.profile;

import java.util.Date;

public class User {
    private int userId;
    private String username;
    private int roleId;
    private String roleName;  // Thêm để dễ kiểm tra role
    private String password;
    private boolean isActive;
    private String fullname;
    private Date birthDate;
    private String gender;
    private String email;
    private String phoneNumber;
    private String address;
    private String cccd;
    private String ethnicity;
    private String nation;
    private int departmentId;
    private String departmentName;
    private int degreeId;
    private String degreeName;
    private int positionId;
    private String positionName;
    
    // Constructors
    public User() {}
    
    public User(int userId, String username, String fullname, String email, 
                String phoneNumber, Date birthDate, String gender) {
        this.userId = userId;
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.gender = gender;
    }
    
    // Getters và Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public int getRoleId() { return roleId; }
    public void setRoleId(int roleId) { this.roleId = roleId; }
    
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public boolean isActive() { return isActive; }
    public void setIsActive(boolean isActive) { this.isActive = isActive; }
    
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getCccd() { return cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }
    
    public String getEthnicity() { return ethnicity; }
    public void setEthnicity(String ethnicity) { this.ethnicity = ethnicity; }
    
    public String getNation() { return nation; }
    public void setNation(String nation) { this.nation = nation; }
    
    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }
    
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    
    public int getPositionId() { return positionId; }
    public void setPositionId(int positionId) { this.positionId = positionId; }
    
    public String getPositionName() { return positionName; }
    public void setPositionName(String positionName) { this.positionName = positionName; }
    
    public int getDegreeId() { return degreeId; }
    public void setDegreeId(int degreeId) { this.degreeId = degreeId; }
    
    public String getDegreeName() { return degreeName; }
    public void setDegreeName(String degreeName) { this.degreeName = degreeName; }
    
    // Helper methods để kiểm tra role
    public boolean isHRManager() {
        return "HR Manager".equalsIgnoreCase(roleName) || roleId == 1;
    }
    
    public boolean isHR() {
        return "HR".equalsIgnoreCase(roleName) || roleId == 2;
    }
    
    public boolean isDepartmentManager() {
        return "Department Manager".equalsIgnoreCase(roleName) || roleId == 3;
    }
    
    public boolean isEmployee() {
        return "Employee".equalsIgnoreCase(roleName) || roleId == 4;
    }
    
    public boolean canEditProfile(int targetUserId) {
        // HR Manager có thể edit tất cả
        if (isHRManager()) return true;
        
        // HR có thể edit department manager và employee
        if (isHR()) return true;
        
        // User chỉ có thể edit chính mình
        return this.userId == targetUserId;
    }
}