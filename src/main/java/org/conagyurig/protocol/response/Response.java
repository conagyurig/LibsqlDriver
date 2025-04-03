package org.conagyurig.protocol.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    private List<ResultItem> results;
    private String baton;

    public List<ResultItem> getResults() {
        return results;
    }

    public void setResults(List<ResultItem> results) {
        this.results = results;
    }

    public String getBaton() {
        return baton;
    }

    public void setBaton(String baton) {
        this.baton = baton;
    }
}
