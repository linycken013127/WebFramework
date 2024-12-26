package org.example.framework;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;

public class HttpResponse {
    private StatusLine statusLine;
    private List<Header> headers;
    private Object body;

    public HttpResponse(StatusLine statusLine, List<Header> headers) {
        this.statusLine = statusLine;
        this.headers = headers;
    }

    public void setBody(Object response) {
        this.body = response;
    }

    public void setCode(int code) {
        statusLine.setStatusCode(code);
    }

    public void send(HttpExchange exchange) throws IOException {
        try {
            exchange.sendResponseHeaders(statusLine.getStatusCode(), body.toString().getBytes().length);
            exchange.getResponseBody().write(body.toString().getBytes());
            exchange.getResponseBody().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
