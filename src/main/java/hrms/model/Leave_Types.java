package hrms.model;

public class Leave_Types {

    private int LeaveTypeID;
    private String LeaveTypeName;

    public Leave_Types() {
    }

    public Leave_Types(int leaveTypeID, String leaveTypeName) {
        LeaveTypeID = leaveTypeID;
        LeaveTypeName = leaveTypeName;
    }

    public int getLeaveTypeID() {
        return LeaveTypeID;
    }

    public void setLeaveTypeID(int leaveTypeID) {
        LeaveTypeID = leaveTypeID;
    }

    public String getLeaveTypeName() {
        return LeaveTypeName;
    }

    public void setLeaveTypeName(String leaveTypeName) {
        LeaveTypeName = leaveTypeName;
    }

}
