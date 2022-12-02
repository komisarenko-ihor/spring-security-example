package com.example.springsecurityexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http.
                authorizeRequests(
                        authorize -> {
                            authorize.antMatchers("/").permitAll();
                        }
                )
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic()
                .and().build();
    }
}
