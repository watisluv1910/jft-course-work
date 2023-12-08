package com.app.backend.advice

import com.app.backend.exception.TokenRefreshException
import com.app.backend.payload.token.response.TokenRefreshExceptionResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import java.sql.Timestamp

/**
 * Advice class for handling token-related exceptions.
 *
 * This class provides methods to handle specific exceptions related to access token refresh.
 *
 * @constructor Creates a new instance of [TokenControllerAdvice].
 */
@RestControllerAdvice
class TokenControllerAdvice {

    /**
     * Handles [TokenRefreshException].
     *
     * @param e the [TokenRefreshException] that occurred.
     * @param request the [WebRequest] during which the exception occurred.
     * @return [TokenRefreshExceptionResponse] with exception details.
     */
    @ExceptionHandler(value = [TokenRefreshException::class])
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleTokenRefreshException(e: TokenRefreshException, request: WebRequest): TokenRefreshExceptionResponse {
        return TokenRefreshExceptionResponse(
            HttpStatus.FORBIDDEN.value(),
            Timestamp(System.currentTimeMillis()),
            e.message ?: "No message provided",
            request.getDescription(false)
        )
    }
}
