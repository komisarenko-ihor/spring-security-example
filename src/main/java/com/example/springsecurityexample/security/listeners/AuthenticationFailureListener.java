package com.example.springsecurityexample.security.listeners;

import com.example.springsecurityexample.domain.security.LoginFailure;
import com.example.springsecurityexample.domain.security.User;
import com.example.springsecurityexample.repositories.security.LoginFailureRepository;
import com.example.springsecurityexample.repositories.security.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Component
public class AuthenticationFailureListener {

    private final LoginFailureRepository loginFailureRepository;
    private final UserRepository userRepository;

    @EventListener
    public void listener(AuthenticationFailureBadCredentialsEvent event) {

        log.debug("Login Failure");

        if (event.getSource() instanceof UsernamePasswordAuthenticationToken token) {

            LoginFailure.LoginFailureBuilder loginFailureBuilder = LoginFailure.builder();

            if (token.getPrincipal() instanceof String username) {

                loginFailureBuilder.username(username);

                log.debug("Attempted Username: " + username);

                Optional<User> user = userRepository.findByUsername(username);
                user.ifPresent(loginFailureBuilder::user);
            }

            if (token.getDetails() instanceof WebAuthenticationDetails details) {

                loginFailureBuilder.sourceIp(details.getRemoteAddress());

                log.debug("Source IP: " + details.getRemoteAddress());
            }

            LoginFailure loginFailure = loginFailureRepository.save(loginFailureBuilder.build());
            log.debug("Login Failure saved Id: " + loginFailure.getId());

            if (loginFailure.getUser() != null) {
                lockUserAccount(loginFailure.getUser());
            }
        }
    }

    private void lockUserAccount(User user) {

        List<LoginFailure> failures = loginFailureRepository.findAllByUserAndCreatedDateIsAfter(
                user, Timestamp.valueOf(LocalDateTime.now().minusDays(1)));

        if (failures.size() > 3) {
            log.debug("Locking User Account... ");
            user.setAccountNonLocked(false);
            userRepository.save(user);
        }
    }
}
