package com.example.springsecurityexample.security;

import com.example.springsecurityexample.domain.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class OrderAuthenticationManager {

    public boolean clientIdMatches(Authentication authentication, UUID clientId) {
        User authenticatedUser = (User) authentication.getPrincipal();

        log.debug("Auth User Client id: " + authenticatedUser.getClient().getId() + " Client id: " + clientId);

        return authenticatedUser.getClient().getId().equals(clientId);
    }
}
