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
    public void saveUser() {

        userService.saveUser();
    }

    @PostMapping("/api/modifyUser")
    public void modifyUser(String id) {

        userService.updateUser(id);
    }

    @PostMapping("/api/deleteUser")
    public void deleteUser(String id) {

        userService.deleteUser(id);
    }
}
