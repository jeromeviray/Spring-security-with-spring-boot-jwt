package com.project.springbootjwt.user;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {

    Authentication getAuthenticated();
}
