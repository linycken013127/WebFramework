package org.example.framework;

public class RequestLine {
    private String method;
    private HttpUrl url;
    private String httpVersion;

    public RequestLine(String method, HttpUrl url, String httpVersion) {
        this.method = method;
        this.url = url;
        this.httpVersion = httpVersion;
    }

    public String getMethod() {
        return method;
    }



    public String getHttpVersion() {
        return httpVersion;
    }
}
