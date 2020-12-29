package com.kenrou.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class DemoController {

    @GetMapping("/api/demo/index")
    public Object index() {
        return "Hello demo index";
    }
}
