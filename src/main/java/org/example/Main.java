package org.example;

import org.example.app.controller.TestController;
import org.example.framework.WebFramework;

public class Main {
    public static void main(String[] args) {
        WebFramework web = new WebFramework();
        web.addRoute("/test", new TestController());
        web.start();
    }
}
