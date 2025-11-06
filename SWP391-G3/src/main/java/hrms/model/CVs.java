package hrms.model;

import java.time.LocalDate;

public class CVs {

    private int cvID;
    private int jdID;
    private String name;
    private LocalDate dob;
    private String gender;
    private String address;
    private String nationality;
    private String email;
    private String phone;
    private String experience;
    private String education;
    private String skills;
    private String aboutMe;
    private String status;

    // Constructor mặc định
    public CVs() {
    }

    // Constructor đọc CV từ DB
    public CVs(int cvID, int jdID, String name, LocalDate dob, String gender, String address, String nationality, String email,
            String phone, String experience, String education, String skills, String aboutMe, String status) {
        this.cvID = cvID;
        this.jdID = jdID;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.nationality = nationality;
        this.email = email;
        this.phone = phone;
        this.experience = experience;
        this.education = education;
        this.skills = skills;
        this.aboutMe = aboutMe;
        this.status = status;
    }

    // Constructor nộp CV mới
    public CVs(int jdID, String name, LocalDate dob, String gender, String address, String nationality, String email,
            String phone, String experience, String education, String skills, String aboutMe, String status) {
        this.jdID = jdID;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.nationality = nationality;
        this.email = email;
        this.phone = phone;
        this.experience = experience;
        this.education = education;
        this.skills = skills;
        this.aboutMe = aboutMe;
        this.status = status;
    }

    public int getCvID() {
        return cvID;
    }

    public void setCvID(int cvID) {
        this.cvID = cvID;
    }

    public int getJdID() {
        return jdID;
    }

    public void setJdID(int jdID) {
        this.jdID = jdID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
