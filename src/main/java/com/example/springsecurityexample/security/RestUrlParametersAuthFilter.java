package com.example.springsecurityexample.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
public class RestUrlParametersAuthFilter extends AbstractRestAuthFilter {

    public RestUrlParametersAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    protected String getPassword(HttpServletRequest request) {
        return Optional.ofNullable(request.getParameter("apiSecret")).orElse("");
    }

    @Override
    protected String getUserName(HttpServletRequest request) {
        return Optional.ofNullable(request.getParameter("apiKey")).orElse("");
    }
}
