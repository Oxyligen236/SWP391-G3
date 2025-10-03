package hrms.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import hrms.model.Task;
import hrms.model.TaskAssignees;

public class TaskDAO {

    private final String jdbcURL = "jdbc:mysql://localhost:3306/yourdb"; 
    private final String jdbcUser = "1234"; 
    private final String jdbcPass = "1234"; 

    public TaskDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
    }

    // 🔹 Lấy toàn bộ Task
    public List<Task> getAllTasks() {
        List<Task> list = new ArrayList<>();
        String sql = "SELECT * FROM Task";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Task t = new Task();
                t.setTaskId(rs.getInt("taskid"));
                t.setTaskTypeId(rs.getInt("typeid"));
                t.setTitle(rs.getString("title"));
                t.setDescription(rs.getString("description"));
                t.setStatus(rs.getString("status"));
                t.setAssignedBy(rs.getString("assignedby"));
                t.setStartDate(rs.getDate("startdate"));
                t.setEndDate(rs.getDate("enddate"));
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Lay Task theo id
    public Task getTaskById(int id) {
        String sql = "SELECT t.taskid, t.typeid, t.title, t.description, t.status, "
                + "t.assignedby, t.startdate, t.enddate "
                + "FROM Task t "
                + "WHERE t.taskid=?";

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Task task = new Task(
                        rs.getInt("taskid"),
                        rs.getInt("typeid"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("status"),
                        rs.getString("assignedby"),
                        rs.getDate("startdate"),
                        rs.getDate("enddate")
                );

                // Lấy danh sách receiverId từ TaskAssignees
                List<Integer> receivers = new ArrayList<>();
                String sql2 = "SELECT receiverid FROM TaskAssignees WHERE taskid=?";
                try (PreparedStatement ps2 = conn.prepareStatement(sql2)) {
                    ps2.setInt(1, id);
                    ResultSet rs2 = ps2.executeQuery();
                    while (rs2.next()) {
                        receivers.add(rs2.getInt("receiverid"));
                    }
                }

                return task;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertTask(Task task, TaskAssignees assignees) {
        String sqlTask = "INSERT INTO Task(typeid,title,description,status,assignedby,startdate,enddate) VALUES (?,?,?,?,?,?,?)";
        String sqlAssignees = "INSERT INTO TaskAssignees(taskid,receiverid) VALUES(?,?)";

        try (Connection conn = getConnection(); PreparedStatement psTask = conn.prepareStatement(sqlTask, Statement.RETURN_GENERATED_KEYS)) {

            psTask.setInt(1, task.getTaskTypeId());
            psTask.setString(2, task.getTitle());
            psTask.setString(3, task.getDescription());
            psTask.setString(4, task.getStatus());
            psTask.setString(5, task.getAssignedBy());
            psTask.setDate(6, task.getStartDate());
            psTask.setDate(7, task.getEndDate());
            psTask.executeUpdate();

            ResultSet rs = psTask.getGeneratedKeys();
            if (rs.next()) {
                int newTaskId = rs.getInt(1);
                task.setTaskId(newTaskId);

                // Insert nhiều receiverId
                if (assignees.getReceiverId() != null && !assignees.getReceiverId().isEmpty()) {
                    try (PreparedStatement psAssignees = conn.prepareStatement(sqlAssignees)) {
                        for (Integer receiverId : assignees.getReceiverId()) {
                            psAssignees.setInt(1, newTaskId);
                            psAssignees.setInt(2, receiverId);
                            psAssignees.addBatch();
                        }
                        psAssignees.executeBatch();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
