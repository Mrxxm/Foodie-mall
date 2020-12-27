package com.kenrou.controller;

import com.kenrou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/getUser")
    public Object getUser(String id) {

        return userService.getUser(id);
    }

    @PostMapping("/api/saveUser")
    public Object saveUser() {
        userService.saveUser();

        return "OK";
    }

    @PostMapping("/api/modifyUser")
    public Object modifyUser(String id) {
        userService.updateUser(id);

        return "OK";
    }

    @PostMapping("/api/deleteUser")
    public Object deleteUser(String id) {
        userService.deleteUser(id);

        return "OK";
    }
}
