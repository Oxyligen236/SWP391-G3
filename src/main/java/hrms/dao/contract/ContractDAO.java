package hrms.dao.contract;

import java.sql.Connection;
import java.sql.Date;
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
        String sql = "SELECT c.*, t.Name AS TypeName, u.FullName AS FullName, " +
                 "p.Name AS PositionName, s.FullName AS SignerName " +  
                 "FROM Contract c " +
                 "JOIN Contract_Type t ON c.TypeID = t.TypeID " +
                 "LEFT JOIN Users u ON c.UserID = u.UserID " +
                 "LEFT JOIN Positions p ON c.positionID = p.PositionID " +  
                 "LEFT JOIN Users s ON c.SignerID = s.UserID " +
                 "ORDER BY ContractID";

        try (
            PreparedStatement ps = connection.prepareStatement(sql); 
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
                    contract.setTypeName(rs.getString("TypeName"));
                    contract.setPositionId(rs.getInt("positionId"));
                    contract.setSignerId(rs.getInt("signerId"));
                    contract.setPositionName(rs.getString("PositionName"));
                    contract.setSignerName(rs.getString("SignerName"));

                    String full = rs.getString("FullName");
                    contract.setContractName(full != null ? full : "");

                    contracts.add(contract);
            }
            return contracts;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching contracts: " + e.getMessage());
        }
    }

    public void addContract(Contract contract) {
        String sql = "INSERT INTO Contract(UserID, Start_Date, End_Date, Sign_Date, Duration, BaseSalary, Note, TypeID, positionID, SignerID) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, contract.getUserId());
            ps.setDate(2, java.sql.Date.valueOf(contract.getStartDate()));
            ps.setDate(3, contract.getEndDate() != null ? java.sql.Date.valueOf(contract.getEndDate()) : null);
            ps.setDate(4, java.sql.Date.valueOf(contract.getSignDate()));
            ps.setInt(5, contract.getDuration());
            ps.setDouble(6, contract.getBaseSalary());
            ps.setString(7, contract.getNote());
            ps.setInt(8, contract.getTypeID());
            ps.setInt(9, contract.getPositionId());
            ps.setInt(10, contract.getSignerId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Contract getContractById(int contractId) {
        Contract contract = null;
        String sql = "SELECT c.*, t.Name AS TypeName, u.FullName AS FullName, " +
                 "p.Name AS PositionName, s.FullName AS SignerName " +
                 "FROM Contract c " +
                 "JOIN Contract_Type t ON c.TypeID = t.TypeID " +
                 "LEFT JOIN Users u ON c.UserID = u.UserID " +
                 "LEFT JOIN Positions p ON c.positionID = p.PositionID " +
                 "LEFT JOIN Users s ON c.SignerID = s.UserID " +
                 "WHERE c.ContractID = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, contractId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    contract = new Contract();
                    contract.setContractId(rs.getInt("ContractID"));
                    contract.setUserId(rs.getInt("UserID"));

                    Date sd = rs.getDate("Start_Date");
                    contract.setStartDate(sd != null ? sd.toLocalDate() : null);

                    Date ed = rs.getDate("End_Date");
                    contract.setEndDate(ed != null ? ed.toLocalDate() : null);

                    Date sg = rs.getDate("Sign_Date");
                    contract.setSignDate(sg != null ? sg.toLocalDate() : null);

                    contract.setDuration(rs.getInt("Duration"));
                    contract.setBaseSalary(rs.getDouble("BaseSalary"));
                    contract.setNote(rs.getString("Note"));
                    contract.setTypeID(rs.getInt("TypeID"));
                    contract.setTypeName(rs.getString("TypeName"));
                    contract.setPositionId(rs.getInt("positionId"));
                    contract.setSignerId(rs.getInt("signerId"));
                    contract.setPositionName(rs.getString("PositionName"));
                    contract.setSignerName(rs.getString("SignerName"));

                    String full = rs.getString("FullName");
                    contract.setContractName(full != null ? full : "");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching contract by ID: " + e.getMessage());
        }
        return contract;
    }

    public List<Contract> getContracts(String searchField, String searchValue, String fromDate, String toDate,
                                       String sortField, String sortOrder) throws Exception {
        List<Contract> list = new ArrayList<>();
        DBContext db = new DBContext();
        StringBuilder sql = new StringBuilder(
        "SELECT c.*, t.Name AS TypeName, " +
        "p.Name AS PositionName, " +  
        "s.FullName AS SignerName " +  
        "FROM Contract c " +
        "JOIN Contract_Type t ON c.TypeID = t.TypeID " +
        "LEFT JOIN Positions p ON c.positionID = p.PositionID " +  
        "LEFT JOIN Users s ON c.SignerID = s.UserID " +            
        "WHERE 1=1"
    );
        List<Object> params = new ArrayList<>();

        
        String col = null;
        if (searchField != null) {
            switch (searchField) {
                case "userId": col = "c.UserID"; break;
                case "duration": col = "c.Duration"; break;
                case "baseSalary": col = "c.BaseSalary"; break;
                case "startDate": col = "c.Start_Date"; break;
                case "endDate": col = "c.End_Date"; break;
                case "signDate": col = "c.Sign_Date"; break;
                default: col = null; break;
            }
        }

        
        if (col != null && searchValue != null && !searchValue.trim().isEmpty()) {
            if ("c.UserID".equals(col)) {
                sql.append(" AND ").append(col).append(" = ?");
                params.add(Integer.parseInt(searchValue.trim()));
            } else if ("c.Duration".equals(col)) {
                sql.append(" AND ").append(col).append(" = ?");
                params.add(Integer.parseInt(searchValue.trim()));
            } else if ("c.BaseSalary".equals(col)) {
                sql.append(" AND ").append(col).append(" = ?");
                params.add(Double.parseDouble(searchValue.trim()));
            } else if (col.endsWith("_Date")) {
                
            } else {
                sql.append(" AND ").append(col).append(" LIKE ?");
                params.add("%" + searchValue.trim() + "%");
            }
        }

        
        if (("startDate".equals(searchField) || "endDate".equals(searchField) || "signDate".equals(searchField))
                && fromDate != null && !fromDate.isEmpty() && toDate != null && !toDate.isEmpty()) {
            String dateCol = ("startDate".equals(searchField)) ? "c.Start_Date"
                           : ("endDate".equals(searchField)) ? "c.End_Date"
                           : "c.Sign_Date";
            sql.append(" AND ").append(dateCol).append(" BETWEEN ? AND ?");
            params.add(Date.valueOf(fromDate));
            params.add(Date.valueOf(toDate));
        }

        
        String orderBy = "";
        if (sortField != null) {
            String sf = sortField.trim();
            if ("baseSalary".equals(sf)) {
                orderBy = "c.BaseSalary";
            } else if ("duration".equals(sf)) {
                orderBy = "c.Duration";
            }
        }
        if (!orderBy.isEmpty()) {
            String so = "ASC";
            if ("desc".equalsIgnoreCase(sortOrder)) {
                so = "DESC";
            }
            sql.append(" ORDER BY ").append(orderBy).append(" ").append(so);
        } else {
            sql.append(" ORDER BY c.ContractID");
        }

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            
            for (int i = 0; i < params.size(); i++) {
                Object p = params.get(i);
                int idx = i + 1;
                if (p instanceof Date) {
                    ps.setDate(idx, (Date) p);
                } else if (p instanceof Integer) {
                    ps.setInt(idx, (Integer) p);
                } else if (p instanceof Double) {
                    ps.setDouble(idx, (Double) p);
                } else {
                    ps.setString(idx, p.toString());
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Contract c = new Contract();
                    c.setContractId(rs.getInt("ContractID"));
                    c.setUserId(rs.getInt("UserID"));
                    Date sd = rs.getDate("Start_Date");
                    c.setStartDate(sd != null ? sd.toLocalDate() : null);
                    Date ed = rs.getDate("End_Date");
                    c.setEndDate(ed != null ? ed.toLocalDate() : null);
                    Date sigd = rs.getDate("Sign_Date");
                    c.setSignDate(sigd != null ? sigd.toLocalDate() : null);
                    c.setDuration(rs.getInt("Duration"));
                    c.setBaseSalary(rs.getDouble("BaseSalary"));
                    c.setTypeID(rs.getInt("TypeID"));
                    c.setNote(rs.getString("Note"));
                    c.setTypeName(rs.getString("TypeName"));
                    c.setPositionId(rs.getInt("positionId"));
                    c.setSignerId(rs.getInt("signerId"));
                    c.setPositionName(rs.getString("PositionName"));
                    c.setSignerName(rs.getString("SignerName"));
                    list.add(c);
                }
            }
        }

        return list;
    }

    public List<Contract> getContractsByUserId(int userId) throws SQLException {
    List<Contract> contracts = new ArrayList<>();
    String sql = "SELECT c.*, t.Name AS TypeName, u.FullName AS FullName, " +
                     "p.Name AS PositionName, s.FullName AS SignerName " +
                     "FROM Contract c " +
                     "JOIN Contract_Type t ON c.TypeID = t.TypeID " +
                     "LEFT JOIN Users u ON c.UserID = u.UserID " +
                     "LEFT JOIN Positions p ON c.PositionID = p.PositionID " +
                     "LEFT JOIN Users s ON c.SignerID = s.UserID " +
                     "WHERE c.UserID = ? ORDER BY c.Start_Date DESC";
    
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setInt(1, userId);
        try (ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            Contract contract = new Contract();
            contract.setContractId(rs.getInt("ContractID"));
            contract.setUserId(rs.getInt("UserID"));
            Date startDate = rs.getDate("Start_Date");
            contract.setStartDate(startDate != null ? startDate.toLocalDate() : null);
            Date endDate = rs.getDate("End_Date");
            contract.setEndDate(endDate != null ? endDate.toLocalDate() : null);
            Date signDate = rs.getDate("Sign_Date");
            contract.setSignDate(signDate != null ? signDate.toLocalDate() : null);
            contract.setDuration(rs.getInt("Duration"));
            contract.setBaseSalary(rs.getDouble("BaseSalary"));
            contract.setNote(rs.getString("Note"));
            contract.setTypeID(rs.getInt("TypeID"));
            contract.setTypeName(rs.getString("TypeName"));
            contract.setPositionId(rs.getInt("PositionId"));
            contract.setSignerId(rs.getInt("SignerId"));
            contract.setPositionName(rs.getString("PositionName"));
            contract.setSignerName(rs.getString("SignerName"));
            contracts.add(contract);
        }
    }
    }
        return contracts;
    }

    public boolean updateContractNote(int contractId, String note) {
        String sql = "UPDATE Contract SET Note = ? WHERE ContractID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, note);
            ps.setInt(2, contractId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
