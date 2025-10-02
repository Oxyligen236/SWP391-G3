package hrms.model.profile;

import java.util.Date;

public class User {
    private int userID;
    private String fullName;
    private Date birthDate;
    private String gender;
    private String email;
    private String phoneNumber;
    private String address;
    private String cccd;
    private String ethnicity;
    private String nation;
    private int departmentID;
    private int degreeID;
    private int positionID;

    // Getters & Setters
    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCccd() { return cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }

    public String getEthnicity() { return ethnicity; }
    public void setEthnicity(String ethnicity) { this.ethnicity = ethnicity; }

    public String getNation() { return nation; }
    public void setNation(String nation) { this.nation = nation; }

    public int getDepartmentID() { return departmentID; }
    public void setDepartmentID(int departmentID) { this.departmentID = departmentID; }

    public int getDegreeID() { return degreeID; }
    public void setDegreeID(int degreeID) { this.degreeID = degreeID; }

    public int getPositionID() { return positionID; }
    public void setPositionID(int positionID) { this.positionID = positionID; }
}
