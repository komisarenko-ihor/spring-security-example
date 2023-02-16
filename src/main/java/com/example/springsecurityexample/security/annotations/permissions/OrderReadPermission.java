package com.example.springsecurityexample.security.annotations.permissions;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize(
        "hasAuthority('order.read') " +
                "OR hasAuthority('client.order.read') " +
                "AND @orderAuthenticationManager.clientIdMatches(authentication, #clientId)"
)
public @interface OrderReadPermission {
}
