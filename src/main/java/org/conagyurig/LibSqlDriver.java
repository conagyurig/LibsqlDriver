package org.conagyurig;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

import static org.conagyurig.LibSqlConstants.CONNECTION_PREFIX;

public class LibSqlDriver implements Driver {

    static {
        try {
            DriverManager.registerDriver(new LibSqlDriver());
        } catch (SQLException e) {
            throw new RuntimeException("Failed to register LibSql driver", e);
        }
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        if (!acceptsURL(url)) {
            return null;
        }
        String dbPath = url.substring(CONNECTION_PREFIX.length()).trim();
        if (dbPath.isBlank()) {
            throw new SQLException("Invalid database path in JDBC URL: " + url);
        }
        return new LibSqlConnection(dbPath, info);
    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        if (url == null || url.isBlank()) {
            return false;
        }

        return url.startsWith(CONNECTION_PREFIX);
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 1;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
