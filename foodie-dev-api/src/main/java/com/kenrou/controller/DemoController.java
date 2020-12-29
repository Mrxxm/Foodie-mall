package com.kenrou.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ApiIgnore
@RestController
public class DemoController {

    final static Logger logger = LoggerFactory.getLogger(DemoController.class);

    @GetMapping("/api/demo/index")
    public Object index() {

        logger.debug("debug: hello~~");
        logger.info("info: hello~~");
        logger.warn("warn: hello~~");
        logger.error("error: hello~~");

        return "Hello demo index";
    }

    @GetMapping("/setSession")
    public Object setSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("userInfo", "new user");
        session.setMaxInactiveInterval(3600);
        session.getAttribute("userInfo");
        session.removeAttribute("userInfo");

        return "ok";
    }
}
