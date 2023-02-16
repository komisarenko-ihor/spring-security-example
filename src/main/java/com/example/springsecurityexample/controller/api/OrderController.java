package com.example.springsecurityexample.controller.api;

import com.example.springsecurityexample.dto.OrderDto;
import com.example.springsecurityexample.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/client/{clientId}/")
@AllArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PreAuthorize(
            "hasAuthority('order.create') " +
                    "OR hasAuthority('client.order.create') " +
                    "AND @orderAuthenticationManager.clientIdMatches(authentication, #clientId)"
    )
    @PostMapping("orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto placeOrder(@PathVariable UUID clientId) {
        return orderService.placeOrder(clientId);
    }

    @PreAuthorize(
            "hasAuthority('order.read') " +
                    "OR hasAuthority('client.order.read') " +
                    "AND @orderAuthenticationManager.clientIdMatches(authentication, #clientId)"
    )
    @GetMapping("orders")
    public List<OrderDto> listOrders(@PathVariable UUID clientId) {
        return orderService.listOrders(clientId);
    }

    @PreAuthorize(
            "hasAuthority('order.read') " +
                    "OR hasAuthority('client.order.read') " +
                    "AND @orderAuthenticationManager.clientIdMatches(authentication, #clientId)"
    )
    @GetMapping("orders/{orderId}")
    public OrderDto getOrder(@PathVariable UUID clientId, @PathVariable UUID orderId) {
        return orderService.getOrder(clientId, orderId);
    }

    @PreAuthorize(
            "hasAuthority('order.delete') OR hasAuthority('client.order.delete') AND @orderAuthenticationManager.clientIdMatches(authentication, #clientId)"
    )
    @DeleteMapping("orders/{orderId}")
    public void deleteOrder(@PathVariable UUID clientId, @PathVariable UUID orderId) {
        orderService.deleteOrder(clientId, orderId);
    }
}
