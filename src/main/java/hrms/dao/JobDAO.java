package hrms.dao;

import static com.mysql.cj.conf.PropertyKey.PASSWORD;
import static com.mysql.cj.conf.PropertyKey.USER;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import hrms.model.JobDescription;
import hrms.utils.DBContext;
import static jakarta.servlet.SessionTrackingMode.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.LocalDate;

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

    public boolean insertJobDescription(JobDescription jd) {
        String sql = "INSERT INTO job_description (ticketID, jobTitle, status, startDate, endDate, department, vacancies, responsibilities, requirements, compensation, officeAddress, workingConditions) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/HRMS?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true", "root", "123456"); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, jd.getTicketID());
            ps.setString(2, jd.getJobTitle());
            ps.setString(3, jd.getStatus());
            ps.setObject(4, jd.getStartDate());
            ps.setObject(5, jd.getEndDate());
            ps.setString(6, jd.getDepartment());
            ps.setInt(7, jd.getVacancies());
            ps.setString(8, jd.getResponsibilities());
            ps.setString(9, jd.getRequirements());
            ps.setString(10, jd.getCompensation());
            ps.setString(11, jd.getOfficeAddress());
            ps.setString(12, jd.getWorkingConditions());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
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
