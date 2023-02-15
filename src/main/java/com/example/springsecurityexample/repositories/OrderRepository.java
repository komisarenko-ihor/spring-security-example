package com.example.springsecurityexample.repositories;

import com.example.springsecurityexample.domain.security.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
