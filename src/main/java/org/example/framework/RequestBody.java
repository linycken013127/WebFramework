package org.example.framework;

import com.sun.net.httpserver.HttpExchange;

import java.util.Map;

public class RequestBody {

    private Map<String, Object> jsonMap;

    public RequestBody() {
    }

    public RequestBody(Map<String, Object> jsonMap) {
        this.jsonMap = jsonMap;
    }


}
