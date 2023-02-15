package com.example.springsecurityexample.bootstrap;

import com.example.springsecurityexample.domain.Client;
import com.example.springsecurityexample.domain.security.Authority;
import com.example.springsecurityexample.domain.security.Order;
import com.example.springsecurityexample.domain.security.Role;
import com.example.springsecurityexample.domain.security.User;
import com.example.springsecurityexample.repositories.ClientRepository;
import com.example.springsecurityexample.repositories.OrderRepository;
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
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (authorityRepository.count() == 0) {
            loadSecurityData();
            loadClientData();
            log.debug("Users Loaded: " + userRepository.count());
        }
    }

    private void loadClientData() {
        Role customerRole = roleRepository.findByName("CUSTOMER").orElseThrow();

        Client client1 = saveClient("ClientName1");
        Client client2 = saveClient("ClientName2");
        Client client3 = saveClient("ClientName3");

        saveUser("UserName1", "password1", customerRole, client1);
        saveUser("UserName2", "password2", customerRole, client2);
        saveUser("UserName3", "password3", customerRole, client3);

        createOrder(client1);
        createOrder(client2);
        createOrder(client3);
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

        // order authorities admin
        Authority createOrder = saveAuthority("order.create");
        Authority readOrder = saveAuthority("order.read");
        Authority updateOrder = saveAuthority("order.update");
        Authority deleteOrder = saveAuthority("order.delete");
        // order authorities client
        Authority createOrderClient = saveAuthority("client.order.create");
        Authority readOrderClient = saveAuthority("client.order.read");
        Authority updateOrderClient = saveAuthority("client.order.update");
        Authority deleteOrderClient = saveAuthority("client.order.delete");

        Role adminRole = saveRole("ADMIN");
        Role customerRole = saveRole("CUSTOMER");
        Role userRole = saveRole("USER");

        adminRole.setAuthorities(new HashSet<>(Set.of(
                createCustomer, updateCustomer, deleteCustomer, readCustomer,
                createBook, updateBook, deleteBook, readBook,
                createUser, updateUser, deleteUser, readUser,
                createOrder, readOrder, updateOrder, deleteOrder)));
        customerRole.setAuthorities(new HashSet<>(Set.of(
                readCustomer, readBook,
                createOrderClient, readOrderClient, updateOrderClient, deleteOrderClient)));
        userRole.setAuthorities(new HashSet<>(Set.of(readUser)));

        roleRepository.saveAll(Arrays.asList(adminRole, customerRole, userRole));

        saveUser("Admin1", "password1", adminRole);
        saveUser("Admin2", "password12", adminRole);
        saveUser("User1", "password2", userRole);
        saveUser("Customer1", "password3", customerRole);
    }

    private void createOrder(Client client) {
        orderRepository.save(Order.builder()
                .client(client)
                .build());
    }

    private Client saveClient(String name) {
        return clientRepository.save(Client.builder()
                .name(name)
                .apiKey(UUID.randomUUID())
                .build());
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
                .password(passwordEncoder.encode(password))
                .role(role)
                .build()
        );
    }

    private void saveUser(String username, String password, Role role, Client client) {
        userRepository.save(User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role(role)
                .client(client)
                .build()
        );
    }
}
