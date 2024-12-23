package org.example.framework;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.util.ArrayList;
import java.util.List;

public class Router {
    private WebFramework webFramework;
    private List<Route> routers = new ArrayList<>();

    public Router(WebFramework webFramework) {
        this.webFramework = webFramework;
    }

    public void route(String path, HttpHandler handler) {
        Route route = new Route(path, handler);
        routers.add(route);
    }

    public void settingRoute(HttpServer server) {
        for (Route route : routers) {
            server.createContext(route.getPath(), route.getHandler());
        }
    }
}
