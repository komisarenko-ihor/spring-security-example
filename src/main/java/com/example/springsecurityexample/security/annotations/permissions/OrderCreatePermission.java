package com.example.springsecurityexample.security.annotations.permissions;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize(
        "hasAuthority('order.create') " +
                "OR hasAuthority('client.order.create') " +
                "AND @orderAuthenticationManager.clientIdMatches(authentication, #clientId)"
)
public @interface OrderCreatePermission {
}
