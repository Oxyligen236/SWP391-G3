package hrms.model;

public class CVs {

    int cvID;
    int jdID;
    String name;
    String email;
    String phone;
    String cv_Description;
    String status;

    public CVs() {
    }

    public CVs(int cvID, int jdID, String name, String email, String phone, String cv_Description, String status) {
        this.cvID = cvID;
        this.jdID = jdID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cv_Description = cv_Description;
        this.status = status;
    }

    public int getCVid() {
        return cvID;
    }

    public void setCVid(int cVid) {
        cvID = cVid;
    }

    public int getJDid() {
        return jdID;
    }

    public void setJDid(int jDid) {
        jdID = jDid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getCV_Description() {
        return cv_Description;
    }

    public void setCV_Description(String cV_Description) {
        cv_Description = cV_Description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
