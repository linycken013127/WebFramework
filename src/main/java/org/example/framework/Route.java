package org.example.framework;

import com.sun.net.httpserver.HttpHandler;

public class Route {
    private String path;
    private HttpHandler handler;

    public Route(String path, HttpHandler handler) {
        this.path = path;
        this.handler = handler;
    }

    public String getPath() {
        return path;
    }

    public HttpHandler getHandler() {
        return handler;
    }
}
