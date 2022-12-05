package com.example.springsecurityexample.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.LdapShaPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new LdapShaPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        return http.
                authorizeRequests(
                        authorize -> {
                            authorize
                                    .antMatchers("/").permitAll()
                                    .antMatchers(HttpMethod.GET, "/api/user/*").permitAll()
                                    .mvcMatchers(HttpMethod.GET, "/api/user/upc/{upc}").permitAll();
                        }
                )
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic()
                .and().build();
    }

    @Autowired
    public void authenticationManager(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1")
                .password("{SSHA}0zBtqAcp789xljsjrbEevScRrC/YvvXcLl4B6A==")
                .roles("ADMIN")
                .and()
                .withUser("user2")
                .password("{SSHA}Xhz6VcgbleyxahYUrAtgHuX/jb7xOFiM5gR6+A==")
                .roles("USER");
    }
}
