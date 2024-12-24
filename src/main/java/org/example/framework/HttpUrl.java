package org.example.framework;

public class HttpUrl {

    private final String scheme;
    private final String host;
    private final int port;
    private final String path;
    private final String query;

    public HttpUrl(String scheme, String host, int port, String path, String query) {
        this.scheme = scheme;
        this.host = host;
        this.port = port;
        this.path = path;
        this.query = query;
    }
}
