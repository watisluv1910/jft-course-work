package com.app.backend.security.handler

import com.app.backend.repo.UserRefreshTokenRepository
import com.app.backend.repo.UserRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Component

/**
 * Logout handler for managing the removal of refresh tokens upon user logout.
 *
 * @property refreshTokenRepository repository for managing user refresh tokens.
 * @property userRepository repository for managing user entities.
 */
@Component
class RefreshTokenHandler(
    val refreshTokenRepository: UserRefreshTokenRepository,
    val userRepository: UserRepository
): LogoutHandler {

    /**
     * Causes a logout to be completed.
     * @param request HTTP request
     * @param response HTTP response
     * @param authentication current principal details
     */
    override fun logout(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val username = SecurityContextHolder.getContext().authentication.principal.toString()
        userRepository.findByUsername(username)?.let {
            refreshTokenRepository.deleteByUser(
                userRepository.findOneByUsername(username)
            )
        }
    }
}