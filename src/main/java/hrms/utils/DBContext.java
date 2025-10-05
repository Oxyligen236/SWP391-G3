package hrms.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    protected Connection connection;
    
    public DBContext() {
        try {
           
            String url = "jdbc:mysql://localhost:3306/HRM?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String username = "root";  
            String password = "123456"; 
            
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            
        
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