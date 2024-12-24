package org.example.app.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.framework.RequestLine;

import java.io.IOException;

public class TestController implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 將 exchange 轉 http Request object
        // content type
        // force ocp
        String httpVersion = exchange.getProtocol();
        RequestLine requestLine = new RequestLine(exchange.getRequestMethod(), exchange.getRequestURI(), httpVersion);
        if ("GET".equals(exchange.getRequestMethod())) {
            String response = "Hello World!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();
        }
    }
}
