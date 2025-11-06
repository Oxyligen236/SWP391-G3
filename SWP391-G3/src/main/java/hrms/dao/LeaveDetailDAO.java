package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import hrms.model.LeaveDetail;
import hrms.utils.DBContext;

public class LeaveDetailDAO extends DBContext {

    public boolean add(LeaveDetail detail) {
        String sql = "INSERT INTO LeaveDetail (TicketID, Start_Date, End_Date, LeaveTypeID) "
                + "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, detail.getTicketID());
            st.setDate(2, java.sql.Date.valueOf(detail.getStart_Date()));
            st.setDate(3, java.sql.Date.valueOf(detail.getEnd_Date()));
            st.setInt(4, detail.getLeaveTypeID());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in LeaveDetailDAO.add: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public LeaveDetail getByTicketId(int ticketId) {
        String sql = "SELECT * FROM LeaveDetail WHERE TicketID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, ticketId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                LeaveDetail detail = new LeaveDetail();
                detail.setTicketID(rs.getInt("TicketID"));
                detail.setStart_Date(rs.getDate("Start_Date").toLocalDate());
                detail.setEnd_Date(rs.getDate("End_Date").toLocalDate());
                detail.setLeaveTypeID(rs.getInt("LeaveTypeID"));
                return detail;
            }
        } catch (SQLException e) {
            System.err.println("Error in LeaveDetailDAO.getByTicketId: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<LeaveDetail> getAll() {
        List<LeaveDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM LeaveDetail ORDER BY Start_Date DESC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                LeaveDetail detail = new LeaveDetail();
                detail.setTicketID(rs.getInt("TicketID"));
                detail.setStart_Date(rs.getDate("Start_Date").toLocalDate());
                detail.setEnd_Date(rs.getDate("End_Date").toLocalDate());
                detail.setLeaveTypeID(rs.getInt("LeaveTypeID"));
                list.add(detail);
            }
        } catch (SQLException e) {
            System.err.println("Error in LeaveDetailDAO.getAll: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(LeaveDetail detail) {
        String sql = "UPDATE LeaveDetail SET Start_Date = ?, End_Date = ?, LeaveTypeID = ? "
                + "WHERE TicketID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setDate(1, java.sql.Date.valueOf(detail.getStart_Date()));
            st.setDate(2, java.sql.Date.valueOf(detail.getEnd_Date()));
            st.setInt(3, detail.getLeaveTypeID());
            st.setInt(4, detail.getTicketID());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in LeaveDetailDAO.update: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int ticketId) {
        String sql = "DELETE FROM LeaveDetail WHERE TicketID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, ticketId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in LeaveDetailDAO.delete: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<LeaveDetail> getByUserId(int userId) {
        List<LeaveDetail> list = new ArrayList<>();
        String sql = "SELECT ld.* FROM LeaveDetail ld "
                + "JOIN Ticket t ON ld.TicketID = t.TicketID "
                + "WHERE t.UserID = ? "
                + "ORDER BY ld.Start_Date DESC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                LeaveDetail detail = new LeaveDetail();
                detail.setTicketID(rs.getInt("TicketID"));
                detail.setStart_Date(rs.getDate("Start_Date").toLocalDate());
                detail.setEnd_Date(rs.getDate("End_Date").toLocalDate());
                detail.setLeaveTypeID(rs.getInt("LeaveTypeID"));
                list.add(detail);
            }
        } catch (SQLException e) {
            System.err.println("Error in LeaveDetailDAO.getByUserId: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public List<LeaveDetail> getByDateRange(LocalDate startDate, LocalDate endDate) {
        List<LeaveDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM LeaveDetail "
                + "WHERE Start_Date >= ? AND End_Date <= ? "
                + "ORDER BY Start_Date";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setDate(1, java.sql.Date.valueOf(startDate));
            st.setDate(2, java.sql.Date.valueOf(endDate));
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                LeaveDetail detail = new LeaveDetail();
                detail.setTicketID(rs.getInt("TicketID"));
                detail.setStart_Date(rs.getDate("Start_Date").toLocalDate());
                detail.setEnd_Date(rs.getDate("End_Date").toLocalDate());
                detail.setLeaveTypeID(rs.getInt("LeaveTypeID"));
                list.add(detail);
            }
        } catch (SQLException e) {
            System.err.println("Error in LeaveDetailDAO.getByDateRange: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public List<LeaveDetail> getByLeaveTypeID(int leaveTypeID) {
        List<LeaveDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM LeaveDetail WHERE LeaveTypeID = ? ORDER BY Start_Date DESC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, leaveTypeID);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                LeaveDetail detail = new LeaveDetail();
                detail.setTicketID(rs.getInt("TicketID"));
                detail.setStart_Date(rs.getDate("Start_Date").toLocalDate());
                detail.setEnd_Date(rs.getDate("End_Date").toLocalDate());
                detail.setLeaveTypeID(rs.getInt("LeaveTypeID"));
                list.add(detail);
            }
        } catch (SQLException e) {
            System.err.println("Error in LeaveDetailDAO.getByLeaveTypeID: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public boolean hasOverlappingLeave(int userId, LocalDate startDate, LocalDate endDate, int excludeTicketId) {
        String sql = "SELECT COUNT(*) FROM LeaveDetail ld "
                + "JOIN Ticket t ON ld.TicketID = t.TicketID "
                + "WHERE t.UserID = ? "
                + "AND ld.TicketID != ? "
                + "AND t.Status != 'Rejected' "
                + "AND ((ld.Start_Date BETWEEN ? AND ?) "
                + "OR (ld.End_Date BETWEEN ? AND ?) "
                + "OR (? BETWEEN ld.Start_Date AND ld.End_Date))";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, excludeTicketId);
            st.setDate(3, java.sql.Date.valueOf(startDate));
            st.setDate(4, java.sql.Date.valueOf(endDate));
            st.setDate(5, java.sql.Date.valueOf(startDate));
            st.setDate(6, java.sql.Date.valueOf(endDate));
            st.setDate(7, java.sql.Date.valueOf(startDate));

            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error in LeaveDetailDAO.hasOverlappingLeave: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public long calculateLeaveDays(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate) + 1;
    }

    public long getTotalLeaveDaysByUserAndYear(int userId, int year) {
        String sql = "SELECT ld.Start_Date, ld.End_Date "
                + "FROM LeaveDetail ld "
                + "JOIN Ticket t ON ld.TicketID = t.TicketID "
                + "WHERE t.UserID = ? AND t.Status = 'Approved' "
                + "AND YEAR(ld.Start_Date) = ?";
        long totalDays = 0;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                LocalDate startDate = rs.getDate("Start_Date").toLocalDate();
                LocalDate endDate = rs.getDate("End_Date").toLocalDate();
                totalDays += calculateLeaveDays(startDate, endDate);
            }
        } catch (SQLException e) {
            System.err.println("Error in LeaveDetailDAO.getTotalLeaveDaysByUserAndYear: " + e.getMessage());
            e.printStackTrace();
        }
        return totalDays;
    }
}
