package com.app.backend.security.config

import com.app.backend.model.user.User
import com.app.backend.repo.UserRepository
import com.app.backend.security.service.UserDetailsServiceImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.stereotype.Component

/**
 * Authentication manager for processing authentication requests.
 *
 * This manager is responsible for authenticating user credentials by interacting with the [UserRepository]
 * and [UserDetailsServiceImpl].
 *
 * @property userRepository The repository for managing user entities.
 * @property userDetailsService The service for loading user details during authentication.
 */
@Component
class AuthenticationManagerImpl(
    private val userRepository: UserRepository,
    private val userDetailsService: UserDetailsServiceImpl
): AuthenticationManager {

    /**
     * Attempts to authenticate the passed [Authentication] object, returning a
     * fully populated `Authentication` object (including granted authorities)
     * if successful.
     *
     * @param authentication authentication request object
     * @return fully authenticated object including credentials
     * @throws AuthenticationException if authentication fails
     */
    override fun authenticate(authentication: Authentication?): Authentication {
        authentication ?: throw object: AuthenticationException("Authentication object is null"){}

        val username: String = authentication.principal.toString()
        val password: String = authentication.credentials.toString()

        val user: User = userRepository.findOneByUsername(username)

        if (password != user.password) {
            throw object: AuthenticationException("Bad credentials"){}
        }

        return UsernamePasswordAuthenticationToken(
            username,
            null,
            userDetailsService.loadUserByUsername(username).authorities
        ).apply {
            details = authentication.details
        }
    }
}
