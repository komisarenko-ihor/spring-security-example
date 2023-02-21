package com.example.springsecurityexample.security.listeners;

import com.example.springsecurityexample.domain.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthenticationFailureListener {

    @EventListener
    public void listener(AuthenticationFailureBadCredentialsEvent event) {

        log.debug("Login Failure");

        if (event.getSource() instanceof UsernamePasswordAuthenticationToken token) {

            if (token.getPrincipal() instanceof String user) {

                log.debug("Attempted Username: " + user);
            }

            if (token.getDetails() instanceof WebAuthenticationDetails details) {

                log.debug("Source IP: " + details.getRemoteAddress());
            }
        }
    }
}
