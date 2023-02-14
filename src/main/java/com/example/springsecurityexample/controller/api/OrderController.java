package com.example.springsecurityexample.controller.api;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/client/{clientId}/")
public class OrderController {

    @GetMapping("/orders")
    public List<String> listOrders(@PathVariable UUID clientId) {
        return Collections.emptyList();
    }

    @PostMapping("/orders")
    public String placeOrder(@PathVariable UUID clientId) {
        return "Saved order";
    }

    @GetMapping("/orders/{{orderId}")
    public String getOrder(@PathVariable UUID clientId, @PathVariable UUID orderId) {
        return "Order by id";
    }

    @PutMapping("/orders/{orderId}/pickup")
    public void pickupOrder(@PathVariable UUID clientId, UUID orderId) {
        // pickup order
    }
}
