package hrms.model;

public class Account {
<<<<<<< HEAD
    private int accountID;
    private int userID;
    private int roleID;
    private String username;
    private String password;
    private boolean isActive;

    public Account() {}

    public int getAccountID() { return accountID; }
    public void setAccountID(int accountID) { this.accountID = accountID; }

    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public int getRoleID() { return roleID; }
    public void setRoleID(int roleID) { this.roleID = roleID; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }
=======

    private int accountID;
    private int userID;
    private String username;
    private String password;
    private int role;
    private boolean isActive;

    public Account() {
    }

    public Account(int accountID, int userID, String username, String password, int role, boolean isActive) {
        this.accountID = accountID;
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
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

>>>>>>> 10fe2e1d1659060b47d0e575b95baeb50b1f6f37
}
