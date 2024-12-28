package org.example;

import org.example.framework.WebFramework;

public class Main {
    public static void main(String[] args) {
        WebFramework web = new WebFramework(9999);
        // force 可以讓 Client 客製化 Controller
        // force ocp


        // force 希望讓 Client 可以輕鬆使用 appController::test 就知道執行哪個 function 哪個 controller
        // force OCP
        AppController appController = new AppController();

        web.get("/test", appController::test);
        web.post("/test", appController::create);
        web.addPlugin(new JsonHandler());
        web.launch();
    }
}
