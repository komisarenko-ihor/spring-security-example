package com.example.springsecurityexample.repositories;

import com.example.springsecurityexample.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
