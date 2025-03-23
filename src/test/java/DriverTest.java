import java.sql.*;
import java.util.Properties;

public class DriverTest {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("password", "...");
        String url = "...";
        Connection conn = DriverManager.getConnection(url, properties);
        Statement readStatement = conn.createStatement();
        String readQuery = "SELECT * FROM Job LIMIT 10;";
        ResultSet resultSet = readStatement.executeQuery(readQuery);
        while(resultSet.next()) {
            System.out.println(resultSet.getInt(1));
            System.out.println(resultSet.getString(2));
            System.out.println(resultSet.getString(3));
        }
        Statement updateStatement = conn.createStatement();
        int count = updateStatement.executeUpdate("INSERT INTO job(id, createdAt, status) VALUES (2, 'tomorrow', 'pending')");
        System.out.println(count);
        resultSet = readStatement.executeQuery(readQuery);
        while(resultSet.next()) {
            System.out.println(resultSet.getInt(1));
            System.out.println(resultSet.getString(2));
            System.out.println(resultSet.getString(3));
        }
        updateStatement.executeUpdate("DELETE FROM job WHERE id = 2;");

        conn.close();
    }
}
