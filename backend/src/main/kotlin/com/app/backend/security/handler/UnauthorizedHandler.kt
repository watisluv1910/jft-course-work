package com.app.backend.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.lang.NonNull
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Implementation of [AuthenticationEntryPoint].
 *
 * Used to commence an authentication scheme at the point of request processing
 * when an authentication is required but isn't present.
 */
@Component
class UnauthorizedHandler(
    val logger: Logger = LoggerFactory.getLogger(UnauthorizedHandler::class.java)
) : AuthenticationEntryPoint {

    /**
     * Commences an authentication scheme.
     *
     * This method is automatically called when a client tries to access a secured REST resource
     * without supplying any credentials.
     *
     * @param request that resulted in an AuthenticationException.
     * @param response for the user agent to begin authentication.
     * @param authException that caused the invocation.
     *
     * @throws IOException in case of an I/O error.
     * @throws ServletException in case of general servlet exception.
     */
    @Throws(IOException::class, ServletException::class)
    override fun commence(
        @NonNull request: HttpServletRequest,
        @NonNull response: HttpServletResponse,
        @NonNull authException: AuthenticationException
    ) {
        logger.error("Unauthorized error: ${authException.message}")
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val body = mapOf<String, Any?>(
            "error" to "Unauthorized",
            "message" to  authException.message,
            "path" to request.servletPath,
            "timestamp" to SimpleDateFormat("MM-dd-yyyy HH:mm:ss z").format(Date()),
            "status" to HttpServletResponse.SC_UNAUTHORIZED
        )
        val mapper = ObjectMapper()
        mapper.writeValue(response.outputStream, body)
    }
}
