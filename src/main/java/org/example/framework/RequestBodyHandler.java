package org.example.framework;

import com.sun.net.httpserver.HttpExchange;

public abstract class RequestBodyHandler {
    protected RequestBodyHandler next;
    protected String contextType;

    public RequestBody handler(HttpExchange exchange, String contextType) {
        if (this.contextType == null) {
            throw new IllegalArgumentException("Handler Context-Type 尚未設定");
        }

        if (match(contextType)) {
            return execute(exchange);
        } else if (next != null) {
            return next.handler(exchange, contextType);
        }
        throw new IllegalArgumentException("無法處理的 Context-Type: " + contextType);
    }

    public abstract RequestBody execute(HttpExchange exchange);

    public boolean match(String contextType) {
        return this.contextType.equals(contextType);
    }
}
