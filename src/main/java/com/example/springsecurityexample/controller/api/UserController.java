package com.example.springsecurityexample.controller.api;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/some")
    public String getSomeUser() {
        return "User controller some user";
    }

    @DeleteMapping("/some")
    public void deleteSome() {
        // delete some
    }
}
