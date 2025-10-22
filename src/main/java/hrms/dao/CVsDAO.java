package hrms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hrms.dto.CVJobDetailDTO;
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
                rs.getString(7),
                rs.getString(8),
                rs.getString(9),
                rs.getString(10)
        );
    }

    private CVJobDetailDTO extractCVJobDetailFromResultSet(ResultSet rs) throws SQLException {
        return new CVJobDetailDTO(
                rs.getInt(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9),
                rs.getString(10),
                rs.getString(11)
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
        String sql = "insert into CVs (jobID, name, gender,address, nationality, mail, phone_number, cv_Description, status) values (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cv.getJdID());
            st.setString(2, cv.getName());
            st.setString(3, cv.getGender());
            st.setString(4, cv.getNationality());
            st.setString(5, cv.getEmail());
            st.setString(4, cv.getPhone());
            st.setString(5, cv.getCv_Description());
            st.setString(6, cv.getStatus());
            int row = st.executeUpdate();
            return row > 0;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }

    }

    public List<CVJobDetailDTO> getAllCVJobTitle() {
        String sql = "select cv.cv_ID,jd.jobid, jd.jobTitle, cv.name, cv.gender,cv.address, cv.nationality, cv.mail, cv.phone_number, cv.cv_Description, cv.status "
                + "from CVs cv left join Job_Description jd ON cv.jobID = jd.jobID";
        List<CVJobDetailDTO> cvDTOList = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                CVJobDetailDTO dto = extractCVJobDetailFromResultSet(rs);
                cvDTOList.add(dto);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return cvDTOList;
    }

    public CVJobDetailDTO getCvWithJobTitle(int cvId) {
        String sql = "select cv.cv_ID,jd.jobid,  jd.jobTitle, cv.name, cv.gender,cv.address, cv.nationality, cv.mail, cv.phone_number, cv.cv_Description, cv.status "
                + "from CVs cv left join Job_Description jd ON cv.jobID = jd.jobID where cv.cv_ID = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cvId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return extractCVJobDetailFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<CVJobDetailDTO> searchCVs(int jobID, String name, String email, String phone, String gender, String status) {
        StringBuilder sql = new StringBuilder("select cv.cv_ID,jd.jobid, jd.jobTitle, cv.name, cv.gender,cv.address, cv.nationality, cv.mail, cv.phone_number, cv.cv_Description, cv.status "
                + "from CVs cv left join Job_Description jd ON cv.jobID = jd.jobID where 1=1");
        List<Object> parameters = new ArrayList<>();
        if (jobID > 0) {
            sql.append(" and cv.jobID = ?");
            parameters.add(jobID);
        }
        if (name != null && !name.isEmpty()) {
            sql.append(" and cv.name like ?");
            parameters.add("%" + name + "%");
        }
        if (email != null && !email.isEmpty()) {
            sql.append(" and cv.mail like ?");
            parameters.add("%" + email + "%");
        }
        if (phone != null && !phone.isEmpty()) {
            sql.append(" and cv.phone_number like ?");
            parameters.add("%" + phone + "%");
        }
        if (gender != null && !gender.isEmpty()) {
            sql.append(" and cv.gender like ?");
            parameters.add("%" + gender + "%");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" and cv.status like ?");
            parameters.add("%" + status + "%");
        }

        List<CVJobDetailDTO> cvDTOList = new ArrayList<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql.toString());
            for (int i = 0; i < parameters.size(); i++) {
                st.setObject(i + 1, parameters.get(i));
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                CVJobDetailDTO dto = extractCVJobDetailFromResultSet(rs);
                cvDTOList.add(dto);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return cvDTOList;
    }

}
