package com.example.springsecurityexample.repositories.security;

import com.example.springsecurityexample.domain.security.LoginFailure;
import com.example.springsecurityexample.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface LoginFailureRepository extends JpaRepository<LoginFailure, Integer> {

    List<LoginFailure> findAllByUserAndCreatedDateIsAfter(User user, Timestamp timestamp);
}
