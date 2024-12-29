package org.example.framework;

import org.example.app.RegistrationRequest;

public class HttpRequest<T> {
    private final RequestLine requestLine;
    private final HttpUrl url;
    private final Headers headers;
    private RequestBody requestBody;

    public HttpRequest(RequestLine requestLine, HttpUrl url, Headers headers) {
        this.requestLine = requestLine;
        this.url = url;
        this.headers = headers;
    }

    public String getPath() {
        return url.getPath();
    }

    public String getMethod() {
        return requestLine.getMethod();
    }

    public void readBodyAsObject(Class<T> type) {

    }
}
