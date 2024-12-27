package org.example.framework;

import java.util.HashMap;
import java.util.Map;

public class Headers {
    Map<String, Object> headers = new HashMap<>();
    public Headers() {
    }

    public void add(String key, String value) {
        headers.put(key, value);
    }

    public Object get(String key) {
        return headers.get(key);
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public String getContentType() {
        return (String) headers.get("Content-Type");
    }
}
