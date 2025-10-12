package hrms.dto;

public class CVJobDetailDTO {

    private int cvID;
    private int jdID;
    private String jdTitle;
    private String name;
    private String gender;
    private String address;
    private String nationality;
    private String email;
    private String phone;

    private String cv_Description;
    private String status;

    public CVJobDetailDTO() {
    }

    public CVJobDetailDTO(int cvID, int jdID, String jdTitle, String name, String gender, String address, String nationality, String email, String phone, String cv_Description, String status) {
        this.cvID = cvID;
        this.jdID = jdID;
        this.jdTitle = jdTitle;
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.nationality = nationality;
        this.email = email;
        this.phone = phone;
        this.cv_Description = cv_Description;
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

    public String getCv_Description() {
        return cv_Description;
    }

    public void setCv_Description(String cv_Description) {
        this.cv_Description = cv_Description;
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
