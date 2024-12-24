package org.example;

import org.example.app.controller.TestController;
import org.example.framework.WebFramework;

public class Main {
    public static void main(String[] args) {
        WebFramework web = new WebFramework(9999);
        web.addRoute("/test", new TestController());
        web.start();
    }
}
