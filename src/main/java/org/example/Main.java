package org.example;

import org.example.framework.BaseController;
import org.example.framework.WebFramework;

public class Main {
    public static void main(String[] args) {
        WebFramework web = new WebFramework(9999);
        // force 可以讓 Client 客製化 Controller
        // force ocp

        AppController controller = new AppController();
        web.addController(controller);
        web.addRoute("/test", controller);
        web.start();
    }
}
