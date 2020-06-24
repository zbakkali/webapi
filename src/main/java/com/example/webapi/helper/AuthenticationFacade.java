package com.example.webapi.helper;

import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface AuthenticationFacade {
    Authentication getAuthentication();
    Optional<String> getPrincipalName();
}
