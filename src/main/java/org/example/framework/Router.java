package org.example.framework;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public class Router {
    private List<Route> routers = new ArrayList<>();
    private final Set<String> ALLOWED_METHODS = Set.of("GET", "POST", "PUT", "DELETE");
    private final HttpHandler handle;

    public Router(BaseController handle) {
        this.handle = handle;
    }

    public void route(String path, String method, Function<Object, Object> controller) {
        if (!ALLOWED_METHODS.contains(method)) {
            throw new IllegalArgumentException("Method not allowed: " + method);
        }
        Route route = new Route(path, method, controller);
        routers.add(route);
    }

    public void settingRoute(HttpServer server) {
        for (Route route : routers) {
            server.createContext(route.getPath(), handle);
        }
    }

    public Route findRoute(HttpRequest request) {
        for (Route route : routers) {
            if (route.getPath().equals(request.getPath()) && route.getMethod().equals(request.getMethod())) {
                return route;
            }
        }
        throw new IllegalArgumentException("Route not found" + request.getPath());
    }
}
