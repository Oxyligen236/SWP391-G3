package hrms.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Ticket;
import hrms.utils.DBContext;

public class TicketDAO extends DBContext {

    private Ticket extractTicketFromResultSet(ResultSet rs) throws SQLException {
        Date createDate = rs.getDate("Create_Date");
        Date approveDate = rs.getDate("Approve_Date");

        return new Ticket(
                rs.getInt("TicketID"),
                rs.getInt("UserID"),
                rs.getInt("Ticket_Type_ID"),
                createDate != null ? createDate.toLocalDate() : null,
                rs.getString("Ticket_Content"),
                rs.getInt("ApproverID"),
                approveDate != null ? approveDate.toLocalDate() : null,
                rs.getString("Comment"),
                rs.getString("Status")
        );
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

    public int add(Ticket t) {

        String sql = "insert into Ticket (userID, ticket_Type_ID, create_Date, ticket_Content, status) values (?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setInt(1, t.getUserID());
            st.setInt(2, t.getTicket_Type_ID());

            if (t.getCreate_Date() != null) {
                st.setDate(3, Date.valueOf(t.getCreate_Date()));
            } else {
                st.setDate(3, Date.valueOf(LocalDate.now()));
            }

            st.setString(4, t.getTicket_Content());
            st.setString(5, t.getStatus());

            int affectedRows = st.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    System.out.println("Generated TicketID: " + generatedId);
                    return generatedId;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in TicketDAO.add: " + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    public boolean update(Ticket t) {

        String sql = "update Ticket set userID = ?, ticket_Type_ID = ?, create_Date = ?, ticket_Content = ?, ApproverID = ?, approve_Date = ?, comment = ?, status = ? where ticketID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, t.getUserID());
            st.setInt(2, t.getTicket_Type_ID());

            if (t.getCreate_Date() != null) {
                st.setDate(3, Date.valueOf(t.getCreate_Date()));
            } else {
                st.setNull(3, java.sql.Types.DATE);
            }

            st.setString(4, t.getTicket_Content());

            if (t.getApproverID() != 0) {
                st.setInt(5, t.getApproverID());
            } else {
                st.setNull(5, java.sql.Types.INTEGER);
            }

            if (t.getApprove_Date() != null) {
                st.setDate(6, Date.valueOf(t.getApprove_Date()));
            } else {
                st.setNull(6, java.sql.Types.DATE);
            }

            st.setString(7, t.getComment());
            st.setString(8, t.getStatus());
            st.setInt(9, t.getTicketID());
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public List<Ticket> getTicketsByUserId(int userId) {
        List<Ticket> list = new ArrayList<>();
        String sql = "select * from Ticket where userID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
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

    public List<Ticket> getTicketsByApproveID(int approveID) {
        List<Ticket> list = new ArrayList<>();
        // Sửa: approveID → ApproverID
        String sql = "select * from Ticket where ApproverID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, approveID);
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

    public List<Ticket> getTicketsByDepartmentId(int departmentId) {
        List<Ticket> list = new ArrayList<>();
        String sql = "SELECT t.* FROM Ticket t "
                + "JOIN `Users` u ON t.UserID = u.UserID "
                + "WHERE u.DepartmentID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, departmentId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Ticket t = extractTicketFromResultSet(rs);
                list.add(t);
            }
        } catch (SQLException e) {
            System.out.println("Error in getTicketsByDepartmentId: " + e.getMessage());
        }
        return list;
    }
}
