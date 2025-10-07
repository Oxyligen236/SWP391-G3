package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import hrms.model.OTDetail;
import hrms.utils.DBContext;

public class OTDetailDAO extends DBContext {
    public boolean add(OTDetail detail) {
        String sql = "INSERT INTO OTDetail (TicketID, OT_Date, Start_Time, End_Time) " +
                     "VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, detail.getTicketID());
            st.setDate(2, java.sql.Date.valueOf(detail.getOt_Date()));
            st.setTime(3, java.sql.Time.valueOf(detail.getStart_Time()));
            st.setTime(4, java.sql.Time.valueOf(detail.getEnd_Time()));
            
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in OTDetailDAO.add: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
 
    public List<OTDetail> getByTicketId(int ticketId) {
        List<OTDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM OTDetail WHERE TicketID = ? ORDER BY OT_Date, Start_Time";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, ticketId);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                OTDetail detail = new OTDetail();
                detail.setTicketID(rs.getInt("TicketID"));
                detail.setOt_Date(rs.getDate("OT_Date").toLocalDate());
                detail.setStart_Time(rs.getTime("Start_Time").toLocalTime());
                detail.setEnd_Time(rs.getTime("End_Time").toLocalTime());
                list.add(detail);
            }
        } catch (SQLException e) {
            System.err.println("Error in OTDetailDAO.getByTicketId: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
    
    
    public OTDetail getByTicketIdAndDate(int ticketId, LocalDate otDate) {
        String sql = "SELECT * FROM OTDetail WHERE TicketID = ? AND OT_Date = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, ticketId);
            st.setDate(2, java.sql.Date.valueOf(otDate));
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                OTDetail detail = new OTDetail();
                detail.setTicketID(rs.getInt("TicketID"));
                detail.setOt_Date(rs.getDate("OT_Date").toLocalDate());
                detail.setStart_Time(rs.getTime("Start_Time").toLocalTime());
                detail.setEnd_Time(rs.getTime("End_Time").toLocalTime());
                return detail;
            }
        } catch (SQLException e) {
            System.err.println("Error in OTDetailDAO.getByTicketIdAndDate: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    

    public List<OTDetail> getAll() {
        List<OTDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM OTDetail ORDER BY OT_Date DESC, Start_Time";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                OTDetail detail = new OTDetail();
                detail.setTicketID(rs.getInt("TicketID"));
                detail.setOt_Date(rs.getDate("OT_Date").toLocalDate());
                detail.setStart_Time(rs.getTime("Start_Time").toLocalTime());
                detail.setEnd_Time(rs.getTime("End_Time").toLocalTime());
                list.add(detail);
            }
        } catch (SQLException e) {
            System.err.println("Error in OTDetailDAO.getAll: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
    
  
    public boolean update(OTDetail detail) {
        String sql = "UPDATE OTDetail SET Start_Time = ?, End_Time = ? " +
                     "WHERE TicketID = ? AND OT_Date = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setTime(1, java.sql.Time.valueOf(detail.getStart_Time()));
            st.setTime(2, java.sql.Time.valueOf(detail.getEnd_Time()));
            st.setInt(3, detail.getTicketID());
            st.setDate(4, java.sql.Date.valueOf(detail.getOt_Date()));
            
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in OTDetailDAO.update: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
 
    public boolean deleteByTicketId(int ticketId) {
        String sql = "DELETE FROM OTDetail WHERE TicketID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, ticketId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in OTDetailDAO.deleteByTicketId: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
  
    public boolean delete(int ticketId, LocalDate otDate) {
        String sql = "DELETE FROM OTDetail WHERE TicketID = ? AND OT_Date = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, ticketId);
            st.setDate(2, java.sql.Date.valueOf(otDate));
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error in OTDetailDAO.delete: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
  
    public List<OTDetail> getByUserId(int userId) {
        List<OTDetail> list = new ArrayList<>();
        String sql = "SELECT od.* FROM OTDetail od " +
                     "JOIN Ticket t ON od.TicketID = t.TicketID " +
                     "WHERE t.UserID = ? " +
                     "ORDER BY od.OT_Date DESC, od.Start_Time";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                OTDetail detail = new OTDetail();
                detail.setTicketID(rs.getInt("TicketID"));
                detail.setOt_Date(rs.getDate("OT_Date").toLocalDate());
                detail.setStart_Time(rs.getTime("Start_Time").toLocalTime());
                detail.setEnd_Time(rs.getTime("End_Time").toLocalTime());
                list.add(detail);
            }
        } catch (SQLException e) {
            System.err.println("Error in OTDetailDAO.getByUserId: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
    

    public List<OTDetail> getByMonth(int month, int year) {
        List<OTDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM OTDetail " +
                     "WHERE MONTH(OT_Date) = ? AND YEAR(OT_Date) = ? " +
                     "ORDER BY OT_Date, Start_Time";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, month);
            st.setInt(2, year);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                OTDetail detail = new OTDetail();
                detail.setTicketID(rs.getInt("TicketID"));
                detail.setOt_Date(rs.getDate("OT_Date").toLocalDate());
                detail.setStart_Time(rs.getTime("Start_Time").toLocalTime());
                detail.setEnd_Time(rs.getTime("End_Time").toLocalTime());
                list.add(detail);
            }
        } catch (SQLException e) {
            System.err.println("Error in OTDetailDAO.getByMonth: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
    
 
    public List<OTDetail> getByDateRange(LocalDate startDate, LocalDate endDate) {
        List<OTDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM OTDetail " +
                     "WHERE OT_Date BETWEEN ? AND ? " +
                     "ORDER BY OT_Date, Start_Time";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setDate(1, java.sql.Date.valueOf(startDate));
            st.setDate(2, java.sql.Date.valueOf(endDate));
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                OTDetail detail = new OTDetail();
                detail.setTicketID(rs.getInt("TicketID"));
                detail.setOt_Date(rs.getDate("OT_Date").toLocalDate());
                detail.setStart_Time(rs.getTime("Start_Time").toLocalTime());
                detail.setEnd_Time(rs.getTime("End_Time").toLocalTime());
                list.add(detail);
            }
        } catch (SQLException e) {
            System.err.println("Error in OTDetailDAO.getByDateRange: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
    

    public double getTotalOTHoursByUserAndMonth(int userId, int month, int year) {
        String sql = "SELECT od.Start_Time, od.End_Time " +
                     "FROM OTDetail od " +
                     "JOIN Ticket t ON od.TicketID = t.TicketID " +
                     "WHERE t.UserID = ? AND t.Status = 'Approved' " +
                     "AND MONTH(od.OT_Date) = ? AND YEAR(od.OT_Date) = ?";
        double totalHours = 0.0;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, month);
            st.setInt(3, year);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                OTDetail detail = new OTDetail();
                detail.setStart_Time(rs.getTime("Start_Time").toLocalTime());
                detail.setEnd_Time(rs.getTime("End_Time").toLocalTime());
                totalHours += detail.getTotalHours();
            }
        } catch (SQLException e) {
            System.err.println("Error in OTDetailDAO.getTotalOTHoursByUserAndMonth: " + e.getMessage());
            e.printStackTrace();
        }
        return totalHours;
    }
    

    public boolean hasOverlappingOT(int userId, LocalDate otDate, LocalTime startTime, 
                                   LocalTime endTime, int excludeTicketId) {
        String sql = "SELECT COUNT(*) FROM OTDetail od " +
                     "JOIN Ticket t ON od.TicketID = t.TicketID " +
                     "WHERE t.UserID = ? " +
                     "AND od.TicketID != ? " +
                     "AND t.Status != 'Rejected' " +
                     "AND od.OT_Date = ? " +
                     "AND ((od.Start_Time BETWEEN ? AND ?) " +
                     "OR (od.End_Time BETWEEN ? AND ?) " +
                     "OR (? BETWEEN od.Start_Time AND od.End_Time))";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, excludeTicketId);
            st.setDate(3, java.sql.Date.valueOf(otDate));
            st.setTime(4, java.sql.Time.valueOf(startTime));
            st.setTime(5, java.sql.Time.valueOf(endTime));
            st.setTime(6, java.sql.Time.valueOf(startTime));
            st.setTime(7, java.sql.Time.valueOf(endTime));
            st.setTime(8, java.sql.Time.valueOf(startTime));
            
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error in OTDetailDAO.hasOverlappingOT: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
