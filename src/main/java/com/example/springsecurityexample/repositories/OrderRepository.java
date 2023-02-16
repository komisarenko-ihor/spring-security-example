package com.example.springsecurityexample.repositories;

import com.example.springsecurityexample.domain.Client;
import com.example.springsecurityexample.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findAllByClient(Client client);

    @Query("select o from orders o where o.id =?1 and " +
            "(true = :#{hasAuthority('order.read')} or o.client.id = ?#{principal?.client?.id})")
    Order findOrderByIdSecure(UUID orderId);
}
