package org.example.framework;

import com.sun.net.httpserver.HttpExchange;

public class BaseRequestBodyHandler extends RequestBodyHandler {
    public BaseRequestBodyHandler(RequestBodyHandler next) {
        this.contextType = "";
        this.next = next;
    }

    @Override
    public RequestBody execute(HttpExchange exchange) {

        System.out.println("BaseRequestBodyHandler");
        return new RequestBody();
    }

    @Override
    public boolean match(String contextType) {
        if (contextType == null) {
            return true;
        }

        return super.match(contextType);
    }
}
