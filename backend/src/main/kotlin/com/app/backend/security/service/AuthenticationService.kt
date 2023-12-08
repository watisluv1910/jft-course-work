package com.app.backend.security.service

import com.app.backend.model.user.User
import com.app.backend.model.user.UserDetailsImpl
import com.app.backend.model.user.role.EUserRole
import com.app.backend.payload.MessageResponse
import com.app.backend.payload.token.response.TokenExpirationResponse
import com.app.backend.payload.user.request.LoginRequest
import com.app.backend.payload.user.request.RegisterRequest
import com.app.backend.payload.user.response.LoginInternalResponse
import com.app.backend.payload.token.response.TokenRefreshInternalResponse
import com.app.backend.payload.user.response.UserInfoResponse
import com.app.backend.repo.UserRepository
import com.app.backend.repo.UserRoleRepository
import com.app.backend.security.config.AuthManager
import com.app.backend.security.token.TokenUtils
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.*

/**
 * Service class handling user authentication operations such as registration and login.
 *
 * Interacts with repositories, authentication managers, and utility classes to manage authentication.
 *
 * @property userRepository repository for managing user entities.
 * @property userRoleRepository repository for managing user roles.
 * @property userDetailsService service for managing user details.
 * @property authManager custom authentication manager.
 * @property passwordEncoder custom password encoder.
 * @property tokenUtils utility class for handling JWT tokens.
 */
@Service
class AuthenticationService(
    val userRepository: UserRepository,
    val userRoleRepository: UserRoleRepository,
    val userDetailsService: UserDetailsServiceImpl,
    val authManager: AuthManager,
    val passwordEncoder: PasswordEncoder,
    val tokenUtils: TokenUtils
) {

    /**
     * Registers a new user based on the provided registration request.
     *
     * Takes a [RegisterRequest], validates the provided user details, encodes the password, assigns user roles,
     * and saves the user entity in the repository.
     *
     * @param request [RegisterRequest] containing user registration details.
     * @return [MessageResponse] indicating the success of the registration process.
     * @throws IllegalArgumentException if the provided user is already registered.
     */
    fun register(request: RegisterRequest): MessageResponse {
        val (user, roleNames) = request.toModel()

        if (userRepository.existsByUsername(user.username) ||
            userRepository.existsByUserEmail(user.userEmail)) {
            throw IllegalArgumentException("Provided user is already registered")
        } else {
            user.apply { password = passwordEncoder.encode(password) }
            for (roleName in roleNames) addRoleToUser(user, roleName)
        }

        userRepository.save(user)

        return MessageResponse(
            "The user ${user.username} registered successfully."
        )
    }

    /**
     * Logs in a user based on the provided login request.
     *
     * Authenticates the user using the [authentication manager][AuthManager], sets the authentication in the security context,
     * generates access and refresh tokens, and returns a response
     * containing [token details][TokenExpirationResponse] and [user information][UserInfoResponse].
     *
     * @param request [LoginRequest] containing user login details.
     * @return [LoginInternalResponse] containing access and refresh tokens, token expiration details, and user information.
     * @throws ResponseStatusException if the login credentials are invalid.
     */
    fun login(request: LoginRequest): LoginInternalResponse {
        val foundUser = userRepository.findOneByUsername(request.username)

        val authentication = authManager.authenticate(
            UsernamePasswordAuthenticationToken(
                foundUser.username,
                foundUser.password
            )
        )

        SecurityContextHolder.getContext().authentication = authentication

        val userDetails = userDetailsService.loadUserByUsername(foundUser.username!!) as UserDetailsImpl

        return LoginInternalResponse(
            tokenUtils.generateAccessTokenCookie(foundUser).toString(),
            tokenUtils.generateRefreshTokenCookie(userDetails, foundUser).toString(),
            TokenExpirationResponse(
                accessTokenExpiresAt = Date(System.currentTimeMillis() + tokenUtils.accessTokenExpirationMs.toLong()),
                refreshTokenExpiresAt = Date(System.currentTimeMillis() + tokenUtils.refreshTokenExpirationMs.toLong())
            ),
            UserInfoResponse.build(foundUser)
        )
    }

    /**
     * Updates the access token based on the provided refresh token in the request cookies.
     *
     * @param request [HttpServletRequest] containing the refresh token in cookies.
     * @return [TokenRefreshInternalResponse] containing the updated access token and expiration details.
     * @throws ResponseStatusException if the provided refresh token is not valid.
     */
    fun updateAccessToken(request: HttpServletRequest): TokenRefreshInternalResponse {
        val refreshToken = tokenUtils.getRefreshTokenFromCookies(request)
        if (!refreshToken.isNullOrBlank() &&
            tokenUtils.validateToken(refreshToken) &&
            tokenUtils.isRefreshTokenSaved(refreshToken)
        ) {
            val username = tokenUtils.extractUsername(refreshToken)
            val foundUser = userRepository.findOneByUsername(username)
            return TokenRefreshInternalResponse(
                accessTokenCookie = tokenUtils.generateAccessTokenCookie(foundUser).toString(),
                accessTokenExpiresAt = Date(
                    System.currentTimeMillis() + tokenUtils.accessTokenExpirationMs.toLong()
                ),
                message = MessageResponse("Access token updated successfully")
            )
        } else {
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Provided refresh token is not valid"
            )
        }
    }

    /**
     * Adds a role to the specified user.
     *
     * Takes a [User] entity and a role name,
     * retrieves the corresponding role from the repository,
     * and adds it to the [user's roles][User.roles].
     *
     * @param user [User] entity to which the role will be added.
     * @param roleName name of the role to be added.
     * @throws RuntimeException if the specified role is not found.
     */
    private fun addRoleToUser(user: User, roleName: String) {
        userRoleRepository.findByRoleName(EUserRole.valueOf(roleName))?.let {
            user.roles.add(it)
        } ?: throw RuntimeException("Error: Role $roleName was not found")
    }
}