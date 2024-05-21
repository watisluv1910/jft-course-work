package com.app.backend.exception

import org.springframework.security.core.AuthenticationException

/**
 * @author Vladislav Nasevich
 */
class PrincipalNotFoundException : AuthenticationException {

    /**
     * Constructs an `AuthenticationException` with the specified message and no
     * root cause.
     *
     * @param msg the detail message.
     */
    constructor(msg: String?) : super(msg)

    /**
     * Constructs an `AuthenticationException` with the specified message and root
     * cause.
     *
     * @param msg   the detail message.
     * @param cause the root cause.
     */
    constructor(msg: String?, cause: Throwable?) : super(msg, cause)
}
