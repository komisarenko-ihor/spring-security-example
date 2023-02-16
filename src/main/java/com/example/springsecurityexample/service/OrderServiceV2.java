package com.example.springsecurityexample.service;

import com.example.springsecurityexample.domain.Client;
import com.example.springsecurityexample.domain.Order;
import com.example.springsecurityexample.dto.OrderDto;
import com.example.springsecurityexample.repositories.ClientRepository;
import com.example.springsecurityexample.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderServiceV2 {

    private OrderRepository orderRepository;
    private ClientRepository clientRepository;

    public OrderDto getOrder(UUID orderId) {
        Order order = orderRepository.findOrderByIdSecure(orderId);
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order Not found");
        }
        return OrderDto.builder().id(order.getId()).build();
    }

    public List<OrderDto> listOrders(UUID clientId) {
        List<Order> orders;
        if (clientId != null) {
            Client client = clientRepository.findById(clientId).orElseThrow();
            orders = orderRepository.findAllByClient(client);
        } else {
            orders = orderRepository.findAll();
        }

        return orders.stream().map(order -> OrderDto.builder().id(order.getId()).build()).collect(Collectors.toList());
    }
}
