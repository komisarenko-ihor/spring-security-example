package com.example.springsecurityexample.security.listeners;

import com.example.springsecurityexample.domain.security.LoginSuccess;
import com.example.springsecurityexample.domain.security.User;
import com.example.springsecurityexample.repositories.security.LoginSuccessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class AuthenticationSuccessListener {

    private final LoginSuccessRepository loginSuccessRepository;

    @EventListener
    public void listen(AuthenticationSuccessEvent event) {

        log.debug("Login Success");

        if (event.getSource() instanceof UsernamePasswordAuthenticationToken token) {
            LoginSuccess.LoginSuccessBuilder loginSuccessBuilder = LoginSuccess.builder();


            if (token.getPrincipal() instanceof User user) {

                loginSuccessBuilder.user(user);

                log.debug("User name logged in: " + user.getUsername());
            }

            if (token.getDetails() instanceof WebAuthenticationDetails details) {

                loginSuccessBuilder.sourceIp(details.getRemoteAddress());

                log.debug("Source IP: " + details.getRemoteAddress());
            }

            LoginSuccess loginSuccess = loginSuccessRepository.save(loginSuccessBuilder.build());
            log.debug("Login Success saved Id: " + loginSuccess.getId());
        }
    }
}
