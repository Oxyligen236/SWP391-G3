package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Ticket;
import hrms.utils.DBContext;

public class TicketDAO extends DBContext {
    private Ticket extractTicketFromResultSet(ResultSet rs) throws SQLException {
        return new Ticket(
                rs.getInt("ticketID"),
                rs.getInt("userID"),
                rs.getInt("ticket_Type_ID"),
                rs.getDate("create_Date"),
                rs.getString("ticket_Content"),
                rs.getInt("approveID"),
                rs.getDate("approve_Date"),
                rs.getString("comment"),
                rs.getString("status"));
    }

    public List<Ticket> getAll() {
        List<Ticket> list = new ArrayList<>();
        String sql = "select * from Ticket";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Ticket t = extractTicketFromResultSet(rs);
                list.add(t);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public Ticket getTicketById(int id) {
        String sql = "select * from Ticket where ticketID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return extractTicketFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void addTicket(Ticket t) {
        String sql = "insert into Ticket (userID, ticket_Type_ID, create_Date, ticket_Content, approveID, approve_Date, comment, status) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, t.getUserID());
            st.setInt(2, t.getTicket_Type_ID());
            st.setDate(3, new java.sql.Date(t.getCreate_Date().getTime()));
            st.setString(4, t.getTicket_Content());
            if (t.getApproveID() != 0) {
                st.setInt(5, t.getApproveID());
            } else {
                st.setNull(5, java.sql.Types.INTEGER);
            }
            if (t.getApprove_Date() != null) {
                st.setDate(6, new java.sql.Date(t.getApprove_Date().getTime()));
            } else {
                st.setNull(6, java.sql.Types.DATE);
            }
            st.setString(7, t.getComment());
            st.setString(8, t.getStatus());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void updateTicket(Ticket t) {
        String sql = "update Ticket set userID = ?, ticket_Type_ID = ?, create_Date = ?, ticket_Content = ?, approveID = ?, approve_Date = ?, comment = ?, status = ? where ticketID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, t.getUserID());
            st.setInt(2, t.getTicket_Type_ID());
            st.setDate(3, new java.sql.Date(t.getCreate_Date().getTime()));
            st.setString(4, t.getTicket_Content());
            if (t.getApproveID() != 0) {
                st.setInt(5, t.getApproveID());
            } else {
                st.setNull(5, java.sql.Types.INTEGER);
            }
            if (t.getApprove_Date() != null) {
                st.setDate(6, new java.sql.Date(t.getApprove_Date().getTime()));
            } else {
                st.setNull(6, java.sql.Types.DATE);
            }
            st.setString(7, t.getComment());
            st.setString(8, t.getStatus());
            st.setInt(9, t.getTicketID());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    


}
