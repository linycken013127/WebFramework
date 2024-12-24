package org.example.framework;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebFramework {

    private final int port;
    private Router router;

    public WebFramework(int port) {
        this.port = port;
        router = new Router();
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

    public void addRoute(String path, BaseController controller) {
//        if (!path.startsWith("/")) {
//            path = "/" + path;
//        }
//        if (!path.endsWith("/")) {
//            path = path + "/";
//        }

        router.route(path, controller);
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

        // todo
        if ("GET".equals(exchange.getRequestMethod())) {
            String response = "Hello World!";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();
        }
    }
}
