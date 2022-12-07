package com.example.springsecurityexample.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class RestHeaderAuthFilter extends AbstractRestAuthFilter {

    public RestHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    protected String getPassword(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Api-Secret")).orElse("");
    }

    @Override
    protected String getUserName(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("Api-Key")).orElse("");
    }
}
