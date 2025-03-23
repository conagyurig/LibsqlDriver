package org.conagyurig.protocol.response;

public class ResultResponse {
    private String type;
    private Result result;

    public String getType() {
        return type;
    }

    public Result getResult() {
        return result;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setResult(Result result) {
        this.result = result;
    }
}
