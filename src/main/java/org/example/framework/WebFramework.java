package org.example.framework;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class WebFramework {

    private final int port;
    private Router router;
    private BaseController controller;
    private RequestBodyHandler requestBodyHandler;

    public WebFramework(int port) {
        this.port = port;

        BaseController handler = new BaseController();
        handler.setFramework(this);
        router = new Router(handler);

        requestBodyHandler = new BaseRequestBodyHandler(null);
    }

    public void launch() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);

            router.settingRoute(server);

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


    //
    // 在執行 request.readBodyAsObject(RegisterRequest.class) 這一行時，HttpRequest 介面的實作會執行以下流程：
    //
    // [x] 首先會先參考此 HTTP 請求 Headers 中的 content-type 欄位 [附錄-5]，根據此欄位的值來辨別 Request Body 中所用的格式，並分別為每一種格式提供一種解序列化方式。
    // [^] 好比若此 HTTP 請求 Headers 中的 content-type 欄位為 application/json ，則會透過第三方 Json 套件來去解析 HTTP Request Body 中 JSON 格式的字串，將字串解序列化成 RegisterRequest.class 型別。
    // [pass] 而若此 HTTP 請求 Headers 中的 content-type 欄位為 application/xml ，則會透過第三方 XML 套件來去解析 HTTP Request Body 中 XML 格式的字串，將字串解序列化成 RegisterRequest.class 型別。
    // [^] 以此類推，Web Framework 中能支援越多格式越好。如果遇到尚未支援的 content-type 格式，則此時會拋出例外，中斷請求。（請見章節 [D]，此時應該拋出 500 HTTP Status Code 表示例外)
    // 而當此 Handler Method 執行結束後，Handler Method 會回傳一 UserResponse 型別的物件作為回應。此時 Web Framework 會執行以下流程來結束此次 HTTP 請求：
    //
    // 參考此 Handler Method 的配置，其中一項配置是「此 Handler Method」在回應時欲指定的 content-type 。好比次此 register Handler method 可以透過指定其 content-type 為 application/json 說，來要求以 JSON 格式來序列化其回應的 UserResponse 物件。其他 content-type 格式也是用，如 application/xml 。
    // 如果此 Handler Method 指定的 content-type 未被支援，則此時會拋出例外，中斷請求。（請見章節 [D]，此時拋出 500 HTTP Status Code 表示例外)
    public void handle(HttpExchange exchange) throws IOException {
        try {
            HttpRequest request = exchangeToRequest(exchange);

            Route route = router.findRoute(request);

            List<Header> headers = new ArrayList<>();
            HttpResponse httpResponse = new HttpResponse(
                    new StatusLine(exchange.getProtocol(), 500),
                    headers
            );

//            if (request.getMethod().equals(route.getMethod())) {
                Object response = route.handle(request);

                httpResponse.setCode(200);
                httpResponse.setBody(response);
//            }

            httpResponse.send(exchange);
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, 0);
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
        Headers headers = new Headers();
        headers.setHeaders(exchange.getRequestHeaders().entrySet().stream()
                .collect(
                        HashMap::new,
                        (m, e) -> m.put(e.getKey(), e.getValue().get(0)),
                        HashMap::putAll
                )
        );

        RequestBody requestBody = requestBodyHandler.handler(exchange, headers.getContentType());
        return new HttpRequest(requestLine, url, headers);
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

    public void addPlugin(RequestBodyHandler handler) {
        handler.setNext(requestBodyHandler);
        requestBodyHandler = handler;
    }
}
