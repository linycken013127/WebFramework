package org.example.framework;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.util.function.Function;

public class Route {
    private String path;
    private final String method;
    private Function<Object, Object> handler;

    public Route(String path, String method, Function<Object, Object> handler) {
        this.path = path;
        this.method = method;
        this.handler = handler;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public Function<Object, Object> getHandler() {
        return handler;
    }
}
