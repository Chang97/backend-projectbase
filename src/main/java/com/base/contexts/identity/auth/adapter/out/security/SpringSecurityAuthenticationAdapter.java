package com.base.contexts.identity.auth.adapter.out.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.base.contexts.identity.auth.application.port.out.AuthenticationPort;
import com.base.platform.exception.ValidationException;
import com.base.platform.security.userdetails.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpringSecurityAuthenticationAdapter implements AuthenticationPort {

    private final AuthenticationManager authenticationManager;

    @Override
    public UserPrincipal authenticate(String loginId, String rawPassword) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginId, rawPassword);
        try {
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return (UserPrincipal) authentication.getPrincipal();
        } catch (AuthenticationException ex) {
            throw new ValidationException("Invalid login credentials.");
        }
    }
}
