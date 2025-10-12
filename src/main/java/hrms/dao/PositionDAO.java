package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Position;
import hrms.utils.DBContext;

public class PositionDAO extends DBContext {

    public String getNameById(Integer id) {
        if (id == null) return null;
        String sql = "SELECT Name FROM Positions WHERE PositionID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Position> getAll() {
        List<Position> list = new ArrayList<>();
        String sql = "SELECT PositionID, Name FROM Positions";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Position p = new Position();
                p.setPositionId(rs.getInt("PositionID"));
                p.setName(rs.getString("Name"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
