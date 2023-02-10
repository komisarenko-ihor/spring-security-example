package com.example.springsecurityexample.bootstrap;

import com.example.springsecurityexample.domain.security.Authority;
import com.example.springsecurityexample.domain.security.User;
import com.example.springsecurityexample.repositories.security.AuthorityRepository;
import com.example.springsecurityexample.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (authorityRepository.count() == 0) {
            loadSecurityData();
            log.debug("Users Loaded: " + userRepository.count());
        }
    }

    private void loadSecurityData() {
        saveUser("Admin1", passwordEncoder.encode("password1"), saveAuthority("ADMIN"));
        saveUser("User1", passwordEncoder.encode("password2"), saveAuthority("USER"));
        saveUser("Customer1", passwordEncoder.encode("password3"), saveAuthority("CUSTOMER"));
    }

    private Authority saveAuthority(String role) {
        return authorityRepository.save(Authority.builder().role(role).build());
    }

    private void saveUser(String username, String password, Authority authority) {
        userRepository.save(User.builder()
                .username(username)
                .password(password)
                .authority(authority)
                .build()
        );
    }
}
