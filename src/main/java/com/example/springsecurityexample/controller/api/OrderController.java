package com.example.springsecurityexample.controller.api;

import com.example.springsecurityexample.dto.OrderDto;
import com.example.springsecurityexample.security.annotations.permissions.OrderCreatePermission;
import com.example.springsecurityexample.security.annotations.permissions.OrderDeletePermission;
import com.example.springsecurityexample.security.annotations.permissions.OrderReadPermission;
import com.example.springsecurityexample.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/client/{clientId}/")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @OrderCreatePermission
    @PostMapping("orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto placeOrder(@PathVariable UUID clientId) {
        return orderService.placeOrder(clientId);
    }

    @OrderReadPermission
    @GetMapping("orders")
    public List<OrderDto> listOrders(@PathVariable UUID clientId) {
        return orderService.listOrders(clientId);
    }

    @OrderReadPermission
    @GetMapping("orders/{orderId}")
    public OrderDto getOrder(@PathVariable UUID clientId, @PathVariable UUID orderId) {
        return orderService.getOrder(clientId, orderId);
    }

    @OrderDeletePermission
    @DeleteMapping("orders/{orderId}")
    public void deleteOrder(@PathVariable UUID clientId, @PathVariable UUID orderId) {
        orderService.deleteOrder(clientId, orderId);
    }
}
