package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Degree;
import hrms.utils.DBContext;

public class DegreeDAO extends DBContext {

    public String getNameById(Integer id) {
        if (id == null)
            return null;
        String sql = "SELECT Name FROM Degree WHERE DegreeID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Name");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Degree> getAll() {
        List<Degree> list = new ArrayList<>();
        String sql = "SELECT DegreeID, Name FROM Degree ORDER BY Name ASC";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Degree d = new Degree();
                d.setDegreeId(rs.getInt("DegreeID"));
                d.setName(rs.getString("Name"));
                list.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean exists(String name) {
        if (name == null || name.trim().isEmpty()) 
            return false;
            
        String sql = "SELECT COUNT(*) FROM Degree WHERE Name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int insertIfNotExists(String name) throws SQLException {
        if (name == null || name.trim().isEmpty()) 
            return 0;
        if (exists(name)) 
            return 0;
            
        String sql = "INSERT INTO Degree (Name) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            return ps.executeUpdate();
        }
    }

    public Degree getByName(String name) {
        if (name == null || name.trim().isEmpty()) 
            return null;
            
        String sql = "SELECT DegreeID, Name FROM Degree WHERE Name = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Degree d = new Degree();
                    d.setDegreeId(rs.getInt("DegreeID"));
                    d.setName(rs.getString("Name"));
                    return d;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int createReturnId(String name) {
        if (name == null || name.trim().isEmpty()) 
            return 0;
            
        String sql = "INSERT INTO Degree (Name) VALUES (?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, name.trim());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}