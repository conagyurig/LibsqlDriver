package org.conagyurig.protocol.request;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Request {
    public String type;
    public Statement stmt;
    public Request(String type, Statement stmt) {
        this.type = type;
        this.stmt = stmt;
    }
    public Request(String type) {
        this.type = type;
    }
}
