package com.jrs296.ems.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("api")
public class TestController {
    @GetMapping("/")
    public String indexTest() {
        return "Build Successful, server up and running";
    }
}
