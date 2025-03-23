package org.conagyurig.protocol.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Statement {
    public String sql;
    public List<Argument> args;
    public List<NamedArgument> named_args;
    public Statement(String sql) {
        this.sql = sql;
    }
    public static Statement withNamedArgs(String sql, List<NamedArgument> named_args) {
        Statement statement = new Statement(sql);
        statement.named_args = named_args;
        return statement;
    }
    public static Statement withArgs(String sql, List<Argument> args) {
        Statement statement = new Statement(sql);
        statement.args = args;
        return statement;
    }
}
