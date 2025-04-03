package org.conagyurig;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.conagyurig.protocol.request.*;
import org.conagyurig.protocol.response.Response;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LibSqlClient {
    private static final String TURSO_PREFIX = "/v2/pipeline";
    private final URI uri;
    private final String authToken;
    private final ExecutorService clientExecutor;
    private final HttpClient client;
    private final ObjectMapper objectMapper;
    private final ObjectWriter objectWriter;
    private final TransactionState transactionState;

    public LibSqlClient(String url, String authToken, TransactionState transactionState) {
        this.uri = URI.create("https://" + url + TURSO_PREFIX);
        this.authToken = authToken;
        this.clientExecutor = Executors.newVirtualThreadPerTaskExecutor();
        this.client = HttpClient.newBuilder()
                .executor(this.clientExecutor)
                .build();
        this.transactionState = transactionState;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
    }

    public Response executeQuery(String query) {
        return sendRequest(buildRequestBatch(List.of(new Request("execute", new Statement(query)))));
    }

    public Response executeBatch(List<String> batch) {
        List<Request> requests = new ArrayList<>();
        for (String sql : batch) {
            requests.add(new Request("execute", new Statement(sql)));
        }
        return sendRequest(buildRequestBatch(requests));
    }

    public Response executeQueryWithArgs(String query, List<Object> args) {
        List<Argument> arguments = args.stream().map(ArgumentMapper::convertJavaObjToArgument).toList();
        return sendRequest(buildRequestBatch(List.of(new Request("execute", Statement.withArgs(query, arguments)))));
    }

    public Response executeQueryWithNamedArgs(String query, Map<String, Object> args) {
        List<NamedArgument> arguments = args.entrySet().stream()
                .map((entry) -> new NamedArgument(entry.getKey(), ArgumentMapper.convertJavaObjToArgument(entry.getValue())))
                .toList();
        return sendRequest(buildRequestBatch(List.of(new Request("execute", Statement.withNamedArgs(query, arguments)))));
    }

    private Response sendRequest(RequestBatch requestBatch) {
        try {
            String jsonPayload = objectWriter.writeValueAsString(requestBatch);
            System.out.println(jsonPayload);
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .header("User-Agent", "LibSqlDriver/1.0")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload, StandardCharsets.UTF_8));

            if (authToken != null && !authToken.isBlank()) {
                requestBuilder.header("Authorization", "Bearer " + authToken);
            }

            HttpRequest httpRequest = requestBuilder.build();
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            if (response.statusCode() == 200 && response.body() != null) {
                try {
                    Response mappedResponse = objectMapper.readValue(response.body(), Response.class);
                    if (mappedResponse.getBaton() != null && transactionState.isInTransaction()) {
                        transactionState.setBaton(mappedResponse.getBaton());
                    }
                    return mappedResponse;
                } catch (JsonProcessingException e) {
                    throw new RuntimeException("Failed to parse response from LibSQL", e);
                }
            } else {
                throw new IOException("Failed to execute query: " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private RequestBatch buildRequestBatch(List<Request> requests) {
        RequestBatch requestBatch = new RequestBatch();
        if (transactionState.isInTransaction() && transactionState.getBaton() == null) {
            Request beginTransaction = new Request("execute", new Statement("BEGIN"));
            requestBatch.addRequest(beginTransaction);
        } else if (transactionState.isInTransaction() && transactionState.getBaton() != null) {
            requestBatch.setBaton(transactionState.getBaton());
        }
        requestBatch.addRequests(requests);
        if (!transactionState.isInTransaction()) {
            requestBatch.addRequest(new Request("close"));
        }
        return requestBatch;
    }

    public void close() {
        clientExecutor.close();
    }
}