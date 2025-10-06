package hrms.dao.contract;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.ContractType;
import hrms.utils.DBContext;

public class ContractTypeDAO {
    public List<ContractType> getAllTypes() {
        List<ContractType> list = new ArrayList<>();
        String sql = "SELECT TypeID, Name FROM Contract_Type";
        DBContext dbContext = new DBContext();

        try (Connection conn = dbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ContractType type = new ContractType();
                    type.setTypeID(rs.getInt("TypeID"));
                    type.setTypeName(rs.getString("TypeName"));
                    list.add(type);
                }
             } catch (SQLException e) {
                 e.printStackTrace(); 
        }
        return list;
    }
    public ContractType getTypeById(int id) {
        ContractType type = null;
        String sql = "SELECT TypeID, Name FROM Contract_Type WHERE TypeID = ?";
        DBContext dbContext = new DBContext();

        try (Connection conn = dbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    type = new ContractType();
                    type.setTypeID(rs.getInt("TypeID"));
                    type.setTypeName(rs.getString("TypeName"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return type;
    }
}