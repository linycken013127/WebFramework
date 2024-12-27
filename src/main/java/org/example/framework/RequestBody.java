package org.example.framework;

import com.sun.net.httpserver.HttpExchange;

public class RequestBody {
    private final HttpExchange body;

    public RequestBody(HttpExchange body) {
        this.body = body;
    }
}
