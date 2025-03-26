package org.conagyurig;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

public class LibSqlResultSetMetaData implements ResultSetMetaData {

    private final LibSqlResultSet resultSet;

    public LibSqlResultSetMetaData(LibSqlResultSet resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public int getColumnCount() throws SQLException {
        return resultSet.getCols() == null ? 0 : resultSet.getCols().size();
    }

    @Override
    public boolean isAutoIncrement(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isCaseSensitive(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isSearchable(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isCurrency(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int isNullable(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isSigned(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getColumnDisplaySize(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public String getColumnLabel(int column) throws SQLException {
        if (column < 0 || resultSet.getCols() == null || resultSet.getCols().isEmpty() || column > resultSet.getCols().size()) {
            throw new SQLException("invalid column");
        }
        return resultSet.getCols().get(column - 1).getName();
    }

    @Override
    public String getColumnName(int column) throws SQLException {
        if (column < 0 || resultSet.getCols() == null || resultSet.getCols().isEmpty() || column > resultSet.getCols().size()) {
            throw new SQLException("invalid column");
        }
        return resultSet.getCols().get(column - 1).getName();
    }

    @Override
    public String getSchemaName(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getPrecision(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getScale(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public String getTableName(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public String getCatalogName(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getColumnType(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public String getColumnTypeName(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isReadOnly(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isWritable(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isDefinitelyWritable(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public String getColumnClassName(int column) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }
}
