package hrms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import hrms.model.JobDescription;
import hrms.utils.DBContext;

public class JobDAO extends DBContext {

    private JobDescription extractJobFromResultSet(ResultSet rs) throws SQLException {
        return new JobDescription(
                rs.getInt(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getString(4),
                rs.getDate(5).toLocalDate(),
                rs.getDate(6).toLocalDate(),
                rs.getString(7),
                rs.getInt(8),
                rs.getString(9),
                rs.getString(10),
                rs.getString(11),
                rs.getString(12),
                rs.getString(13)
        );
    }

    public List<JobDescription> getAll() {
        String sql = "select * from Job_Description";
        try {
            var st = connection.prepareStatement(sql);
            List<JobDescription> list = new java.util.ArrayList<>();
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                JobDescription job = extractJobFromResultSet(rs);
                list.add(job);
            }
            return list;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public JobDescription getJobByCvId(int id) {
        String sql = "select * from Job_Description where cvID = ?";
        try {
            var st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return extractJobFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}