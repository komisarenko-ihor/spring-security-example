package com.example.springsecurityexample.controller.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @GetMapping("/{id}")
    public String getCustomer(@PathVariable String id) {
        return "Customer controller";
    }

    @PreAuthorize("hasAuthority('customer.read')")
    @GetMapping("/")
    public List<String> getCustomers() {
        return Collections.emptyList();
    }

    @PreAuthorize("hasAuthority('customer.create')")
    @PostMapping("/")
    public void postCustomer() {
        // creating customer
    }
}
