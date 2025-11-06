package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Leave_Types;
import hrms.utils.DBContext;

public class Leave_TypesDAO extends DBContext {

    public List<Leave_Types> getAllLeaveTypes() {
        List<Leave_Types> list = new ArrayList<>();
        String sql = "SELECT LeaveTypeID, LeaveTypeName FROM Leave_Types";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Leave_Types lt = new Leave_Types();
                lt.setLeaveTypeID(rs.getInt("LeaveTypeID"));
                lt.setLeaveTypeName(rs.getString("LeaveTypeName"));
                list.add(lt);
            }
        } catch (SQLException e) {
            System.err.println("Error in Leave_TypesDAO.getAllLeaveTypes: " + e.getMessage());
        }
        return list;
    }

    public Leave_Types getLeaveTypeById(int id) {
        String sql = "SELECT LeaveTypeID, LeaveTypeName FROM Leave_Types WHERE LeaveTypeID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Leave_Types(rs.getInt("LeaveTypeID"), rs.getString("LeaveTypeName"));
            }
        } catch (SQLException e) {
            System.err.println("Error in Leave_TypesDAO.getLeaveTypeById: " + e.getMessage());
        }
        return null;
    }

    public boolean addLeaveType(Leave_Types leaveType) {
        String sql = "INSERT INTO Leave_Types (LeaveTypeName) VALUES (?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, leaveType.getLeaveTypeName());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in Leave_TypesDAO.addLeaveType: " + e.getMessage());
        }
        return false;
    }

    public boolean updateLeaveType(Leave_Types leaveType) {
        String sql = "UPDATE Leave_Types SET LeaveTypeName = ? WHERE LeaveTypeID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, leaveType.getLeaveTypeName());
            st.setInt(2, leaveType.getLeaveTypeID());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in Leave_TypesDAO.updateLeaveType: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteLeaveType(int id) {
        String sql = "DELETE FROM Leave_Types WHERE LeaveTypeID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in Leave_TypesDAO.deleteLeaveType: " + e.getMessage());
        }
        return false;
    }
}
