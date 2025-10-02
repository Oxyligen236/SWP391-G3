package hrms.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    protected Connection connection;
    
    public DBContext() {
        try {
            // Cấu hình kết nối MySQL
            String url = "jdbc:mysql://localhost:3306/HRM2?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String username = "root";  // Thay đổi username của bạn
            String password = "123456"; // Thay đổi password của bạn
            
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Tạo kết nối
            connection = DriverManager.getConnection(url, username, password);
            
            System.out.println("Kết nối MySQL thành công!");
            
        } catch (ClassNotFoundException ex) {
            System.err.println("Không tìm thấy MySQL JDBC Driver: " + ex.getMessage());
        } catch (SQLException ex) {
            System.err.println("Lỗi kết nối cơ sở dữ liệu: " + ex.getMessage());
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Đã đóng kết nối MySQL!");
            } catch (SQLException ex) {
                System.err.println("Lỗi khi đóng kết nối: " + ex.getMessage());
            }
        }
    }

   
}