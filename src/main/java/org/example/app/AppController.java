package org.example.app;

import org.example.framework.BaseController;

public class AppController extends BaseController {

    public AppController() {
    }

    public Object test(Object o) {
        System.out.println("test");
        return "Hello World";
    }

    public Object create(Object o) {
        return "create";
    }

    public UserDto registration(Object o) {
        return new UserDto("4", "email", "NewUser");
    }
}
