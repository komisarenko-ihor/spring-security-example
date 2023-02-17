package com.example.springsecurityexample.repositories;

import com.example.springsecurityexample.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {

    Optional<Client> findAllByName(String name);
}
