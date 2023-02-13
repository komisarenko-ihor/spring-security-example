package com.example.springsecurityexample.config;

import com.example.springsecurityexample.security.CustomPasswordEncoderFactories;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return CustomPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        return http
                .csrf().disable()
                .authorizeRequests(
                        authorize -> {
                            authorize
                                    .antMatchers("/h2-console/**").permitAll() //do not use in production!
                                    .antMatchers("/").permitAll()
                                    .antMatchers(HttpMethod.GET, "/api/user/*").permitAll()
                                    .mvcMatchers(HttpMethod.DELETE, "/api/user/{upc}").hasRole("ADMIN")
                                    .mvcMatchers(HttpMethod.GET, "/api/user/upc/{upc}").permitAll()
                                    .mvcMatchers(HttpMethod.GET, "/api/customer/{id}")
                                        .hasAnyRole("ADMIN", "CUSTOMER");
                        }
                )
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic()
                .and()

                //h2 console config
                .headers()
                .frameOptions()
                .sameOrigin().and()
                //h2 console config

                .build();
    }
}
