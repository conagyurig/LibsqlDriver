package org.conagyurig.protocol.response;

public class ResultItem {
    private String type;
    private ResultResponse response;

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
}
