package org.conagyurig.protocol.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultItem {
    private String type;
    private ResultResponse response;
    private Error error;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ResultResponse getResponse() {
        return response;
    }

    public void setResponse(ResultResponse response) {
        this.response = response;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
