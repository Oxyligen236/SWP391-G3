package hrms.model;

import java.sql.Date;

public class User {
    private int userId;

    private String fullname;
    private Date birthDate;
    private String gender;
    private String email;
    private String phoneNumber;
    private String cccd;
    private String address;
    private String ethnicity;
    private String nation;

    private Integer degreeId;
    private Integer positionId;
    private Integer departmentId;

    public User() {
    }

    public User(int userId, String fullname, String email) {
        this.userId = userId;
        this.fullname = fullname;
        this.email = email;
    }

    public User(int userId, String fullname, Date birthDate, String gender,
            String email, String phoneNumber, String cccd, String address,
            String ethnicity, String nation,
            Integer degreeId, Integer positionId, Integer departmentId) {
        this.userId = userId;
        this.fullname = fullname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.cccd = cccd;
        this.address = address;
        this.ethnicity = ethnicity;
        this.nation = nation;
        this.degreeId = degreeId;
        this.positionId = positionId;
        this.departmentId = departmentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Integer getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(Integer degreeId) {
        this.degreeId = degreeId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", fullname='" + fullname + '\'' +
                ", birthDate=" + birthDate +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", cccd='" + cccd + '\'' +
                ", address='" + address + '\'' +
                ", ethnicity='" + ethnicity + '\'' +
                ", nation='" + nation + '\'' +
                ", degreeId=" + degreeId +
                ", positionId=" + positionId +
                ", departmentId=" + departmentId +
                '}';
    }
}