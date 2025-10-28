package hrms.model;

public class Account {

    private int accountID;
    private int userID;
    private String username;
    private String password;
    private int role;
    private boolean isActive;
    private String googleEmail;

    public Account() {
    }

    public Account(int accountID, int userID, String username, String password, int role, boolean isActive, String googleEmail) {
        this.accountID = accountID;
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
        this.googleEmail = googleEmail;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public String getGoogleEmail() {
        return googleEmail;
    }

    public void setGoogleEmail(String googleEmail) {
        this.googleEmail = googleEmail;
    }
}
