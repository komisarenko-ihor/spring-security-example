package com.example.springsecurityexample.repositories.security;

import com.example.springsecurityexample.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
