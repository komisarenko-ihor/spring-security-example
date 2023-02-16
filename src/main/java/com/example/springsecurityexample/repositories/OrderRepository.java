package com.example.springsecurityexample.repositories;

import com.example.springsecurityexample.domain.Client;
import com.example.springsecurityexample.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findAllByClient(Client client);
}
