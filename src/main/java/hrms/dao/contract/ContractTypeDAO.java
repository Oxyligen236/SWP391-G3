package hrms.dao.contract;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.ContractType;
import hrms.utils.DBContext;

public class ContractTypeDAO extends DBContext {

    public List<ContractType> getAllTypes() {
        List<ContractType> list = new ArrayList<>();
        String sql = "SELECT * FROM Contract_Type";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ContractType type = new ContractType();
                type.setTypeID(rs.getInt(1));  // TypeID
                type.setTypeName(rs.getString(2)); // Name
                list.add(type);
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public ContractType getTypeById(int id) {
        ContractType type = null;
        String sql = "SELECT * FROM Contract_Type WHERE TypeID = ?";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if(rs.next()) {
                    type = new ContractType();
                    type.setTypeID(rs.getInt(1));  // TypeID
                    type.setTypeName(rs.getString(2)); // Name
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}
