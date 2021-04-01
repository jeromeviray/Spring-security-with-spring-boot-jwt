package com.project.springbootjwt.exception;

public class AuthenticatingCredentialsException extends RuntimeException{
    public AuthenticatingCredentialsException(String message) {
        super(message);
    }

    public AuthenticatingCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
