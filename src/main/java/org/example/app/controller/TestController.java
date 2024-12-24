package org.example.app.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.framework.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestController implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 將 exchange 轉 http Request object
        // content type
        // force ocp
        String httpVersion = exchange.getProtocol();
        HttpUrl url = new HttpUrl(
                exchange.getRequestURI().getScheme(),
                exchange.getRequestURI().getHost(),
                exchange.getRequestURI().getPort(),
                exchange.getRequestURI().getPath(),
                exchange.getRequestURI().getQuery()
        );
        RequestLine requestLine = new RequestLine(exchange.getRequestMethod(), url, httpVersion);
        List<Header> headers = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : exchange.getRequestHeaders().entrySet()) {
            Header header = new Header(entry.getKey(), entry.getValue());
            headers.add(header);
        }
        RequestBody requestBody = new RequestBody();
        HttpRequest request = new HttpRequest(requestLine, url, headers, requestBody);


        if ("GET".equals(exchange.getRequestMethod())) {
            String response = "Hello World!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();
        }
    }
}
