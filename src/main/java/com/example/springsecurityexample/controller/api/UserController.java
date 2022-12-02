package com.example.springsecurityexample.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping
    public String getName() {
        return "User controller";
    }

    @GetMapping("/{value}")
    public String getSomeValue(@PathVariable String value) {
        return "User controller value: " + value;
    }

    @GetMapping("/upc/{upc}")
    public String getSomeUPCValue(@PathVariable String upc) {
        return "User controller upcValue: " + upc;
    }
}
