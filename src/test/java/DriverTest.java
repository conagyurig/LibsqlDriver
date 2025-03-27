import java.sql.*;
import java.util.Properties;

public class DriverTest {
    public static void main(String[] args) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("password","...");
        String url = "...";
        Connection conn = DriverManager.getConnection(url, properties);
        conn.setAutoCommit(false);
        Statement updateStatement = conn.createStatement();
        int count = updateStatement.executeUpdate("INSERT INTO job(createdAt, status) VALUES ('tomorrow', 'pending')");
        Statement secondUpdateStatement = conn.createStatement();
        int secondCount = updateStatement.executeUpdate("INSERT INTO job(createdAt, status) VALUES ('now', 'todo')");
        conn.commit();
        conn.setAutoCommit(true);
        PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM job WHERE status = ?");
        preparedStatement.setString(1,"pending");
        ResultSet preparedResults = preparedStatement.executeQuery();
//        while(preparedResults.next()) {
//            System.out.println(preparedResults.getInt(1));
//            System.out.println(preparedResults.getString(2));
//            System.out.println(preparedResults.getString(3));
//        }
        conn.close();
    }
}
