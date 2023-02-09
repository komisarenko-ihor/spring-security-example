package com.example.springsecurityexample.repositories.security;

import com.example.springsecurityexample.domain.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
}
