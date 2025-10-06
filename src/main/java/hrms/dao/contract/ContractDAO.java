package hrms.dao.contract;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Contract;
import hrms.utils.DBContext;

public class ContractDAO {

    public List<Contract> getAllContracts() {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT c.*, t.Name AS TypeName"
                   + "FROM Contract c"
                   + "JOIN Contract_Type t ON c.Type = t.TypeID";
        DBContext dbContext = new DBContext();

        try (Connection conn = dbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql); 
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Contract contract = new Contract();
                contract.setContractID(rs.getInt("ContractID"));
                contract.setUserId(rs.getInt("UserId"));
                contract.setStartDate(rs.getDate("Start_Date"));
                contract.setEndDate(rs.getDate("End_Date"));
                contract.setSignDate(rs.getDate("Sign_Date"));
                contract.setDuration(rs.getInt("Duration"));
                contract.setBaseSalary(rs.getDouble("BaseSalary"));
                contract.setNote(rs.getString("Note"));
                contract.setTypeID(rs.getInt("Type"));
                contract.setTypeName(rs.getString("TypeName"));
                contracts.add(contract);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contracts;
    }

    public void addContract(Contract contract) {
        DBContext dbContext = new DBContext();
        String sql = "INSERT INTO Contract(UserID, Start_Date, End_Date, Sign_Date, Duration, BaseSalary, Note, Type) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, contract.getUserId());
            ps.setDate(2, new java.sql.Date(contract.getStartDate().getTime()));
            ps.setDate(3, new java.sql.Date(contract.getEndDate().getTime()));
            ps.setDate(4, new java.sql.Date(contract.getSignDate().getTime()));
            ps.setInt(5, contract.getDuration());
            ps.setDouble(6, contract.getBaseSalary());
            ps.setString(7, contract.getNote());
            ps.setInt(8, contract.getTypeID());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
