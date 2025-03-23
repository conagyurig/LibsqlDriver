package org.conagyurig.protocol.request;

import java.util.ArrayList;
import java.util.List;

public class RequestBatch {
    public List<Request> requests;

    public RequestBatch(List<Request> requests) {
        this.requests = requests;
    }

    public RequestBatch(Request request) {
        this.requests = new ArrayList<>();
        this.requests.add(request);
        this.requests.add(new Request("close"));
    }
}
