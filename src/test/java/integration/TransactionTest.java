package integration;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest extends IntegrationTestBase{
    @Test
    public void testCreateTransactionCommit() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            statement.execute("INSERT INTO test_table (name) VALUES ('testName')");
            int updateCount = statement.getUpdateCount();
            assertEquals(1, updateCount);
            statement.execute("INSERT INTO test_table (name) VALUES ('testName')");
            conn.commit();
        }

        try (Statement statement = conn.createStatement()) {
            statement.execute("SELECT name FROM test_table WHERE name = 'testName'");
            ResultSet rs = statement.getResultSet();
            assertTrue(rs.next());
            assertEquals("testName", rs.getString("name"));
            assertTrue(rs.next());
            assertEquals("testName", rs.getString("name"));
        }
    }

    @Test
    public void testCreateTransactionRollback() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            statement.execute("INSERT INTO test_table (name) VALUES ('testName')");
            int updateCount = statement.getUpdateCount();
            assertEquals(1, updateCount);
            statement.execute("INSERT INTO test_table (name) VALUES ('testName')");
            conn.rollback();
        }

        try (Statement statement = conn.createStatement()) {
            statement.execute("SELECT name FROM test_table WHERE name = 'testName'");
            ResultSet rs = statement.getResultSet();
            assertFalse(rs.next());
        }
    }
}
