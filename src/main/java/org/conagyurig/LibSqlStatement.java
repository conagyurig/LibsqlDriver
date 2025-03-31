package org.conagyurig;

import org.conagyurig.protocol.response.*;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class LibSqlStatement implements Statement {
    private Connection connection;
    protected final LibSqlClient client;
    protected LibSqlResultSet resultSet;
    protected List<ResultItem> currentResults;
    protected Integer currentResultIndex;
    protected Integer updateCount;
    protected Integer last_insert_rowid;
    private boolean generatedKeysRequested = false;
    private boolean closed = false;

    public LibSqlStatement(LibSqlConnection connection, LibSqlClient client) {
        this.client = client;
        this.connection = connection;
    }

    @Override
    public boolean execute(String sql) throws SQLException {
        Response response = client.executeQuery(sql);
        return handleResponse(response);
    }

    protected boolean handleResponse(Response response) throws SQLException {
        validateNoErrors(response);
        this.currentResults = extractExecuteResults(response);
        this.currentResultIndex = 0;

        Optional<ResultItem> queryResult = findFirstResultSet(currentResults);
        if (queryResult.isPresent()) {
            this.resultSet = new LibSqlResultSet(queryResult.get().getResponse().getResult());
            return true;
        }

        Optional<Result> updateResult = findLastUpdateResult(currentResults);
        updateResult.ifPresent(r -> {
            this.updateCount = r.getAffected_row_count();
            if (r.getLast_insert_rowid() != null) {
                this.last_insert_rowid = Integer.parseInt(r.getLast_insert_rowid());
            }
        });

        return false;
    }

    private void validateNoErrors(Response response) throws SQLException {
        List<String> errors = getErrors(response);
        if (!errors.isEmpty()) {
            throw new SQLException("Query failed: " + String.join("; ", errors));
        }
    }

    private Optional<ResultItem> findFirstResultSet(List<ResultItem> results) {
        return results.stream().filter(this::hasResultSet).findFirst();
    }

    private Optional<Result> findLastUpdateResult(List<ResultItem> results) {
        return results.stream()
                .map(r -> r.getResponse().getResult())
                .filter(r -> r.getAffected_row_count() != null)
                .reduce((first, second) -> second);
    }


    @Override
    public ResultSet executeQuery(String sql) throws SQLException {
        if (execute(sql)) {
            return resultSet;
        }
        throw new SQLException("Query did not produce a result set");
    }

    @Override
    public int executeUpdate(String sql) throws SQLException {
        if (!execute(sql)) {
            return updateCount;
        }
        throw new SQLException("Query returned a ResultSet");
    }

    protected List<ResultItem> extractExecuteResults(Response response) {
        return response.getResults()
                .stream()
                .filter(resultItem -> resultItem.getResponse().getType().equals("execute"))
                .toList();
    }

    protected List<String> getErrors(Response response) {
        return response.getResults()
                .stream()
                .filter(resultItem -> resultItem.getError() != null)
                .map(resultItem -> resultItem.getError().getMessage())
                .toList();
    }

    boolean hasResultSet(ResultItem resultItem) {
        Result result = resultItem.getResponse().getResult();
        List<Column> cols = result.getCols();
        if (cols == null || cols.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection = null;
        }
        resultSet = null;
        closed = true;
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getMaxRows() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void cancel() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void clearWarnings() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return resultSet;
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return updateCount;
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getFetchDirection() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getFetchSize() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getResultSetType() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void clearBatch() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int[] executeBatch() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connection;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        if (last_insert_rowid == null) {
            return new LibSqlResultSet(new Result(List.of(), List.of()));
        }

        Column cols = new Column("GENERATED_KEY", "long");
        Cell cell = new Cell("long", String.valueOf(last_insert_rowid));
        Result result = new Result(List.of(cols), List.of(List.of(cell)));

        return new LibSqlResultSet(result);
    }

    @Override
    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        this.generatedKeysRequested = (autoGeneratedKeys == Statement.RETURN_GENERATED_KEYS);
        return executeUpdate(sql);
    }

    @Override
    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        this.generatedKeysRequested = (autoGeneratedKeys == Statement.RETURN_GENERATED_KEYS);
        return execute(sql);
    }

    @Override
    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean execute(String sql, String[] columnNames) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public int getResultSetHoldability() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isClosed() throws SQLException {
        return closed;
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isPoolable() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public void closeOnCompletion() throws SQLException {
        throw new SQLFeatureNotSupportedException();
    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
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
