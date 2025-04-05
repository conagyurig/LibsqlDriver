package integration;

import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class BasicCRUDStatementTest extends IntegrationTestBase {

    @Test
    public void testCreate() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute("INSERT INTO test_table (name) VALUES ('testName')");
            int updateCount = statement.getUpdateCount();
            assertEquals(1, updateCount);
        }

        try (Statement statement = conn.createStatement()) {
            statement.execute("SELECT name FROM test_table WHERE name = 'testName'");
            ResultSet rs = statement.getResultSet();
            assertTrue(rs.next());
            assertEquals("testName", rs.getString("name"));
        }
    }

    @Test
    public void testUpdate() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute("INSERT INTO test_table (name) VALUES ('testName')");
            int updateCount = statement.getUpdateCount();
            assertEquals(1, updateCount);
        }

        try (Statement statement = conn.createStatement()) {
            statement.execute("UPDATE test_table SET name = 'newTestName' WHERE name = 'testName'");
            int updateCount = statement.getUpdateCount();
            assertEquals(1, updateCount);
        }

        try (Statement statement = conn.createStatement()) {
            statement.execute("SELECT name FROM test_table WHERE name = 'newTestName'");
            ResultSet rs = statement.getResultSet();

            assertTrue(rs.next());
            assertFalse(rs.next());
        }
    }

    @Test
    public void testDelete() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute("INSERT INTO test_table (name) VALUES ('testName')");
            int updateCount = statement.getUpdateCount();
            assertEquals(1, updateCount);
        }

        try (Statement statement = conn.createStatement()) {
            statement.execute("DELETE FROM test_table WHERE name = 'testName'");
            int updateCount = statement.getUpdateCount();
            assertEquals(1, updateCount);
        }

        try (Statement statement = conn.createStatement()) {
            statement.execute("SELECT name FROM test_table WHERE name = 'testName'");
            ResultSet rs = statement.getResultSet();

            assertFalse(rs.next());
        }
    }

    @Test
    public void testInvalidInsertThrowsError() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute("INSERT INTO test_table (name) VALUES (null)");
        } catch (SQLException e) {
            assertTrue(e.getMessage().contains("NOT NULL constraint failed"));
        }
    }

    @Test
    public void testInvalidSQLThrowsError() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute("INSERT TO test_table (name) VALUES (null)");
        } catch (SQLException e) {
            assertTrue(e.getMessage().contains("SQL string could not be parsed"));
        }
    }
}

