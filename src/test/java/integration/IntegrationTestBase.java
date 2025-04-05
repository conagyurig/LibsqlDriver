package integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public abstract class IntegrationTestBase {

    protected Connection conn;

    @BeforeEach
    public void setUp() throws SQLException {
        Properties props = new Properties();
        props.setProperty("password", TestConfig.getToken());
        conn = DriverManager.getConnection(TestConfig.getJdbcUrl(), props);

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS test_table");
            stmt.execute("CREATE TABLE test_table (id INTEGER PRIMARY KEY, name TEXT NOT NULL)");
        }
    }

    @AfterEach
    public void tearDown() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS test_table");
        }
        conn.close();
    }
}
