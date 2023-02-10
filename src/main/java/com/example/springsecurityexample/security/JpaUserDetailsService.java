package com.example.springsecurityexample.security;

import com.example.springsecurityexample.domain.security.Authority;
import com.example.springsecurityexample.domain.security.User;
import com.example.springsecurityexample.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User name: " + username + " not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), user.getEnabled(), user.getAccountNonExpired(),
                user.getCredentialsNonExpired(), user.getAccountNonLocked(),
                convertToSpringAuthorities(user.getAuthorities()));
    }

    private Collection<? extends GrantedAuthority> convertToSpringAuthorities(Set<Authority> authorities) {
        return authorities != null && authorities.size() > 0
                ? authorities.stream()
                        .map(Authority::getRole)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet())
                : new HashSet<>();
    }
}
