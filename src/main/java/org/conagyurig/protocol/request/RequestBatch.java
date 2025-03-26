package org.conagyurig.protocol.request;

import java.util.ArrayList;
import java.util.List;

public class RequestBatch {
    private String baton = null;
    public List<Request> requests;

    public RequestBatch(List<Request> requests) {
        this.requests = requests;
    }

    public RequestBatch() {
        this.requests = new ArrayList<>();
    }

    public String getBaton() {
        return baton;
    }

    public void setBaton(String baton) {
        this.baton = baton;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public void addRequest(Request request) {
        this.requests.add(request);
    }
}
