package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Ticket_Types;
import hrms.utils.DBContext;

public class Ticket_TypesDAO extends DBContext {
    
    /**
     * Lấy Ticket Type theo ID
     * @param id - Ticket_Type_ID
     * @return Ticket_Types object hoặc null nếu không tìm thấy
     */
    public Ticket_Types getTicketTypeById(int id) {
        String sql = "SELECT Ticket_Type_ID, Name FROM Ticket_Types WHERE Ticket_Type_ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                Ticket_Types ticketType = new Ticket_Types();
                ticketType.setTicket_Type_ID(rs.getInt("Ticket_Type_ID"));
                ticketType.setName(rs.getString("Name"));
                return ticketType;
            }
        } catch (SQLException e) {
            System.err.println("Error in getTicketTypeById: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Lấy tất cả Ticket Types
     * @return List<Ticket_Types>
     */
    public List<Ticket_Types> getAllTicketTypes() {
        List<Ticket_Types> list = new ArrayList<>();
        String sql = "SELECT Ticket_Type_ID, Name FROM Ticket_Types ORDER BY Ticket_Type_ID";
        
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                Ticket_Types ticketType = new Ticket_Types();
                ticketType.setTicket_Type_ID(rs.getInt("Ticket_Type_ID"));
                ticketType.setName(rs.getString("Name"));
                list.add(ticketType);
            }
        } catch (SQLException e) {
            System.err.println("Error in getAllTicketTypes: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Thêm Ticket Type mới
     * @param ticketType - Ticket_Types object
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean addTicketType(Ticket_Types ticketType) {
        String sql = "INSERT INTO Ticket_Types (Name) VALUES (?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, ticketType.getName());
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error in addTicketType: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Cập nhật Ticket Type
     * @param ticketType - Ticket_Types object
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean updateTicketType(Ticket_Types ticketType) {
        String sql = "UPDATE Ticket_Types SET Name = ? WHERE Ticket_Type_ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, ticketType.getName());
            st.setInt(2, ticketType.getTicket_Type_ID());
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error in updateTicketType: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Xóa Ticket Type theo ID
     * @param id - Ticket_Type_ID
     * @return true nếu thành công, false nếu thất bại
     */
    public boolean deleteTicketType(int id) {
        String sql = "DELETE FROM Ticket_Types WHERE Ticket_Type_ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error in deleteTicketType: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}