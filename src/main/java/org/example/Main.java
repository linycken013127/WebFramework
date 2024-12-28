package org.example;

import org.example.app.AppController;
import org.example.framework.JsonHandler;
import org.example.framework.WebFramework;

public class Main {
    public static void main(String[] args) {
        WebFramework web = new WebFramework(9999);

        AppController appController = new AppController();

        web.get("/test", appController::test);
        web.post("/test", appController::create);

        web.post("/api/users", appController::registration);

        web.addPlugin(new JsonHandler());
        web.launch();
    }
}
