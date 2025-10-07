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
                rs.getInt(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7)
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

    public void updateCV(CVs cv) {
        String sql = "update CVs set jdID = ?, name = ?, email = ?, phone = ?, cv_Description = ?, status = ? where cvID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cv.getJDid());
            st.setString(2, cv.getName());
            st.setString(3, cv.getEmail());
            st.setString(4, cv.getPhone());
            st.setString(5, cv.getCV_Description());
            st.setString(6, cv.getStatus());
            st.setInt(7, cv.getCVid());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public CVs getCVById(int id) {
        String sql = "select * from CVs where cvID = ?";
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
        String sql = "insert into CVs (jdID, name, email, phone, cv_Description, status) values (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cv.getJDid());
            st.setString(2, cv.getName());
            st.setString(3, cv.getEmail());
            st.setString(4, cv.getPhone());
            st.setString(5, cv.getCV_Description());
            st.setString(6, cv.getStatus());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return true;
    }

}
