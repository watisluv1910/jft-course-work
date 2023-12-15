package com.app.backend.security.config

import com.app.backend.security.handler.RefreshTokenHandler
import com.app.backend.security.handler.UnauthorizedHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter

/**
 * Default configuration class for Spring Security.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfig(
    val authFilter: AuthFilter,
    val unauthorizedHandler: UnauthorizedHandler,
    val refreshTokenHandler: RefreshTokenHandler,
    val authenticationManager: AuthManager
) {

    @Value("\${tsr.app.jwt-cookie-name}")
    lateinit var jwtCookieName: String

    @Value("\${tsr.app.jwt-refresh-cookie-name}")
    lateinit var jwtRefreshCookieName: String

    /**
     * Configures the security filter chain.
     * @param http [HttpSecurity] object to configure.
     * @return configured [SecurityFilterChain] object.
     * @throws Exception if an error occurs during the configuration process.
     */
    @Bean
    @Throws(Exception::class)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf {
                disable()
            }
            cors {  }
            exceptionHandling {
                authenticationEntryPoint = unauthorizedHandler
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            authorizeHttpRequests {
                authorize(EndpointRequest.toAnyEndpoint(), permitAll)
                authorize("/favicon.ico", permitAll)
                authorize("/api/auth/**", permitAll)
                authorize("/api/user/**", hasAnyRole("USER"))
                authorize("/api/**", authenticated)
                authorize(anyRequest, denyAll)
            }
            logout {
                logoutUrl = "/api/auth/logout"
//                addLogoutHandler(refreshTokenHandler)
                addLogoutHandler(CookieClearingLogoutHandler(jwtCookieName, jwtRefreshCookieName))
                addLogoutHandler(
                    HeaderWriterLogoutHandler(
                        ClearSiteDataHeaderWriter(
                            ClearSiteDataHeaderWriter.Directive.ALL
                        )
                    )
                )
                permitAll = true
            }
            addFilterBefore<UsernamePasswordAuthenticationFilter>(authFilter)
        }

        http.authenticationManager(authenticationManager)
        return http.build()
    }
}
