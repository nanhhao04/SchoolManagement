package schoolmanagement;

import java.sql.*;

public class jdbcSqlServer {
    // Khai báo các biến kết nối
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=schoolmanage3;encrypt=true;trustServerCertificate=true";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123456789";

    public static void main(String[] args) {
        try {
            // 1. Load the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Driver loaded");

            // 2. Kết nối tới cơ sở dữ liệu
            Connection connection = getConnection();
            if (connection != null) {
                System.out.println("Database connected");

                // 3. Thực hiện truy vấn
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM MONHOC");

                // Kiểm tra số cột trong tập kết quả
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                System.out.println("Number of columns in result set: " + columnCount);

                while (resultSet.next()) {
                    // Sửa lại theo số cột thực tế
                    System.out.println(resultSet.getString(1) + "\t" +  // MaMonHoc
                                       resultSet.getString(2));         // TenMonHoc
                }

                // 4. Đóng kết nối
                resultSet.close();
                statement.close();
                connection.close();
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
