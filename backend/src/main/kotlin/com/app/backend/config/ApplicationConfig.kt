package com.app.backend.config

import com.app.backend.security.service.UserDetailsServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

/**
 * Configuration class for application-specific settings and beans.
 *
 * This class defines configurations related to authentication and user details service.
 *
 * @param userDetailsService [UserDetailsServiceImpl] used for providing user details.
 */
@Configuration
class ApplicationConfig(
    private val userDetailsService: UserDetailsServiceImpl
) {

    /**
     * Defines an instance of [DaoAuthenticationProvider] that uses
     * the [UserDetailsServiceImpl] and [PasswordEncoder]
     * to authenticate users.
     * @return the [AuthenticationProvider] instance.
     */
    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    /**
     * Defines an instance of [BCryptPasswordEncoder] that uses BCrypt algorithm.
     * @return the [PasswordEncoder] instance.
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}
