package com.cherry.med.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager manager;

    public Authentication authenticate(String login, String password) {
        // Merges login and password creating a single "token"
        var usernamePassword = new UsernamePasswordAuthenticationToken(login, password);
        return manager.authenticate(usernamePassword); // Authenticate using "token"
    }
}
