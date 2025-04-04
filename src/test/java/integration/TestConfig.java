package integration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestConfig {
    public static String getJdbcUrl() {
        return System.getenv().getOrDefault("TURSO_JDBC_URL", readProperty("TURSO_JDBC_URL"));
    }

    public static String getToken() {
        return System.getenv().getOrDefault("TURSO_JDBC_TOKEN", readProperty("TURSO_JDBC_TOKEN"));
    }

    private static String readProperty(String key) {
        try (InputStream input = new FileInputStream(".env.test")) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty(key);
        } catch (IOException ex) {
            throw new RuntimeException("Missing test configuration: " + key, ex);
        }
    }
}
