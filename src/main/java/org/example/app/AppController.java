package org.example.app;

import org.example.framework.BaseController;
import org.example.framework.HttpRequest;

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

    public UserDto registration(HttpRequest request) {
        request.readBodyAsObject(RegistrationRequest.class);
        return new UserDto("4", "email", "NewUser");
    }
}
