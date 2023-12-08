package com.app.backend.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Thrown when an operation regarding token refreshment fails.
 *
 *
 * Responds with a status of [HttpStatus.FORBIDDEN].
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
class TokenRefreshException(
    token: String,
    message: String
): RuntimeException("Failed for $token: $message")
