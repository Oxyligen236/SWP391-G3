package hrms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.WorkHistory;
import hrms.utils.DBContext;

public class WorkHistoryDAO extends DBContext {

    private WorkHistory extractFromResultSet(ResultSet rs) throws SQLException {
        return new WorkHistory(
                rs.getInt("HistoryID"),
                rs.getInt("UserID"),
                rs.getString("Type"),
                rs.getDate("Effective_Date").toLocalDate(),
                rs.getString("Old_Value"),
                rs.getString("New_Value"),
                rs.getString("Description"),
                rs.getString("Evaluate")
        );
    }

    public List<WorkHistory> getAll() {
        List<WorkHistory> list = new ArrayList<>();
        String sql = "SELECT * FROM WorkHistory";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<WorkHistory> getByUserId(int userId) {
        List<WorkHistory> list = new ArrayList<>();
        String sql = "SELECT * FROM WorkHistory WHERE UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(extractFromResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<WorkHistory> getFilteredHistory(
            String search) throws SQLException {

        List<WorkHistory> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT * FROM WorkHistory WHERE 1=1 "
        );

        if (search != null && !search.trim().isEmpty()) {
            sql.append(" AND Type LIKE ? ");
        }

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int index = 1;

            if (search != null && !search.trim().isEmpty()) {
                ps.setString(index++, "%" + search + "%");
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                WorkHistory wh = new WorkHistory();
                wh.setHistoryID(rs.getInt("HistoryID"));
                wh.setUserID(rs.getInt("UserID"));
                wh.setType(rs.getString("type"));
                wh.setDescription(rs.getString("description"));
                wh.setOldValue(rs.getString("old_Value"));
                wh.setNewValue(rs.getString("new_Value"));
                wh.setEvaluate(rs.getString("evaluate"));
                wh.setEffectiveDate(rs.getDate("effective_Date").toLocalDate());

                list.add(wh);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean addWorkHistory(int userID, String type, String oldValue, String newValue, 
                                   String description, String evaluate) {
        String sql = "INSERT INTO WorkHistory (UserID, Type, Effective_Date, Old_Value, New_Value, Description, Evaluate) " +
                     "VALUES (?, ?, CURDATE(), ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userID);
            ps.setString(2, type);
            ps.setString(3, oldValue);
            ps.setString(4, newValue);
            ps.setString(5, description);
            ps.setString(6, evaluate);
            
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
