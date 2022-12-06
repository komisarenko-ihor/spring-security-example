package com.example.springsecurityexample.config;

import com.example.springsecurityexample.security.CustomPasswordEncoderFactories;
import com.example.springsecurityexample.security.RestHeaderAuthFilter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Bean
    PasswordEncoder passwordEncoder() {
        return CustomPasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        return http
                .addFilterBefore(restHeaderAuthFilter(auth.getOrBuild()), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable()
                .authorizeRequests(
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
    public void authenticationManager() throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1")
                .password("{bcrypt}$2a$10$.5EifMBQAw2C3u6ClHILJ.0VLl1VcmoPoprOe4TNG8VorxnFSkCce")
                .roles("ADMIN")
                .and()
                .withUser("user2")
                .password("{sha256}67a789841117d6e16ccff0bced842e0ff18b3a78ec7f5c9c9ab0129360af9a56118d068072faadf6")
                .roles("USER")
                .and()
                .withUser("user3")
                .password("{bcrypt15}$2a$15$h3QdQBjL4eia3CiJ8dtnjuaXmsdU./1EPh/..4DZCc43IVy3ne2Wu")
                .roles("USER");
    }
}
