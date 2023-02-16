package com.example.springsecurityexample.controller.api;

import com.example.springsecurityexample.domain.Client;
import com.example.springsecurityexample.domain.security.User;
import com.example.springsecurityexample.dto.OrderDto;
import com.example.springsecurityexample.security.annotations.permissions.OrderReadPermissionV2;
import com.example.springsecurityexample.service.OrderServiceV2;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v2/client/")
@AllArgsConstructor
public class OrderControllerV2 {

    private OrderServiceV2 orderService;

    @OrderReadPermissionV2
    @GetMapping("orders/{orderId}")
    public OrderDto getOrder(@PathVariable UUID orderId) {
        return orderService.getOrder(orderId);
    }

    @OrderReadPermissionV2
    @GetMapping("orders")
    public List<OrderDto> listOrders(@AuthenticationPrincipal User user) {
        return orderService.listOrders(
                Optional.ofNullable(user.getClient()).map(Client::getId).orElse(null));
    }
}
