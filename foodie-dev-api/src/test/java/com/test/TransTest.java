package com.test;

import com.kenrou.Application;
import com.kenrou.service.TestTransService;
import com.kenrou.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Application.class)
public class TransTest {

    @Autowired
    private UserService userService;

    @Autowired
    private TestTransService testTransService;

//    @Test
    public void myTest() {

    }
}
