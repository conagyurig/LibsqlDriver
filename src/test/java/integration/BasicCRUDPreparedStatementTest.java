package integration;

import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class BasicCRUDPreparedStatementTest extends IntegrationTestBase {

    @Test
    public void testCreate() throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO test_table (name) VALUES (?)")) {
            ps.setString(1, "testName");
            int updateCount = ps.executeUpdate();
            assertEquals(1, updateCount);
        }

        try (PreparedStatement ps = conn.prepareStatement("SELECT name FROM test_table WHERE name = ?")) {
            ps.setString(1, "testName");
            ResultSet rs = ps.executeQuery();
            assertTrue(rs.next());
            assertEquals("testName", rs.getString("name"));
        }
    }

    @Test
    public void testUpdate() throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO test_table (name) VALUES (?)")) {
            ps.setString(1, "testName");
            ps.executeUpdate();
        }

        try (PreparedStatement ps = conn.prepareStatement("UPDATE test_table SET name = ? WHERE name = ?")) {
            ps.setString(1, "newTestName");
            ps.setString(2,"testName");
            int updateCount = ps.executeUpdate();
            assertEquals(1, updateCount);
        }

        try (PreparedStatement ps = conn.prepareStatement("SELECT name FROM test_table WHERE name = ?")) {
            ps.setString(1, "newTestName");
            ResultSet rs = ps.executeQuery();

            assertTrue(rs.next());
            assertFalse(rs.next());
        }
    }

    @Test
    public void testDelete() throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO test_table (name) VALUES (?)")) {
            ps.setString(1, "testName");
            ps.executeUpdate();
        }

        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM test_table WHERE name = ?")) {
            ps.setString(1, "testName");
            int updateCount = ps.executeUpdate();
            assertEquals(1, updateCount);
        }

        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM test_table")) {
            ResultSet rs = ps.executeQuery();

            assertFalse(rs.next());
        }
    }
}

