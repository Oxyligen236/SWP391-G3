package hrms.dto;

import java.time.LocalDate;

public class CVJobDetailDTO {

    private int cvID;
    private int jdID;
    private String jdTitle;
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

    public CVJobDetailDTO() {
    }

    public CVJobDetailDTO(int cvID, int jdID, String jdTitle, String name, LocalDate dob, String gender, String address,
            String nationality, String email, String phone, String experience, String education, String skills,
            String aboutMe, String status) {
        this.cvID = cvID;
        this.jdID = jdID;
        this.jdTitle = jdTitle;
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

    public String getJdTitle() {
        return jdTitle;
    }

    public void setJdTitle(String jdTitle) {
        this.jdTitle = jdTitle;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
