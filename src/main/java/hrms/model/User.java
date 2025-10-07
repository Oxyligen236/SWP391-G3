package hrms.model;

import java.sql.Date;

public class User {
    // Primary Key
    private int userId;

    // Basic Information
    private String fullname;
    private String email;
    private String phoneNumber;
    private Date birthDate;
    private String gender;
    private String address;
    private String nation;
    private String ethnicity;

    // Department & Position
    private Integer departmentId;
    private Integer positionId;

    // Degree
    private Integer degreeId;

    // Constructors
    public User() {}

    // Constructor rút gọn
    public User(int userId, String fullname, String email) {
        this.userId = userId;
        this.fullname = fullname;
        this.email = email;
    }

    // Full constructor
    public User(int userId, String fullname, String email,
                String phoneNumber, Date birthDate, String gender, String address,
                String nation, String ethnicity, Integer departmentId,
                Integer positionId, Integer degreeId) {
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
        this.positionId = positionId;
        this.degreeId = degreeId;
    }

    // Getters & Setters
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

    public Integer getPositionId() { return positionId; }
    public void setPositionId(Integer positionId) { this.positionId = positionId; }

    public Integer getDegreeId() { return degreeId; }
    public void setDegreeId(Integer degreeId) { this.degreeId = degreeId; }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", fullname='" + fullname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                ", gender='" + gender + '\'' +
                ", address='" + address + '\'' +
                ", nation='" + nation + '\'' +
                ", ethnicity='" + ethnicity + '\'' +
                ", departmentId=" + departmentId +
                ", positionId=" + positionId +
                ", degreeId=" + degreeId +
                '}';
    }
}
