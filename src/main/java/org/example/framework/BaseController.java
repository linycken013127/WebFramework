package org.example.framework;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class BaseController implements HttpHandler {

    private WebFramework framework;

    public BaseController() {
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        framework.handle(exchange);
    }

    public void setFramework(WebFramework framework) {
        this.framework = framework;
    }
}
