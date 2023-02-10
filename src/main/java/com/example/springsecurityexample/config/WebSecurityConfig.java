package com.example.springsecurityexample.config;

import com.example.springsecurityexample.security.CustomPasswordEncoderFactories;
import com.example.springsecurityexample.security.RestHeaderAuthFilter;
import com.example.springsecurityexample.security.RestUrlParametersAuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {

    AuthenticationManagerBuilder auth;

    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager) {
        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    public RestUrlParametersAuthFilter restUrlParametersAuthFilter(AuthenticationManager authenticationManager) {
        RestUrlParametersAuthFilter filter = new RestUrlParametersAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return CustomPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        return http
                .addFilterBefore(restHeaderAuthFilter(auth.getOrBuild()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(restUrlParametersAuthFilter(auth.getOrBuild()), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .authorizeRequests(
                        authorize -> {
                            authorize
                                    .antMatchers("/h2-console/**").permitAll() //do not use in production!
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
                .and()

                //h2 console config
                .headers()
                .frameOptions()
                .sameOrigin().and()
                //h2 console config

                .build();
    }
}
