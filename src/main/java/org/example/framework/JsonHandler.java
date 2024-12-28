package org.example.framework;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JsonHandler extends RequestBodyHandler {
    public JsonHandler() {
        this.contextType = "application/json";
        this.next = null;
    }
    public JsonHandler(BaseRequestBodyHandler baseRequestBodyHandler) {
        this.contextType = "application/json";
        this.next = baseRequestBodyHandler;
    }

    @Override
    public RequestBody execute(HttpExchange exchange) {
        try {
            InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            reader.close();

            System.out.println("Request Body: " + requestBody);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(requestBody.toString(), Map.class);

            System.out.println("Parsed JSON: " + jsonMap);

            return new RequestBody(jsonMap);
        } catch (Exception e) {
            throw new IllegalArgumentException("無法解析 JSON 格式的 Request Body: " + e.getMessage());
        }
    }
}
