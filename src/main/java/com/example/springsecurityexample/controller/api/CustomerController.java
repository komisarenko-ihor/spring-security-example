package com.example.springsecurityexample.controller.api;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @GetMapping("/{id}")
    public String getCustomer(@PathVariable String id) {
        return "Customer controller";
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping("/")
    public List<String> getCustomers() {
        return Collections.emptyList();
    }
}
