package hrms.dto;

import java.sql.Date;

public class UserDTO {
    // ===== Basic Info =====
    private int userId;
    private String fullname;
    private String email;
    private String phoneNumber;
    private Date birthDate;
    private String gender;
    private String address;
    private String nation;
    private String ethnicity;

    // ===== Related Info =====
    private Integer departmentId;
    private String departmentName;  // From Department table

    private Integer positionId;
    private String positionName;    // From Positions table

    private Integer degreeId;
    private String degreeName;      // From Degree table

    // ===== Constructors =====
    public UserDTO() {}

    public UserDTO(int userId, String fullname, String email,
                   String phoneNumber, Date birthDate, String gender, String address,
                   String nation, String ethnicity,
                   Integer departmentId, String departmentName,
                   Integer positionId, String positionName,
                   Integer degreeId, String degreeName) {
        this.userId = userId;
        this.fullname = fullname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
        this.nation = nation;
        this.ethnicity = ethnicity;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.positionId = positionId;
        this.positionName = positionName;
        this.degreeId = degreeId;
        this.degreeName = degreeName;
    }

    // ===== Getters & Setters =====
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

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

    public String getNation() { return nation; }
    public void setNation(String nation) { this.nation = nation; }

    public String getEthnicity() { return ethnicity; }
    public void setEthnicity(String ethnicity) { this.ethnicity = ethnicity; }

    public Integer getDepartmentId() { return departmentId; }
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public Integer getPositionId() { return positionId; }
    public void setPositionId(Integer positionId) { this.positionId = positionId; }

    public String getPositionName() { return positionName; }
    public void setPositionName(String positionName) { this.positionName = positionName; }

    public Integer getDegreeId() { return degreeId; }
    public void setDegreeId(Integer degreeId) { this.degreeId = degreeId; }

    public String getDegreeName() { return degreeName; }
    public void setDegreeName(String degreeName) { this.degreeName = degreeName; }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", nation='" + nation + '\'' +
                ", ethnicity='" + ethnicity + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", positionName='" + positionName + '\'' +
                ", degreeName='" + degreeName + '\'' +
                '}';
    }
}
