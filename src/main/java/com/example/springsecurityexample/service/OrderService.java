package com.example.springsecurityexample.service;

import com.example.springsecurityexample.domain.Client;
import com.example.springsecurityexample.domain.Order;
import com.example.springsecurityexample.dto.OrderDto;
import com.example.springsecurityexample.repositories.ClientRepository;
import com.example.springsecurityexample.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private ClientRepository clientRepository;

    public OrderDto placeOrder(UUID clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        Order savedOrder = orderRepository.save(Order.builder().id(UUID.randomUUID()).client(client).build());
        return OrderDto.builder().id(savedOrder.getId()).build();
    }

    public List<OrderDto> listOrders(UUID clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow();
        List<Order> orders = orderRepository.findAllByClient(client);
        return orders.stream().map(order -> OrderDto.builder().id(order.getId()).build()).collect(Collectors.toList());
    }

    public OrderDto getOrder(UUID clientId, UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        if (!order.getClient().getId().equals(clientId)) {
            throw new RuntimeException("Order not found");
        }
        return OrderDto.builder().id(order.getId()).build();
    }

    public void deleteOrder(UUID clientId, UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        if (!clientId.equals(order.getClient().getId())) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(orderId);
    }
}
