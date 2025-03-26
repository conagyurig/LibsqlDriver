package org.conagyurig.protocol.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {
    private List<Column> cols;
    private List<List<Cell>> rows;
    private Integer affected_row_count;
    private String last_insert_rowid;

    public Result() {}

    public Result(List<Column> cols, List<List<Cell>> rows) {
        this.cols = cols;
        this.rows = rows;
    }

    public List<Column> getCols() {
        return cols;
    }

    public void setCols(List<Column> cols) {
        this.cols = cols;
    }

    public List<List<Cell>> getRows() {
        return rows;
    }

    public void setRows(List<List<Cell>> rows) {
        this.rows = rows;
    }

    public Integer getAffected_row_count() {
        return affected_row_count;
    }

    public void setAffected_row_count(Integer affected_row_count) {
        this.affected_row_count = affected_row_count;
    }

    public String getLast_insert_rowid() {
        return last_insert_rowid;
    }

    public void setLast_insert_rowid(String last_insert_rowid) {
        this.last_insert_rowid = last_insert_rowid;
    }
}
