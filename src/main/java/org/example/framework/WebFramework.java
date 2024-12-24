package org.example.framework;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

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

    public void addRoute(String path, HttpHandler handler) {
//        if (!path.startsWith("/")) {
//            path = "/" + path;
//        }
//        if (!path.endsWith("/")) {
//            path = path + "/";
//        }
        router.route(path, handler);
    }

    public Router getRouter() {
        return router;
    }
}
