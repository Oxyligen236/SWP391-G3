package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.model.CVs;
import hrms.utils.DBContext;

public class CVsDAO extends DBContext {

    private CVs extractCVFromResultSet(ResultSet rs) throws SQLException {
        return new CVs(
                rs.getInt("cv_ID"),
                rs.getInt("jobID"),
                rs.getString("name"),
                rs.getString("gender"),
                rs.getString("address"),
                rs.getString("nationality"),
                rs.getString("mail"),
                rs.getString("phone_number"),
                rs.getString("cv_Description"),
                rs.getString("status")
        );
    }

    public List<CVs> getAll() {
        String sql = "select * from CVs";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            List<CVs> list = new ArrayList<>();
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                CVs cvs = extractCVFromResultSet(rs);
                list.add(cvs);
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean updateCvStatus(int cvId, String status) {
        String sql = "update CVs set status = ? where cv_ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, status);
            st.setInt(2, cvId);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public CVs getCvById(int id) {
        String sql = "select * from CVs where cv_ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return extractCVFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean addCV(CVs cv) {
        String sql = "insert into CVs (jobID, name, gender, address, nationality, mail, phone_number, cv_Description, status) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cv.getJdID());
            st.setString(2, cv.getName());
            st.setString(3, cv.getGender());
            st.setString(4, cv.getAddress());
            st.setString(5, cv.getNationality());
            st.setString(6, cv.getEmail());
            st.setString(7, cv.getPhone());
            st.setString(8, cv.getCv_Description());
            st.setString(9, cv.getStatus());
            int row = st.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }

    public List<CVs> searchCVs(int jobID, String name, String email, String phone, String gender, String status) {
        StringBuilder sql = new StringBuilder("select * from CVs where 1=1");
        List<Object> parameters = new ArrayList<>();

        if (jobID > 0) {
            sql.append(" and jobID = ?");
            parameters.add(jobID);
        }
        if (name != null && !name.isEmpty()) {
            sql.append(" and name like ?");
            parameters.add("%" + name + "%");
        }
        if (email != null && !email.isEmpty()) {
            sql.append(" and mail like ?");
            parameters.add("%" + email + "%");
        }
        if (phone != null && !phone.isEmpty()) {
            sql.append(" and phone_number like ?");
            parameters.add("%" + phone + "%");
        }
        if (gender != null && !gender.isEmpty()) {
            sql.append(" and gender = ?");
            parameters.add(gender);
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" and status like ?");
            parameters.add("%" + status + "%");
        }

        List<CVs> cvList = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql.toString());
            for (int i = 0; i < parameters.size(); i++) {
                st.setObject(i + 1, parameters.get(i));
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                CVs cv = extractCVFromResultSet(rs);
                cvList.add(cv);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return cvList;
    }
}
