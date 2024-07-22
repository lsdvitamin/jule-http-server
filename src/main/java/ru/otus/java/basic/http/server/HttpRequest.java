package ru.otus.java.basic.http.server;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String rawRequest;
    private String uri;
    private HttpMethod method;
    private Map<String, String> parameters;

    public String getUri() {
        return uri;
    }

    public HttpRequest(String rawRequest) {
        this.rawRequest = rawRequest;
        this.parse();
    }

    private void parse() {
        int startIndex = rawRequest.indexOf(' ');
        int endIndex = rawRequest.indexOf(' ', startIndex + 1);
        this.uri = rawRequest.substring(startIndex + 1, endIndex);
        this.method = HttpMethod.valueOf(rawRequest.substring(0, startIndex));
        this.parameters = new HashMap<>();
        if (uri.contains("?")) {
            String[] elements = uri.split("[?]");
            this.uri = elements[0];
            String[] keysValues = elements[1].split("&");
            for (String o : keysValues) {
                String[] keyValue = o.split("=");
                this.parameters.put(keyValue[0], keyValue[1]);
            }
        }
    }

    public boolean containsParameter(String key) {
        return parameters.containsKey(key);
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public void printInfo(boolean showRawRequest) {
        System.out.println("uri: " + uri);
        System.out.println("method: " + method);
        if (showRawRequest) {
            System.out.println(rawRequest);
        }
    }
}
