package hrms.dao.contract;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.Contract;
import hrms.utils.DBContext;

public class ContractDAO extends DBContext{

    public List<Contract> getAllContracts() {
        List<Contract> contracts = new ArrayList<>();
        String sql = "SELECT c.*, t.Name AS TypeName "
           + "FROM Contract c "
           + "JOIN Contract_Type t ON c.TypeID = t.TypeID";
        DBContext dbContext = new DBContext();

        try (Connection conn = dbContext.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql); 
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Contract contract = new Contract();
                    contract.setContractId(rs.getInt("ContractID"));
                    contract.setUserId(rs.getInt("UserID"));
                    contract.setStartDate(rs.getDate("Start_Date").toLocalDate());
                    contract.setEndDate(rs.getDate("End_Date").toLocalDate());
                    contract.setSignDate(rs.getDate("Sign_Date").toLocalDate());
                    contract.setDuration(rs.getInt("Duration"));
                    contract.setBaseSalary(rs.getDouble("BaseSalary"));
                    contract.setNote(rs.getString("Note"));
                    contract.setTypeID(rs.getInt("TypeID"));
                    contracts.add(contract);
            }
            System.out.println("Contracts loaded: " + contracts.size()); // Debug
            return contracts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching contracts: " + e.getMessage());
        }
    }

    public void addContract(Contract contract) {
        DBContext dbContext = new DBContext();
        String sql = "INSERT INTO Contract(UserID, Start_Date, End_Date, Sign_Date, Duration, BaseSalary, Note, TypeID) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = dbContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, contract.getUserId());
            ps.setDate(2, java.sql.Date.valueOf(contract.getStartDate()));
            ps.setDate(3, contract.getEndDate() != null ? java.sql.Date.valueOf(contract.getEndDate()) : null);
            ps.setDate(4, java.sql.Date.valueOf(contract.getSignDate()));
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
