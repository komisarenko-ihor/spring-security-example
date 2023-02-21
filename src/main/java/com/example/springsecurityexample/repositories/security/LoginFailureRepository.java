package com.example.springsecurityexample.repositories.security;

import com.example.springsecurityexample.domain.security.LoginFailure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginFailureRepository extends JpaRepository<LoginFailure, Integer> {
}
