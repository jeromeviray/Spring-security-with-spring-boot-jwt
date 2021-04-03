package com.project.springbootjwt.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeImpl implements AuthenticationFacade{
    @Override
    public Authentication getAuthenticated() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication();
    }
}
