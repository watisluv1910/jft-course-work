package com.app.backend.controller

import com.app.backend.payload.user.request.LoginRequest
import com.app.backend.payload.user.request.RegisterRequest
import com.app.backend.payload.user.request.TokenRefreshRequest
import com.app.backend.payload.user.response.LoginResponse
import com.app.backend.payload.token.response.TokenRefreshResponse
import com.app.backend.security.service.AuthenticationService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Controller responsible for all authentication-related operations.
 *
 * Handles requests to create, verify, and refresh authentication tokens for users,
 * as well as to register, login, and logout users.
 *
 * @property authenticationService service for handling authentication operations.
 */
@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    val authenticationService: AuthenticationService
) {

    /**
     * Handle the user registration request.
     *
     * @param request The [RegisterRequest] payload containing details of the user to be registered.
     * @return [ResponseEntity] containing message of the registration result.
     */
    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest) =
        ResponseEntity
            .ok(authenticationService.register(request))

    /**
     * Handle the user authentication request.
     *
     * @param request The [LoginRequest] payload containing the username and password of the user to be authenticated.
     * @return [ResponseEntity] containing details of the authentication result including JWT tokens.
     */
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<*> {
        val (
            accessTokenCookie,
            refreshTokenCookie,
            refreshTokenExpirationDate,
            userInfo
        ) = authenticationService.login(request)
        return ResponseEntity
            .accepted()
            .header(
                HttpHeaders.SET_COOKIE,
                accessTokenCookie,
                refreshTokenCookie
            ).body(
                LoginResponse(
                    user = userInfo,
                    refreshTokenExpirationDate = refreshTokenExpirationDate
                )
            )
    }
}
