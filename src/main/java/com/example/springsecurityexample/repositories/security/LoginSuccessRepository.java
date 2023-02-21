package com.example.springsecurityexample.repositories.security;

import com.example.springsecurityexample.domain.security.LoginSuccess;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginSuccessRepository extends JpaRepository<LoginSuccess, Integer> {
}
