package hrms.dao;

import hrms.model.WorkHistory;
import hrms.utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
}
