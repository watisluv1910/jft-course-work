package com.app.backend.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Vladislav Nasevich
 */
public class PrincipalNotFoundException extends AuthenticationException {

    /**
     * Constructs an {@code AuthenticationException} with the specified message and no
     * root cause.
     *
     * @param msg the detail message
     */
    public PrincipalNotFoundException(String msg) {
        super(msg);
    }

    /**
     * Constructs an {@code AuthenticationException} with the specified message and root
     * cause.
     *
     * @param msg   the detail message
     * @param cause the root cause
     */
    public PrincipalNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
