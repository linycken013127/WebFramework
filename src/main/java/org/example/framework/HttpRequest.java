package org.example.framework;

import java.util.List;

public class HttpRequest {
    private final RequestLine requestLine;
    private final HttpUrl url;
    private final Headers headers;
    private final RequestBody requestBody;


    public HttpRequest(RequestLine requestLine, HttpUrl url, Headers headers, RequestBody requestBody) {
        this.requestLine = requestLine;
        this.url = url;
        this.headers = headers;
        this.requestBody = requestBody;
    }

    public String getPath() {
        return url.getPath();
    }

    public String getMethod() {
        return requestLine.getMethod();
    }
}
