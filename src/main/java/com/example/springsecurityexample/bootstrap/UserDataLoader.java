package com.example.springsecurityexample.bootstrap;

import com.example.springsecurityexample.domain.security.Authority;
import com.example.springsecurityexample.domain.security.Role;
import com.example.springsecurityexample.domain.security.User;
import com.example.springsecurityexample.repositories.security.AuthorityRepository;
import com.example.springsecurityexample.repositories.security.RoleRepository;
import com.example.springsecurityexample.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (authorityRepository.count() == 0) {
            loadSecurityData();
            log.debug("Users Loaded: " + userRepository.count());
        }
    }

    private void loadSecurityData() {
        // library authorities
        Authority createBook = saveAuthority("library.create");
        Authority readBook = saveAuthority("library.read");
        Authority updateBook = saveAuthority("library.update");
        Authority deleteBook = saveAuthority("library.delete");
        // customer authorities
        Authority createCustomer = saveAuthority("customer.create");
        Authority readCustomer = saveAuthority("customer.read");
        Authority updateCustomer = saveAuthority("customer.update");
        Authority deleteCustomer = saveAuthority("customer.delete");
        // user authorities
        Authority createUser = saveAuthority("user.create");
        Authority readUser = saveAuthority("user.read");
        Authority updateUser = saveAuthority("user.update");
        Authority deleteUser = saveAuthority("user.delete");

        Role adminRole = saveRole("ADMIN");
        Role customerRole = saveRole("CUSTOMER");
        Role userRole = saveRole("USER");

        adminRole.setAuthorities(new HashSet<>(Set.of(
                createCustomer, updateCustomer, deleteCustomer, readCustomer,
                createBook, updateBook, deleteBook, readBook,
                createUser, updateUser, deleteUser, readUser)));
        customerRole.setAuthorities(new HashSet<>(Set.of(readCustomer, readBook)));
        userRole.setAuthorities(new HashSet<>(Set.of(readUser)));

        roleRepository.saveAll(Arrays.asList(adminRole, customerRole, userRole));

        saveUser("Admin1", passwordEncoder.encode("password1"), adminRole);
        saveUser("Admin2", passwordEncoder.encode("password12"), adminRole);
        saveUser("User1", passwordEncoder.encode("password2"), userRole);
        saveUser("Customer1", passwordEncoder.encode("password3"), customerRole);
    }

    private Authority saveAuthority(String permission) {
        return authorityRepository.save(Authority.builder().permission(permission).build());
    }

    private Role saveRole(String name) {
        return roleRepository.save(Role.builder().name(name).build());
    }

    private void saveUser(String username, String password, Role role) {
        userRepository.save(User.builder()
                .username(username)
                .password(password)
                .role(role)
                .build()
        );
    }
}
