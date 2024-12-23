package org.example.framework;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebFramework {

    private Router router;

    public WebFramework() {
        router = new Router(this);
    }

    public void start() {
        try {
            // 創建 HttpServer，監聽端口 8080
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

            // 設定處理器，處理 "/" 路徑的請求
            router.settingRoute(server);

            // 啟動伺服器
//            server.setExecutor(null); // 使用默認執行緒
            server.start();
        } catch (IOException e) {
            System.err.println("Server failed to start: " + e.getMessage());
        }
    }

    public void addRoute(String path, HttpHandler handler) {
        router.route(path, handler);
    }

    public Router getRouter() {
        return router;
    }
}
