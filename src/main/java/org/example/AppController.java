package org.example;

import org.example.framework.BaseController;

public class AppController extends BaseController {

    public AppController() {
    }

    // return 可以由 Client 自行決定
    // function parameter 可以由 Client 自行決定
    public Object test(Object o) {
        System.out.println("test");
        return "Hello World";
    }
}
