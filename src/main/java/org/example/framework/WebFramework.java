package org.example.framework;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class WebFramework {

    private final int port;
    private Router router;
    private BaseController controller;

    public WebFramework(int port) {
        BaseController handler = new BaseController();
        handler.setFramework(this);
        this.port = port;
        router = new Router(handler);
    }

    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            router.settingRoute(server);

            // 啟動伺服器
//            server.setExecutor(null); // 使用默認執行緒
            server.start();
        } catch (IOException e) {
            System.err.println("Server failed to start: " + e.getMessage());
        }
    }

    public Router getRouter() {
        return router;
    }

    public void addController(BaseController controller) {
        controller.setFramework(this);
    }

    public void handle(HttpExchange exchange) throws IOException {
        // 將 exchange 轉 http Request object
        // content type
        // force ocp
        HttpRequest request = exchangeToRequest(exchange);

        Route route = router.findRoute(request);

        if (request.getMethod().equals(route.getMethod())) {
            route.getHandler().apply(request);
        }

        // todo
        if ("GET".equals(exchange.getRequestMethod())) {
            String response = "Test Hello World!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();
        }
    }

    private HttpRequest exchangeToRequest(HttpExchange exchange) {
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
        return new HttpRequest(requestLine, url, headers, requestBody);
    }

    public void addRoute(String path, String method, Function<Object, Object> controller) {
//        if (!path.startsWith("/")) {
//            path = "/" + path;
//        }
//        if (!path.endsWith("/")) {
//            path = path + "/";
//        }

        router.route(path, method, controller);
    }

    // Force OCP
    public void get(String path, Function<Object, Object> controller) {
        addRoute(path, "GET", controller);
    }

    public void post(String path, Function<Object, Object> controller) {
        addRoute(path, "POST", controller);
    }

    public void put(String path, Function<Object, Object> controller) {
        addRoute(path, "PUT", controller);
    }

    public void delete(String path, Function<Object, Object> controller) {
        addRoute(path, "DELETE", controller);
    }
}
